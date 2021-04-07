package com.yooni.intra.model.response.service;

import com.yooni.intra.model.response.SingleResult;

public class ResponseService {

    public enum CommonResponse{
        SUCCESS(0,"SUCCESS"),
        FAIL(-1,"FAIL");

        int code;
        String msg;

        CommonResponse(int code, String msg){
            this.code = code;
            this.msg = msg;
        }

        public int getCode(){
            return code;
        }

        public String getMsg(){
            return msg;
        }

        public <T> SingleResult<T> getSingleResult(T data){
            SingleResult<T> result = new SingleResult<>();
            result.setData(data);
            setSuccessResult(result);
            return result;
        }
    }
}
