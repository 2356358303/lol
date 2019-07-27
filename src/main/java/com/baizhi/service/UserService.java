package com.baizhi.service;

import com.baizhi.entity.Mapss;
import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String,Object> selectAllUser(Integer page, Integer rows);
    List<User> selectAll();
    Integer count(Integer day);
    List<Mapss> findByCity(String sex);
    User selectById(String id);
}
