package kr.co.uclick.repository;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import kr.co.uclick.entity.Phone;

public class CustomPhoneRepositoryImpl implements CustomPhoneRepository {

	private static final Logger logger = LoggerFactory.getLogger(CustomPhoneRepositoryImpl.class);
    
    @Autowired
    private SessionFactory sessionFactory;
    
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
//    @Cacheable(value = "area") // 캐시를 읽어올 때... key가 자동으로 id로 잡힘
    public Phone selectOne(Long id) {
        String hql = "FROM Phone p WHERE p.id = " + id;
        Query query = getSession().createQuery(hql).setCacheable(true);
        return (Phone) query.uniqueResult();
    }

//    @CacheEvict(value = "area", key = "#phone.id") // 캐시를 삭제할 때... 캐시 100 100 100 100 으로 읽어오면 안되기 때문
    public void updateOne(Phone phone) {
    	getSession().saveOrUpdate(phone);
    }
    
//    @CacheEvict(value = "area", key = "#phone.id") // 캐시를 삭제할 때... 캐시 100 100 100 100 으로 읽어오면 안되기 때문
    public void deleteOne(Phone phone) {
    	getSession().delete(phone);
    }
    
}