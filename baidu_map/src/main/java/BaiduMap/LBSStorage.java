package BaiduMap;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhongChaoYuan
 * @create 2018-09-01 20:23
 **/
public class LBSStorage {

    //创建表geoyable
    @Test
    public void demo1() throws IOException {
        //创建http对象
        HttpClient httpClient = HttpClients.createDefault();
        //创建请求参数
        HttpPost httpPost = new HttpPost("http://api.map.baidu.com/geodata/v3/geotable/create");
        //绑定参数
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("name", "mytable1"));
        nameValuePairs.add(new BasicNameValuePair("geotype", "1"));
        nameValuePairs.add(new BasicNameValuePair("is_published", "1"));
        nameValuePairs.add(new BasicNameValuePair("ak", "vqrmNcocSFyezmi5fnuoIiCsEQGyC9P8"));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, Charsets.UTF_8));
        //发送请求
        HttpResponse httpResponse = httpClient.execute(httpPost);
        //打印数据
        HttpEntity httpEntity = httpResponse.getEntity();
        System.out.println(EntityUtils.toString(httpEntity));

    }

    //查询表
    @Test
    public void demo2() throws IOException {
        //创建http对象
        HttpClient httpClient = HttpClients.createDefault();
        //创建请求参数
        HttpGet httpGet = new HttpGet("http://api.map.baidu.com/geodata/v3/geotable/list?ak=vqrmNcocSFyezmi5fnuoIiCsEQGyC9P8");
        //发送请求
        HttpResponse httpResponse = httpClient.execute(httpGet);
        //打印数据
        HttpEntity httpEntity = httpResponse.getEntity();
        System.out.println(EntityUtils.toString(httpEntity));
    }
}
