package com.scut.softwareBigHomework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scut.softwareBigHomework.dto.UserDto;
import com.scut.softwareBigHomework.entity.User;
import com.scut.softwareBigHomework.mapper.UserMapper;
import com.scut.softwareBigHomework.service.UserService;
import com.scut.softwareBigHomework.utils.CommonResponse;
import com.scut.softwareBigHomework.utils.JwtUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;


    @Override
    public CommonResponse login(@RequestBody UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDto.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return CommonResponse.fail("用户名不存在");
        }
        if (!user.getPassword().equals(userDto.getPassword())) {
            return CommonResponse.fail("密码错误");
        }
        String token = JwtUtils.generateToken(user.getUsername(), user.getId());
        // TODO: 加上过期时间，未知的idea的bug导致无法导入TimeUnit
        redisTemplate.opsForValue().set("user:"+user.getId(),token);
        return CommonResponse.success(token);
    }

    @Override
    public CommonResponse register(@RequestBody UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDto.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            return CommonResponse.fail("用户名已存在");
        }

        String code = (String) redisTemplate.opsForValue().get("code:" + userDto.getEmail());
        if (code == null) {
            return CommonResponse.fail("验证码已过期");
        }
        if (!code.equals(userDto.getCode())) {
            return CommonResponse.fail("验证码错误");
        }
        redisTemplate.delete("code:" + userDto.getEmail());

        user = new User();
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setDepartmentId(userDto.getDepartmentId());
        user.setPhone(userDto.getPhone());
        user.setRealName(userDto.getRealName());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userMapper.insert(user);
        return CommonResponse.success(null);
    }

    @Override
    public CommonResponse sms(UserDto userDto) {
        // 创建邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        // 设置收件人
        message.setTo(userDto.getEmail());
        // 设置邮件主题
        message.setSubject("验证码");
        // 设置邮件内容
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        message.setText("您的验证码是：" + code +", 请在5分钟内输入");

        // TODO: 加上过期时间，未知的idea的bug导致无法导入TimeUnit
        redisTemplate.opsForValue().set("code:" + userDto.getEmail(), code);
        // 发送邮件
        mailSender.send(message);
        return new CommonResponse(200, "发送成功", null);
    }

    @Override
    public CommonResponse getUsers(Integer departmentId) {
        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = userMapper.selectList(new QueryWrapper<User>().eq("department_id", departmentId));
        for (User user : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setRealName(user.getRealName());
            userDto.setEmail(user.getEmail());
            userDto.setPhone(user.getPhone());
            userDto.setDepartmentId(user.getDepartmentId());
            userDto.setLeaderId(user.getLeaderId());
            userDtos.add(userDto);
        }
        return CommonResponse.success(userDtos);
    }

    @Override
    public CommonResponse logout(String token) {

        redisTemplate.delete("user:"+JwtUtils.getId(token));
        return CommonResponse.success(null);

    }

    @Override
    public CommonResponse updateDepartmentId(String token, UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        String id = JwtUtils.getId(token);
        queryWrapper.eq("id", id);
        User user = userMapper.selectOne(queryWrapper);
        user.setDepartmentId(userDto.getDepartmentId());
        userMapper.updateById(user);
        return CommonResponse.success(null);
    }

}
