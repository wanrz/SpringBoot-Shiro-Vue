package com.heeexy.example.engine.controller;


import com.heeexy.example.engine.bean.FeatureBean;
import com.heeexy.example.engine.bean.LipLabels;
import com.heeexy.example.engine.bean.LipRead;
import com.heeexy.example.engine.bean.LiveVideo;
import com.heeexy.example.engine.bean.base.RequestBean;
import com.heeexy.example.engine.bean.base.ResultBean;
import com.heeexy.example.engine.service.FeatureService;
import com.heeexy.example.engine.service.VideoService;
import com.heeexy.example.engine.util.Base64Coder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yckj 测试类
 */
@RestController
@RequestMapping("/test")
public class TestController {

	static class OOMObject {

	}

	
	@RequestMapping("/get")
	private ResultBean<FeatureBean> get(RequestBean feature) {
		try {
			List<OOMObject> objects = new ArrayList<>();
			while (true) {
				objects.add(new OOMObject());
				System.out.println("正在添加对象");
			}
//			throw new OutOfMemoryError();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<FeatureBean>(e);
		}
	}

	/**
	 * <p>
	 * Title: exactFeature
	 * </p>
	 * <p>
	 * Description:图片提取特征接口
	 * </p>
	 */
	@RequestMapping("/exactFeature")
	private ResultBean<FeatureBean> exactFeature(RequestBean feature) {
		try {
			byte[] bytes = Base64Coder.decode(feature.getImg().replaceAll("[\r\n]+", ""));
			FeatureService featureService = FeatureService.newInstance();
			featureService.init();
			return new ResultBean<FeatureBean>(featureService.exactFeature(bytes, "201911260001"));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<FeatureBean>(e);
		}
	}

	/**
	 * <p>
	 * Title: computeSimilarity
	 * </p>
	 * <p>
	 * Description:两张图片相似度比对接口
	 * </p>
	 */
	@RequestMapping("/computeSimilarity")
	private ResultBean<FeatureBean> computeSimilarity(RequestBean CompareExto) {
		try {
			FeatureService featureService = FeatureService.newInstance();
			featureService.init();
			return new ResultBean<FeatureBean>(featureService.computeSimilarity(
					CompareExto.getImgA() == null ? null
							: Base64Coder.decode(CompareExto.getImgA().replaceAll("[\r\n]+", "")),
					CompareExto.getImgB() == null ? null
							: Base64Coder.decode(CompareExto.getImgB().replaceAll("[\r\n]+", "")),
					CompareExto.getFeatureA() == null ? null
							: Base64Coder.decode(CompareExto.getFeatureA().replaceAll("[\r\n]+", "")),
					CompareExto.getFeatureB() == null ? null
							: Base64Coder.decode(CompareExto.getFeatureB().replaceAll("[\r\n]+", "")),
					"201911260001"));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<FeatureBean>(e);
		}
	}

	@RequestMapping("/detectLiveNew")
	private ResultBean<FeatureBean> detectLiveNew(RequestBean live) {
		try {
			byte[] bytes = Base64Coder.decode(live.getImg().replaceAll("[\r\n]+", ""));
			FeatureService featureService = FeatureService.newInstance();
			featureService.init();
			return new ResultBean<FeatureBean>(featureService.detectLive(bytes, "201911260001"));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<FeatureBean>(e);
		}
	}

	/**
	 * <p>
	 * Description:获取唇语标签
	 * </p>
	 * 
	 * @param requestBean
	 * @return 标签
	 */
	@RequestMapping("/getlabel")
	private ResultBean<LipLabels> getlabel(RequestBean requestBean) {
		try {
			VideoService videoService = VideoService.newInstance();
			LipLabels labels = videoService.getlabel(requestBean.getiLabelLen(), "201911260001");
			return new ResultBean<LipLabels>(labels);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<LipLabels>(e);
		}
	}

	/**
	 * <p>
	 * Title: lipRecog
	 * </p>
	 * <p>
	 * Description:唇语识别
	 * </p>
	 * 
	 * @param lipRecog
	 * @return 识别结果
	 */
	@RequestMapping("/lipRecog")
	private ResultBean<LipRead> lipRecog(RequestBean lipRecog) {
		try {
			VideoService videoService = VideoService.newInstance();
			LipRead lipRead = videoService.lipRecog(lipRecog.getVideoPath(), lipRecog.getLabel(), lipRecog.getToken(),
					"201911260001");
			return new ResultBean<LipRead>(lipRead);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<LipRead>(e);
		}
	}

	public static String encodeBase64File(String path) throws Exception {
		File file = new File(path);
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		return clearBase64String(Base64Coder.encodeLines(buffer));
	}

	/**
	 * <p>
	 * Title: VideoRecog
	 * </p>
	 * <p>
	 * Description:视频识别
	 * </p>
	 * 
	 * @param lipRecog
	 * @return 识别结果
	 */
	@RequestMapping("/videoRecog")
	private ResultBean<LiveVideo> VideoRecog(RequestBean lipRecog) {
		try {
			VideoService videoService = VideoService.newInstance();
			LiveVideo liveVideo = videoService.videoRecog(lipRecog.getVideoPath(), "201911260001");
//			System.out.println(JSON.toJSONString(liveVideo));
			return new ResultBean<LiveVideo>(liveVideo);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<LiveVideo>(e);
		}
	}

	/**
	 * <p>
	 * Title: exactFeature
	 * </p>
	 * <p>
	 * Description:销毁算法实例
	 * </p>
	 */
	@RequestMapping("/destroyFeature")
	private ResultBean<Boolean> destroyFeature() {
		try {
			FeatureService featureService = FeatureService.newInstance();
			featureService.destroy();
			return new ResultBean<Boolean>(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<Boolean>(e);
		}
	}
	
	/**
	 * <p>
	 * Title: exactFeature
	 * </p>
	 * <p>
	 * Description:销毁算法实例
	 * </p>
	 */
	@RequestMapping("/destroyVideo")
	private ResultBean<Boolean> destroyVideo() {
		try {
			VideoService videoService = VideoService.newInstance();
			videoService.destroy();
			return new ResultBean<Boolean>(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<Boolean>(e);
		}
	}

	@RequestMapping("/initFeature")
	private ResultBean<Boolean> initFeature() {
		try {
			FeatureService featureService = FeatureService.newInstance();
			featureService.init();
			return new ResultBean<Boolean>(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<Boolean>(e);
		}
	}

	@RequestMapping("/initVideo")
	private ResultBean<Boolean> initVideo() {
		try {
			VideoService videoService = VideoService.newInstance();
			videoService.init();
			return new ResultBean<Boolean>(true);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<Boolean>(e);
		}
	}
	
	/**
	 * 将字节数组进行base64编码，然后替换换行和回车等字符
	 * 
	 * @param str
	 * @return
	 */
	public static String clearBase64String(String str) {
		return str.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "").replaceAll("\\s", "+");
	}

}
