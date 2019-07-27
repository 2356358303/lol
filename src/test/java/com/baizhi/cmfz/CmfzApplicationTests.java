package com.baizhi.cmfz;

import com.baizhi.dao.AdminDao;
import com.baizhi.dao.ArticleDao;
import com.baizhi.dao.BannerDao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Article;
import com.baizhi.entity.Banner;
import com.baizhi.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzApplicationTests {
    @Autowired
    private AdminDao adminDao;
    @Test
    public void contextLoads() {
        Example example=new Example(Admin.class);
        example.createCriteria().andEqualTo("username","111");
        Admin admin=adminDao.selectOneByExample(example);
        System.out.println(admin);
    }

    @Test
    public void test5(){
        Example example = new Example(Admin.class);
        example.createCriteria().andIsNull("username").andLike("nickname","小%");
        List<Admin> admins = adminDao.selectByExample(example);
        System.out.println(admins.size());
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }
    @Test
    public void test1(){
        List<Admin> admins = adminDao.selectAll();
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }
    @Autowired
    private BannerDao bannerDao;
@Test
    public void selectList() {
        List<Banner> banners=bannerDao.selectAll();
     System.out.println(banners);
     }
    @Autowired
    private UserDao userDao;
    @Test
    public void selectAllUser() {
        Map<String, Object> map = new HashMap<>();

        List<User> admins = userDao.selectAll();
        for (User admin : admins) {
            System.out.println(admin);
        }
    }
    @Autowired
    private ArticleDao articleDao;
    @Test
    public void edit() {
        Date date=new Date();
         Article article1=new Article("e3990638-99b8-4603-a844-8b74530e93f5", "9", "29", "9",new Date() );
        int i = articleDao.updateByPrimaryKeySelective(article1);
        if(i == 0){
            throw new RuntimeException("修改文章失败");
        }
    }
}
