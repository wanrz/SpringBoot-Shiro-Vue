/**
 * Project Name：ibis1.2 
 * File Name：lipRead.java 
 * Package Name：com.cloudwalk.common.engine.face.eoc 
 * Description: TODO Description. 
 * @date: 2017年11月14日 下午6:51:58 
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
 * ClassName:lipRead 
 * Description: 唇语信息. 
 * Date:     2017年11月14日 下午6:51:58 
 * @author   冯德志
 * @version  
 * @since    JDK 1.7
 */
public class LipRead{

	/**
	 * 唇语检测错误时，返回具体错误码
	 */
	private int code;
	
	/**
	 * 错误信息
	 */
	private String errorInfo;
	
	/**
	 * 返回最佳人脸jpg格式
	 */
	private String faceImage;
	
	/** 返回分数*/  
	private float computeScore;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getFaceImage() {
		return faceImage;
	}

	public void setFaceImage(String faceImage) {
		this.faceImage = faceImage;
	}

	public float getComputeScore() {
		return computeScore;
	}

	public void setComputeScore(float computeScore) {
		this.computeScore = computeScore;
	}
	

}

