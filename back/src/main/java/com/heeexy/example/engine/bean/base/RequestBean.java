package com.heeexy.example.engine.bean.base;

import java.io.Serializable;

/**
 * @auth Liwei
 * @description 请求对象封装
 * @date 2019/11/23
 */
public class RequestBean implements Serializable {

    private static final long serialVersionUID = 4298422823099430196L;

    /**
     * 提特征图片
     */
    private String img;

    /**
     * 特征A
     */
    private String featureA;

    /**
     * 特征B
     */
    private String featureB;

    /**
     * 图片A
     */
    private String imgA;

    /**
     * 图片B
     */
    private String imgB;

    /**
     * 唇语标签长度
     */
    private int iLabelLen;

    /**
     * 唇语标签
     * @return
     */
    private String label;

    /**
     * 处于使用得token
     */
    private String token;
    
    String videoPath;
	//mp4
	String type;
  
    public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getFeatureA() {
        return featureA;
    }

    public void setFeatureA(String featureA) {
        this.featureA = featureA;
    }

    public String getFeatureB() {
        return featureB;
    }

    public void setFeatureB(String featureB) {
        this.featureB = featureB;
    }

    public String getImgA() {
        return imgA;
    }

    public void setImgA(String imgA) {
        this.imgA = imgA;
    }

    public String getImgB() {
        return imgB;
    }

    public void setImgB(String imgB) {
        this.imgB = imgB;
    }

    public int getiLabelLen() {
        return iLabelLen;
    }

    public void setiLabelLen(int iLabelLen) {
        this.iLabelLen = iLabelLen;
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

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
    
}
