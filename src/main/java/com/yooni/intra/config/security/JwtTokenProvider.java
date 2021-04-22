package com.yooni.intra.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class JwtTokenProvider{

    @Value("spring.jwt.secret")
    private String secretKey;

    private long tockenValid = 1000L*60*60;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String userPk, List<String> roles){
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles",roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tockenValid))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token){
        //UserDetails userDetails = userDetailsService.loadUserByUsername(this.())
        
    }
}
