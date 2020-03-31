package com.heeexy.example.common.enums;
/**
 * @author yckj
 * 返回结果枚举类
 *
 */
public enum ResultType {

	SUCCESS("0", "成功"), FAIL("1", "失败"), ERROR("-1", "系统异常");

	ResultType(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private String code;
	private String msg;

	public String getValue() {
		return this.code;
	}

	public String getMsg() {
		return this.msg;
	}

	public ResultType from(String value) {
		for (ResultType item : ResultType.values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		}
		return null;
	}

}
