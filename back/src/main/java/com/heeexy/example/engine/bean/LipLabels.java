/**
 * Project Name：ibis1.2 
 * File Name：lipLabels.java 
 * Package Name：com.cloudwalk.common.engine.face.eoc 
 * Description: TODO Description. 
 * @date: 2017年11月14日 下午7:03:51 
 * @author 冯德志
 * @version 
 * @since JDK 1.7
 ************************************************************
 *序号       修改时间            修改人           修改内容
 * 1
 ************************************************************
 *@Copyright: @2010-2017 重庆中科云丛科技有限公司  All Rights Reserved.
 */

package com.heeexy.example.engine.bean;
/**
 * ClassName:lipLabels 
 * Description: 唇语标签. 
 * Date:     2017年11月14日 下午7:03:51 
 * @author   冯德志
 * @version  
 * @since    JDK 1.7
 */
public class LipLabels{

	private static final long serialVersionUID = -8208301579081611515L;
	
	/** 
	 *  响应码
	 */  
	private int code;
	
	/**
	 * 唇语标签
	 */
	private String label;
	

	/**
	 * token信息
	 */
	private String token;


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	
}

