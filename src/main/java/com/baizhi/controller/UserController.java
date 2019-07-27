package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Mapss;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.goeasy.GoEasy;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("selectAllUser")
    @ResponseBody
    public Map<String,Object> selectAllUser(Integer page, Integer rows){
        Map<String,Object> map=userService.selectAllUser(page,rows);
        System.out.println(map);
        return  map;
    }@RequestMapping("out")
    public void out(HttpServletRequest request, HttpServletResponse resp) throws Exception {

//        做文件的导出
//        1.注入userService查询出所有的用户   list
            List<User> list =userService.selectAll();
//        2.导出


        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生表"),
                User.class, list);



        String fileName = "用户报表("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+").xls";

        fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
        resp.setContentType("application/vnd.ms-excel");
        resp.setHeader("content-disposition","attachment;filename="+fileName);
        workbook.write(resp.getOutputStream());

    }
    @RequestMapping("/count")
    @ResponseBody
    public Map<String,Object> count(Integer day) {
        Integer count = userService.count(7);
        Integer count1 = userService.count(15);
        Integer count2 = userService.count(30);
        Integer count3 = userService.count(60);
        Integer count4 = userService.count(90);
        Integer count5 = userService.count(180);

        Map<String,Object> map = new HashMap<>();

        Integer[] counts = {count, count1, count2, count3, count4, count5};
        map.put("data",counts);
//        String[] days = {"7天", "15天", "30天", "60天", "90天", "半年", "一年"};
       // HashMap map = new HashMap();
//        map.put("days", days);
       // map.put("count", counts);
       // System.out.println(map);
        return map;
    }
    @RequestMapping("/city")
    @ResponseBody
    public Map city(){
        Map<String,Object>map=new HashMap<>();
        List list=userService.findByCity("男");
        map.put("male",list);
        List list2=userService.findByCity("女");
        map.put("female",list2);
        return map;
    }

}
