package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Mapss;
import com.baizhi.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    @Override
    public Map<String, Object> selectAllUser(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        User user =new User();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> users =userDao.selectByRowBounds(user,rowBounds);
        int count = userDao.selectCount(user);
        map.put("page", page);       //当前页码数值
        map.put("rows", users);    //当前页中的banner数据
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1); //总页数
        map.put("records", count);   //总条数
        return map;
    }

    @Override
    public List<User> selectAll() {
        List<User>users=userDao.selectAll();
        System.out.println(users);
        return users;
    }

    @Override
    public Integer count(Integer day) {
        return userDao.count(day);
    }

    @Override
    public List<Mapss> findByCity(String sex) {
      List<Mapss>list=userDao.findByCity(sex);
      return list;
    }

    @Override
    public User selectById(String id) {
        User user = userDao.selectByPrimaryKey(id);
        return user;
    }
}
