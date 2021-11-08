package jp.ac.hcs.GreenShower.ai;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;

/**
 * 1件分の校正結果
 * 
 */
@Data
public class ProofreadingData {
	
	/* 結果ID */
	private String resultID;
	
	/* 処理ステータス */
	private String status;
	
	/* メッセージ */
	private String message;
	
	/* リクエストで指定したチェック対象文 */
	private String inputSentence;
	
	/* チェックロジック入力用に正規化した文 */
	private String normalizedSentence;
	
	/* チェック後の文。指摘箇所を<<>>で示す。 */
	private String checkedSentence;
	
	/* 指定内容を格納した配列	 */
	private List<AlertsData> alerts = new LinkedList<AlertsData>();
}
