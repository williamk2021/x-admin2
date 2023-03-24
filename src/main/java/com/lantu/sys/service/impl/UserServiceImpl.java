package com.lantu.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lantu.common.utils.JwtUtil;
import com.lantu.common.vo.Result;
import com.lantu.sys.entity.User;
import com.lantu.sys.mapper.UserMapper;
import com.lantu.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-03-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Map<String, Object> login(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        User loginUser = this.baseMapper.selectOne(wrapper);
        if (loginUser!=null&&passwordEncoder.matches(user.getPassword(),loginUser.getPassword())){
            //暂时用uuid，终极方案jwt
            //String key="user:"+ UUID.randomUUID();

            loginUser.setPassword(null);
           // redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);
            String token = jwtUtil.createToken(loginUser);
            Map<String,Object>data=new HashMap<>();
            data.put("token",token);
            return data;
        }

        return null;
    }

    /*@Override
    public Map<String, Object> login(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        wrapper.eq(User::getPassword,user.getPassword());
        User loginUser = this.baseMapper.selectOne(wrapper);
        if (loginUser!=null){
            String key="user:"+ UUID.randomUUID();
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);
            Map<String,Object>data=new HashMap<>();
            data.put("token",key);
            return data;
        }

        return null;
    }*/

    @Override
    public Map<String, Object> getUserInfo(String token) {
        //根据token获取用户信息
       // Object obj = redisTemplate.opsForValue().get(token);
        User loginUser=null;
        try {
            loginUser = jwtUtil.parseToken(token, User.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (loginUser!=null){
           //User loginUser =JSON.parseObject(JSON.toJSONString(obj), User.class);
           Map<String,Object>data=new HashMap<>();
           data.put("name",loginUser.getUsername());
           data.put("avatar",loginUser.getAvatar());
            List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles",roleList);
            return data;

        }
        return null;
    }

    @Override
    public void logout(String token) {
        //redisTemplate.delete(token);

    }


}
