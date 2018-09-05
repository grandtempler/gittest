package kr.co.uclick.repository;

import kr.co.uclick.entity.Phone;

public interface CustomPhoneRepository {

	public Phone selectOne(Long id);
	
	void updateOne(Phone Phone);
	
	void deleteOne(Phone phone);
	
}
