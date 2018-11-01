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


// 云检索 
public class BaiduMapCloudSearchTest {
	@Test
	// 周边检索
	public void demo1() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(
				"http://api.map.baidu.com/geosearch/v3/nearby?ak=vqrmNcocSFyezmi5fnuoIiCsEQGyC9P8&geotable_id=194192&q=钟楼&location=108.95378,34.267939&radius=5000");

		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
		System.out.println(EntityUtils.toString(httpEntity));
	}

	@Test
	// 本地检索
	public void demo2() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(
				"http://api.map.baidu.com/geosearch/v3/local?region=北京&ak=zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf&geotable_id=153944&location=116.647367,39.899552&radius=1000");

		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}

	@Test
	// 云地理编码
	public void demo3() throws IOException {
			String address = URLEncoder.encode("好嫂子", "utf-8");
			String url = "http://api.map.baidu.com/cloudgc/v1?geotable_id=153944&ak=zbLsuDDL4CS2U0M4KezOZZbGUY9iWtVf&address="
					+ address;
			System.out.println(url);

			HttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		System.out.println(EntityUtils.toString(httpEntity));
	}
}
