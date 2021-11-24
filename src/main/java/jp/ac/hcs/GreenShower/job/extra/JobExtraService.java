package jp.ac.hcs.GreenShower.job.extra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class JobExtraService {

	@Autowired
	JobExtraRepository jobExtraRepository;
	public boolean listRegistion(String apply_id,String user_id) {
		int rowNumber = 0;
		try {
			rowNumber = jobExtraRepository.listRegistion(apply_id,user_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}

		return rowNumber > 0;
	}

	public boolean documentReceirt(JobExtraForm form,String apply_id,String user_id) {
		int rowNumber = 0;
		try {
			if(form.isReceipt_of_resume()) {
				rowNumber = jobExtraRepository.updateResume(apply_id);
			}
			if(form.isReceipt_of_university_tranriptsscripts()) {
				rowNumber = jobExtraRepository.updateUniversityTranriptsscripts(apply_id);
			}
			if(form.isReceipt_of_university_diploma()) {
				rowNumber = jobExtraRepository.updateUniversityDiploma(apply_id);
			}
			if(form.isReceipt_of_hcs_diploma()) {
				rowNumber = jobExtraRepository.updateHcsDiploma(apply_id);
			}
			if(form.isReceipt_of_hcs_transcript()) {
				rowNumber = jobExtraRepository.updateHcsTranscript(apply_id);
			}
			if(form.isReceipt_of_health_certificate()) {
				rowNumber = jobExtraRepository.updateHealthCertificate(apply_id);
			}
			if(form.isReceipt_of_high_school_transcript()) {
				rowNumber = jobExtraRepository.updateHighSchoolTranscript(apply_id);
			}
			if(form.isReceipt_of_recommendation()) {
				rowNumber = jobExtraRepository.updateRecommendation(apply_id);
			}
			if(form.isReceipt_of_personal_information_agreement()) {
				rowNumber = jobExtraRepository.updateInformationAgreement(apply_id);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}

		return rowNumber > 0;
	}
}
