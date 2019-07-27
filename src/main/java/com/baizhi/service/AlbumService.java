package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;

import java.util.List;
import java.util.Map;

public interface AlbumService {

    Map<String,Object> selectAllAlbum(Integer page, Integer rows);
    String add(Album album);
    void edit(Album album);
    void del(Album album);
    List<Album>selectAll();

}
