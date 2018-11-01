package BaiduMap;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

// web开放服务 
public class BaiduMapWebServiceTest {
	@Test
	// Place Suggestion API
	public void demo1() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(
				"http://api.map.baidu.com/place/v2/suggestion?ak=zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf&region=全国&q=和平&output=json");

		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}

	@Test
	// 地理编码 api
	public void demo2() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		String address = URLEncoder.encode("北京市海淀区上地十街10号", "utf-8");
		HttpGet httpGet = new HttpGet(
				"http://api.map.baidu.com/geocoder/v2/?ak=zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf&output=json&address="
						+ address);

		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}

	@Test
	// 路线规划距离和行驶时间
	public void demo3() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(
				"http://api.map.baidu.com/routematrix/v2/riding?ak=zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf&output=json&origins=40.056878,116.30815&destinations=40.063597,116.364973");

		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}

	@Test
	// 路线规划
	public void demo4() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(
				"http://api.map.baidu.com/direction/v2/transit?ak=zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf&output=json&origin=40.056878,116.30815&destination=40.063597,116.364973");

		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}

	@Test
	// ip高精度定位
	public void demo5() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(
				"http://api.map.baidu.com/highacciploc/v1?ak=zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf&qterm=pc&callback_type=json&coord=bd09ll");

		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}

	@Test
	// 转换坐标
	public void demo6() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(
				"http://api.map.baidu.com/geoconv/v1/?ak=zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf&output=json&coords=114.21892734521,29.575429778924");

		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}
}
