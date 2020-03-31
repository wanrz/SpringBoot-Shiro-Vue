package com.heeexy.example.engine.enums;


/**
 * <p>ClassName: LibReadResultCode</p>
 * Description:云从1:1人脸、视频引擎错误码<br/>
 * @date 2019年11月10日 下午12:45:38 
 * @author yckj0914
 * @version 1.0
 * @since JDK 1.7
 */ 
public enum CloudwalkEngineErrorCode {
	/*  多进程SDK返回错误码  */
	FG_ERR_OK(0, "成功"),
	FG_ERR_NO_MEMORY(-1, "内存不足"),
	FG_ERR_INVALID_PARAM(-2, "参数无效"),
	FG_ERR_DATA_TOO_LONG(-3, "输入数据太长"),
	FG_ERR_SYS_ERR(-4, "系统调用失败"),
	FG_ERR_ENGINE(-5, "调用算法失败"),
	FG_ERR_BUSSY(-6, "繁忙，无法响应当前请求"),
	FG_ERR_TIMEOUT(-7, "超时"),
	FG_ERR_CONFIG_FILE(-8, "读取算法配置文件失败"),
	FG_ERR_CREATE_PROCESS(-9, "创建进程失败"),
	FG_ERR_PROCESS_TOO_MANY(-10, "创建进程超出允许数量"),
	FG_ERR_LICENCE(-11, "授权失败"),
	FG_ERR_IMG(-12, "图像解码失败"),
	FG_ERR_NO_FACE(-13, "图像中无人脸"),
	FG_ERR_FEA_LEN_INVALID(-14, "特征长度无效"),
	
	/*  人脸识别算法SDK错误码  */
	CW_FACE_NOT_DETECT_FACE(1 ,"未检测到人脸"),
	CW_FACE_DECODE_FAIL(3 ,"图片解码失败"),
	CW_FACE_NO_INTERSECTION(5 ,"人脸框范围和图片无交集"),
	
	/*  图片防hack算法SDK错误码  */
	CW_SVR_SIL_LIV_WAITE_FRAME(200, "等待下一帧"),
    CW_SVR_SIL_LIV_ERR_CREATE_HANDLE(300, "未创建句柄"),
    CW_SVR_SIL_LIV_ERR_FREE_HANDLE(301, "未释放句柄"),
	CW_SVR_SIL_LIV_ERR_UNAUTHORIZED(302, "未授权"),
	CW_SVR_SIL_LIV_ERR_MODEL_INIT(303, "模型未初始化"),
    CW_SVR_SIL_LIV_ERR_EMPTY_IMAGE(304, "输入图片为空"),
    CW_SVR_SIL_LIV_ERR_NUM_LMK(305, "关键点数目错误"),
    CW_SVR_SIL_LIV_ERR_EMPTY_LMK(306, "关键点为空"),
    CW_SVR_SIL_LIV_ERR_LMK_OUT_OF_IMAGE(307, "关键点超出图像尺寸"),
    CW_SVR_SIL_LIV_ERR_UNCONT_FRAMES(308, "不连续的图像帧"),
    CW_SVR_SIL_LIV_ERR_ALG(309, "提取特征失败"),
    CW_SVR_SIL_LIV_ERR_BASE64(310, "输入base64信息错误"),
    CW_SVR_SIL_LIV_ERR_MAKE_DIR(311, "创建文件夹失败"),
    CW_SVR_SIL_LIV_ERR_VIDEO_DECODE(312, "视频解码失败"),
    CW_SVR_SIL_LIV_ERR_SHORT_VIDEO(313, "视频太短"),
    CW_SVR_SIL_LIV_ERR_FACE_DET(314, "人脸检测失败"),
    CW_SVR_SIL_LIV_ERR_SMALL_BUFFER(315, "获取最佳人脸的buffer太小"),
    CW_SVR_SIL_LIV_ERR_DISABLED(316, "开关未打开"),
	CW_SVR_SIL_LIV_ERR_PARAM_INIT(317, "batch输入参数未初始化"),
	CW_SVR_SIL_LIV_ERR_NOT_IMPLEMENTED(318, "功能未实现"),
	
	/*  视频防hack算法SDK错误码  */
	IS_LIVE(-500, "活体"), 
	NOT_LIVE(-501, "非活体"),
	RUN_ERROR(-502, "运行错误"),
	
	/*  唇语识别算法SDK错误码  */
	LIP_LABEL_NOT_VALID_ATTACK(-1003, "标签不是我们后端生成的或者标签过期"),
	LIP_FAILED_WITH_ERROR_CODE(-1002, "唇语识别错误"),
	LIP_FAILED_BUT_NOT_ATTACK(-1001, "唇语识别没有通过"),
	LIP_ALIVE(-1000, "活体"),
	LIP_MOUTH_CUT_ATTACK(-999, "抠嘴攻击"),
	LIP_BORDER_ATTACK(-998, "边框攻击"),
	LIP_VIDEO_REPLAY_ATTACK(-997, "视频回放攻击"),
	LIP_HALF_FACE_ATTACK(-996, "半边脸攻击"),
	LIP_CHANGE_FACE_ATTACK(-995, "换人脸攻击或人脸丢失严重"),
	LIP_EYE_CUT_ATTACK(-994, "抠眼攻击"),
	ERROR_100(100, "模型未加载"),
	ERROR_101(101, "视频文件不存在或解码失败"),
	ERROR_102(102, "输入的唇语信息错误"),
	ERROR_103(103, "返回原始图片的空间太小"),
	ERROR_104(104, "返回对齐人脸的空间太小"),
	ERROR_105(105, "最佳人脸编码失败"),
	ERROR_106(106, "最佳人脸空间不足"),
	ERROR_107(107, "最佳人脸编码方式不支持");
	

	CloudwalkEngineErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private int code;
	private String msg;

	public int getValue() {
		return this.code;
	}

	public String getMsg() {
		return this.msg;
	}

	public static CloudwalkEngineErrorCode from(int value) {
		for (CloudwalkEngineErrorCode item : CloudwalkEngineErrorCode.values()) {
			if (item.getValue()==value) {
				return item;
			}
		}
		return null;
	}

}
