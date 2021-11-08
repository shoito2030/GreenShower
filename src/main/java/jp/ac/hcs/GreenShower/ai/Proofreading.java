package jp.ac.hcs.GreenShower.ai;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 誤字脱字チェックを行う recruit社の校正APIを活用する 
 * @see https://a3rt.recruit.co.jp/product/proofreadingAPI/
 */
public class Proofreading {
	
	/** API リクエストURL */
	private final static String URL = "https://api.a3rt.recruit.co.jp/proofreading/v2/typo?apikey=%s&sentence=%s";
	
	/* APIキー */
	private final static String KEY = "";
	
	private final static String MARKUP_HTML_OPEN = "<span class='mark font-weight-bold text-danger' style='background-color:yellow'>";
	private final static String MARKUP_HTML_CLOSE = "</span>";
	
	public static void main(String[] args) {
		proofreading("先生に報告したのですが返信がなかったため、念のためスラックにメッセージを遺しまし");
	}

	/**
	 * 文章を校正APIに渡し結果を得る
	 * @param sentence メモや備考
	 * @return 異常あり - 加工済みのHTML文字列 異常なし - 加工なしの文章
	 */
	public static String proofreading(String sentence) {
		if(KEY.isBlank() || KEY.isEmpty()) {
			return sentence;
		}
		
		String json = getResult(String.format(URL, KEY, sentence));
		
		Map<String, Object> result = jsonStringToMap(json);
		String status = String.valueOf(result.get("status"));
		
		if(status.equals("1")) {
			String checkedSentence = (String) result.get("checkedSentence");
			return toHtml(checkedSentence);
		} else {
			return sentence;	
		}
	}

	/**
	 * APIに問い合わせ結果を得る
	 * @param urlString リクエストを含むURL文字列
	 * @return result json形式のレスポンス
	 */
	private static String getResult(String urlString) {
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
	 * JSON文字列をMapに変換
	 * @param json json文字列
	 * @return json文字列を読み込んだMapオブジェクト。失敗した場合はnull
	 */
	private static Map<String, Object> jsonStringToMap(String json) {
		Map<String, Object> map = null;

		ObjectMapper mapper = new ObjectMapper();

		try {
			map = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}
	
	/**
	 * 校正結果をHTML文字列に変換し、怪しい箇所を強調表示する
	 * @param checkedSentence  校正済み文字列
	 * @return html
	 */
	private static String toHtml(String checkedSentence) {
		Pattern pattern = Pattern.compile("<<");
        Matcher matcher = pattern.matcher(checkedSentence);
        String replaced = matcher.replaceAll(MARKUP_HTML_OPEN);
        
        pattern = Pattern.compile(">>");
        matcher = pattern.matcher(replaced);
        String html = matcher.replaceAll(MARKUP_HTML_CLOSE);
        
		return html;
	}
}