package com.heeexy.example.engine.service;


import com.heeexy.example.engine.enums.CloudwalkEngineErrorCode;
import com.heeexy.example.engine.bean.FeatureBean;
import com.heeexy.example.engine.library.CwfaceLibrary;
import com.heeexy.example.engine.util.JnaUtils;
import com.heeexy.example.engine.util.NativeString;
import com.heeexy.example.engine.util.PropsUtil;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.concurrent.*;

/**
 * <p>
 * ClassName: FeatureService
 * </p>
 * Description:1:1特征服务<br/>
 *
 * @date 2019年11月26日 上午9:27:46
 * @author yckj0914
 * @version 1.0
 * @since JDK 1.7
 */
public class FeatureService {

	private static Logger logger = LoggerFactory.getLogger(FeatureService.class);

	/** 算法句柄 */
	private static long handler;

	/** 授权码 */
	private static String key;

	/** 人脸配置文件路径 */
	private static String configure;
	/** 活体线程数目 */
	private static int num_threads;
	/** 活体模型文件所在文件夹 */
	private static String model_dir;
	/** 活体攻击类型检测开关 */
	private static int det_mode;

	/** 算法进程数目 */
	private static int process_cnt;
	/** 线程池中任务队列的数量 */
	private static int queue_cnt;
	/** 线程存活时间 */
	private static int keepAliveTime;
	/** java接口调用超时时间 */
	private static int apiTimeoutTime;
	/** sdk接口调用超时时间 */
	private static int sdkTimeoutTime;
	/** 成功标志 */
	private static final int SUCCESS = 0;

	private static boolean initflag = false;

	static {
		key = PropsUtil.getProperty("key.conf");
		configure = PropsUtil.getProperty("face.lib.path.conf");

		model_dir = PropsUtil.getProperty("live.model.dir");
		num_threads = PropsUtil.getIntProperty("live.threads.num");
		det_mode = PropsUtil.getIntProperty("live.det.mode");

		process_cnt = PropsUtil.getIntProperty("config.process.cnt");
		queue_cnt = PropsUtil.getIntProperty("config.queue.cnt");
		keepAliveTime = PropsUtil.getIntProperty("config.keepalive.time");
		
		apiTimeoutTime = PropsUtil.getIntProperty("api.timeout.time");
		sdkTimeoutTime = PropsUtil.getIntProperty("sdk.timeout.time")*1000;
	}

	/** 线程池配置 */
	private ThreadPoolExecutor threadPool;

	private static FeatureService instance = null;

	public FeatureService() {

	}

	/**
	 * <p>
	 * Title: newInstance
	 * </p>
	 * <p>
	 * Description:初始化实例
	 * </p>
	 * 
	 * @return instance
	 */
	public static synchronized FeatureService newInstance() {
		if (null == instance) {
			instance = new FeatureService();
		}
		return instance;
	}

	/**
	 * <p>
	 * Title: init
	 * </p>
	 * <p>
	 * Description:初始化算法实例
	 * </p>
	 * 
	 */
	public synchronized void init() throws Exception {
		if (true == initflag) {
			return;
		}
		// 获取进程数
		int defaultcnt = (int) Math.ceil(Runtime.getRuntime().availableProcessors() * 0.7);
		process_cnt = process_cnt > 0 ? process_cnt : defaultcnt;
		// 初始化线程池
		threadPool = new ThreadPoolExecutor(process_cnt, process_cnt, keepAliveTime, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(queue_cnt));
		// 初始化算法
		LongByReference handle = new LongByReference();
		int result = CwfaceLibrary.INSTANCE.CWFaceEngine_Open(handle, process_cnt,
				new NativeString(configure).getPointer(), new NativeString(model_dir).getPointer(), num_threads,
				det_mode, new NativeString(key).getPointer());
		if (result == SUCCESS) {
			initflag = true;
			handler = handle.getValue();
			logger.info("初始化handler: {}", handler);
		} else {
			String msg= CloudwalkEngineErrorCode.from(result) == null ? "算法错误" : CloudwalkEngineErrorCode.from(result).getMsg();
			logger.info("系统初始化算法handler异常{}, 异常结果:{}",result,msg);
			throw new Exception("系统初始化算法handler异常, 异常结果:" + msg);
		}
	}

	/**
	 * <p>
	 * Title: destroy
	 * </p>
	 * <p>
	 * Description:销毁算法实例
	 * </p>
	 */
	public void destroy() {
		if (handler != 0) {
			int result = CwfaceLibrary.INSTANCE.CWFaceEngine_Close(handler);
			initflag = false;
			logger.info("销毁算法实例：{}, 算法实例：{}", result, handler);
		}
	}

	/**
	 * <p>
	 * Title: exactFeature
	 * </p>
	 * <p>
	 * Description:提取图片特征
	 * </p>
	 *
	 * @param picBytes
	 * @param flowNo
	 * @return 特征
	 * @throws Exception
	 */
	public FeatureBean exactFeature(byte[] picBytes, String flowNo) throws Exception {
		Future<FeatureBean> futures = threadPool.submit(new Callable<FeatureBean>() {
			@Override
			public FeatureBean call() throws Exception {
				return exactFeatureSo(picBytes, flowNo);
			}
		});

		try {
			if(apiTimeoutTime>0) {
				return futures.get(apiTimeoutTime, TimeUnit.SECONDS);
			}else {
				return futures.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("获取提特征结果失败");
		}
	}

	private FeatureBean exactFeatureSo(byte[] picBytes, String flowNo) throws Exception {
		FeatureBean featureBean = new FeatureBean();
		logger.info("提取图片特征,流水编号:{}", flowNo);
		if (StringUtils.isEmpty(flowNo) || flowNo.length() > 60) {
			flowNo = "流水号为空或长度过长";
		}
		Pointer feature = new Memory(1024 * 10);
		IntByReference feature_len = new IntByReference();
		float[] quality_score = new float[30];
		int result = CwfaceLibrary.INSTANCE.CWFaceEngine_Pic_GetFeature(handler, new NativeString(flowNo).getPointer(),
				JnaUtils.bytes2Pointer(picBytes), picBytes.length, feature, feature_len, quality_score, sdkTimeoutTime);
		featureBean.setResult(result);
		if (result == SUCCESS) {
			featureBean.setFeature(feature.getByteArray(0, feature_len.getValue()));
//			featureBean.setScore(quality_score);
		}
		featureBean.setDesc(CloudwalkEngineErrorCode.from(result) == null ? "算法错误" : CloudwalkEngineErrorCode.from(result).getMsg());
		return featureBean;
	}

	/**
	 * 两张图片/特征比对接口
	 * 
	 * @param imgA
	 * @param imgB
	 * @param featureA
	 * @param featureB
	 * @return 比对结果
	 * @throws Exception
	 */
	public FeatureBean computeSimilarity(byte[] imgA, byte[] imgB, byte[] featureA, byte[] featureB, String flowNo)
			throws Exception {
		FeatureBean result = new FeatureBean();
		if (featureA != null && featureB != null) {
			result = computeFeatureSimilarity(featureA, featureB, flowNo);
		} else {
			if (featureA != null) {
				result = computePicAndFeatureSimilarity(imgB, featureA, flowNo);
			}
			if (featureB != null) {
				result = computePicAndFeatureSimilarity(imgA, featureB, flowNo);
			}
			if (featureA == null && featureA == null) {
				result = computePicSimilarity(imgA, imgB, flowNo);
			}
		}
		return result;
	}

	/**
	 * 图片图片比对接口
	 *
	 * @param imgA
	 * @param imgB
	 * @return 比对结果
	 * @throws Exception
	 */
	public FeatureBean computePicSimilarity(byte[] imgA, byte[] imgB, String flowNo) throws Exception {
		Future<FeatureBean> futures = threadPool.submit(new Callable<FeatureBean>() {
			@Override
			public FeatureBean call() throws Exception {
				logger.info("两图片比对,流水编号:{}", flowNo);
				return computePicSimilaritySo(imgA, imgB, flowNo);
			}
		});
		try {
			if(apiTimeoutTime>0) {
				return futures.get(apiTimeoutTime, TimeUnit.SECONDS);
			}else {
				return futures.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("获取比对结果失败");
		}
	}

	/**
	 * 图片特征比对
	 *
	 * @param imgA
	 * @param featureB
	 * @param flowNo
	 * @return
	 */
	public FeatureBean computePicAndFeatureSimilarity(byte[] imgA, byte[] featureB, String flowNo) throws Exception {
		Future<FeatureBean> futures = threadPool.submit(new Callable<FeatureBean>() {
			@Override
			public FeatureBean call() throws Exception {
				logger.info("图片特征比对,流水编号:{}", flowNo);
				return computePicAndFeatureSimilaritySo(imgA, featureB, flowNo);
			}
		});
		try {
			if(apiTimeoutTime>0) {
				return futures.get(apiTimeoutTime, TimeUnit.SECONDS);
			}else {
				return futures.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("获取比对结果失败");
		}
	}

	/**
	 * 特征特征比对
	 *
	 * @param featureA
	 * @param featureB
	 * @param flowNo
	 * @return
	 * @throws Exception
	 */
	public FeatureBean computeFeatureSimilarity(byte[] featureA, byte[] featureB, String flowNo) throws Exception {
		Future<FeatureBean> futures = threadPool.submit(new Callable<FeatureBean>() {
			@Override
			public FeatureBean call() throws Exception {
				logger.info("两特征比对,流水编号:{}", flowNo);
				return computeSimilarityFeatureSo(featureA, featureB, flowNo);
			}
		});
		try {
			if(apiTimeoutTime>0) {
				return futures.get(apiTimeoutTime, TimeUnit.SECONDS);
			}else {
				return futures.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("获取比对结果失败");
		}
	}

	private FeatureBean computePicSimilaritySo(byte[] imgA, byte[] imgB, String flowNo) {
		if (StringUtils.isEmpty(flowNo) || flowNo.length() > 60) {
			flowNo = "流水号为空或长度过长";
		}
		FloatByReference score = new FloatByReference();
		int result = CwfaceLibrary.INSTANCE.CWFaceEngine_Pic_CompareSimilarity(handler,
				new NativeString(flowNo).getPointer(), JnaUtils.bytes2Pointer(imgA), imgA.length,
				JnaUtils.bytes2Pointer(imgB), imgB.length, score, sdkTimeoutTime);
		FeatureBean featureBean = new FeatureBean();
		featureBean.setResult(result);
		featureBean.setDesc(CloudwalkEngineErrorCode.from(result) == null ? "算法错误" : CloudwalkEngineErrorCode.from(result).getMsg());
		if (result == SUCCESS) {
			featureBean.setComputeScore(convertScore(score.getValue()));
		}
		return featureBean;
	}

	private FeatureBean computePicAndFeatureSimilaritySo(byte[] imgA, byte[] featureB, String flowNo) {
		if (StringUtils.isEmpty(flowNo) || flowNo.length() > 60) {
			flowNo = "流水号为空或长度过长";
		}
		FeatureBean featureBean = new FeatureBean();
		FloatByReference score = new FloatByReference();
		int result = CwfaceLibrary.INSTANCE.CWFaceEngine_Pic_Feature_CompareSimilarity(handler,
				new NativeString(flowNo).getPointer(), JnaUtils.bytes2Pointer(imgA), imgA.length,
				JnaUtils.bytes2Pointer(featureB), featureB.length, score, sdkTimeoutTime);
		featureBean.setResult(result);
		featureBean.setDesc(CloudwalkEngineErrorCode.from(result) == null ? "算法错误" : CloudwalkEngineErrorCode.from(result).getMsg());
		if (result == SUCCESS) {
			featureBean.setComputeScore(convertScore(score.getValue()));
		}
		return featureBean;
	}

	private FeatureBean computeSimilarityFeatureSo(byte[] featureA, byte[] featureB, String flowNo) {
		if (StringUtils.isEmpty(flowNo) || flowNo.length() > 60) {
			flowNo = "流水号为空或长度过长";
		}
		FeatureBean featureBean = new FeatureBean();
		FloatByReference score = new FloatByReference();
		int result = CwfaceLibrary.INSTANCE.CWFaceEngine_Feature_CompareSimilarity(handler,
				new NativeString(flowNo).getPointer(), JnaUtils.bytes2Pointer(featureA),
				JnaUtils.bytes2Pointer(featureB), featureA.length, score, sdkTimeoutTime);

		featureBean.setResult(result);
		featureBean.setDesc(CloudwalkEngineErrorCode.from(result) == null ? "算法错误" : CloudwalkEngineErrorCode.from(result).getMsg());
		if (result == SUCCESS) {
			featureBean.setComputeScore(convertScore(score.getValue()));
		}
		return featureBean;
	}

	/**
	 * <p>
	 * Title: detectLive
	 * </p>
	 * <p>
	 * Description:图片活体检测
	 * </p>
	 * 
	 * @param imgBytes
	 * @return 检测结果
	 * @throws Exception
	 */
	public FeatureBean detectLive(byte[] imgBytes, String flowNo) throws Exception {
		Future<FeatureBean> futures = threadPool.submit(new Callable<FeatureBean>() {
			@Override
			public FeatureBean call() throws Exception {
				logger.info("图片活体检测,流水编号:{}", flowNo);
				return antiHack(imgBytes, flowNo);
			}
		});
		try {
			if(apiTimeoutTime>0) {
				return futures.get(apiTimeoutTime, TimeUnit.SECONDS);
			}else {
				return futures.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("获取提图片活体检测结果失败");
		}
	}

	private FeatureBean antiHack(byte[] imgBytes, String flowNo) {
		FeatureBean featureBean = new FeatureBean();
		String log_dir = "";
		FloatByReference score = new FloatByReference();
		if (StringUtils.isEmpty(flowNo) || flowNo.length() > 60) {
			flowNo = "流水号为空或长度过长";
		}
		int result = CwfaceLibrary.INSTANCE.CWFaceEngine_AntiHack(handler, JnaUtils.bytes2Pointer(flowNo.getBytes()),
				new NativeString(log_dir).getPointer(), JnaUtils.bytes2Pointer(imgBytes), imgBytes.length, score,
				sdkTimeoutTime);
		featureBean.setResult(result);
		featureBean.setDesc(CloudwalkEngineErrorCode.from(result) == null ? "算法错误" : CloudwalkEngineErrorCode.from(result).getMsg());
		featureBean.setComputeScore(convertScore(score.getValue()));
		logger.info("静默视频识别,返回分数:{}", convertScore(score.getValue()));
		return featureBean;
	}

	/**
	 * <p>
	 * Title: convertScore
	 * </p>
	 * <p>
	 * Description:转换分数
	 * </p>
	 * 
	 * @param score
	 * @return 百分制，保留两位小数的分数
	 */
	private float convertScore(float score) {
		BigDecimal bigScore = new BigDecimal(score * 100);
		return bigScore.setScale(2, BigDecimal.ROUND_DOWN).floatValue();
	}

}
