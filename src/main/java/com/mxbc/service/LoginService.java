package com.mxbc.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.mxbc.Entity.EmployeeEntity;
import com.mxbc.mapper.EmployeeMapper;
import com.mxbc.utils.TokenUtils;
import com.mxbc.expection.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 登录service
     * @param user
     * @return
     */

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final long TOKEN_EXPIRATION_SECONDS = 3600;

    public EmployeeEntity login(EmployeeEntity user) {
        // 先从 Redis 中获取 token
        String redisKey = "user:" + user.getUserName(); // 使用用户名作为键名
        String token = stringRedisTemplate.opsForValue().get(redisKey);

        if (token != null) {
            // 如果 Redis 中存在 token，则根据 token 反解出用户信息
            String userId;
            try {
                userId = JWT.decode(token).getAudience().get(0);
            } catch (JWTDecodeException j) {
                throw new ServiceException("Token 解析失败");
            }

            EmployeeEntity userDB = employeeMapper.getEmployeeById(Integer.valueOf(userId));
            if (userDB == null) {
                throw new ServiceException("账户不存在");
            }

            if (!user.getPasswordHash().equals(userDB.getPasswordHash())) {
                throw new ServiceException("用户名或密码错误");
            }

            // 密码正确，则重新设置 token 过期时间并返回用户信息
            stringRedisTemplate.expire(redisKey, TOKEN_EXPIRATION_SECONDS, TimeUnit.SECONDS);
            userDB.setToken(token);
            return userDB;
        }

        // 如果 Redis 中不存在 token，则从数据库中查询用户信息
        EmployeeEntity userDB = employeeMapper.selectByName(user.getUserName());
        if (userDB == null) {
            throw new ServiceException("账户不存在");
        }

        if (!user.getPasswordHash().equals(userDB.getPasswordHash())) {
            throw new ServiceException("用户名或密码错误");
        }

        // 生成新的 token
        token = TokenUtils.createToken(userDB.getEmployeeId().toString(), userDB.getPasswordHash());

        // 将 token 存储到 Redis 中，键为用户名，值为 token，设置过期时间
        stringRedisTemplate.opsForValue().set(redisKey, token, TOKEN_EXPIRATION_SECONDS, TimeUnit.SECONDS);

        // 返回用户信息
        userDB.setToken(token);
        return userDB;
    }



    public EmployeeEntity register(EmployeeEntity user) {
        EmployeeEntity userDB =employeeMapper.selectByName(user.getUserName());
        if (userDB != null){
            //抛异常
            throw new com.mxbc.expection.ServiceException("用户名已存在");
        }
        user.setUserName(user.getUserName());
        employeeMapper.insertEmployee(user);
        return user;
    }
}
