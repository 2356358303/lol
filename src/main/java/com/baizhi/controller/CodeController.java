package com.baizhi.controller;

import com.baizhi.util.SecurityCode;
import com.baizhi.util.SecurityImage;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOError;
import java.io.IOException;

@Controller
@RequestMapping("/code")
public class CodeController {
    @RequestMapping("/getCode")
    public void code(HttpServletResponse response , HttpServletRequest request)throws IOException{
        //获取验证码数字
        String code= SecurityCode.getSecurityCode();
        //存到session
        request.getSession().setAttribute("code",code);
        //获取验证码图片
        BufferedImage image =SecurityImage.createImage(code);
        //设置响应内容
         /** ServletOutputStream outputStream =response.getOutputStream();*/
        response.setContentType("image/png");
         ImageIO.write(image,"png",response.getOutputStream());
    }

}
