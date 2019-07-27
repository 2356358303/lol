package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;
@Data
public class User {
    @Id
    @Excel(name = "编号",width = 20,height = 15)
    private String id;
    @Excel(name = "姓名",width = 20,height = 15)
    private String username;
    private String password;
    private String phone;
    private String salt;
    private String dharma;
    private String province;
    @Excel(name = "城市",width = 20,height = 15)
    private String city;
    private String sign;
    private String photo;
    @Excel(name = "性别",width = 20,height = 15)
    private String  sex;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "生日",width = 20,height = 15,format = "yyyy-MM-dd HH:mm:ss")
    private Date create_date;
}
