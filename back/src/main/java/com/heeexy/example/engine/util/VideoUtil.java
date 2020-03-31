package com.heeexy.example.engine.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * <p>
 * ClassName: VideoUtil
 * </p>
 * Description:视频工具类<br/>
 * 
 * @date 2019年11月30日 下午4:58:02
 * @author yckj0914
 * @version 1.0
 * @since JDK 1.7
 */
public class VideoUtil {
	/**
	 * <p>
	 * Title: saveVideo
	 * </p>
	 * <p>
	 * Description:保存视频
	 * </p>
	 * 
	 * @param path
	 * @param base64
	 * @throws Exception
	 */
	public static void saveVideo(String path, String base64) throws Exception {
		byte[] b = decodeBase64(base64); // 对mp4进行解码
		for (int i = 0; i < b.length; ++i) {
			if (b[i] < 0) {// 调整异常数据
				b[i] += 256;
			}
		}
		OutputStream out = new FileOutputStream(path);
		out.write(b);
		out.flush();
		out.close();
	}

	/**
	 * <p>
	 * Title: removeVideo
	 * </p>
	 * <p>
	 * Description:清除视频
	 * </p>
	 * 
	 * @param path
	 */
	public static void removeVideo(String path) {
		File f = new File(path);
		if (f.exists()) {
			f.delete();
		}
	}

	/**  
	 * <p>Title: getVideoPath</p>  
	 * <p>Description: </p>  
	 * @return  视频路径
	 */  
	public static String getVisdeoPath() {
		// 创建存放视频的文件夹
		String videoPath = System.getProperty("user.dir") + File.separator + "video";
		File f = new File(videoPath);
		if (!f.exists()) {
			f.mkdirs();
		}
		String videoFilePath = f + File.separator + UUIDUtils.get32LowerCase() + ".mp4"; // 指定视频生成的路径
		return videoFilePath;
	}

	/**
	 * 对base64编码后的字符串进行反解
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] decodeBase64(String str) {
		return Base64Coder.decode(clearBase64String(str));
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
