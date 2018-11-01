package elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import dd.domain.Article;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.junit.Test;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author ZhongChaoYuan
 * @create 2018-09-05 14:32
 **/
public class ElasticsearchDemo {

    //建立文档
    @Test
    public void demo1() throws IOException {
        //创建服务器对象
        Client client = TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress
                        .getByName("127.0.0.1"), 9300));
        //描述json对象
        XContentBuilder builder =  XContentFactory
                .jsonBuilder()
                .startObject()
                .field("id",1)
                .field("name","贱人窑")
                .field("city","乾县")
                .endObject();
        //建立文档
        client.prepareIndex("blog1","article","1").setSource(builder).get();
        //关闭连接
        client.close();
    }

    //搜索文档
    @Test
    public void demo2() throws Exception {
        //创建服务器
        Client client = TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress
                        .getByName("127.0.0.1"), 9300));
        //查询数据
        SearchResponse searchResponse = client.prepareSearch("blog1")
                .setTypes("article")
                .setQuery(QueryBuilders.matchAllQuery())
                .get();
        //打印查询结果
        printResponse(searchResponse);
        //关闭服务器
        client.close();
    }

    //搜索文档 QueryBuilders.queryStringQuery("搜索内容")
    @Test
    public void demo3() throws Exception {
        //创建服务器
        Client client = TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress
                        .getByName("127.0.0.1"), 9300));
        //查询数据
        SearchResponse searchResponse = client.prepareSearch("blog1")
                .setTypes("article")
                //.setQuery(QueryBuilders.queryStringQuery("贱"))
                .setQuery(QueryBuilders.wildcardQuery("name","贱人"))
                .get();
        //打印查询结果
        printResponse(searchResponse);
        //关闭服务器
        client.close();
    }

    //索引操作
    @Test
    public void demo4() throws Exception {
        //创建服务器
        Client client = TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress
                        .getByName("127.0.0.1"), 9300));
        //创建索引
        client.admin().indices().prepareCreate("blog2").get();
        //删除索引
        //client.admin().indices().prepareDelete("blog2").get();
        //关闭服务器
        client.close();
    }

    //添加映射
    @Test
    public void demo5() throws Exception {
        //创建服务器
        Client client = TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress
                        .getByName("127.0.0.1"), 9300));
        //添加映射
        XContentBuilder builder = XContentFactory
                .jsonBuilder()
                .startObject().startObject("article").startObject("properties")
                .startObject("id").field("type","integer").field("store","yes").endObject()
                .startObject("title").field("type","string").field("store","yes").field("analyzer","ik").endObject()
                .startObject("content").field("type","string").field("store","yes").field("analyzer","ik").endObject()
                .endObject().endObject().endObject();

        PutMappingRequest mapping = Requests.putMappingRequest("blog2").type("article").source(builder);
        client.admin().indices().putMapping(mapping).get();

        //关闭服务器
        client.close();
    }

    //建立文档
    @Test
    public void demo6() throws IOException, ExecutionException, InterruptedException {
        //创建服务器对象
        Client client = TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress
                        .getByName("127.0.0.1"), 9300));
        //描述json对象
        Article article = new Article();
        article.setId(2);
        article.setTitle("搜索工作其实很快乐");
        article.setContent("我们希望我们的搜索解决方案要快，我们希望有一个零配置和一个完全免费的搜索模式，我们希望能够简单地使用JSON通过HTTP的索引数据，我们希望我们的搜索服务器始终可用，我们希望能够一台开始并扩展到数百，我们要实时搜索，我们要简单的多租户，我们希望建立一个云的解决方案。Elasticsearch旨在解决所有这些问题和更多的问题。");

        ObjectMapper objectMapper = new ObjectMapper();
        // 建立文档
        client.prepareIndex("blog2", "article", article.getId().toString())
                .setSource(objectMapper.writeValueAsString(article)).get();

        // 修改文档
        // client.prepareUpdate("blog2", "article", article.getId().toString())
        // .setDoc(objectMapper.writeValueAsString(article)).get();

        // 修改文档
        // client.update(
        // new UpdateRequest("blog2", "article", article.getId()
        // .toString()).doc(objectMapper.writeValueAsString(article))).get();

        // 删除文档
        // client.prepareDelete("blog2", "article", article.getId().toString()).get();

        // 删除文档
        //client.delete(
        //        new DeleteRequest("blog2", "article", article.getId()
        //                .toString())).get();
        //关闭连接
        client.close();
    }

    @Test
    // 批量查询100条记录
    public void demo7() throws IOException, InterruptedException,
            ExecutionException {
        // 创建连接搜索服务器对象
        Client client = TransportClient
                .builder()
                .build()
                .addTransportAddress(
                        new InetSocketTransportAddress(InetAddress
                                .getByName("127.0.0.1"), 9300));

        ObjectMapper objectMapper = new ObjectMapper();

        for (int i = 1; i <= 100; i++) {
            // 描述json 数据
            Article article = new Article();
            article.setId(i);
            article.setTitle(i + "搜索工作其实很快乐");
            article.setContent(i
                    + "我们希望我们的搜索解决方案要快，我们希望有一个零配置和一个完全免费的搜索模式，我们希望能够简单地使用JSON通过HTTP的索引数据，我们希望我们的搜索服务器始终可用，我们希望能够一台开始并扩展到数百，我们要实时搜索，我们要简单的多租户，我们希望建立一个云的解决方案。Elasticsearch旨在解决所有这些问题和更多的问题。");

            // 建立文档
            client.prepareIndex("blog2", "article", article.getId().toString())
                    .setSource(objectMapper.writeValueAsString(article)).get();
        }
        // 关闭连接
        client.close();
    }

    @Test
    // 分页搜索
    public void demo8() throws IOException {
        // 创建连接搜索服务器对象
        Client client = TransportClient
                .builder()
                .build()
                .addTransportAddress(
                        new InetSocketTransportAddress(InetAddress
                                .getByName("127.0.0.1"), 9300));
        // 搜索数据
        // get() === execute().actionGet()
        SearchRequestBuilder searchRequestBuilder = client
                .prepareSearch("blog2").setTypes("article")
                .setQuery(QueryBuilders.matchAllQuery());

        // 查询第2页数据，每页20条
        searchRequestBuilder.setFrom(20).setSize(20);

        SearchResponse searchResponse = searchRequestBuilder.get();
        printResponse(searchResponse);

        // 关闭连接
        client.close();
    }

    @Test
    // 高亮查询结果 处理 搜索
    public void demo9() throws IOException {
        // 创建连接搜索服务器对象
        Client client = TransportClient
                .builder()
                .build()
                .addTransportAddress(
                        new InetSocketTransportAddress(InetAddress
                                .getByName("127.0.0.1"), 9300));

        ObjectMapper objectMapper = new ObjectMapper();

        // 搜索数据
        SearchRequestBuilder searchRequestBuilder = client
                .prepareSearch("blog2").setTypes("article")
                .setQuery(QueryBuilders.termQuery("title", "搜索"));

        // 高亮定义
        searchRequestBuilder.addHighlightedField("title"); // 对title字段进行高亮
        searchRequestBuilder.setHighlighterPreTags("<em>"); // 前置元素
        searchRequestBuilder.setHighlighterPostTags("</em>");// 后置元素

        SearchResponse searchResponse = searchRequestBuilder.get();

        SearchHits hits = searchResponse.getHits(); // 获取命中次数，查询结果有多少对象
        System.out.println("查询结果有：" + hits.getTotalHits() + "条");
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit searchHit = iterator.next(); // 每个查询对象

            // 将高亮处理后内容，替换原有内容 （原有内容，可能会出现显示不全 ）
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            HighlightField titleField = highlightFields.get("title");

            // 获取到原有内容中 每个高亮显示 集中位置 fragment 就是高亮片段
            Text[] fragments = titleField.fragments();
            String title = "";
            for (Text text : fragments) {
                title += text;
            }
            // 将查询结果转换为对象
            Article article = objectMapper.readValue(
                    searchHit.getSourceAsString(), Article.class);

            // 用高亮后内容，替换原有内容
            article.setTitle(title);

            System.out.println(article);
        }
        // 关闭连接
        client.close();
    }
    //获取所有结果的方法
    private void printResponse(SearchResponse searchResponse) {
        SearchHits hits = searchResponse.getHits();
        System.out.println("结果有" + hits.totalHits() + "条");
        //迭代
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            SearchHit fields = iterator.next(); //查询每一个对象
            System.out.println(fields.getSourceAsString()); //字符串形式打印
            System.out.println(fields.getSource().get("name"));
        }
    }
}
