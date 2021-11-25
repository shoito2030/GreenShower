package jp.ac.hcs.GreenShower.job.request;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.ac.hcs.GreenShower.ai.ProofreadingService;
import lombok.extern.slf4j.Slf4j;

/**
 * 就職活動申請に関する処理を行うControllerクラス
 * 
 */
@Slf4j
@Controller
public class JobNotification {

	@Autowired
	private JobRequestService jobRequestService;

	@Autowired
	private ProofreadingService proofreadingService;

	@Autowired
	private HttpSession session;

	@GetMapping("/job/request/notification")
	public String getNotificationList(Principal principal, Model model) {
		Optional<JobRequestEntity> jobRequestEntity;
		String role = ((Authentication) principal).getAuthorities().toString().replace("[", "").replace("]", "");

		jobRequestEntity = jobRequestService.selectAllNotfication(principal.getName(), role);
		
		List<JobRequestData> jobRequestList = jobRequestEntity.get().getJobRequestList();
		List<JobRequestData> jobRequestNotfiList = new ArrayList<JobRequestData>();
		
		if(role.equals("ROLE_TEACHER")) {
			for(JobRequestData data: jobRequestList) {	
				switch(data.getStatus().getId()) {
					case "2":
					jobRequestNotfiList.add(data);
					break;
					case "6":
					jobRequestNotfiList.add(data);
					break;
				}
			}
		}
		if(role.equals("ROLE_STUDENT")) {
			for(JobRequestData data: jobRequestList) {	
				switch(data.getStatus().getId()) {
					case "1":
					jobRequestNotfiList.add(data);
					break;
					case "3":
					jobRequestNotfiList.add(data);
					break;
					case "4":
					jobRequestNotfiList.add(data);
					break;
					case "5":
					jobRequestNotfiList.add(data);
					break;
				}
			}
		}
		model.addAttribute("jobRequestList", jobRequestNotfiList);
		return "job/request/notfication";
	}
}