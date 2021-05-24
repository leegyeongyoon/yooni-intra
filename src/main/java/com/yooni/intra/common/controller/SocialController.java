package com.yooni.intra.common.controller;

import com.google.gson.Gson;
import com.yooni.intra.common.service.user.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/social/login")
public class SocialController {
    @Autowired
    private Environment environment;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    @Autowired
    private KakaoService kakaoService;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    @GetMapping
    public ModelAndView socialLogin(ModelAndView modelAndView){
        StringBuilder loginUrl = new StringBuilder()
                .append(environment.getProperty("spring.social.kakao.url.login"))
                .append("?client_id=").append(kakaoClientId)
                .append("&response_type=code")
                .append("&redirect_uri=").append(baseUrl).append(kakaoRedirect);
        modelAndView.addObject("loginUrl",loginUrl);
        modelAndView.setViewName("social/login");
        return modelAndView;
    }

    @GetMapping(value = "/kakao")
    public ModelAndView redirectKakao(ModelAndView modelAndView, @RequestParam String code){
        modelAndView.addObject("authInfo",kakaoService.getKakaoTokenInfo(code));
        modelAndView.setViewName("social/redirectKakao");
        return modelAndView;
    }
}
