package kr.co.uclick.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.User;
import kr.co.uclick.repository.UserRepository;
import kr.co.uclick.vo.PaginationVO;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	// Create & Update
	@CacheEvict(value = "area", allEntries = true)
	public void save(User user) {
		userRepository.save(user);
	}

	// Read
	@Cacheable(value = "area")
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	@Cacheable(value = "area")
	@Transactional(readOnly = true)
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	@Cacheable(value = "area")
	@Transactional(readOnly = true)
	public int countAll() {
		return userRepository.findAll().size();
	}

	// Read
	@Cacheable(value = "area")
	@Transactional(readOnly = true)
	public List<User> findUserByName(String name) {
		return userRepository.findUserByName(name);
	}

	@Cacheable(value = "area")
	@Transactional(readOnly = true)
	public List<User> findUserByNameContaining(String name) {
		return userRepository.findUserByNameContaining(name);
	}
	
	@Cacheable(value = "area")
	@Transactional(readOnly = true)
	public int countUserByNameContaining(String name) {
		return findUserByNameContaining(name).size();
	}
	
	@Cacheable(value = "area")
	@Transactional(readOnly = true)
	public Page<User> findUserByNameContaining(String name, Pageable pageable) {
		return userRepository.findUserByNameContaining(name, pageable);
	}
	
	// Read
	@Cacheable(value = "area")
	@Transactional(readOnly = true)
	public User findById(Long userId) {
		Optional<User> ou = userRepository.findById(userId);
		User u = ou.get();
		return u;
	}
	
	@Cacheable(value = "area")
	@Transactional(readOnly = true)
    public User selectOne(Long id) {
        return userRepository.selectOne(id);
    }
    
	// Update
    @CacheEvict(value = "area", allEntries = true)
    public void updateOne(User user) {
    	userRepository.updateOne(user);
    }
	
	// Delete
    @CacheEvict(value = "area", allEntries = true)
	public void delete(User user) {
		userRepository.delete(user);
	}
	
    // Delete
    @CacheEvict(value = "area", allEntries = true)
	public void deleteOne(User user) {
		userRepository.deleteOne(user);
	}
	
	// users 중복제거 메서드 : phone 리스트에서 user 리스트를 뽑아낼 때 사용
	public List<User> deduplication(List<User> users) {
		
		List<User> deduplicatedUsers = new ArrayList<User>();
		for (User user : users) {
		    if (!deduplicatedUsers.contains(user)) {
		    	deduplicatedUsers.add(user);
		    }
		}
		return deduplicatedUsers;
	}
	
	public PaginationVO calcPagination(int page, int pageSize, int count) {
		
		PaginationVO paginationVO = new PaginationVO();

		int pagenationSize = 10; // 페이지네이션 버튼 최대 갯수
		int totalpage = (int)(Math.ceil(((double)count/pageSize)));
		paginationVO.setTotalpage(totalpage);
		
		int first = 1;
		paginationVO.setFirst(first);
		
		int last = totalpage;
		paginationVO.setLast(last);
		
		if (page > last) page=last;

		int start = page;
		if (start < first) {
			start = first;
		} else if (start > last) {
			start = last;
		}
		start = (int)(Math.floor((start-1)/pagenationSize))*pagenationSize+1;
		paginationVO.setStart(start);
		
		int end = start + pagenationSize - 1;
		if (end > last) {
			end = last;
		}
		paginationVO.setEnd(end);
		
		int prev = start - pagenationSize;
		if (prev < 1) prev = 1;
		paginationVO.setPrev(prev);
		
		int next = start + pagenationSize;
		if (next > last) next = last;
		paginationVO.setNext(next);

		return paginationVO;
	}
	
}
