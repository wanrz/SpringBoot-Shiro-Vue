package com.heeexy.example.engine.bean.base;


import com.heeexy.example.common.enums.ResultType;

public class ResultBean<T> {
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}


	private String code;
	private String msg;
	private T data;
	
	
	public ResultBean() {
		super();
	}
	
	public ResultBean(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public ResultBean(T data) {
		super();
		this.code=ResultType.SUCCESS.getValue();
		this.msg=ResultType.SUCCESS.getMsg();
		this.data=data;
	}


	public ResultBean(Throwable e) {
		super();
		this.code= ResultType.FAIL.getValue();
		this.msg = e.getMessage();
	}



}
