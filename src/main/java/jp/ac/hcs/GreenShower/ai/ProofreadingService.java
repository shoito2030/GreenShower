package jp.ac.hcs.GreenShower.ai;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 誤字脱字チェックを行う recruit社の校正APIを活用する 
 * @see "https://a3rt.recruit.co.jp/product/proofreadingAPI/"
 */
@Service
@Transactional
public class ProofreadingService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	/** API リクエストURL */
	private final static String URL = "https://api.a3rt.recruit.co.jp/proofreading/v2/typo?apikey={KEY}&sentence={sentence}";
	
	/* APIキー */
	private final static String KEY = "DZZJue7PdWJJi8yXJQHUN4827W1uYpo8";
	
	private final static String MARKUP_HTML_OPEN = "<span class='mark fw-bold text-danger' style='background-color:yellow'>";
	private final static String MARKUP_HTML_CLOSE = "</span>";
	
	/**
	 * 文章を校正APIに渡し結果を得る
	 * 
	 * @param sentence メモや備考
	 * @return 異常あり - 加工済みのHTML文字列 異常なし - 加工なしの文章
	 */
	public Optional<ProofreadingData> proofreading(String sentence) {
		if (KEY.isBlank() || KEY.isEmpty()) {
			return Optional.empty();
		}

		String json;

		// APIへアクセスして、結果を取得
		try {
			json = restTemplate.getForObject(URL, String.class, KEY, sentence);
		} catch (IllegalArgumentException e) {
			// APIキーが取得できなかった場合に発生する
			e.printStackTrace();
			return Optional.empty();
		}
		
		ProofreadingData data = new ProofreadingData();

		// jsonクラスへの変換失敗のため、例外処理
		try {
			// 変換クラスを作成し、文字列からjsonクラスへ変換する（例外の可能性あり）
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(json);

			String status = node.get("status").asText();
			System.out.println("検閲文字列: "+ sentence);
			System.out.println("検閲ステータス: "+ status);
			if (status.equals("1")) {
				// マッピング
				data.setResultID(node.get("resultID").asText());
				data.setStatus(status);
				data.setMessage(node.get("message").asText());
				data.setInputSentence(node.get("inputSentence").asText());
				data.setNormalizedSentence(node.get("normalizedSentence").asText());
				
				// 指摘箇所をHTMLに加工する
				data.setCheckedSentence(toHtml(node.get("checkedSentence").asText()));

				// alertsパラメータの抽出
				for (JsonNode alert : node.get("alerts")) {
					// マッピング
					AlertsData alertsData = new AlertsData();
					alertsData.setPos(alert.get("pos").asText());
					alertsData.setWord(alert.get("word").asText());
					alertsData.setScore(alert.get("score").asText());

					List<String> suggestions = new LinkedList<String>();
					
					for (JsonNode suggestion : alert.get("suggestions")) {
						suggestions.add(suggestion.asText());
					}
					
					alertsData.setSuggestions(suggestions);
					data.getAlerts().add(alertsData);
				}
			}
		} catch (IOException e) {
			// 例外発生時は、エラーメッセージの詳細を標準エラー出力
			e.printStackTrace();
			return Optional.empty();
		}
		return Optional.ofNullable(data);		
	}

	/**
	 * 校正結果をHTML文字列に変換し、怪しい箇所を強調表示する
	 * @param checkedSentence  校正済み文字列
	 * @return html
	 */
	private String toHtml(String checkedSentence) {
		Pattern pattern = Pattern.compile("<<");
        Matcher matcher = pattern.matcher(checkedSentence);
        String replaced = matcher.replaceAll(MARKUP_HTML_OPEN);
        
        pattern = Pattern.compile(">>");
        matcher = pattern.matcher(replaced);
        String html = matcher.replaceAll(MARKUP_HTML_CLOSE);
        
		return html;
	}
}