package com.yhh.practice.springboot.magic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

/**
 * 12  * 下载相关的工具类
 * 13  *
 * 14  * @author xwer
 * 15  *
 * 16
 */
public class DownLoadUtils {

    /**
     * 20      * 下载图片工具
     * 21      *
     * 22      * @param urlString
     * 23      *            图片链接地址
     * 24      * @param filename
     * 25      *            图片的文件名字
     * 26      * @param savePath
     * 27      *            图片保存的路径
     * 28      * @throws Exception
     * 29
     */
    public static void download(String urlString, String filename, String savePath) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        // 设置请求头
        con.addRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)");
        // 设置请求超时为5s
        con.setConnectTimeout(5 * 1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }

    /**
     * 62      * 截取真实文件名
     * 63      *
     * 64      * @param fileName
     * 65      * @return
     * 66
     */
    public static String subFileName(String fileName) {
        // 查找最后一个 \出现位置
        int index = fileName.lastIndexOf("\\");
        if (index == -1) {
            return fileName;
        }
        return fileName.substring(index + 1);
    }

    /**
     * 77      * 获得随机UUID文件名
     * 78      *
     * 79      * @param fileName
     * 80      * @return
     * 81
     */
    public static String generateRandonFileName(String fileName) {
        // 获得扩展名
        String ext = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID().toString().replace("-", "") + ext;
    }
}