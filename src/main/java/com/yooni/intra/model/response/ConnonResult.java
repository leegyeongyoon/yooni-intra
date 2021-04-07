package com.yooni.intra.model.response;

import io.swagger.annotations.ApiModelProperty;

public class ConnonResult {
    @ApiModelProperty(value = "응답 성공여부 : true/false")
    private boolean success;

    @ApiModelProperty(value = "응답 코드 : >= 0 정상 , < 0 비정상")
    private boolean code;

    @ApiModelProperty(value = "응답 메시지 ")
    private String message;
}
