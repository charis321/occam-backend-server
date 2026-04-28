package com.charis.occam_spm_sys.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
	private final int code;
    private final String msg;
    private Object data;
    
    public BusinessException() {
    	super("業務例外");
        this.code = 500;
        this.msg = "業務例外";
    }
    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    public BusinessException(int code, String msg, Object data) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
   
}
