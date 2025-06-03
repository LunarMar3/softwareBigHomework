package com.scut.softwareBigHomework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scut.softwareBigHomework.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
