package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
@Controller
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("selectAllArticle")
    @ResponseBody
    public Map<String, Object> selectAllArticle(Integer page, Integer rows) {
        Map<String, Object> map = articleService.selectAllArticle(page, rows);
        System.out.println(map);
        return map;
    }
    //        {"error":0,"url":"\/ke4\/attached\/W020091124524510014093.jpg"}
    @RequestMapping("upload")
    @ResponseBody
    public Map<String,Object> upload(HttpServletRequest request, MultipartFile articleImage) {
        System.out.println(articleImage+"asdadasdasdasdasda");
        Map<String, Object> map = new HashMap<>();
//        文件上传
        try {
            articleImage.transferTo(new File(request.getSession().getServletContext().getRealPath("image"),articleImage.getOriginalFilename()));
            map.put("error",0);
            map.put("url","http://localhost:8989/cmfz/image/"+articleImage.getOriginalFilename());

//            http://localhost:8989/cmfz/image/"+articleImage.getOriginalFilename()
//            request获取
//            http://ip
//            端口
//            项目名


        } catch (IOException e) {
            map.put("error",1);
        }
        return map;
    }

    @RequestMapping("browser")
    @ResponseBody
    public Map<String,Object> browser(HttpServletRequest request){
        File file = new File(request.getSession().getServletContext().getRealPath("image"));
        File[] files = file.listFiles();

        Map<String, Object> map = new HashMap<>();
        //文件夹的网络路径
        map.put("current_url","http://localhost:8989/cmfz/image/");
//        当前文件夹中的文件的数量
        map.put("total_count",files.length);
        ArrayList<Object> list = new ArrayList<>();
        for (File img : files) {
            HashMap<String, Object> imgObject = new HashMap<>();
            imgObject.put("is_dir",false);
            imgObject.put("has_file",false);
            imgObject.put("filesize",img.length());
            imgObject.put("is_photo",true);
            imgObject.put("filetype", FilenameUtils.getExtension(img.getName()));
            imgObject.put("filename", img.getName());
            imgObject.put("datetime", "2018-06-06 00:36:39");
            list.add(imgObject);
        }

        map.put("file_list",list);
        return map;
    }
    @RequestMapping("edit")
    @ResponseBody
    public Map<String, Object> edit(Article article, String oper) {
        System.out.println("oper:"+oper);
        System.out.println("article"+article);
        Map<String, Object> map = new HashMap<>();
        try {
            if ("add".equals(oper)) {
                articleService.add(article);
            }
            if ("edit".equals(oper)) {
                articleService.edit(article);
            }
            if ("del".equals(oper)) {
                articleService.del(article);
            }
            map.put("status",true);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }
}