package jp.ac.hcs.GreenShower.job.report;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
/**
 * 就職活動申請報告をまとめて管理する為のエンティティクラス
 */
@Data
public class JobReportEntity {
	
	private List<JobReportData> jobReportList = new ArrayList<JobReportData>();
}
