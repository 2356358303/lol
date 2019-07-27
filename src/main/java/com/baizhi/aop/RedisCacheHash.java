package com.baizhi.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;

@Configuration
@Aspect
public class RedisCacheHash {
    @Autowired
    private Jedis jedis;

/**
 * 将业务层的查询方法添加到缓存中
 * @param proceedingJoinPoint
 * @return
 * @throws Throwable
 */
@Around("execution(* com.baizhi.service.*.selectAll*(..))")
public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

//        判断当前注解是否存在
    MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
    Method method = methodSignature.getMethod();
    boolean b = method.isAnnotationPresent(com.baizhi.annotation.RedisCache.class);
    Object result = null;
    if(b){
//            存在注解
        StringBuilder sb = new StringBuilder();

//           获取类的名称
        String className = proceedingJoinPoint.getTarget().getClass().getName();
//          获取方法的名称
        String methodName = proceedingJoinPoint.getSignature().getName();
//           获取参数实参
        Object[] args = proceedingJoinPoint.getArgs();

//            sb.append(className).append(".").append(methodName);
        sb.append(methodName);

        for (Object arg : args) {
            sb.append(arg);
        }

        String key = sb.toString();
        System.out.println("key:"+key);

//           判断redis中是否含有这个key
        if ( jedis.hexists(className, key) ){
//               缓存中存在该key
            String json = jedis.hget(className, key);
            result = JSONObject.parse(json);
        }else{
//                缓存中不含有该key,放行方法
            result = proceedingJoinPoint.proceed();
//                将查询的结果放入到redis中缓存起来
            jedis.hset(className, key, JSONObject.toJSONString(result));
        }
        jedis.close();
        return result;
    }else {
//            不存在注解
        result = proceedingJoinPoint.proceed();
    }
    jedis.close();
    return result;
}


    /**
     * 执行增删改方法之后清除当前业务层的查询缓存
     * @param joinPoint
     */
//    @After("execution(* com.baizhi.service.*.*(..)) && !execution(* com.baizhi.service.*.select*(..))")
    @AfterReturning("execution(* com.baizhi.service.*.*(..)) && !execution(* com.baizhi.service.*.select*(..))")
    public void after(JoinPoint joinPoint){
//        获取当前执行目标对象的全名
        String className = joinPoint.getTarget().getClass().getName();
        System.out.println("className:"+className);
        jedis.del(className);

//        关闭jedis的连接
        jedis.close();
    }

}

