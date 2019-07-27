package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @RequestMapping("selectAllBanner")
    @ResponseBody
    public Map<String,Object> selectAllBanner(Integer page,Integer rows){
        Map<String, Object> map = bannerService.selectAllBanner(page, rows);
        return map;
    }
    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(String oper, Banner banner){
        Map<String, Object> map = new HashMap<>();
        if(oper.equals("add")){
            map = add(banner);
        }
        if(oper.equals("edit")){
            map = edit(banner);
        }
        if(oper.equals("del")){
            map = del(banner);
        }
        return map;
    }

    public Map<String,Object> add(Banner banner){
        Map<String, Object> map = new HashMap<>();
        try {
            String id = bannerService.add(banner);
            map.put("status",true);
            map.put("message",id);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }

    public Map<String,Object> edit(Banner banner){
        Map<String, Object> map = new HashMap<>();
        try {
            if("".equals(banner.getCover())){
                banner.setCover(null);
            }
            bannerService.edit(banner);
            map.put("status",true);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }

    public Map<String,Object> del(Banner banner){
        Map<String, Object> map = new HashMap<>();
        try {
            bannerService.del(banner);
            map.put("status",true);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }

    @RequestMapping("upload")
    public void upload(String id, MultipartFile cover, HttpServletRequest request) throws IOException {
        System.out.println("id:"+id);

//        文件上传
        cover.transferTo(new File(request.getSession().getServletContext().getRealPath("/banner/img"),cover.getOriginalFilename()));
        System.out.println("cover upload over!");
//        根据id修改图片的名字
        Banner banner = new Banner();
        banner.setId(id);
        banner.setCover(cover.getOriginalFilename());
        bannerService.edit(banner);

    }



}
