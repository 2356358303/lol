package com.baizhi.service;

import com.baizhi.annotation.RedisCache;
import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceIMpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Override
    @RedisCache
    public Map<String, Object> selectAllArticle(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        Article article =new Article();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Article> users =articleDao.selectByRowBounds(article,rowBounds);
        int count = articleDao.selectCount(article);
        map.put("page", page);       //当前页码数值
        map.put("rows", users);    //当前页中的banner数据
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1); //总页数
        map.put("records", count);   //总条数
        return map;
    }

    @Override
    public void add(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreateDate(new Date());
        int i = articleDao.insertSelective(article);
        if(i == 0){
            throw new RuntimeException("添加文章失败");
        }else{
            GoEasy goEasy=new GoEasy("http://rest-hangzhou.goeasy.io","BC-63fce27e1a4646c7a6842b0dcaf47989");
            goEasy.publish( "title",article.getContent());
        }
    }

    @Override
    public void edit(Article article) {
        int i = articleDao.updateByPrimaryKeySelective(article);
        if(i == 0){
            throw new RuntimeException("修改文章失败");
        }
    }

    @Override
    public List<Article> selectArticleByGuruId(String id) {
        Article article = new Article();
        article.setAuthor(id);
        List<Article>list=articleDao.select(article);
        return list;
    }

    @Override
    public List<Article> selectAll() {
        List<Article> articles = articleDao.selectAll();
        return articles;
    }

    @Override
    public void del(Article article) {
        int i = articleDao.deleteByPrimaryKey(article.getId());
        if(i == 0){
            throw new RuntimeException("文章删除失败");
        }
    }
}
