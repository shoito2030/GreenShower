package jp.ac.hcs.GreenShower.main;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.Principal;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jp.ac.hcs.GreenShower.user.UserData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class LoginServiceTest {
	@Autowired
	LoginService loginService;

	@Test
    @WithMockUser(username="isida@xxx.co.jp")
	void GetLoginUserInfoの正常系テスト() {
		//1.ready
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//2.do
		UserData userData = loginService.getLoginUserInfo((Principal) auth.getPrincipal());
		//3.assert
		assertNotNull(userData);
		//4.logs
		log.warn("[GetLoginUserInfoの正常系テスト]userData:" + userData);
		
	}

	@Test
	void testJudgeUserStatus() {
		fail("まだ実装されていません");
	}

}
