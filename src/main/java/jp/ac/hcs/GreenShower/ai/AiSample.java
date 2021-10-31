package jp.ac.hcs.GreenShower.ai;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AiSample {

	public static void main(String[] args) {

		// ** 郵便番号検索API リクエストURL */
		String URL = "https://api.a3rt.recruit.co.jp/proofreading/v2/typo?apikey=%s&sentence=%s";
		String KEY = "";
		String SENTENCE = "私の得意分野は画像処利と自然言語処理です。";
		
		String json = getResult(String.format(URL, KEY, SENTENCE));
		
		Map<String, Object> result = jsonStringToMap(json);
		System.out.println(result);

	}

	public static String getResult(String urlString) {
		String result = "";
		try {
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String tmp = "";
			while ((tmp = in.readLine()) != null) {
				result += tmp;
			}
			in.close();
			con.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * JSON文字列をMapに
	 * @param json json文字列
	 * @return json文字列を読み込んだMapオブジェクト。失敗した場合はnull
	 */
	public static Map<String, Object> jsonStringToMap(String json) {
		Map<String, Object> map = null;

		ObjectMapper mapper = new ObjectMapper();

		try {
			map = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}
}