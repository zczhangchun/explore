import com.monkey.springboot.webjarProject.exception.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

//package com.zhangchun.springboot.webjarProject.handler;
//
//import com.zhangchun.springboot.webjarProject.exception.MyException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by smlz on 2019/3/19.
// */
@ControllerAdvice
public class MyExceptionHanlder {


    /**
     * 浏览器和其他客户端都返回了json 数组，不满足自适应
     * @param e
     * @param request
     * @return
     */
/*    @ExceptionHandler(value= MyException.class)
    @ResponseBody
    public Map<String,Object> dealException(MyException e, HttpServletRequest request){
        Map<String,Object> retInfo = new HashMap<>();
        retInfo.put("code",e.getCode());
        retInfo.put("msg",e.getMsg());
        return retInfo;
    }*/

/*    @ExceptionHandler(value= MyException.class)
    public String dealException(MyException e, HttpServletRequest request){
        Map<String,Object> retInfo = new HashMap<>();
        retInfo.put("code",e.getCode());
        retInfo.put("msg",e.getMsg());
        return "forward:/error";
    }*/

    @ExceptionHandler(value= MyException.class)
    public String dealException(MyException e, HttpServletRequest request){
        Map<String,Object> retInfo = new HashMap<>();
        retInfo.put("code",e.getCode());
        retInfo.put("msg",e.getMsg());
        request.setAttribute("javax.servlet.error.status_code",500);
        request.setAttribute("ext",retInfo);
        return "forward:/error";
    }

}
