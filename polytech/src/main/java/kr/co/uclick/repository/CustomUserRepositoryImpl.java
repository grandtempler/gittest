package kr.co.uclick.repository;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import kr.co.uclick.entity.User;

public class CustomUserRepositoryImpl implements CustomUserRepository {

	private static final Logger logger = LoggerFactory.getLogger(CustomUserRepositoryImpl.class);
    
    @Autowired
    private SessionFactory sessionFactory;
    
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
//    @Cacheable(value = "area") // 한 개체를 읽어올 때 캐시를 저장하도록 함.. 다음 부턴 캐시값을 읽어옴
    public User selectOne(Long id) {
    	String hql = "FROM User u WHERE u.id = " + id;
        Query query = getSession().createQuery(hql).setCacheable(true);
        return (User) query.uniqueResult();
    }

//    @CacheEvict(value = "area", key = "#user.id") // 한 개체를 업데이트 할 때 캐시를 
    public void updateOne(User user) {
    	getSession().saveOrUpdate(user);
    }
    
//    @CacheEvict(value = "area", key = "#user.id") // 한 개체를 삭제 할 때 캐시를 삭제 
    public void deleteOne(User user) {
    	getSession().delete(user);
    }


//    @Cacheable(value = "area") // 캐시를 읽어올 때... key가 자동으로 id로 잡힘
//    public List<User> findUserByNameContaining(String name) {
//    	
//    	
//        sleep(3000);
//        
//        return findUserByNameContaining(name);
//    }
//    
//    @CacheEvict(value = "area", key = "#user.id") // 캐시를 삭제할 때... 캐시 100 100 100 100 으로 읽어오면 안되기 때문
//    public void updateUser(List<User> users) {
//    	getSession().saveOrUpdate(users);
//    }
//
//    private void sleep(long millis) {
//        try {
//            Thread.sleep(millis);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

}

