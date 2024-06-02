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

    private static final long TOKEN_EXPIRATION_SECONDS = 3600;

    public EmployeeEntity login(EmployeeEntity user) {
        // 先从 Redis 中获取用户信息
        String redisKey = "user:" + user.getUserName(); // 使用用户名作为键名

        EmployeeEntity userFromCache = (EmployeeEntity) redisTemplate.opsForValue().get(redisKey);
        if (userFromCache != null) {
            // 如果缓存中存在用户信息，则直接返回
            if (!user.getPasswordHash().equals(userFromCache.getPasswordHash())) {
                throw new ServiceException("用户名或密码错误");
            }
            // 密码正确，则重新设置 token 过期时间并返回用户信息
            redisTemplate.expire(redisKey, TOKEN_EXPIRATION_SECONDS, TimeUnit.SECONDS);
            return userFromCache;
        }

        // 如果 Redis 中不存在用户信息，则从数据库中查询用户信息
        EmployeeEntity userFromDB = employeeMapper.selectByName(user.getUserName());
        if (userFromDB == null) {
            throw new ServiceException("账户不存在");
        }

        if (!user.getPasswordHash().equals(userFromDB.getPasswordHash())) {
            throw new ServiceException("用户名或密码错误");
        }

        // 生成新的 token
        String token = TokenUtils.createToken(userFromDB.getEmployeeId().toString(), userFromDB.getPasswordHash());
        userFromDB.setToken(token); // 设置用户对象的token字段

        // 将用户信息存储到 Redis 中，键为 "user:" + userName，值为用户对象，设置过期时间
        redisTemplate.opsForValue().set(redisKey, userFromDB, TOKEN_EXPIRATION_SECONDS, TimeUnit.SECONDS);

        // 返回用户信息
        return userFromDB;
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
