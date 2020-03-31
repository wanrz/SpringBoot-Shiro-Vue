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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;

/**
 * @description: 测试类
 * @className: IcbcController
 * @date 2020/3/5 12:36
 * @author YCKJ0914
 * @version 1.0
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/icbc")
public class IcbcController {

    private static Logger logger = LoggerFactory.getLogger(IcbcController.class);

    /**
     * @description: 图片提取特征接口 TODO
     * @date 2020/3/5 12:34
     * @param requestBean
     * @return com.cloudwalk.bean.base.ResultBean<com.cloudwalk.bean.FeatureBean>
     */
    @RequestMapping("/exactFeature")
    private ResultBean<FeatureBean> exactFeature(@RequestBody RequestBean requestBean) {
        try {
            long startTime = System.currentTimeMillis();
            byte[] bytes = Base64Coder.decode(requestBean.getImg().replaceAll("[\r\n]+", ""));
            FeatureService featureService = FeatureService.newInstance();
//			featureService.init();
            logger.info("实例化响应时间####:{}", System.currentTimeMillis() - startTime);
            FeatureBean featureBean = featureService.exactFeature(bytes, "201911260001");
//			System.out.println("@@@@@" + Arrays.toString(featureBean.getFeature()));
//			System.out.println("@@@@@" + featureBean.getFeature().length);
//			System.out.println("@@@@@" + featureBean.getDesc());
            logger.info("####提特征接口全过程总耗时####:{}", System.currentTimeMillis() - startTime);
            return new ResultBean<FeatureBean>(featureBean);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean<FeatureBean>(e);
        }
    }

    /**
     * @description: 两张图片相似度比对接口 TODO
     * @date 2020/3/5 12:38
     * @param requestBean
     * @return com.cloudwalk.bean.base.ResultBean<com.cloudwalk.bean.FeatureBean>
     */
    @RequestMapping("/computeSimilarity")
    private ResultBean<FeatureBean> computeSimilarity(@RequestBody RequestBean requestBean) {
        try {
            long startTime = System.currentTimeMillis();
            FeatureService featureService = FeatureService.newInstance();
            FeatureBean featureBean = featureService.computeSimilarity(
                    requestBean.getImgA() == null ? null : Base64Coder.decode(requestBean.getImgA().replaceAll("[\r\n]+", "")),
                    requestBean.getImgB() == null ? null : Base64Coder.decode(requestBean.getImgB().replaceAll("[\r\n]+", "")),
                    requestBean.getFeatureA() == null ? null : Base64Coder.decode(requestBean.getFeatureA().replaceAll("[\r\n]+", "")),
                    requestBean.getFeatureB() == null ? null : Base64Coder.decode(requestBean.getFeatureB().replaceAll("[\r\n]+", "")),
                    "201911260001");
            logger.info("####两张图片相似度比对接口全过程总耗时####:{}", System.currentTimeMillis() - startTime);
            return new ResultBean<FeatureBean>(featureBean);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean<FeatureBean>(e);
        }
    }

    @RequestMapping("/detectLiveNew")
    /**
     * @description: 图片防hack接口 TODO
     * @date 2020/3/5 12:39
     * @param live
     * @return com.cloudwalk.bean.base.ResultBean<com.cloudwalk.bean.FeatureBean>
     */
    private ResultBean<FeatureBean> detectLiveNew(@RequestBody RequestBean live) {
        try {
            long startTime = System.currentTimeMillis();
            byte[] bytes = Base64Coder.decode(live.getImg().replaceAll("[\r\n]+", ""));
            FeatureService featureService = FeatureService.newInstance();
            FeatureBean featureBean = featureService.detectLive(bytes, "201911260001");
            logger.info("####图片防hack接口全过程总耗时####:{}", System.currentTimeMillis() - startTime);
            return new ResultBean<FeatureBean>(featureBean);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean<FeatureBean>(e);
        }
    }

    /**
     * @description: 获取唇语标签 TODO
     * @date 2020/3/5 12:39
     * @param requestBean
     * @return com.cloudwalk.bean.base.ResultBean<com.cloudwalk.bean.LipLabels>
     */
    @RequestMapping("/getlabel")
    @CrossOrigin
    private ResultBean<LipLabels> getlabel(@RequestBody RequestBean requestBean) {
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
     * @description: 唇语识别 TODO
     * @date 2020/3/5 12:40
     * @param lipRecog
     * @return com.cloudwalk.bean.base.ResultBean<com.cloudwalk.bean.LipRead>
     */
    @RequestMapping("/lipRecog")
    @CrossOrigin
    private ResultBean<LipRead> lipRecog(@RequestBody RequestBean lipRecog) {
        try {
            long startTime = System.currentTimeMillis();
            VideoService videoService = VideoService.newInstance();
            LipRead lipRead = videoService.lipRecog(lipRecog.getVideoPath(), lipRecog.getLabel(), lipRecog.getToken(),
                    "201911260001");
            logger.info("####唇语识别接口全过程总耗时####:{}", System.currentTimeMillis() - startTime);
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
     * @description: 销毁算法实例 TODO
     * @date 2020/3/5 12:44
     * @param
     * @return com.cloudwalk.bean.base.ResultBean<java.lang.Boolean>
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
     * @description: 视频识别 TODO
     * @date 2020/3/5 12:40
     * @param lipRecog
     * @return com.cloudwalk.bean.base.ResultBean<com.cloudwalk.bean.LiveVideo>
     */
    @RequestMapping("/videoRecog")
    private ResultBean<LiveVideo> VideoRecog(@RequestBody RequestBean lipRecog) {
        try {
            long startTime = System.currentTimeMillis();
            VideoService videoService = VideoService.newInstance();
            LiveVideo liveVideo = videoService.videoRecog(lipRecog.getVideoPath(), "201911260001");
            logger.info("####视频识别接口全过程总耗时####:{}", System.currentTimeMillis() - startTime);
            return new ResultBean<LiveVideo>(liveVideo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean<LiveVideo>(e);
        }
    }

    /**
     * @description: 销毁算法实例 TODO
     * @date 2020/3/5 12:41
     * @param
     * @return com.cloudwalk.bean.base.ResultBean<java.lang.Boolean>
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

    /**
     * @description: 初始化人脸算法实例 TODO
     * @date 2020/3/5 12:41
     * @param
     * @return com.cloudwalk.bean.base.ResultBean<java.lang.Boolean>
     */
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

    /**
     * @description: 初始化视频算法实例 TODO
     * @date 2020/3/5 12:42
     * @param
     * @return com.cloudwalk.bean.base.ResultBean<java.lang.Boolean>
     */
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
     * @description: 将字节数组进行base64编码，然后替换换行和回车等字符 TODO
     * @date 2020/3/5 12:43
     * @param str
     * @return string
     */
    public static String clearBase64String(String str) {
        return str.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "").replaceAll("\\s", "+");
    }

}
