package kr.co.uclick.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.uclick.configuration.SpringConfiguration;
import kr.co.uclick.controller.ServiceController;
import kr.co.uclick.entity.User;
import kr.co.uclick.vo.PaginationVO;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class UserServiceTest {

	@Autowired
	UserService userService;
	
	@Autowired
	PhoneService phoneService;
	
	ServiceController serviceController = new ServiceController();
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);
	
//	  //CREATE-------------------------------------------
//	  @Test
//	  public void testSave() {
//	      Optional<Users> o = userRepository.findById(1L);
//	      Users u = o.get();
//	      
//	      Phone p = new Phone();
//	      p.setId(1L);
//	      p.setUser(u);
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
//  @Test
//  public void testFindAll() {
//      List<User> us = userService.findByName("송태양");
//      assertEquals("??", us.get(0).getName());
//  }
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
//	  // READ---------------------------------------------
//	  @Test
//	  public void testFindAll() {	      
//		  
////		  List<User> users = userService.findUserByName("윤희철");
////		  
////		  for (User u : users) {
////			  logger.info("Name : "+u.getName());
////		  }
//		  
//	      List<User> us = userService.findAll();
//	      List<Phone> p = us.get(0).getPhoneList();
//	      for (Phone pp : p) {
//	    	  logger.info("Phonenumber : " + pp.getPhonenumber());
//	    	  assertEquals("??", pp.getPhonenumber());
//	      }
//		  
//	  }

//	@Transactional(readOnly = true)
//	public List<User> setUsers(String searchusername, String searchphonenumber) {
//		
//		List users = new ArrayList<User>();
//		
//		if (searchusername != null) {
//			users = userService.findUserByNameContaining(searchusername);
//		} else if (searchphonenumber != null) {
//			List<Phone> phoneList;
//			phoneList = phoneService.findPhoneByPhonenumberContaining(searchphonenumber);
//			
//			for(Phone phone : phoneList) {
//				users.add(phone.getUser());
//			}
//
//		} else {
//			users = userService.findAll();
//		}
//		return users;
//	}
	
	
//	  @Test
//	  public void testFindByNameContaining() {	      
//		  
////		  List<User> users = userService.findUserByName("윤희철");
////		  
////		  for (User u : users) {
////			  logger.info("Name : "+u.getName());
////		  }
//		  
////	      List<User> us = userService.findUserByNameContaining("희철");
////	      assertEquals("??", us.get(0).getName());
//		  
////		  User u = userService.findById(53L);
////		  assertEquals("??", u.getAge());
////		  userService.delete(u);
//		  
////		  assertEquals(null, userService.findById(53L).getAge());
//		  String a = null;
//		  
//		  List<User> users = setUsers(a, "010-5166-7286");
//
//		  
//		  assertEquals(1, users.size());
//
//	  }
	  
//	  
//	  @Test
//      public void testUpdateOne() { // 캐시 삭제...
//          StopWatch sw = new StopWatch();
//          sw.start();
//          User s1 = userService.selectOne(356L);
//          sw.stop();
//          System.out.println("1. total time : " + sw.getTotalTimeSeconds());
//
//          sw = new StopWatch();
//          sw.start();
//          User s2 = userService.selectOne(356L);
//          sw.stop();
//          System.out.println("2. total time : " + sw.getTotalTimeSeconds());
//          
//
//          // 캐시 초기화
//          s1.setName("a");
//          userService.updateOne(s1);
//          
//
//          sw = new StopWatch();
//          sw.start();
//          User s3 = userService.selectOne(356L);
//          sw.stop();
//          System.out.println("3. total time : " + sw.getTotalTimeSeconds());
//          
//          sw = new StopWatch();
//          sw.start();
//          User s4 = userService.selectOne(356L);
//          sw.stop();
//          System.out.println("4. total time : " + sw.getTotalTimeSeconds());
//          
//          assertEquals(1, s1.getId().intValue());
//      }
//	
//	  @Test
//	  public void testfindByNameContaining() { // 캐시 삭제...
//	      StopWatch sw = new StopWatch();
//	      sw.start();
//	      List<User> s1 = userService.findUserByNameContaining("희철");
//	      sw.stop();
//	      System.out.println("1. total time : " + sw.getTotalTimeSeconds());
//	
//	      sw = new StopWatch();
//	      sw.start();
//	      List<User> s2 = userService.findUserByNameContaining("희철");
//	      sw.stop();
//	      System.out.println("2. total time : " + sw.getTotalTimeSeconds());
//	      
//	
////	      // 캐시 초기화
////	      s1.get(0).setName("희철짱");
////	      userService.up(s1);
////	      
////	
////	      sw = new StopWatch();
////	      sw.start();
////	      List<User> s3 = userService.findUserByNameContaining("희철");
////	      sw.stop();
////	      System.out.println("3. total time : " + sw.getTotalTimeSeconds());
////	      
////	      sw = new StopWatch();
////	      sw.start();
////	      List<User> s4 = userService.findUserByNameContaining("희철");
////	      sw.stop();
////	      System.out.println("4. total time : " + sw.getTotalTimeSeconds());
////	      
//	      assertEquals(1, s1.get(0).getName());
//	  }

	@Test
	public void test() {
		
		PageRequest pageable = new PageRequest(0, 10, new Sort(Direction.DESC, "id")); // pageable 변수.. page-1 을 해주어야 인덱스 값으로 들어간다.
		
//		List<User> users = serviceController.setUsers("태양", "", "1", "1");
		
		PaginationVO paginationVO = new PaginationVO();	
//		paginationVO.setTotalpage(pageable.);
		
		
		
//		paginationVO = userService.calcPagination(1, 10, 141);
		

		assertEquals(11, userService.countAll());
		
	}
	
	
	
	
	
//	@Test
//	public void test() {
//		
//		Optional<Users> ousers = userRepository.findById(1L);
//		
////		List<Users> users = new ArrayList( userRepository.findAll() );
//
//		Users users = ousers.get();
//		
//		assertEquals(2L, users.getId().longValue());
////		assertEquals(3, users.get(0).getId().longValue());
//		
//		assertEquals(3, userRepository.findById(1L));
//		assertEquals(3, userRepository.);
//	}
	

}
