package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import it.sauronsoftware.jave.Encoder;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @RequestMapping("selectChaptersBuAlbumId")
    @ResponseBody
    public Map<String,Object> selectChaptersBuAlbumId(String albumId,Integer page,Integer rows){
        Map<String, Object> map = new HashMap<>();
        map = chapterService.selectAllChapter(albumId, page, rows);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody
    public Map<String,Object> edit(String oper, Chapter chapter, String albumId){
        HashMap<String, Object> map = new HashMap<>();
        if(oper.equals("add")){
            try {
                chapter.setAlbumId(albumId);
                System.out.println("C"+"111111111");
                String id = chapterService.add(chapter,albumId);
                map.put("status",true);
                map.put("message",id);
            } catch (Exception e) {
                map.put("status",true);
                map.put("message",e.getMessage());
            }
        }
        return map;
    }

    @RequestMapping("upload")
    @ResponseBody
    public void upload(MultipartFile name, String id, HttpSession session) throws Exception {
        System.out.println("-----------upload begin------------");

        File file = new File(session.getServletContext().getRealPath("/album/music"), name.getOriginalFilename());
//        文件上传
        name.transferTo(file);

//        修改chapter中name的属性
        Chapter chapter = new Chapter();
        chapter.setId(id);
        chapter.setName(name.getOriginalFilename());
//        获取文件大小
        BigDecimal size = new BigDecimal(name.getSize());
        BigDecimal dom = new BigDecimal(1024);
        BigDecimal bigDecimal = size.divide(dom).divide(dom).setScale(2, BigDecimal.ROUND_HALF_UP);
        chapter.setSize(bigDecimal+"MB");
//        获取文件时长
        Encoder encoder = new Encoder();
        long duration = encoder.getInfo(file).getDuration();
        chapter.setDuration(duration/1000/60+":"+duration/1000%60);

        chapterService.edit(chapter);
    }

}
