package com.heeexy.example.engine.service;

import com.heeexy.example.engine.enums.CloudwalkEngineErrorCode;
import com.heeexy.example.engine.bean.LipLabels;
import com.heeexy.example.engine.bean.LipRead;
import com.heeexy.example.engine.bean.LiveVideo;
import com.heeexy.example.engine.library.CwvideoLibrary;
import com.heeexy.example.engine.util.Base64Coder;
import com.heeexy.example.engine.util.NativeString;
import com.heeexy.example.engine.util.PropsUtil;
import com.heeexy.example.engine.util.VideoUtil;
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
 * <p>ClassName: VideoService</p>
 * Description:视频检测服务<br/>
 * @date 2019年12月6日 下午11:51:32 
 * @author yckj0914
 * @version 1.0
 * @since JDK 1.7
 */ 
public class VideoService {

	private static Logger logger = LoggerFactory.getLogger(VideoService.class);

	/** 算法句柄 */
	private static long handler;


	/** 授权码 */
	private static String key;
	/** 唇语配置文件路径 */
	private static String lip_path_conf;
	/** 唇语算法线程数 */
	private static int lip_thread_cnt;
	/** 返回最佳人脸后缀 */
	private static String lip_encoder;
	/** 标签过期时间 */
	private static int lip_labeltime;
	/** 视频配置文件路径 */
	private static String video_path_conf;

	/** 算法进程数目 */
	private static int process_cnt;
	/** 线程池中任务队列的数量 */
	private static int queue_cnt;
	/** 线程存活时间 */
	private static long keepAliveTime;
	/** java接口调用超时时间 */
	private static long apiTimeoutTime;
	/** sdk接口调用超时时间 */
	private static int sdkTimeoutTime;
	/** 成功标志 */
	private static final int SUCCESS = 0;

	private static boolean initflag = false;

	static {
		key = PropsUtil.getProperty("key.conf");

		lip_path_conf = PropsUtil.getProperty("lip.path.conf");
		lip_thread_cnt = PropsUtil.getIntProperty("lip.thread.cnt");
		lip_encoder = PropsUtil.getProperty("lip.encoder");
		lip_labeltime = PropsUtil.getIntProperty("lip.labeltime");
		
		video_path_conf = PropsUtil.getProperty("video.path.conf");

		process_cnt = PropsUtil.getIntProperty("config.process.cnt");
		queue_cnt = PropsUtil.getIntProperty("config.queue.cnt");
		keepAliveTime = PropsUtil.getIntProperty("config.keepalive.time");
		
		apiTimeoutTime = PropsUtil.getIntProperty("api.timeout.time");
		sdkTimeoutTime = PropsUtil.getIntProperty("sdk.timeout.time")*1000;
	}
	
	/** 线程池配置 */
	private ThreadPoolExecutor threadPool;

	private static VideoService instance = null;

	public VideoService() {

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
	public static synchronized VideoService newInstance() {
		if (null == instance) {
			instance = new VideoService();
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
	 * @return
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
		int result=CwvideoLibrary.INSTANCE.CWVLEngine_Open(handle, process_cnt, new NativeString(lip_path_conf).getPointer(), lip_thread_cnt,
				new NativeString(video_path_conf).getPointer(), new NativeString(key).getPointer());
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
			int result = CwvideoLibrary.INSTANCE.CWVLEngine_Close(handler);
			initflag = false;
			logger.info("销毁算法实例：{}, 算法实例：{}", result, handler);
		}
	}
	
	

	/**
	 * <p>
	 * Title: getlabel
	 * </p>
	 * <p>
	 * Description:获取唇语标签
	 * </p>
	 * 
	 * @param iLabelLen
	 * @return 唇语标签
	 * @throws Exception
	 */
	public LipLabels getlabel(int iLabelLen, String flowNo) throws Exception {
		Future<LipLabels> futures = threadPool.submit(new Callable<LipLabels>() {
			@Override
			public LipLabels call() throws Exception {
				return getlabelSo(iLabelLen, flowNo);
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
			throw new Exception("获取唇语标签结果失败");
		}
	}
	

	private LipLabels getlabelSo(int iLabelLen,String flowNo) throws Exception {
		logger.info("获取唇语标签,流水编号:{}", flowNo);
		if(StringUtils.isEmpty(flowNo)||flowNo.length()>60) {
			flowNo="流水号为空或长度过长";
		}
		LipLabels lipRead = new LipLabels();
		Pointer labels = new Memory(iLabelLen+1);
		Pointer token = new Memory(64);
		int result = CwvideoLibrary.INSTANCE.CWVLEngine_GenerateLitLabel(handler,labels, iLabelLen, token,sdkTimeoutTime);
		lipRead.setLabel(new String(labels.getByteArray(0, iLabelLen), "UTF-8"));
		lipRead.setToken(new String(token.getByteArray(0, 16), "UTF-8"));
		lipRead.setCode(result);
		return lipRead;
	}
	
	/**
	 * <p>
	 * Title: lip
	 * </p>
	 * <p>
	 * Description:唇语识别接口
	 * </p>
	 * 
	 * @param videoPath
	 * @param label
	 * @param token
	 * @param flowNo
	 * @return 识别结果
	 * @throws Exception
	 */
	public LipRead lipRecog(String videoPath, String label, String token, String flowNo) throws Exception {
		Future<LipRead> futures = threadPool.submit(new Callable<LipRead>() {
			@Override
			public LipRead call() throws Exception {
				return lipReadSo(videoPath, label, token, flowNo);
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
			throw new Exception("获取唇语识别结果失败");
		}
	}
	
	
	private LipRead lipReadSo(String videoPath, String label, String token, String flowNo) throws Exception {
		logger.info("唇语视频识别,流水编号:{}", flowNo);
		if(StringUtils.isEmpty(flowNo)||flowNo.length()>60) {
			flowNo="流水号为空或长度过长";
		}
		LipRead lipRead = new LipRead();

		FloatByReference confidence = new FloatByReference();

		IntByReference face_buf_size = new IntByReference();
		face_buf_size.setValue(1024 * 1024 * 4);

		Pointer face_buf = new Memory(face_buf_size.getValue() + 1);

		int result =CwvideoLibrary.INSTANCE.CWVLEngine_IsAlive(handler, new NativeString(flowNo).getPointer(), new NativeString(videoPath).getPointer(), new NativeString(label).getPointer(), 
				new NativeString(token).getPointer(), face_buf, face_buf_size, new NativeString(lip_encoder).getPointer(), confidence, lip_labeltime, sdkTimeoutTime);
		
		lipRead.setCode(result);
		if (result == CloudwalkEngineErrorCode.LIP_ALIVE.getValue()) {
			lipRead.setFaceImage(VideoUtil.clearBase64String(Base64Coder.encodeLines(face_buf.getByteArray(0, face_buf_size.getValue()))));
		}
		lipRead.setErrorInfo(CloudwalkEngineErrorCode.from(result) == null ? "算法错误" : CloudwalkEngineErrorCode.from(result).getMsg());
		lipRead.setComputeScore(confidence.getValue());
		logger.info("唇语视频识别,返回分数:{}", lipRead.getComputeScore());
		return lipRead;
	}
	
	
 
	/**  
	 * <p>Title: VideoRecog</p>  
	 * <p>Description:静默视频识别接口 </p>  
	 * @param videoPath
	 * @param flowNo
	 * @return 识别结果
	 * @throws Exception  
	 */  
	public LiveVideo videoRecog(String videoPath, String flowNo) throws Exception {
		Future<LiveVideo> futures = threadPool.submit(new Callable<LiveVideo>() {
			@Override
			public LiveVideo call() throws Exception {
				return livenessDetectSo(videoPath, flowNo);
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
			throw new Exception("获取视频识别结果失败");
		}
	}
	

    private LiveVideo livenessDetectSo(String videoPath, String flowNo) {
    	logger.info("静默视频识别,流水编号:{}", flowNo);
    	if(StringUtils.isEmpty(flowNo)||flowNo.length()>60) {
			flowNo="流水号为空或长度过长";
		}
        FloatByReference confidence = new FloatByReference();
        IntByReference image_len = new IntByReference();
        image_len.setValue(1024 * 1024 * 4);

		Pointer data_img = new Memory(image_len.getValue() + 1);
		
		int result =CwvideoLibrary.INSTANCE.CWVLEngine_SVLivenessDetect(handler, new NativeString(flowNo).getPointer(), new NativeString(videoPath).getPointer(), confidence, data_img, image_len, sdkTimeoutTime);

        LiveVideo liveVideo = new LiveVideo();
        liveVideo.setCode(result);
        liveVideo.setErrorInfo(CloudwalkEngineErrorCode.from(result) == null ? "算法错误" : CloudwalkEngineErrorCode.from(result).getMsg());
        if (result == CloudwalkEngineErrorCode.IS_LIVE.getValue()) {
        	liveVideo.setFaceImage(VideoUtil.clearBase64String(Base64Coder.encodeLines(data_img.getByteArray(0, image_len.getValue()))));
        }
        liveVideo.setComputeScore(convertScore(confidence.getValue()));
        logger.info("静默视频识别,返回分数:{}", liveVideo.getComputeScore());
        return liveVideo;
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
