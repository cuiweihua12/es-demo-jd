package com.cwh;

import com.alibaba.fastjson.JSON;
import com.cwh.pojo.User;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class EsDemoJdApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    //创建索引
    public void createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("test");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);

    }

    @Test
    //增加文档
    public void seaceDoc() throws IOException {
        User user = new User( "崔蔚华", new Date());
        IndexRequest indexRequest = new IndexRequest("test");
        indexRequest.id("2");
        indexRequest.timeout(TimeValue.timeValueSeconds(1));
        indexRequest.source(JSON.toJSONString(user),XContentType.JSON);
        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(index.status());
        System.out.println(index.toString());
    }

    @Test
    //查看索引是否存在
    public void indexIsExit() throws IOException {
        GetRequest request = new GetRequest("test","1");
        GetResponse documentFields = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        System.out.println(documentFields.getSource());
        boolean exists = documentFields.isExists();
        System.out.println(exists);
    }
    @Test
    //更新文档
    public void updateDoc() throws IOException {
        UpdateRequest request = new UpdateRequest("test", "1");
        User user = new User( "周帅丞", new Date());
        request.doc(JSON.toJSONString(user),XContentType.JSON);
        UpdateResponse update = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(update.status());
    }

    @Test
    //删除文档
    public void deleteDoc() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("test", "xMlEvXMBlwI5GLlEnlg9");
        deleteRequest.timeout("1s");
        DeleteResponse delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete.status());
    }

    @Test
    //批量插入文档
    public void buldDoc() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        ArrayList<User> users = new ArrayList<>();
        users.add( new User( "周帅丞", new Date()));
        users.add( new User("周帅丞", new Date()));
        users.add( new User("周帅丞", new Date()));
        users.add( new User("周帅丞", new Date()));
        users.add( new User("周帅丞", new Date()));
        users.add( new User("周帅丞", new Date()));
        users.add( new User("周帅丞", new Date()));
        for (int i = 0; i < users.size(); i++) {
            bulkRequest.add(new IndexRequest("test").id(i+1+"").source(JSON.toJSONString(users.get(i)),XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.status());
        System.out.println(bulk.hasFailures());


    }

    @Test
    //查询
    public void searchDoc() throws IOException {
        SearchRequest test = new SearchRequest("test");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        searchSourceBuilder.query(matchAllQueryBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(2);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        test.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(test, RequestOptions.DEFAULT);
        search.getHits().forEach(System.out::println);
        System.out.println("========================================");
        System.out.println(JSON.toJSONString(search.getHits()));
        System.out.println("========================================");
        for (SearchHit documentFields : search.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
        }

    }

    @Test
    public void JDtest() throws Exception {

        String url = "https://search.jd.com/Search?keyword=水果";
        Document document = Jsoup.parse(new URL(url), 30000);
        Element element = document.getElementById("J_goodsList");
        Elements li = element.getElementsByTag("li");
        for (Element element1 : li) {
            /*String img = element1.getElementsByTag("img").attr("src");
            String price = element1.getElementsByClass("p-price").eq(0).text();
            String title = element1.getElementsByClass("p-name").eq(0).text();
            String shop = element1.getElementsByClass("curr-shop").attr("title");*/
            System.out.println(element1.getElementsByClass("p-commit").eq(0).outerHtml());
           /* System.out.println(img);
            System.out.println(price);
            System.out.println(title);
            System.out.println(shop);*/
        }
    }

    @Test
    public void reptileImg() throws Exception {
        String img_reg="<img.*src=(.*?)[^>]*?>";
        URL url = new URL("http://www.netbian.com/fengjing/");
        URLConnection openConnection = url.openConnection();
        InputStream inputStream = openConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        StringBuffer buffer = new StringBuffer();
        while ((line =bufferedReader.readLine())!=null){
            buffer.append(line,0,line.length());
            buffer.append('\n');
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        Matcher matcher = Pattern.compile(img_reg).matcher(buffer.toString());
        List<String> list = new ArrayList<>();
        while (matcher.find()){
            list.add(matcher.group());
        }
        list.forEach(System.out::println);
    }

    @Test
    public void reptileImg2() throws Exception{
        String url = "http://www.netbian.com/fengjing/";
        Document document = Jsoup.parse(new URL(url), 3000);
        Element element = document.getElementById("main");
        Elements img = element.getElementsByTag("img");
        HashMap<String, String> map = new HashMap<>();
        for (Element element1 : img) {
//            System.out.println(element1.attr("src"));
//            System.out.println(element1.attr("alt"));
            map.put(element1.attr("alt"),element1.attr("src"));
        }
        String filePath = "F:\\upload\\";
        File file = null;

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
            if (filePath.endsWith(File.separator)){
                filePath += File.separator+entry.getKey();
            }
            file = new File(filePath);
            if (!file.exists()){
                file.mkdirs();
            }
            System.out.println(filePath+entry.getKey());
            //创建输出流
            FileOutputStream fileOutputStream = new FileOutputStream(filePath+entry.getKey()+"."+filenameExtension);
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
        System.out.println(file);
    }





}
