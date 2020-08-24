package com.back.weins.Interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.back.weins.Utils.JwtTokenUtil;
import com.back.weins.Utils.MsgUtils.Msg;
import com.back.weins.Utils.MsgUtils.MsgCode;
import com.back.weins.Utils.MsgUtils.MsgUtil;
import com.back.weins.Utils.SessionUtils.SessionUtil;
import com.back.weins.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SessionValidateInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception{

//        boolean status = SessionUtil.checkAuth();
//        if(!status){
//            System.out.println("Failed");
//            Msg msg = MsgUtil.makeMsg(MsgCode.NOT_LOGGED_IN_ERROR);
//            sendJsonBack(response, msg);
//            return false;
//        }
//        System.out.println("Success");
//        System.out.println("前置成功");
        return true;




//        response.setHeader("Access-Control-Allow-Origin", "*");
//        String token = request.getHeader("token");
//        System.out.println(token);
//        if(token==null) return false;
//        String res = token.replace(" ", "");
//        Claims claims = jwtTokenUtil.parseJWT(res);
//
//        String subject = claims.getSubject();
//
//        JSONObject jsonObject = JSON.parseObject(subject);
//        User user = JSON.toJavaObject(jsonObject, User.class);
//        System.out.println(user);
//        return user.getId() >= 0;
//        return true;
    }

    private void sendJsonBack(HttpServletResponse response, Msg msg) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(JSONObject.toJSON(msg));
        } catch (IOException e) {
            System.out.println("send json back error");
        }
    }
}
