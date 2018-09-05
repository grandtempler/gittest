package kr.co.uclick.repository;


import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import kr.co.uclick.entity.Phone;

public interface PhoneRepository
		extends JpaRepository<Phone, Long>, QuerydslPredicateExecutor<Phone>, CustomPhoneRepository {

	@QueryHints(value= {
	           @QueryHint(name="org.hibernate.cacheable", value="true"),
	           @QueryHint(name="org.hibernate.cacheMode", value="NORMAL")
	   })
	public Phone findPhoneByPhonenumber(String phonenumber);
	
	@QueryHints(value= {
	           @QueryHint(name="org.hibernate.cacheable", value="true"),
	           @QueryHint(name="org.hibernate.cacheMode", value="NORMAL")
	   })
	public List<Phone> findPhoneByPhonenumberContaining(String phonenumber);

}
