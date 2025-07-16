package com.charis.occam_spm_sys.common;

import lombok.Data;

@Data
public class Result {
	private int code;
	private String msg;
	private Long total;
	private Object data;
	
	public static Result fail() {
		return result(400, "fail", 0L, null);
	}
	public static Result fail(String msg) {
		return result(400, msg, 0L, null);
	}
	public static Result fail(int code , String msg) {
		return result(code, msg, 0L, null);
	}
	
	
	public static Result success() {
		return result(200, "success", 0L, null);
	}
	public static Result success(String msg) {
		return result(200, msg, 0L, null);
	}
	public static Result success(Object data) {
		return result(200, "success", 0L, data);
	}
	public static Result success(String msg, Object data) {
		return result(200, msg, 0L, data);
	}
	public static Result success(Object data, Long total) {
		return result(200, "success", total, data);
	}
	public static Result warming(int code, String msg, Object data) {
		return result(200, msg, 0L, data);
	}
	
	private static Result result(int code, String msg, Long total, Object data) {
		Result res = new Result();
		res.setCode(code);
		res.setMsg(msg);
		res.setTotal(total);
		res.setData(data);
		return res;
	}
	
}