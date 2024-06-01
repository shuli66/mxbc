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
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//用jwt的加密方法对生成的token进行验证
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //获取token
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
        // 根据token中的userid查询数据库
        EmployeeEntity user = employeeMapper.getEmployeeById(Integer.valueOf((userId)));
        if (user == null) {
            throw new ServiceException("401", "请登录");
        }
        // 用户密码加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPasswordHash())).build();
        try {
            jwtVerifier.verify(token); // 验证token
        } catch (JWTVerificationException e) {
            throw new ServiceException("401", "请登录");
        }
        return true;
    }
}