package jp.ac.hcs.GreenShower.ai;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;
/**
 * 1件の校正結果に含まれる指摘情報群
 *
 */
@Data
public class AlertsData {
	
	/* 指摘箇所。文の先頭からの文字数で表す。0オリジン。 */
	private String pos;
	
	/* 指摘文字 */
	private String word;
	
	/* 指摘した単語の疑わしさを示す指標。0〜1の範囲の値をとり、1に近いほど疑わしい事を意味する。 */
	private String score;
	
	/* 指摘箇所を置き換える候補。より自然な文字から順にlistに格納。 */
	private List<String> suggestions = new LinkedList<String>();
}
