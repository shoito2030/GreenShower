package jp.ac.hcs.GreenShower.report;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


@Service
public class ReportService {

	@Autowired
	ReportRepository reportRepository;
	
	/**
	 * 受験報告情報を全件取得する
	 * 
	 * @return Optional<ReportEntity>
	 */
	public Optional<ReportEntity> selectAll() {
		ReportEntity reportEntity;

		try {
			reportEntity = reportRepository.selectAll();
		} catch (DataAccessException e) {
			e.printStackTrace();
			reportEntity = null;
		}

		return Optional.ofNullable(reportEntity);
	}
	
	
	/**
	 * 受験報告情報を全件取得する
	 * 
	 * @return Optional<ReportData>
	 */
	public Optional<ReportData> selectOne(String report_id) {
		ReportData reportEntity;

		try {
			reportEntity = reportRepository.selectOne(report_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			reportEntity = null;
		}

		return Optional.ofNullable(reportEntity);
	}

}
