package jp.ac.hcs.GreenShower.job.extra;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class JobExtraEntity {
	
	private List<JobExtraData> jobExtraList = new ArrayList<JobExtraData>();
	
}
