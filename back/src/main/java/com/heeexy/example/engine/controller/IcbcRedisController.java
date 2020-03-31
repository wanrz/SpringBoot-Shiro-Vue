package com.heeexy.example.engine.controller;


import com.heeexy.example.common.utils.RedisUtil;
import com.heeexy.example.engine.bean.FeatureBean;
import com.heeexy.example.engine.bean.LipLabels;
import com.heeexy.example.engine.bean.LipRead;
import com.heeexy.example.engine.bean.LiveVideo;
import com.heeexy.example.engine.bean.base.RequestBean;
import com.heeexy.example.engine.bean.base.ResultBean;
import com.heeexy.example.engine.enums.CloudwalkEngineErrorCode;
import com.heeexy.example.engine.service.FeatureService;
import com.heeexy.example.engine.service.VideoService;
import com.heeexy.example.engine.util.Base64Coder;
import com.heeexy.example.engine.util.PropsUtil;
import com.heeexy.example.engine.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author YCKJ0914
 * @version 1.0
 * @description: 测试类
 * @className: IcbcRedisController
 * @date 2020/3/5 11:41
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/icbcRedis")
public class IcbcRedisController {

    private static Logger logger = LoggerFactory.getLogger(IcbcRedisController.class);

    /**
     * redis工具类
     */
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 标签过期时间
     */
    private static int lip_labeltime = PropsUtil.getIntProperty("lip.labeltime");

    /**
     * @param requestBean
     * @param a
     * @return com.cloudwalk.bean.base.ResultBean<com.cloudwalk.bean.FeatureBean>
     * @description: 两张图片相似度比对接口
     * @date 2020/3/5 12:09
     */
    @RequestMapping("/computeSimilarity")
    private ResultBean<FeatureBean> computeSimilarity(@RequestBody RequestBean requestBean, String a) {
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

    /**
     * <p>
     * Title: detectLiveNew
     * </p>
     * <p>
     * Description:图片防hack接口
     * </p>
     *
     * @param live
     * @return
     */
    @RequestMapping("/detectLiveNew")
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
     * <p>
     * Description:获取唇语标签
     * </p>
     *
     * @param requestBean
     * @return 标签
     */

    @RequestMapping("/getlabel")
    @CrossOrigin
    private ResultBean<LipLabels> getlabel(@RequestBody RequestBean requestBean) {
        try {
            VideoService videoService = VideoService.newInstance();
            LipLabels labels = videoService.getlabel(requestBean.getiLabelLen(), "201911260001");

            //redis集群存储
            String uuid = UUIDUtils.get32LowerCase();
            //设置外部token=uuid+内部token，长度为32+16=48
            labels.setToken(uuid + labels.getToken());
            try {
                redisUtil.set(labels.getToken(), labels.getLabel(), lip_labeltime);
                logger.info("获取的标签信息token:{},label:{}", labels.getToken(), labels.getLabel());
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("获取标签catch到redis异常");
            }

            return new ResultBean<LipLabels>(labels);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean<LipLabels>(e);
        }
    }

    /**
     * @return com.cloudwalk.bean.base.ResultBean<com.cloudwalk.bean.FeatureBean>
     * @description: 图片提取特征接口
     * @param: [requestBean]
     * @date 2020/3/5 11:34
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
     * @return com.cloudwalk.bean.base.ResultBean<com.cloudwalk.bean.LipRead>
     * @description: 唇语识别
     * @param: [lipRecog]
     * @date 2020/3/5 11:44
     */
    @RequestMapping("/lipRecog")
    @CrossOrigin
    private ResultBean<LipRead> lipRecog(@RequestBody RequestBean lipRecog) {
        try {
            long startTime = System.currentTimeMillis();
            VideoService videoService = VideoService.newInstance();

            //redis校验
            //传入的token长度不够时,直接返回错误消息
            if (lipRecog.getToken().length() < 32) {
                return new ResultBean<LipRead>(CloudwalkEngineErrorCode.LIP_LABEL_NOT_VALID_ATTACK.getValue() + "", CloudwalkEngineErrorCode.LIP_LABEL_NOT_VALID_ATTACK.getMsg());
            }
            //从传入的48位外部token通过截取获16位取算法内部的token
            String token = lipRecog.getToken().substring(32, lipRecog.getToken().length());
            try {
                String cacheLable = (String) redisUtil.get(lipRecog.getToken());
                if (StringUtils.isEmpty(cacheLable)) {
                    return new ResultBean<LipRead>(CloudwalkEngineErrorCode.LIP_LABEL_NOT_VALID_ATTACK.getValue() + "", CloudwalkEngineErrorCode.LIP_LABEL_NOT_VALID_ATTACK.getMsg());
                }

                if (!lipRecog.getLabel().equals(cacheLable)) {
                    return new ResultBean<LipRead>(CloudwalkEngineErrorCode.LIP_LABEL_NOT_VALID_ATTACK.getValue() + "", CloudwalkEngineErrorCode.LIP_LABEL_NOT_VALID_ATTACK.getMsg());
                }
                //数据校验通过,删除缓存
                redisUtil.del(lipRecog.getToken());
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("唇语识别catch到redis异常");
            }

            //设置算法内部token
            lipRecog.setToken(token);
            LipRead lipRead = videoService.lipRecog(lipRecog.getVideoPath(), lipRecog.getLabel(), lipRecog.getToken(),
                    "201911260001");
            logger.info("####唇语识别接口全过程总耗时####:{}", System.currentTimeMillis() - startTime);
            return new ResultBean<LipRead>(lipRead);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBean<LipRead>(e);
        }
    }

    /**
     * @return
     * @description: TODO
     * @param:
     * @date 2020/3/5 11:46
     */
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return clearBase64String(Base64Coder.encodeLines(buffer));
    }

    /**
     * @description: 视频识别 TODO
     * @date 2020/3/5 13:07
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
     * @description: 将字节数组进行base64编码，然后替换换行和回车等字符 TODO
     * @date 2020/3/5 12:47
     * @param str
     * @return String
     */

    public static String clearBase64String(String str) {
        return str.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "").replaceAll("\\s", "+");
    }

}
