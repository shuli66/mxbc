package com.mxbc.Config;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mxbc.Entity.EmployeeEntity;
import com.mxbc.expection.ServiceException;
import com.mxbc.mapper.EmployeeMapper;
import com.mxbc.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private EmployeeMapper employeeMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取 token
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }
        // 如果不是映射到方法直接通过
        if (handler instanceof HandlerMethod) {
            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if (annotation != null) {
                return true;
            }
        }
        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException("401", "请登录");
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException("401", "请登录");
        }

        // 从 Redis 中查询 token
        String redisKey = "user:" + userId;
        String redisToken = stringRedisTemplate.opsForValue().get(redisKey);
        if (redisToken != null && redisToken.equals(token)) {
            // token 验证成功，重新设置过期时间
            stringRedisTemplate.expire(redisKey, 3600, TimeUnit.SECONDS);
            return true;
        } else {
            // token 验证失败，重新查询数据库进行验证
            EmployeeEntity user = employeeMapper.getEmployeeById(Integer.valueOf(userId));
            if (user == null) {
                throw new ServiceException("401", "请登录");
            }

            // 用户密码加签验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPasswordHash())).build();
            try {
                jwtVerifier.verify(token); // 验证 token
                // token 验证成功，生成新的 token 存入 Redis
                String newToken = TokenUtils.createToken(user.getEmployeeId().toString(), user.getPasswordHash());
                redisKey = "user:" + user.getUserName();
                stringRedisTemplate.opsForValue().set(redisKey, newToken, 3600, TimeUnit.SECONDS);
                return true;
            } catch (JWTVerificationException e) {
                throw new ServiceException("401", "请登录");
            }
        }
    }
}
