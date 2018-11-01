package BaiduMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
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

// 云存储
public class BaiduMapCloudStorageTest {

	// 创建表
	@Test
	public void demo1() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://api.map.baidu.com/geodata/v3/geotable/create");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("name", "mytable1"));
		nameValuePairs.add(new BasicNameValuePair("geotype", "1"));
		nameValuePairs.add(new BasicNameValuePair("is_published", "1"));
		nameValuePairs.add(new BasicNameValuePair("ak", "zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf"));

		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));

	}

	@Test
	// 查询表
	public void demo2() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(
				"http://api.map.baidu.com/geodata/v3/geotable/list?ak=zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf");

		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}

	@Test
	// 查询表结构
	public void demo3() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(
				"http://api.map.baidu.com/geodata/v3/geotable/detail?id=153944&ak=zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf");

		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}

	@Test
	// 创建列
	public void demo4() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://api.map.baidu.com/geodata/v3/column/create");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("geotable_id", "153944"));
		nameValuePairs.add(new BasicNameValuePair("name", "名称"));
		nameValuePairs.add(new BasicNameValuePair("key", "name"));
		nameValuePairs.add(new BasicNameValuePair("type", "3"));
		nameValuePairs.add(new BasicNameValuePair("max_length", "512"));
		nameValuePairs.add(new BasicNameValuePair("is_sortfilter_field", "0"));
		nameValuePairs.add(new BasicNameValuePair("is_search_field", "1"));
		nameValuePairs.add(new BasicNameValuePair("is_index_field", "1"));
		nameValuePairs.add(new BasicNameValuePair("is_unique_field", "1"));
		nameValuePairs.add(new BasicNameValuePair("ak", "zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf"));

		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}

	@Test
	// 查询列
	public void demo5() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(
				"http://api.map.baidu.com/geodata/v3/column/list?geotable_id=153944&ak=zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf");

		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}

	@Test
	// 查询指定id列
	public void demo6() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(
				"http://api.map.baidu.com/geodata/v3/column/detail?id=265695&geotable_id=153944&ak=zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf");

		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}

	@Test
	// 修改列
	public void demo7() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://api.map.baidu.com/geodata/v3/column/update");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id", "265695"));
		nameValuePairs.add(new BasicNameValuePair("geotable_id", "153944"));
		nameValuePairs.add(new BasicNameValuePair("name", "描述"));
		nameValuePairs.add(new BasicNameValuePair("key", "name"));
		nameValuePairs.add(new BasicNameValuePair("type", "3"));
		nameValuePairs.add(new BasicNameValuePair("max_length", "512"));
		nameValuePairs.add(new BasicNameValuePair("is_sortfilter_field", "0"));
		nameValuePairs.add(new BasicNameValuePair("is_search_field", "1"));
		nameValuePairs.add(new BasicNameValuePair("is_index_field", "1"));
		nameValuePairs.add(new BasicNameValuePair("is_unique_field", "1"));
		nameValuePairs.add(new BasicNameValuePair("ak", "zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf"));

		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8));
		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}

	@Test
	// 创建数据 (create poi)
	public void demo8() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://api.map.baidu.com/geodata/v3/poi/create");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("geotable_id", "153944"));
		nameValuePairs.add(new BasicNameValuePair("title", "好嫂子"));
		nameValuePairs.add(new BasicNameValuePair("address", "海淀区建材城西路xx号"));
		nameValuePairs.add(new BasicNameValuePair("latitude", "39.899552"));
		nameValuePairs.add(new BasicNameValuePair("longitude", "116.647367"));
		nameValuePairs.add(new BasicNameValuePair("coord_type", "3"));
		nameValuePairs.add(new BasicNameValuePair("ak", "zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf"));

		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}

	@Test
	// 查询指定条件数据（poi）
	public void demo9() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(
				"http://api.map.baidu.com/geodata/v3/poi/list?geotable_id=153944&ak=zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf");

		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}

	@Test
	// 查询指定id的数据（poi）
	public void demo10() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(
				"http://api.map.baidu.com/geodata/v3/poi/detail?id=1822675338&geotable_id=153944&ak=zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf");

		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}

	@Test
	// 修改数据 (update poi)
	public void demo11() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://api.map.baidu.com/geodata/v3/poi/update");

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id", "1822675338"));
		nameValuePairs.add(new BasicNameValuePair("geotable_id", "153944"));
		nameValuePairs.add(new BasicNameValuePair("title", "好嫂子"));
		nameValuePairs.add(new BasicNameValuePair("address", "海淀区建材城西路xx号"));
		nameValuePairs.add(new BasicNameValuePair("latitude", "40.066269"));
		nameValuePairs.add(new BasicNameValuePair("longitude", "116.353776"));
		nameValuePairs.add(new BasicNameValuePair("coord_type", "3"));
		nameValuePairs.add(new BasicNameValuePair("ak", "zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf"));

		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8));
		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}

	@Test
	public void demoX() {
		System.out.println("\u53c2\u6570\u5fc5\u9700");
	}
}
