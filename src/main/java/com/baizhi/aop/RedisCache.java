package com.baizhi.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;
import java.util.Set;

//@Configuration//声明配置类的注解
//@Aspect //面向切面编程 当前类是一个切面容器读取
public class RedisCache {
    @Autowired//自动装配bean
    private Jedis jedis;

    /**
     * 将业务层的查询方法添加到缓存中
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.baizhi.service.*.select*(..))")//切片捕捉
    public Object cache(ProceedingJoinPoint proceedingJoinPoint)throws Throwable {
//        1.获取将要执行的方法
//        2.判断将要执行的方法上是否含有自定义的缓存注解
//        3.如果含有注解，先去缓存中拿，如果缓存中有直接返回，如果没有查询数据库，并添加到缓存中
//        4.如果没有注解，直接方法放行
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();//获取执行方法
        Method method=signature.getMethod();
        Boolean b=method.isAnnotationPresent(com.baizhi.annotation.RedisCache.class);
        if(b){
            //当前要执行的方法上含有该注解
//            判断redis中是否含有某个key
//            如果含有的话，直接拿缓存
//            如果不含有的话，插数据库，然后放入到缓存中一份
            //获取KEY
            StringBuffer builder=new StringBuffer();
            //获取类的全限定名
            String className=proceedingJoinPoint.getTarget().getClass().getName();
            builder.append(className).append(".");
            //获取方法名
            String methodName=method.getName();
            builder.append(className).append(":");
            //            获取实参
            Object[] args = proceedingJoinPoint.getArgs();          //获取要执行方法中的实参
            for (int i = 0; i < args.length; i++) {
                builder.append(args[i].toString());
                if(i==args.length-1){
                    break;
                }
                builder.append(",");
            }
            String key = builder.toString();
            if(jedis.exists(key)){
//               redis中含有这个key
                String s = jedis.get(key);
                Object result = JSONObject.parse(s);
                return result;
            }else{
//                redis中不含有这个key
                Object result = proceedingJoinPoint.proceed();
//                放入到redis中
                jedis.set(key, JSONObject.toJSONString(result));
                return result;
            }
        }else{
//          当前要执行的方法上不含有该注解
            Object result = proceedingJoinPoint.proceed();
            return result;
        }
    }
    @After("execution(* com.baizhi.service.*.*(..)) && !execution(* com.baizhi.service.*.select*(..))")
    public void after(JoinPoint joinPoint){
//        获取将要执行的方法     banner add edit del
        String className = joinPoint.getTarget().getClass().getName();
//        删除相关的缓存       banner
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            if(key.startsWith(className)){
                jedis.del(key);
            }
        }
    }


}
