package jp.ac.hcs.GreenShower.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
	
	@Autowired
	UserService userService;
	
	@SpyBean
	UserRepository userRepository;

	@Test
	void selectAllメソッドの正常系テスト() {
		// 1.Ready
		// 2.Do
		Optional<UserEntity> userEntity = userService.selectAll();
		// 3.Assert
		assertNotNull(userEntity);
		// 4.Logs
		log.warn("[selectAllメソッドの正常系テスト]userEntity:" + userEntity.toString());
	}
	
	@Test
	void selectAllメソッドの異常系テスト() {
		// 1.Ready
		//Mock
		doThrow(new DataAccessResourceFailureException("")).when(userRepository).selectAll();
		// 2.Do
		Optional<UserEntity> userEntity = userService.selectAll();
		// 3.Assert
		assertTrue(userEntity.isEmpty());
		// 4.Logs
		log.warn("[selectAllメソッドの異常系テスト]userEntity:" + userEntity);
	}

	@Test
	void selectメソッドの正常系テスト() {
		// 1.Ready
		String user_id = "abe@xxx.co.jp";
		// 2.Do
		Optional<UserData> userData = userService.select(user_id);
		// 3.Assert
		assertEquals(true, userData.isPresent());
		// 4.Logs
		log.warn("[selectメソッドの正常系テスト]userData:" + userData);
	}
	
	@Test
	void selectメソッドの異常系テスト() {
		// 1.Ready
		String user_id = "isida@xxx.co.jp";
		//Mock
		doThrow(new DataAccessResourceFailureException("")).when(userRepository).selectOne(anyString());
		// 2.Do
		Optional<UserData> userData = userService.select(user_id);
		// 3.Assert
		assertEquals(true, userData.isEmpty());
		// 4.Logs
		log.warn("[selectメソッドの異常系テスト]userData:" + userData);
	}

	@Test
	void insertメソッドの正常系テスト() {
		// 1.Ready
		UserFormForInsert userData = new UserFormForInsert();
		userData.setUser_id("sorena@xxx.co.jp");
		userData.setEncrypted_password("123456");
		userData.setName("走 麗奈");
		userData.setRole("ROLE_STUDENT");
		userData.setClassroom("S1A1");
		userData.setClass_number("01");
		
		String register_user_id = "abe@xxx.co.jp";
		
		// 2.Do
		boolean result = userService.insert(userData, register_user_id)	;
		// 3.Assert
		assertEquals(true, result);
		// 4.Logs
		log.warn("[insertメソッドの正常系テスト]result:" + result);
	}
	
	@Test
	void insertメソッドの異常系テスト() {
		// 1.Ready
		UserFormForInsert userData = new UserFormForInsert();
		userData.setUser_id("sorena@xxx.co.jp");
		userData.setEncrypted_password("123456");
		userData.setName("走 麗奈");
		userData.setRole("ROLE_STUDENT");
		userData.setClassroom("S1A1");
		userData.setClass_number("01");
		
		String register_user_id = "abe@xxx.co.jp";
		
		//Mock
		doThrow(new DataAccessResourceFailureException("")).when(userRepository).insertOne(any());
		
		// 2.Do
		boolean result = userService.insert(userData, register_user_id)	;
		// 3.Assert
		assertEquals(false, result);
		// 4.Logs
		log.warn("[insertメソッドの異常系テスト]result:" + result);
	}

	@Test
	void deleteメソッドの正常系テスト() {
		// 1.Ready
		String user_id = "isida@xxx.co.jp";
		// 2.Do
		boolean result = userService.delete(user_id);
		// 3.Assert
		assertEquals(true, result);
		// 4.Logs
		log.warn("[deleteメソッドの正常系テスト]result:" + result);
	}
	
	@Test
	void deleteメソッドの異常系テスト() {
		// 1.Ready
		String user_id = "isida@xxx.co.jp";
		//Mock
		doThrow(new DataAccessResourceFailureException("")).when(userRepository).deleteOne(anyString());
		// 2.Do
		boolean result = userService.delete(user_id);
		// 3.Assert
		assertEquals(false, result);
		// 4.Logs
		log.warn("[deleteメソッドの異常系テスト]result:" + result);
	}

	@Test
	void updateForAdminメソッドの正常系テスト() {
		// 1.Ready
		UserFormForUpdate userData = new UserFormForUpdate();
		userData.setUser_id("abe@xxx.co.jp");
		userData.setName("安部華奈");
		userData.setRole("ROLE_TEACHER");
		userData.setClassroom("S2A1");
		userData.setClass_number("02");
		userData.setEnabled(true);
		
		String update_user_id = "abe@xxx.co.jp";
				
		// 2.Do
		boolean result = userService.updateForAdmin(userData, update_user_id);
		// 3.Assert
		assertEquals(true, result);
		// 4.Logs
		log.warn("[updateForAdminメソッドの正常系テスト]result:" + result);
	}
	
	@Test
	void updateForAdminメソッドの異常系テスト() {
		// 1.Ready
		UserFormForUpdate userData = new UserFormForUpdate();
		userData.setUser_id("isida@xxx.co.jp");
		userData.setRole("ROLE_TEACHER");
		userData.setName("石田雄介");
		userData.setClassroom("S2A1");
		userData.setClass_number("02");
		userData.setEnabled(true);
		
		String update_user_id = "abe@xxx.co.jp";
		
		//Mock
		doThrow(new DataAccessResourceFailureException("")).when(userRepository).updateOneForAdmin(any());
		
		// 2.Do
		boolean result = userService.updateForAdmin(userData, update_user_id);
		// 3.Assert
		assertEquals(false, result);
		// 4.Logs
		log.warn("[updateForAdminメソッドの異常系テスト]result:" + result);
	}

}
