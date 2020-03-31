package com.heeexy.example.engine.util;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

/**
 * <p>ClassName: JnaUtils</p>
 * Description:JNA工具类<br/>
 * @date 2019年12月1日 下午2:28:11 
 * @author yckj0914
 * @version 1.0
 * @since JDK 1.7
 */ 
public class JnaUtils {
    public static Pointer bytes2Pointer(byte[] data){
        Pointer pointer=new Memory(data.length);
        pointer = new Memory(data.length);
        pointer.write(0, data, 0, data.length);
        return pointer;
    }
    
    public static byte boolean2byte(boolean flag_save){
		byte a = 0x00;
		if (flag_save) {
			a = 0x01;
		}
		return a;
    }   
    public static boolean byte2boolean(byte flag_save){
		byte a = 0x01;
		if (flag_save==a) {
			return true;
		}
		return false;
    } 
}
