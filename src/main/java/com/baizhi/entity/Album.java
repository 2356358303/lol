package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Album {
    @Id
    private String id;
    private String title;
    private String cover;
    private Integer count;
    private String author;
    private Double score;
    private String broadcast;
    private String brief;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="create_date")
    private Date createDate;
}
