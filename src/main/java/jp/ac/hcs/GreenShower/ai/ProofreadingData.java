package jp.ac.hcs.GreenShower.ai;

import java.util.List;

import lombok.Data;

@Data
public class ProofreadingData {
	
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
	
	/*  */
	private List<String> suggestions;
}
