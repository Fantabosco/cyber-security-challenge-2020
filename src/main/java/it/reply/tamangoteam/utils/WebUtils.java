package it.reply.tamangoteam.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebUtils {

	private static final Logger log = LogManager.getLogger();

	private WebUtils() {
	}

	public static String doGet(String urlAddress, Map<String, String> queryParameters) {
		URL url;
		try {
			url = new URL(urlAddress);
		} catch (MalformedURLException e) {
			log.error("Invalid URL", e);
			return null;
		}
		HttpURLConnection con;
		try {
			con = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			log.error("Cannot connect to URL", e);
			return null;
		}
		try {
			con.setRequestMethod("GET");
		} catch (ProtocolException e) {
			log.error("Cannot set GET method", e);
			return null;
		}
		con.setDoOutput(true);
		try (DataOutputStream out = new DataOutputStream(con.getOutputStream())) {
			out.writeBytes(getParamsString(queryParameters));
			out.flush();

		} catch (IOException e) {
			log.error("Cannot execute request", e);
			return null;
		}

		try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			int statusCode =  con.getResponseCode();
			if(statusCode != 200) {
				log.warn("Response with code: {}", statusCode);
			}
			String inputLine;
			StringBuilder content = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			con.disconnect();
			return content.toString();
		} catch (IOException e) {
			log.error("Cannot read response", e);
			return null;
		}
	}

	private static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();

		for (Map.Entry<String, String> entry : params.entrySet()) {
			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			result.append("&");
		}

		String resultString = result.toString();
		return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
	}

}
