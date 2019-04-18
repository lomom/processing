package com.kin.processing.util;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetHttpImages {

	public static InputStream getHttpsFile(String destUrl) {

		try {
			System.out.println(destUrl);
			URL url = new URL(destUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setDoOutput(true);// 设置允许输出
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			return conn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
