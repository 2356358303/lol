package com.baizhi.service;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {
    void selectOne(String username, String password, String code, HttpServletRequest request);

}
