package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @RequestMapping("selectAllAlbum")
    @ResponseBody
    public Map<String,Object> selectAllAlbum(Integer page,Integer rows){
      Map<String,Object>map=albumService.selectAllAlbum(page,rows);
        System.out.println(map);
      return  map;
    }
    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(String oper ,Album album){
        Map<String, Object> map = new HashMap<>();
        if(oper.equals("add")){
            try {
                String id = albumService.add(album);
                map.put("status","true");
                map.put("message",id);
            }catch (Exception e){
                map.put("status","error");
                map.put("message",e.getMessage());
            }

//            map = add(album);

        }
        if(oper.equals("edit")){
            try {
                albumService.edit(album);
                map.put("status","true");
            }catch (Exception e){
                map.put("status","error");
                map.put("message",e.getMessage());
            }
        }
      /*  if(oper.equals("edit")){
            map = edit(album);
        }
        if(oper.equals("del")){
            map = del(album);
        }*/
        return map;
    }
   /* public Map<String,Object> add(Album album){
        System.out.println(album+"yyyyyyy");
        Map<String, Object> map = new HashMap<>();
        try {
            String id = albumService.add(album);
            map.put("status",true);
            map.put("message",id);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }*/
    @RequestMapping("upload")
    public  void upload(String id, MultipartFile cover, HttpServletRequest request)throws IOException {
        System.out.println("----------------------upload  begin-------------------------");
        System.out.println("上传"+id);
        cover.transferTo(new File(request.getSession().getServletContext().getRealPath("/album/img"),cover.getOriginalFilename()));
        System.out.println("-----------------------upload  over------------------------------------");
        //根据ID修改图片的名字
        Album album=new Album();
        album.setId(id);
        album.setCover(cover.getOriginalFilename());
        System.out.println("album:"+album);
        albumService.edit(album);
    }
}
