package com.baizhi.service;

import com.baizhi.entity.Chapter;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface ChapterService {
    /**
     *
     * @param albumId
     * @param page
     * @param rows
     * @return
     */
    Map<String,Object> selectAllChapter(String albumId,Integer page,Integer rows);
    String add(Chapter chapter,String albumId);
    void edit(Chapter chapter);


}
