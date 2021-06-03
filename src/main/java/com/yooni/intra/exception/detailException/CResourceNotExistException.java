package com.yooni.intra.exception.detailException;

public class CResourceNotExistException extends  RuntimeException{
    public CResourceNotExistException(String msg , Throwable t){
        super(msg,t);
    }
    public CResourceNotExistException(String msg){
        super(msg);
    }
    public CResourceNotExistException(){
        super();
    }
}
