package kr.co.uclick.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.User;
import kr.co.uclick.service.PhoneService;
import kr.co.uclick.service.UserService;
import kr.co.uclick.vo.PaginationVO;

@Controller
public class ServiceController {

//	private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private PhoneService phoneService;
	

	public String setPage(String page) {

		if (page == null || page.equals("")) {
			page = "1"; // 기본 페이지는 1페이지
		}
		return page;
	}
	
	public String setPageSize(String pageSize) {

		if (pageSize == null || pageSize.equals("")) {
			pageSize = "10"; // 기본 출력 리스트 값은 10
		}
		return pageSize;
	}
	
	public String setPhoneAllList(String phonealllist) {

		if (phonealllist == null || phonealllist.equals("")) {
			phonealllist = "false"; // 기본  false
		}
		return phonealllist;
	}

	@Transactional(readOnly = true)
	public PaginationVO setPaginationVO(String searchusername, String searchphonenumber, String page, String pageSize) {
		
		PageRequest pageable = new PageRequest(Integer.parseInt(page)-1, Integer.parseInt(pageSize), new Sort(Direction.DESC, "id"));
		// pageable 변수.. page-1 을 해주어야 인덱스 값으로 들어간다.
		
		PaginationVO paginationVO = new PaginationVO();
		
		List<User> users = new ArrayList<User>(); // paginationVO 객체에 넘겨줄 users 객체
		int count = 1;	
		if (searchusername != null && !searchusername.equals("")) { // 사용자검색 파라미터가 null이 아니고 빈 Str이 아닐 때
			Page<User> pusers = userService.findUserByNameContaining(searchusername, pageable);
			
			count = userService.countUserByNameContaining(searchusername);
			users = pusers.getContent();
//			users = userService.findUserByNameContaining(searchusername);
	
		} else if (searchphonenumber != null && !searchphonenumber.equals("")) { // 핸드폰검색 파라미터가 null이 아니고 빈 Str이 아닐 때
			List<Phone> phoneList = phoneService.findPhoneByPhonenumberContaining(searchphonenumber);

			for(Phone phone : phoneList) {
				users.add(phone.getUser());
			}
			users = userService.deduplication(users);
			count = users.size();
			
			int start = (int)pageable.getOffset();
			int end   = (int)(start + pageable.getPageSize()) > users.size() ? users.size() : (start + pageable.getPageSize());
			Page<User> pusers = new PageImpl<User>(users.subList(start, end), pageable, users.size());
			users = pusers.getContent();

		} else {
			Page<User> pusers = userService.findAll(pageable);
			count = userService.countAll();
			users = pusers.getContent();
		}
		
		paginationVO = userService.calcPagination(Integer.parseInt(page), Integer.parseInt(pageSize), count);
		paginationVO.setUsers(users);
		
		return paginationVO;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET) // 파라미터 받는 법 : get방식
	public String home(Locale locale, @RequestParam Map<String, String> param, Model model) { // page, page당 갯수 : itemCountPerPage

		model.addAttribute("Samples", "홈임둥" );

		return "sample";
	}
	
/*	@RequestMapping(value = "/user", method = RequestMethod.GET) // 파라미터 받는 법 : get방식
	public String user(Locale locale, @RequestParam Map<String, String> param, Model model) { // page, page당 갯수 : itemCountPerPage

		List<User> users = userService.findAll();

		model.addAttribute("users", users );
		model.addAttribute("id", param.get("id") );

		return "user";
	}*/
	
	@RequestMapping(value = "/user_1", method = {RequestMethod.GET, RequestMethod.POST}) // 파라미터 받는 법 : get방식
	public String user_1(Locale locale, @RequestParam Map<String, String> param, Model model) { // page, page당 갯수 : itemCountPerPage

		String page = param.get("page");
		String pageSize = param.get("pageSize");
		
		page = setPage(page);
		pageSize = setPageSize(pageSize);
		
		String searchusername = param.get("searchusername");
		String searchphonenumber = param.get("searchphonenumber");

		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("searchusername", param.get("searchusername"));
		model.addAttribute("searchphonenumber", param.get("searchphonenumber"));
	
		String phonealllist = param.get("phonealllist");
		phonealllist = setPhoneAllList(phonealllist);
		model.addAttribute("phonealllist", phonealllist);
		
		try {
			PaginationVO paginationVO = setPaginationVO(searchusername, searchphonenumber, page, pageSize);
			List<User> users = paginationVO.getUsers(); // 유저 리스트가 들어갈 리스트 users 를 세팅한다.

			model.addAttribute("paginationVO", paginationVO ); // paginationVO 객체를 넘겨줌
			
			model.addAttribute("users", users ); // user list인 users 객체를 넘겨줌
			model.addAttribute("idpl", param.get("idpl") ); // 핸드폰 리스트를 누른 경우 들어오는 해당 사용자의 id + phonelist(pl) 파라미터를 받아 넘겨줌
			model.addAttribute("plist", param.get("plist") ); // 핸드폰 리스트를 누른 경우 들어오는 해당 사용자의 id + phonelist(pl) 파라미터를 받아 넘겨줌
			
			model.addAttribute("userup", param.get("userup") ); // 유저번호로 해당 라인을 업데이트 상태로 만들어주는 userup 파라미터를 받아 넘겨줌 
			model.addAttribute("phup", param.get("phup") ); // 업데이트할 핸드폰의 id인 phup 파라미터를 받아 다시 넘겨줌
	
			model.addAttribute("userinsert", param.get("userinsert")); // 유저 등록인지 여부를 판단할 수 있는 userinsert 파라미터를 받아 다시 넘겨줌
			model.addAttribute("phinsert", param.get("phinsert")); // 폰 등록인지 여부를 판단할 수 있는 phinsert 파라미터를 받아 다시 넘겨줌
			
			
			return "user_1";
			
		} catch(Exception e) {
			model.addAttribute("alertplace", "All List");
			model.addAttribute("alertmessage", "리스트를 불러올 수 없습니다.");
			
			return "alert";
		}
	}
	
	@RequestMapping(value = "/user_search", method = RequestMethod.POST)
	public String user_search(Locale locale, @RequestParam Map<String, String> param, Model model) { // page, page당 갯수 : itemCountPerPage

		String page = param.get("page");
		String pageSize = param.get("pageSize");
		page = setPage(page);
		pageSize = setPageSize(pageSize);
		
		String searchusername = param.get("searchusername");
		String searchphonenumber = param.get("searchphonenumber");

		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("searchusername", param.get("searchusername"));
		model.addAttribute("searchphonenumber", param.get("searchphonenumber"));
		
		String phonealllist = param.get("phonealllist");
		model.addAttribute("phonealllist", phonealllist);
		
/*		System.out.println("user_search :: searchusername: " + searchusername);
		System.out.println("user_search :: searchphonenumber: " + searchphonenumber);*/
		
		try {
			PaginationVO paginationVO = setPaginationVO(searchusername, searchphonenumber, page, pageSize);
			List<User> users = paginationVO.getUsers(); // 유저 리스트가 들어갈 리스트 users 를 세팅한다.

			model.addAttribute("paginationVO", paginationVO ); // paginationVO 객체를 넘겨줌
			
			model.addAttribute("users", users );

			return "redirect:user_1";
			
		} catch (Exception e) {
			
			model.addAttribute("alertplace", "User Search");
			model.addAttribute("alertmessage", "리스트를 불러올 수 없습니다.");
			
			return "alert";
		}
	}
	
	@RequestMapping(value = "/phone_search", method = RequestMethod.POST)
	public String phone_search(Locale locale, @RequestParam Map<String, String> param, Model model) { // page, page당 갯수 : itemCountPerPage

		String page = param.get("page");
		String pageSize = param.get("pageSize");
		page = setPage(page);
		pageSize = setPageSize(pageSize);
		
		String searchusername = param.get("searchusername");
		String searchphonenumber = param.get("searchphonenumber");

		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("searchusername", param.get("searchusername"));
		model.addAttribute("searchphonenumber", param.get("searchphonenumber"));
		
		String phonealllist = param.get("phonealllist");
		model.addAttribute("phonealllist", phonealllist);
		
		try {
			PaginationVO paginationVO = setPaginationVO(searchusername, searchphonenumber, page, pageSize);
			List<User> users = paginationVO.getUsers(); // 유저 리스트가 들어갈 리스트 users 를 세팅한다.

			model.addAttribute("paginationVO", paginationVO ); // paginationVO 객체를 넘겨줌
			
			model.addAttribute("users", users );
			return "redirect:user_1";
			
		} catch (Exception e) {
			
			model.addAttribute("alertplace", "Phone Search");
			model.addAttribute("alertmessage", "리스트를 불러올 수 없습니다.");
			
			return "alert";
		}
	}
	
	@RequestMapping(value = "/user_insert", method = RequestMethod.POST)
	public String user_insert(Locale locale, @RequestParam Map<String, String> param, Model model) { // page, page당 갯수 : itemCountPerPage
		
		String page = param.get("page");
		String pageSize = param.get("pageSize");
		page = setPage(page);
		pageSize = setPageSize(pageSize);
		
		String searchusername = param.get("searchusername");
		String searchphonenumber = param.get("searchphonenumber");

		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("searchusername", param.get("searchusername"));
		model.addAttribute("searchphonenumber", param.get("searchphonenumber"));
		
		String phonealllist = param.get("phonealllist");
		model.addAttribute("phonealllist", phonealllist);
		
		String name = param.get("name");
		String age = param.get("age");
		String sex = param.get("sex");
		String address1 = param.get("address1"); // 업데이트할 전화번호
		String address2 = param.get("address2"); // 업데이트할 텔레콤
		
		try {
			User user = new User();
			user.setName(name);
			user.setAge(age);
			user.setSex(sex);
			user.setAddress1(address1);
			user.setAddress2(address2);
			userService.save(user); // 유저 등록

			PaginationVO paginationVO = setPaginationVO(searchusername, searchphonenumber, page, pageSize);
			List<User> users = paginationVO.getUsers(); // 유저 리스트가 들어갈 리스트 users 를 세팅한다.

			model.addAttribute("paginationVO", paginationVO ); // paginationVO 객체를 넘겨줌
			
			model.addAttribute("users", users );
			model.addAttribute("idpl", param.get("idpl") );

			
			return "redirect:user_1";
		} catch (Exception e) {
			model.addAttribute("alertplace", "User Insert");
			model.addAttribute("alertmessage", "사용자 정보를 저장할 수 없습니다.");
			
			return "alert";
		} 
	}
	
	
	@RequestMapping(value = "/user_update", method = RequestMethod.POST)
	public String user_update(Locale locale, @RequestParam Map<String, String> param, Model model) { // page, page당 갯수 : itemCountPerPage

		String page = param.get("page");
		String pageSize = param.get("pageSize");
		page = setPage(page);
		pageSize = setPageSize(pageSize);
		
		String searchusername = param.get("searchusername");
		String searchphonenumber = param.get("searchphonenumber");

		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("searchusername", param.get("searchusername"));
		model.addAttribute("searchphonenumber", param.get("searchphonenumber"));
		
		String phonealllist = param.get("phonealllist");
		model.addAttribute("phonealllist", phonealllist);
		
		String userup = param.get("userup"); // 업데이트할 유저의 아이디
		String name = param.get("name");
		String age = param.get("age");
		String sex = param.get("sex");
		String address1 = param.get("address1"); // 업데이트할 전화번호
		String address2 = param.get("address2"); // 업데이트할 텔레콤
		
		try {
			User user = new User();
			user.setId(Long.parseLong(userup));
			user.setName(name);
			user.setAge(age);
			user.setSex(sex);
			user.setAddress1(address1);
			user.setAddress2(address2);
			userService.updateOne(user); // 유저 업데이트
	
			PaginationVO paginationVO = setPaginationVO(searchusername, searchphonenumber, page, pageSize);
			List<User> users = paginationVO.getUsers(); // 유저 리스트가 들어갈 리스트 users 를 세팅한다.

			model.addAttribute("paginationVO", paginationVO ); // paginationVO 객체를 넘겨줌
			
			model.addAttribute("users", users );
			model.addAttribute("idpl", param.get("idpl") );
			
			return "redirect:user_1";
		} catch(Exception e) {
			model.addAttribute("alertplace", "User Update");
			model.addAttribute("alertmessage", "사용자 정보를 저장할 수 없습니다.");
			
			return "alert";
		}
	}
	
	@RequestMapping(value = "/user_delete", method = {RequestMethod.GET, RequestMethod.POST}) // DB에서 phone 테이블 외래키를 RESTRICT에서 CASCADE로 변경해야 한다.
	public String user_delete(Locale locale, @RequestParam Map<String, String> param, Model model) { // page, page당 갯수 : itemCountPerPage

		String page = param.get("page");
		String pageSize = param.get("pageSize");
		page = setPage(page);
		pageSize = setPageSize(pageSize);
		
		String searchusername = param.get("searchusername");
		String searchphonenumber = param.get("searchphonenumber");

		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("searchusername", param.get("searchusername"));
		model.addAttribute("searchphonenumber", param.get("searchphonenumber"));
		
		String phonealllist = param.get("phonealllist");
		model.addAttribute("phonealllist", phonealllist);
		
		String userdel = param.get("userdel"); // 삭제할 유저의 아이디
		try {
			User user = userService.selectOne(Long.parseLong(userdel));
	
			userService.deleteOne(user); // 유저 삭제
			
			PaginationVO paginationVO = setPaginationVO(searchusername, searchphonenumber, page, pageSize);
			List<User> users = paginationVO.getUsers(); // 유저 리스트가 들어갈 리스트 users 를 세팅한다.

			model.addAttribute("paginationVO", paginationVO ); // paginationVO 객체를 넘겨줌
			
			model.addAttribute("users", users );
			model.addAttribute("idpl", param.get("idpl") );
			
			return "redirect:user_1";
		} catch (Exception e) {
			model.addAttribute("alertplace", "User Update");
			model.addAttribute("alertmessage", "사용자 정보를 불러올 수 없습니다.");
			
			return "alert";
		}
	}
	
	@RequestMapping(value = "/phone_insert", method = RequestMethod.POST)
	public String phone_insert(Locale locale, @RequestParam Map<String, String> param, Model model) { // page, page당 갯수 : itemCountPerPage

		String page = param.get("page");
		String pageSize = param.get("pageSize");
		page = setPage(page);
		pageSize = setPageSize(pageSize);
		
		String searchusername = param.get("searchusername");
		String searchphonenumber = param.get("searchphonenumber");

		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("searchusername", param.get("searchusername"));
		model.addAttribute("searchphonenumber", param.get("searchphonenumber"));
		
		String phonealllist = param.get("phonealllist");
		model.addAttribute("phonealllist", phonealllist);
		
		String idpl = param.get("idpl"); // 유저의 아이디
		String phonenumber = param.get("phonenumber"); // 업데이트할 전화번호
		String telecom = param.get("telecom"); // 업데이트할 텔레콤
		try {
			if (phoneService.phoneInsertDuplicationChk(phonenumber)) {
				Phone phone = new Phone();
				phone.setPhonenumber(phonenumber);
				phone.setTelecom(telecom);
				phone.setUser(userService.selectOne(Long.parseLong(idpl)));
				phoneService.save(phone); // 핸드폰 업데이트

				PaginationVO paginationVO = setPaginationVO(searchusername, searchphonenumber, page, pageSize);
				List<User> users = paginationVO.getUsers(); // 유저 리스트가 들어갈 리스트 users 를 세팅한다.

				model.addAttribute("paginationVO", paginationVO ); // paginationVO 객체를 넘겨줌
				
				model.addAttribute("users", users );
				model.addAttribute("idpl", param.get("idpl") );
				
				return "redirect:user_1";
	
			} else {
				model.addAttribute("alertplace", "Phone Insert");
				model.addAttribute("alertmessage", "중복된 핸드폰 번호입니다.");
				
				return "alert";
			}
		} catch(Exception e) {
			model.addAttribute("alertplace", "Phone Insert");
			model.addAttribute("alertmessage", "핸드폰 정보를 입력할 수 없습니다.");
			
			return "alert";
		}
	}
	
	
	@RequestMapping(value = "/phone_update", method = RequestMethod.POST)
	public String phone_update(Locale locale, @RequestParam Map<String, String> param, Model model) { // page, page당 갯수 : itemCountPerPage
		String page = param.get("page");
		String pageSize = param.get("pageSize");
		page = setPage(page);
		pageSize = setPageSize(pageSize);
		
		String searchusername = param.get("searchusername");
		String searchphonenumber = param.get("searchphonenumber");

		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("searchusername", param.get("searchusername"));
		model.addAttribute("searchphonenumber", param.get("searchphonenumber"));
		
		String phonealllist = param.get("phonealllist");
		model.addAttribute("phonealllist", phonealllist);
		
		String idpl = param.get("idpl"); // 유저의 아이디
		String phonenumber = param.get("phonenumber"); // 업데이트할 전화번호
		String telecom = param.get("telecom"); // 업데이트할 텔레콤
		String phid = param.get("phid"); // 해당 유저의 수정할 핸드폰 아이디
		try {
			if (phoneService.phoneUpdateDuplicationChk(phonenumber, Long.parseLong(phid))) {
				
				Phone phone = new Phone();
				phone.setId(Long.parseLong(phid));
				phone.setPhonenumber(phonenumber);
				phone.setTelecom(telecom);
				phone.setUser(userService.selectOne(Long.parseLong(idpl)));
				phoneService.updateOne(phone); // 핸드폰 업데이트

				PaginationVO paginationVO = setPaginationVO(searchusername, searchphonenumber, page, pageSize);
				List<User> users = paginationVO.getUsers(); // 유저 리스트가 들어갈 리스트 users 를 세팅한다.

				model.addAttribute("paginationVO", paginationVO ); // paginationVO 객체를 넘겨줌
				
				model.addAttribute("users", users );
				model.addAttribute("idpl", param.get("idpl") );
				
				return "redirect:user_1";
	
			} else {
				
				model.addAttribute("alertplace", "Phone Update");
				model.addAttribute("alertmessage", "중복된 핸드폰 번호입니다.");
				
				return "alert";
			}
		} catch (Exception e) {
			model.addAttribute("alertplace", "Phone Update");
			model.addAttribute("alertmessage", "핸드폰 정보를 입력할 수 없습니다.");
			
			return "alert";
		}
	}
	
	@RequestMapping(value = "/phone_delete", method = {RequestMethod.GET, RequestMethod.POST})
	public String phone_delete(Locale locale, @RequestParam Map<String, String> param, Model model) { // page, page당 갯수 : itemCountPerPage
		
		String page = param.get("page");
		String pageSize = param.get("pageSize");
		page = setPage(page);
		pageSize = setPageSize(pageSize);
		
		String searchusername = param.get("searchusername");
		String searchphonenumber = param.get("searchphonenumber");

		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("searchusername", param.get("searchusername"));
		model.addAttribute("searchphonenumber", param.get("searchphonenumber"));
		
		String phonealllist = param.get("phonealllist");
		model.addAttribute("phonealllist", phonealllist);
		
		String phdel = param.get("phdel"); // 삭제할 핸드폰의 아이디
		try {
			Phone phone = phoneService.selectOne(Long.parseLong(phdel));
			
			phoneService.deleteOne(phone); // 핸드폰 삭제
			
			PaginationVO paginationVO = setPaginationVO(searchusername, searchphonenumber, page, pageSize);
			List<User> users = paginationVO.getUsers(); // 유저 리스트가 들어갈 리스트 users 를 세팅한다.

			model.addAttribute("paginationVO", paginationVO ); // paginationVO 객체를 넘겨줌
			
			model.addAttribute("users", users );
			model.addAttribute("idpl", param.get("idpl") );
			
			return "redirect:user_1";
		} catch (Exception e) {
			model.addAttribute("alertplace", "Phone Delete");
			model.addAttribute("alertmessage", "핸드폰 정보를 불러올 수 없습니다.");
			
			return "alert";
		}
	}
}
