package jp.ac.hcs.GreenShower.main;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jp.ac.hcs.GreenShower.user.UserData;
import jp.ac.hcs.GreenShower.user.UserData.Role;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class LoginServiceTest {
	@Autowired
	LoginService loginService;

	@Test
	void GetLoginUserInfoの正常系テスト() {
		//1.ready
		String name = "isida@xxx.co.jp";
		//2.do
		UserData userData = loginService.getLoginUserInfo(name);
		//3.assert
		assertNotNull(userData);
		//4.logs
		log.warn("[GetLoginUserInfoの正常系テスト]userData:" + userData);
		
	}

	@Test
	void JudgeUserStatusの正常系テスト() {
		//1.ready
		UserData userData = new UserData();
		userData.setName("石田");
		userData.setUser_id("isida@xxx.co.jp");
		userData.setUser_status(1);
		userData.setNumber_of_trials(0);
		userData.setRole(Role.ROLE_STUDENT);
		//2.do
		Boolean boo = loginService.judgeUserStatus(userData);
		//3.assert
		assertTrue(boo);
		//4.logs
		log.warn("[JudgeUserStatusの正常系テスト]boo:" + boo);		
	}
	
	@Test
	void JudgeUserStatusの正常系テスト2() {
		//1.ready
		UserData userData = new UserData();
		userData.setName("isida@xxx.co.jp");
		userData.setUser_status(2);
		userData.setNumber_of_trials(1);
		//2.do
		Boolean boo = loginService.judgeUserStatus(userData);
		//3.assert
		assertFalse(boo);
		//4.logs
		log.warn("[JudgeUserStatusの正常系テスト]boo:" + boo);		
	}

}
