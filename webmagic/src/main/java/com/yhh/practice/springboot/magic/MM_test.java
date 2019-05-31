package com.yhh.practice.springboot.magic;
import java.util.regex.Pattern;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

public class MM_test {
    public static void main(String[] args) {
        //图片的存放路径,PiPline需要用到
        String fileStorePath = "E:\\test999\\";

        //过滤网页的正则  http://www.meizitu.com/a/more_1.html
        String urlPattern = "https://blog.csdn.net[a-z]/[0-9]{1,4}.html";
        //自定义的解析器核心
        MM_Processor mmSprider = new MM_Processor("https://blog.csdn.net/", urlPattern);

        //设置一些种子链接
        String[] urls = {"https://blog.csdn.net",
                "https://blog.csdn.net/tanga842428/article/details/53306338",

        };
        //启动爬虫
        Spider.create(mmSprider).addUrl(urls)
                .setScheduler(new FileCacheQueueScheduler("E:\\test\\"))
                .addPipeline(new MM_Pipeline(fileStorePath))
                .thread(10)
                .run();
    }
}