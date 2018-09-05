package kr.co.uclick.repository;

import kr.co.uclick.entity.User;

public interface CustomUserRepository {

	public User selectOne(Long id);
	
	void updateOne(User user);
	
	void deleteOne(User user);
}
