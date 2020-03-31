package com.heeexy.example.engine.library;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;

/**
 * <p>ClassName: CwfaceLibrary</p>
 * Description:云从人脸算法SDK<br/>
 * @date 2019年12月1日 下午9:29:19 
 * @author yckj0914
 * @version 1.0
 * @since JDK 1.7
 */ 
public interface CwfaceLibrary extends Library {
	public static final String JNA_LIBRARY_NAME = "cwface_sdk";
	public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(CwfaceLibrary.JNA_LIBRARY_NAME);
	public static final CwfaceLibrary INSTANCE = (CwfaceLibrary)Native.loadLibrary(CwfaceLibrary.JNA_LIBRARY_NAME, CwfaceLibrary.class);
	/** <i>native declaration : cwface_sdk.h</i> */
	public static final int FG_ERR_OK = (int)0;
	/** <i>native declaration : cwface_sdk.h</i> */
	public static final int FG_ERR_NO_MEMORY = (int)-1;
	/** <i>native declaration : cwface_sdk.h</i> */
	public static final int FG_ERR_INVALID_PARAM = (int)-2;
	/** <i>native declaration : cwface_sdk.h</i> */
	public static final int FG_ERR_DATA_TOO_LONG = (int)-3;
	/** <i>native declaration : cwface_sdk.h</i> */
	public static final int FG_ERR_SYS = (int)-4;
	/** <i>native declaration : cwface_sdk.h</i> */
	public static final int FG_ERR_ENGINE = (int)-5;
	/** <i>native declaration : cwface_sdk.h</i> */
	public static final int FG_ERR_BUSSY = (int)-6;
	/** <i>native declaration : cwface_sdk.h</i> */
	public static final int FG_ERR_TIMEOUT = (int)-7;
	/** <i>native declaration : cwface_sdk.h</i> */
	public static final int FG_ERR_CONFIG_FILE = (int)-8;
	/** <i>native declaration : cwface_sdk.h</i> */
	public static final int FG_ERR_CREATE_PROCESS = (int)-9;
	/** <i>native declaration : cwface_sdk.h</i> */
	public static final int FG_ERR_PROCESS_TOO_MANY = (int)-10;
	/** <i>native declaration : cwface_sdk.h</i> */
	public static final int FG_ERR_LICENCE = (int)-11;
	/** <i>native declaration : cwface_sdk.h</i> */
	public static final int FG_ERR_IMG = (int)-12;
	/** <i>native declaration : cwface_sdk.h</i> */
	public static final int FG_ERR_NO_FACE = (int)-13;
	/** <i>native declaration : cwface_sdk.h</i> */
	public static final int FG_ERR_FEA_LEN_INVALID = (int)-14;
	
	int CWFaceEngine_Open(LongByReference p_handle, int process_cnt, Pointer configure, Pointer anti_hack_model_dir, int anti_hack_thread_cnt, int anti_hack_mode, Pointer cw_key_licence);
	/**
	 * Function:   CWFaceEngine_Close();
<br>
	 * Description:  Close face engine.
<br>
	 * Parameters:
<br>
	 * handle --  opened handle of the engine.
<br>
	 * return:
<br>
	 * 0 -- No error
<br>
	 * others-- Error<br>
	 * Original signature : <code>int CWFaceEngine_Close(long long)</code><br>
	 * <i>native declaration : cwface_sdk.h:52</i>
	 */
	int CWFaceEngine_Close(long handle);
	/**
	 * Function:   CWFaceEngine_GetFeatureLength();<br>
	 * Description:  get length face feature.<br>
	 * Parameters:<br>
	 * handle --   receive the handle of the facego engine if opened successfully<br>
	 * feature_len    -- buffer to receive length of feature.<br>
	 * timeout_ms -- api timeout, in miliseconds<br>
	 * return:<br>
	 * 0 -- No error<br>
	 * others-- Error<br>
	 * Original signature : <code>int CWFaceEngine_GetFeatureLength(long long, int*, int)</code><br>
	 * <i>native declaration : cwface_sdk.h:67</i><br>
	 */
	int CWFaceEngine_GetFeatureLength(long handle, IntByReference feature_len, int timeout_ms);
	/**
	 * Function:   CWFaceEngine_CompareSimilarity();
<br>
	 * Description:  compare two face image.
<br>
	 * Parameters:
<br>
	 * handle --   receive the handle of the facego engine if opened successfully
<br>
	 * task_num -- task number, string. unique number.
<br>
	 * data_pic_1    	 -- data buffer of pic 1, jpg binary
<br>
	 * pic_1_len    -- pic1 len.
<br>
	 * data_pic_2  	 -- data buffer of pic 2, jpg binary
<br>
	 * pic_2_len    -- pic2 len.
<br>
	 * score   -- receive similarity result.
<br>
	 * timeout_ms -- api timeout, in miliseconds
<br>
	 * return:
<br>
	 * 0 -- No error
<br>
	 * others-- Error<br>
	 * Original signature : <code>int CWFaceEngine_Pic_CompareSimilarity(long long, char*, unsigned char*, int, unsigned char*, int, float*, int)</code><br>
	 * <i>native declaration : cwface_sdk.h:72</i><br>
	 */
	int CWFaceEngine_Pic_CompareSimilarity(long handle, Pointer task_num, Pointer data_pic_1, int pic_1_len, Pointer data_pic_2, int pic_2_len, FloatByReference score, int timeout_ms);
	
	/**
	 * Function:   CWFaceEngine_Feature_CompareSimilarity();
<br>
	 * Description:  compare two feature similarity.
<br>
	 * Parameters:
<br>
	 * handle --   receive the handle of the facego engine if opened successfully
<br>
	 * task_num -- task number, string. unique number.
<br>
	 * data_fea_1    	 -- data buffer of feature 1, jpg binary
<br>
	 * data_fea_2  	 -- data buffer of feature 2, jpg binary
<br>
	 * fea_len    -- feature len.
<br>
	 * score   -- receive similarity result.
<br>
	 * timeout_ms -- api timeout, in miliseconds
<br>
	 * return:
<br>
	 * 0 -- No error
<br>
	 * others-- Error<br>
	 * Original signature : <code>int CWFaceEngine_Feature_CompareSimilarity(long long, char*, unsigned char*, unsigned char*, int, float*, int)</code><br>
	 * <i>native declaration : cwface_sdk.h:92</i><br>
	 */
	int CWFaceEngine_Feature_CompareSimilarity(long handle, Pointer task_num, Pointer data_fea_1, Pointer data_fea_2, int fea_len, FloatByReference score, int timeout_ms);
	
	/**
	 * Function:   CWFaceEngine_Pic_Feature_CompareSimilarity();<br>
	 * Description:  compare face image and face feature similarity.<br>
	 * Parameters:<br>
	 * handle --   receive the handle of the facego engine if opened successfully<br>
	 * task_num -- task number, string. unique number.<br>
	 * data_pic    	 -- data buffer of picture, jpg binary<br>
	 * pic_len  -- picture length<br>
	 * data_fea  	 -- data buffer of feature,binary<br>
	 * fea_len    -- feature len.<br>
	 * score   -- receive similarity result.<br>
	 * timeout_ms -- api timeout, in miliseconds<br>
	 * return:<br>
	 * 0 -- No error<br>
	 * others-- Error<br>
	 * Original signature : <code>int CWFaceEngine_Pic_Feature_CompareSimilarity(long long, char*, unsigned char*, int, unsigned char*, int, float*, int)</code><br>
	 * <i>native declaration : cwface_sdk.h:133</i><br>
	 */
	int CWFaceEngine_Pic_Feature_CompareSimilarity(long handle, Pointer task_num, Pointer data_pic, int pic_len, Pointer data_fea, int fea_len, FloatByReference score, int timeout_ms);
	/**
	 * Function:   CWFaceEngine_GetFeature();
<br>
	 * Description:  get face feature of the image. only one face valid.
<br>
	 * Parameters:
<br>
	 * handle --   receive the handle of the facego engine if opened successfully
<br>
	 * task_num -- task number, string. unique number.
<br>
	 * data_pic   -- data of pic, jpg binary
<br>
	 * len    -- pic len.
<br>
	 * feature   -- buffer to receive the feature
<br>
	 * feature_len    -- buffer to receive length of feature.
<br>
	 * score - buffer to receive score list,  buffer lenght at least 30 * sizeof(float).
<br>
	 * timeout_ms -- api timeout, in miliseconds
<br>
	 * return:
<br>
	 * 0 -- No error
<br>
	 * others-- Error<br>
	 * Original signature : <code>int CWFaceEngine_Pic_GetFeature(long long, char*, unsigned char*, int, unsigned char*, int*, float*, int)</code><br>
	 * <i>native declaration : cwface_sdk.h:113</i><br>
	 */
	int CWFaceEngine_Pic_GetFeature(long handle, Pointer task_num, Pointer data_pic, int pic_len, Pointer feature, IntByReference feature_len, float[] score, int timeout_ms);
	/**
	 * Function:   CWFaceEngine_GetPicIsAlive);<br>
	 * Description:  check pic face is hack or not.<br>
	 * Parameters:<br>
	 * handle --   receive the handle of the facego engine if opened successfully<br>
	 * task_num -- task number, string. unique number.<br>
	 * log_dir  -- anti-hack log directory.<br>
	 * data_pic   -- data of pic, jpg or png binary<br>
	 * len    -- pic len.<br>
	 * hack_type  - returned hack type.<br>
	 * timeout_ms -- api timeout, in miliseconds<br>
	 * return:<br>
	 * 0 -- No error<br>
	 * others-- Error<br>
	 * Original signature : <code>int CWFaceEngine_AntiHack(long long, char*, char*, unsigned char*, int, float*, int)</code><br>
	 * <i>native declaration : cwface_sdk.h:176</i><br>
	 */
	int CWFaceEngine_AntiHack(long handle, Pointer task_num, Pointer log_dir, Pointer data_pic, int pic_len, FloatByReference hack_type, int timeout_ms);

}
