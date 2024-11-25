package com.zaizi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zaizi.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 检测登录
    @Select("select * from users where username = #{username} and passwd = #{passwd}")
    User getUserByUserNameAndPasswd(User user);
    // 添加用户
    @Insert("insert into users (username,passwd,name,role) values (#{username},#{passwd},#{name},#{role})")
    @Options(useGeneratedKeys = true,keyProperty = "userId")
    void addUser(User user);
}
