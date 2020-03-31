package com.heeexy.example.engine.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * <p>
 * ClassName: FeatureBean
 * </p>
 * Description:1:1人脸特征信息<br/>
 * 
 * @date 2020年1月1日 下午12:32:30
 * @author yckj0914
 * @version 1.0
 * @since JDK 1.7
 */
public class FeatureBean implements Serializable {

	private static final long serialVersionUID = 4298422883099430196L;

	/**
	 * 响应结果
	 */
	private int result;
	/**
	 * 响应消息描述
	 */
	private String desc;
	/**
	 * 返回的人脸特征
	 */
	private byte[] feature;
	/**
	 * 返回的人脸检测质量分数数组
	 */
	private float[] score;
	/**
	 * 返回的识别分数
	 */
	private float computeScore;

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public byte[] getFeature() {
		return feature;
	}

	public void setFeature(byte[] feature) {
		this.feature = feature;
	}

	public float[] getScore() {
		return score;
	}

	public void setScore(float[] score) {
		this.score = score;
	}

	public float getComputeScore() {
		return computeScore;
	}

	public void setComputeScore(float computeScore) {
		this.computeScore = computeScore;
	}

	@Override
	public String toString() {
		return "FeatureBean [result=" + result + ", desc=" + desc + ", feature=" + Arrays.toString(feature) + ", score="
				+ Arrays.toString(score) + ", computeScore=" + computeScore + "]";
	}

}
