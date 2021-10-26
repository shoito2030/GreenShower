package jp.ac.hcs.GreenShower.job.request;

import java.util.ArrayList;
import java.util.List;

import jp.ac.hcs.GreenShower.job.common.JobHuntingData;
import lombok.Data;
/**
 * 就職活動申請をまとめて管理する為のエンティティクラス
 */
@Data
public class JobRequestEntity {

	private List<JobHuntingData> jobRequestList = new ArrayList<JobHuntingData>();

}
