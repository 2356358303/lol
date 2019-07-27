package com.baizhi.dao;

import com.baizhi.entity.Mapss;
import com.baizhi.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {
    Integer count(Integer day);
    List<Mapss> findByCity(String sex);
}
