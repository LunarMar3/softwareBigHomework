package com.scut.softwareBigHomework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scut.softwareBigHomework.dto.UserDto;
import com.scut.softwareBigHomework.entity.User;
import com.scut.softwareBigHomework.mapper.UserMapper;
import com.scut.softwareBigHomework.service.UserService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import com.scut.softwareBigHomework.utils.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public CommonResponse login(UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDto.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return CommonResponse.fail("用户名不存在");
        }
        if (!user.getPassword().equals(userDto.getPassword())) {
            return CommonResponse.fail("密码错误");
        }
        return CommonResponse.success(JwtUtils.generateToken(user.getUsername(), user.getId()));
    }

    @Override
    public CommonResponse register(UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDto.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            return CommonResponse.fail("用户名已存在");
        }
        user = new User();
        BeanUtils.copyProperties(userDto, user);
        userMapper.insert(user);
        return CommonResponse.success(null);
    }



}
