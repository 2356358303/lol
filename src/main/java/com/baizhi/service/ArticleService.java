package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {

    Map<String,Object> selectAllArticle(Integer page, Integer rows);
    void add(Article article);
    void del(Article article);
    void edit(Article article);
    List<Article>selectArticleByGuruId(String id);
    List<Article>selectAll();

}
