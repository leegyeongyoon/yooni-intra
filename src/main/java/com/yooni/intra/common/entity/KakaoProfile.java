package com.yooni.intra.common.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class KakaoProfile {
    private Long id;
    private Properties properties;

    @Getter
    @Setter
    @ToString
    private static class Properties{
        private String nickname;
        private String thumbnailImage;
        private String profileImage;

    }
}
