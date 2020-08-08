package com.cwh;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @program: es-demo-jd
 * @description:
 * @author: cuiweihua
 * @create: 2020-08-07 20:28
 */
public class ImgTest {

    @Test
    public void test(){
        String img = "F://upload//苹果mac os catalina夜晚风景壁纸4K/5K/8K高清壁纸";
        String path = img.substring(0, img.lastIndexOf("/"));
        System.out.println(path);

    }
    @Test
    public void reptileImg2() throws Exception{
        Integer index = 2;
        while (true){
            index++;
            String html = "index_"+index+".htm";
            String url = "http://www.netbian.com/dongman/";
            if (index >=2 ){
                url = url+html;
            }
            Document document = Jsoup.parse(new URL(url), 3000);
            Element element = document.getElementById("main");
            Elements img = element.getElementsByTag("img");
            HashMap<String, String> map = new HashMap<>();
            for (Element element1 : img) {
                map.put(element1.attr("alt"),element1.attr("src"));
            }
            String filePath = "F:\\upload\\dongman";
            File file = new File(filePath);
            if (!file.exists()){
                file.mkdirs();
            }
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, String> entry = iterator.next();
                //创建链接
                URL url1 = new URL(entry.getValue());
                //打开链接
                URLConnection connection = url1.openConnection();
                connection.connect();
                //获取网络输入流
                InputStream inputStream = connection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                //获取图片后缀
                String filenameExtension = StringUtils.getFilenameExtension(entry.getValue());
                //判断路径是否时/结尾
                if (!filePath.endsWith(File.separator)){
                    filePath += File.separator;
                }
                //将文件名中 "/" 全部替换为空字符串
                String replaceAll = entry.getKey().replaceAll("/", "");
                System.out.println(filePath+replaceAll);
                //创建输出流
                FileOutputStream fileOutputStream = new FileOutputStream(filePath+replaceAll+"."+filenameExtension);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                byte[] bytes = new byte[4096];
                int length = bufferedInputStream.read(bytes);
                //将文件循环写入硬盘
                while (length != -1){
                    bufferedOutputStream.write(bytes,0,length);
                    length = bufferedInputStream.read(bytes);
                }
                bufferedOutputStream.close();
                bufferedInputStream.close();
                connection.connect();
            }
            if (index == 4){break;}
        }
    }

}
