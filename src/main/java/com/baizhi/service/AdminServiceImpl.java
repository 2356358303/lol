package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    public void selectOne(String name, String password, String code, HttpServletRequest request) {
        HttpSession session = request.getSession();
        //获取session中验证码判断
        String code1=(String) session.getAttribute("code");
        if(code1.equals(code)){
            Admin admin=new Admin();
            admin.setUsername(name);
            Admin admin1=adminDao.selectOne(admin);
            if(admin1!=null){
                if(admin1.getPassword().equals(password)){
                    session.setAttribute("loginAdmin",admin1);
                }else{
                    throw new RuntimeException("密码错误");
                }
            }else{
                throw new RuntimeException("用户名不存在");
            }
        }else{
            throw  new RuntimeException("验证码错误");
        }
    }
}