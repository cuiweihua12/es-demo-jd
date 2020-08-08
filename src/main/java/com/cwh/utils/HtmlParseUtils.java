package com.cwh.utils;

import com.cwh.pojo.Context;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: es-demo-jd
 * @description:
 * @author: cuiweihua
 * @create: 2020-08-05 17:00
 */
@Component
public class HtmlParseUtils {

    public static List<Context> JDParse(String kerword) throws Exception {
        ArrayList<Context> list = new ArrayList<>();

        String url = "https://search.jd.com/Search?keyword="+kerword;
        Document document = Jsoup.parse(new URL(url), 30000);
        Element element = document.getElementById("J_goodsList");
        Elements li = element.getElementsByTag("li");

        Context context = null;
        for (Element element1 : li) {
            String img = element1.getElementsByTag("img").attr("src");
            String price = element1.getElementsByClass("p-price").eq(0).text();
            String title = element1.getElementsByClass("p-name").eq(0).text();
            String shop = element1.getElementsByClass("curr-shop").attr("title");
            context = new Context();
            context.setImg(img);
            context.setPrice(price);
            context.setTitle(title);
            context.setShop(shop);
            list.add(context);
        }
        return list;
    }

}
