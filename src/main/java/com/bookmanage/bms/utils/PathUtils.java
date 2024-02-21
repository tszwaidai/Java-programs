package com.bookmanage.bms.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 获取当前类加载器加载类的根路径
 * */
public class PathUtils {
    public static String getClassLoadRootPath() {
        String path = "";
        try {
            String prePath = URLDecoder.decode(PathUtils.class.getClassLoader().getResource("").getPath(),"utf-8").replace("/target/classes", "");
            String osName = System.getProperty("os.name");
            if (osName.toLowerCase().startsWith("mac")) {
                // 苹果
                path = prePath.substring(0, prePath.length() - 1); //去掉路径末尾的斜杠。
            } else if (osName.toLowerCase().startsWith("windows")) {
                // windows
                path = prePath.substring(1, prePath.length() - 1); //去掉路径开头和末尾的斜杠。
            } else if(osName.toLowerCase().startsWith("linux") || osName.toLowerCase().startsWith("unix")) {
                // unix or linux
                path = prePath.substring(0, prePath.length() - 1); //去掉路径末尾的斜杠。
            } else {
                path = prePath.substring(1, prePath.length() - 1);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return path;
    }

}
