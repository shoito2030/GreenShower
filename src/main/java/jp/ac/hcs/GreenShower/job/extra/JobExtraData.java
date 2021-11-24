package jp.ac.hcs.GreenShower.job.extra;

import jp.ac.hcs.GreenShower.job.common.JobHuntingData;
import lombok.Data;

@Data
public class JobExtraData extends JobHuntingData{

	private boolean receipt_of_resume;
	
	private boolean receipt_of_university_tranriptsscripts;
	
	private boolean receipt_of_university_diploma;
	
	private boolean receipt_of_hcs_transcript;
	
	private boolean receipt_of_hcs_diploma;
	
	private boolean receipt_of_health_certificate;
	
	private boolean receipt_of_high_school_transcript;
	
	private boolean receipt_of_recommendation;
	
	private boolean receipt_of_personal_information_agreement;
}
