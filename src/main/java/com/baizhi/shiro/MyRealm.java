package com.baizhi.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.realm.AuthenticatingRealm;

public class MyRealm extends AuthenticatingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //操作数据库
        String username = (String) authenticationToken.getPrincipal();
        //根据username拿user对象
//        参数1：user.getUsername();
//        参数2：user.getPassword();
        SimpleAccount simpleAccount = new SimpleAccount("zhangsan","123","com.baizhi.shiro.MyRealm");


        return simpleAccount;
    }
}
