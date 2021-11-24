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

	
}
