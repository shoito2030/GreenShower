package jp.ac.hcs.GreenShower.user;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	void selectAllメソッドの正常系テスト() {
		//1.Ready
		//2.Do
		UserEntity userEntity = userRepository.selectAll();
		//3.Assert
		assertNotNull(userEntity);
		//4.logs
		log.warn("[selectAllメソッドの正常系テスト]userEntity:" + userEntity.toString());
	}

	@Test
	void selectOneメソッドの正常系テスト() {
		//1.Ready
		String user_id = "isida@xxx.co.jp";
		//2.Do
		UserData userData = userRepository.selectOne(user_id);
		//3.Assert
		assertNotNull(userData);
		//4.logs
		log.warn("[selectOneメソッドの正常系テスト]userData:" + userData.toString());
	}

	@Test
	void insertOneメソッドの正常系テスト() {
		//1.Ready
		UserData userData = new UserData();
		userData.setUser_id("sorena@xxx.co.jp");
		userData.setEncrypted_password("123456");
		userData.setName("走 麗奈");
		userData.setRole(Role.ROLE_STUDENT);
		userData.setClassroom("S1A1");
		userData.setClass_number("01");
		userData.setRegister_user_id("abe@xxx.co.jp");
		//2.Do
		int result = userRepository.insertOne(userData);
		//3.Assert
		assertEquals(1, result);
		//4.logs
		log.warn("[insertOneメソッドの正常系テスト]result:" + result);
	}

	@Test
	void updateOneForAdminメソッドの正常系テスト() {
		//1.Ready
		UserData userData = new UserData();
		userData.setUser_id("isida@xxx.co.jp");
		userData.setRole(Role.ROLE_TEACHER);
		userData.setName("石田雄介");
		userData.setClassroom("S2A1");
		userData.setClass_number("02");
		userData.setEnabled(true);
		userData.setUpdate_date(new Date());
		userData.setUpdate_user_id("abe@xxx.co.jp");
		//2.Do
		int result = userRepository.updateOneForAdmin(userData);
		//3.Assert
		assertEquals(1, result);
		//4.logs
		log.warn("[updateOneForAdminメソッドの正常系テスト]result:" + result);
	}

	@Test
	void deleteOneメソッドの正常系テスト() {
		//1.Ready
		String user_id = "isida@xxx.co.jp";
		//2.Do
		int result = userRepository.deleteOne(user_id);
		//3.Assert
		assertEquals(1, result);
		//4.logs
		log.warn("[deleteOneメソッドの正常系テスト]result:" + result);
	}

}
