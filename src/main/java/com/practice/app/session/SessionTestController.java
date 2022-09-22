package com.practice.app.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@RestController
@Slf4j
public class SessionTestController {

    @GetMapping(value = "/user/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public void login(HttpServletRequest request,
                      @RequestParam(name="id") String id, @RequestParam(name="password")String password) {

        if(!"".equals(id) && !"".equals(password)){

            HttpSession session = request.getSession(true);  //세션 생성
            session.setMaxInactiveInterval(120);                    //세션 만료시간 설정(초 단위)

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            User user = new User();
            user.setId(id);
            user.setLastLoginDate(dateFormat.format(session.getCreationTime()));
            user.setLastAccessDate(dateFormat.format(session.getLastAccessedTime()));
            System.out.println(session.getCreationTime());
            session.setAttribute("user",user);                          //세션에 유저정보 객체를 저장

            System.out.println("session.getId() : " + session.getId());        //서버에 생성된 세션ID
            System.out.println("session.isNew() : " + session.isNew());        //신규로 생성된 세션인가?

        }

        System.out.println("로그인 완료");
    }

    @GetMapping(value = "/user/activity", produces = MediaType.APPLICATION_JSON_VALUE)
    public void someRequest(HttpServletRequest request) {

        HttpSession session = request.getSession(false);    //세션이 있을 경우 가져오고 없으면 null

        if(session != null){
            System.out.println(session.getId());

            User user = (User)session.getAttribute("user");
            user.setLastAccessDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(session.getLastAccessedTime()));

            System.out.println("요청한 처리를 수행..");
        }else{
            System.out.println("세션이 만료되어 재로그인해야 합니다.");
        }

    }

    @GetMapping(value = "/user/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public void logout(HttpServletRequest request) {

        HttpSession session = request.getSession();
        System.out.println("session.isNew() : " + session.isNew());        //신규로 생성된 세션인가?

        session.invalidate();                   //세션 무효화(세션 만료처리)

        System.out.println("로그아웃 완료");
    }
}
