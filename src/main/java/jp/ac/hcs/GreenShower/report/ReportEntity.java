package jp.ac.hcs.GreenShower.report;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 受験報告をまとめて管理する為のエンティティクラス
 */
@Data
public class ReportEntity {

	private List<ReportData> reportlist = new ArrayList<ReportData>();

}
