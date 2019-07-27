package com.baizhi.service;
import com.baizhi.entity.Banner;

import java.util.List;
import java.util.Map;
public interface BannerService {
        Map<String,Object> selectAllBanner(Integer page,Integer rows);
        String add(Banner banner);
        void edit(Banner banner);
        void del(Banner banner);
        List<Banner> selectAll();
}
