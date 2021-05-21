package com.yooni.intra.common.service.user;

import com.google.gson.Gson;
import com.yooni.intra.common.entity.KakaoProfile;
import com.yooni.intra.exception.detailException.CCommunicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
public class KakaoService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    @Autowired
    private Gson gson;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    public KakaoProfile getKakaoProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        try{
            ResponseEntity<String> response = restTemplate.postForEntity(environment.getProperty("spring.social.kakao.url.profile"), request, String.class);
                    if(response.getStatusCode() == HttpStatus.OK) {
                        return gson.fromJson(response.getBody(), KakaoProfile.class);
                    }

        } catch (Exception e){
            throw new CCommunicationException();
        }
        throw new CCommunicationException();
    }
}
