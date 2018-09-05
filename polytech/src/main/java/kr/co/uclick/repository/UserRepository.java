package kr.co.uclick.repository;


import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import kr.co.uclick.entity.User;


public interface UserRepository
		extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, CustomUserRepository {

	public List<User> findUserByName(String name);
	
	
	
	public List<User> findUserByNameContaining(String name);
	@QueryHints(value= {
	           @QueryHint(name="org.hibernate.cacheable", value="true"),
	           @QueryHint(name="org.hibernate.cacheMode", value="NORMAL")
	   })
	public Page<User> findUserByNameContaining(String name, Pageable pageable);
	
	
	
	public List<User> findAll();
	@QueryHints(value= {
	           @QueryHint(name="org.hibernate.cacheable", value="true"),
	           @QueryHint(name="org.hibernate.cacheMode", value="NORMAL")
	   })
	public Page<User> findAll(Pageable pageable);
}

//	public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable);
	
