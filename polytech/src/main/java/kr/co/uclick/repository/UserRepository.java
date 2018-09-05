package kr.co.uclick.repository;


import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import kr.co.uclick.entity.User;


public interface UserRepository
		extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, CustomUserRepository {

	public List<User> findUserByName(String name);
	
	public List<User> findUserByNameContaining(String name);
	public Page<User> findUserByNameContaining(String name, Pageable pageable);
	
	public List<User> findAll();
	public Page<User> findAll(Pageable pageable);
}

//	public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable);
	
