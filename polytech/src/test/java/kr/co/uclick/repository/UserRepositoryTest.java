package kr.co.uclick.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.uclick.configuration.SpringConfiguration;
import kr.co.uclick.entity.User;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PhoneRepository phoneRepository;
	
//	  //CREATE-------------------------------------------
//	  @Test
//	  public void testSave() {
////	      Optional<User> o = userRepository.findById(1L);
////	      User u = o.get();
//	      
//	      User u2 = new User();
//	      u2.setId(1L);
//	      u2.setName("윤희철");
//	      u2.setAddress1("경기도 수원시");
//	      u2.setAddress2("으아아아아아");
//	      userRepository.save(u2);
//	      
//	      Phone p = new Phone();
//	      p.setId(1L);
//	      p.setUser(userRepository.getOne(1L));
//	      p.setTelecom("KT");
//	      p.setPhonenumber("010-6219-0764");
//	      phoneRepository.save(p);
//	  }
  
//  // READ---------------------------------------------
//  @Test
//  public void testFindAll() {
//      List<Phone> ps = phoneRepository.findAll();
//      assertEquals("??", ps.get(0).getId());
//  }
//  
//  @Test
//  public void testFindById() {
//      Optional<Phone> p = phoneRepository.findById(4L);;
//      assertEquals("df", p.get().getNumber());
//  }
//  
  @Test
  public void testFindAll() {
//      List<User> us = userService.findByName("송태양");
	  PageRequest page = new PageRequest(0, 1, new Sort(Direction.DESC, "id"));

	  Page<User> pusers = userRepository.findUserByNameContaining("태양", page);
	  List<User> users = pusers.getContent();
      assertEquals("??", users.get(0).getName());
  }
//  
// // DELETE---------------------------------------------
//  @Test
//  public void testFindAll() {
//      User u = userService.findById(1L);;
//      userService.delete(u);
//  }
	
//	  //CREATE-------------------------------------------
//	  @Test
//	  public void testSave() {
////	      Optional<Users> ousers = userRepository.findById(1L);
////	      Users users1 = ousers.get();
//	      
//	      User users2 = new User();
//
////	      users2.setId(2L);
//	      users2.setAddress1("서울특별시 광진구");
//	      users2.setAddress2("삐꾸동 삐꾸삐구");
//	      users2.setName("홍길동");
//	      userRepository.save(users2);
//
//	      Phone p = new Phone();
////	      p.setId(2L);
//	      p.setUser(users2);
//	      p.setPhonenumber("010-6219-0764");
//	      p.setTelecom("KT");
//	      phoneRepository.save(p);
//	      
//	  }
//	  
	//  // READ---------------------------------------------
	//  @Test
	//  public void testFindAll() {
//	      List<Phone> ps = phoneRepository.findAll();
//	      assertEquals("??", ps.get(0).getId());
	//  }
	  
	  
	  
	
	
//	@Test
//	public void test() {
//		
//		Optional<User> ouser = userRepository.findById(1L);
////		List<Users> users = new ArrayList( userRepository.findAll() );
//		User user = ouser.get();
//		
//		assertEquals(2L, user.getId().longValue());
////		assertEquals(3, users.get(0).getId().longValue());
//		
//		assertEquals(3, userRepository.findById(1L));
//
//	}
	

}
