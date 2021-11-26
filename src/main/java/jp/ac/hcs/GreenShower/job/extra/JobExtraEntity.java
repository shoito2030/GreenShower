package jp.ac.hcs.GreenShower.job.extra;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * とりまとめ書類をまとめて管理する為のエンティティクラス
 */
@Data
public class JobExtraEntity {
	
	private List<JobExtraData> jobExtraList = new ArrayList<JobExtraData>();
	
}
