package com.monkey.security.practice.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.monkey.security.practice.model.CaptchaImageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

@RestController
public class KaptchController {

    @Autowired
    private DefaultKaptcha kaptchaProducer;

    @RequestMapping("/kaptcha")
    public void kaptcha(HttpSession session, HttpServletResponse response){
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //生成验证码
        String capText = kaptchaProducer.createText();
        //设置失效时间
        CaptchaImageVO captchaImageVO = new CaptchaImageVO(capText, 2 * 60);
        //验证码放到session
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, captchaImageVO);
        //将图片返回给前端
        try (ServletOutputStream out = response.getOutputStream()){
            final BufferedImage image = kaptchaProducer.createImage(capText);
            ImageIO.write(image, "jpg", out);
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
