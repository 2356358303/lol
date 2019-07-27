package com.baizhi.poi;

import com.baizhi.entity.User;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PoiImport1 {
    public static void main(String[] args) throws Exception {

        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("E:/框架阶段/后期项目/day7/用户信息.xls")));
        HSSFSheet sheet = workbook.getSheet("用户信息表");

        List<User> list = new ArrayList<>();

        for (int i = 2; i < sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            User user = new User();

//            Class<?> userClass = Class.forName("com.baizhi.entity.User");
//            Class<? extends User> userClass = user.getClass();
            Class<User> userClass = User.class;
            Field[] fields = userClass.getDeclaredFields();
            for (int j = 0; j < fields.length; j++) {
                HSSFCell cell = row.getCell(j);

//               拿到属性名
                Field field = fields[j];    //获取当前属性
                String fieldName = field.getName(); //获取当前属性的属性名
//                先拿到方法名    通过当前属性获取当前属性的set方法
                String methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);

                if("bir".equals(fieldName)){
                    //                通过方法名拿到方法     通过获取到的set方法的方法名获取当前set方法对象
                    Method method = userClass.getDeclaredMethod(methodName, Date.class);
                    //当前类型为data
                    Date date = cell.getDateCellValue();
                    method.invoke(user,date);
                }else{
                    //                通过方法名拿到方法     通过获取到的set方法的方法名获取当前set方法对象
                    Method method = userClass.getDeclaredMethod(methodName, String.class);
                    String value = cell.getStringCellValue();
//                   调用方法  通过获取到的set方法对象去调用该方法
                    method.invoke(user,value);
                }
            }
            list.add(user);
        }

        for (User user : list) {
            System.out.println(user);
        }
}}
