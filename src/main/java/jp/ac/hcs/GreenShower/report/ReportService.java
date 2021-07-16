package jp.ac.hcs.GreenShower.report;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import jp.ac.hcs.GreenShower.user.UserEntity;


@Service
public class ReportService {

	@Autowired
	ReportRepository reportRepository;
	
	/**
	 * 受験報告情報を全件取得する
	 * 
	 * @return Optional<UserEntity>
	 */
	public Optional<UserEntity> selectAll() {
		UserEntity userEntity;

		try {
			userEntity = reportRepository.selectAll();
		} catch (DataAccessException e) {
			e.printStackTrace();
			userEntity = null;
		}

		return Optional.ofNullable(userEntity);
	}

}
