package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Banner {

    /**
     * 轮播图表
     */
    @Id
    private String  id;
    private String name;
    private String cover;
    private String description;
    private String status;
    //将时间数据转换为JSON
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="create_date")
    private Date createDate;

}
