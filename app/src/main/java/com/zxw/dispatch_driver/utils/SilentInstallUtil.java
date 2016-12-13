package com.zxw.dispatch_driver.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * author：CangJie on 2016/8/31 10:10
 * email：cangjie2016@gmail.com
 */
public class SilentInstallUtil {

    /**
     * 执行具体的静默安装逻辑，需要手机ROOT。
     * @param path
     *          要安装的apk文件的路径
     * @return 安装成功返回true，安装失败返回false。
     */
    public boolean install(String path){
    	String[] args = { "pm", "install", "-r", path };  
    	boolean result = false;  
    	ProcessBuilder processBuilder = new ProcessBuilder(args);  
    	Process process = null;  
    	InputStream errIs = null;  
    	InputStream inIs = null;  
    	try {  
    	    ByteArrayOutputStream baos = new ByteArrayOutputStream();  
    	    int read = -1;  
    	    process = processBuilder.start();  
    	    errIs = process.getErrorStream();  
    	    while ((read = errIs.read()) != -1) {  
    	        baos.write(read);  
    	    }  
    	    inIs = process.getInputStream();  
    	    while ((read = inIs.read()) != -1) {  
    	        baos.write(read);  
    	    }  
    	    byte[] data = baos.toByteArray();  
    	    result = true;  
    	} catch (IOException e) {  
    	    e.printStackTrace();  
    	} catch (Exception e) {  
    	    e.printStackTrace();  
    	} finally {  
    	    try {  
    	        if (errIs != null) {  
    	            errIs.close();  
    	        }  
    	        if (inIs != null) {  
    	            inIs.close();  
    	        }  
    	    } catch (IOException e) {  
    	        e.printStackTrace();  
    	    }  
    	    if (process != null) {  
    	        process.destroy();  
    	    }  
    	}  
    	return result; 
    }
}
