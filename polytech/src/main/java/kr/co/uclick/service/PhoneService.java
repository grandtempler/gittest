package kr.co.uclick.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.User;
import kr.co.uclick.repository.PhoneRepository;

@Service
@Transactional
public class PhoneService {

	@Autowired
	private PhoneRepository phoneRepository;

	// Create & Update
	@CacheEvict(value = "area", allEntries = true)
	public void save(Phone phone) {
		phoneRepository.save(phone);
	}

	// Read
	@Cacheable(value = "area")
	@Transactional(readOnly = true)
	public List<Phone> findAll() {
		return phoneRepository.findAll();
	}

	// Read
	@Cacheable(value = "area")
	@Transactional(readOnly = true)
	public Phone findPhoneByPhonenumber(String phonenumber) {
		return phoneRepository.findPhoneByPhonenumber(phonenumber);
	}
	
	@Cacheable(value = "area")
	@Transactional(readOnly = true)
	public List<Phone> findPhoneByPhonenumberContaining(String phonenumber) {
		return phoneRepository.findPhoneByPhonenumberContaining(phonenumber);
	}
	
	@Cacheable(value = "area")
	@Transactional(readOnly = true)
    public Phone selectOne(Long id) {
        return phoneRepository.selectOne(id);
    }
    
	@CacheEvict(value = "area", allEntries = true)
    public void updateOne(Phone phone) {
    	phoneRepository.updateOne(phone);
    }

	// Read
	@Cacheable(value = "area")
	@Transactional(readOnly = true)
	public Phone findById(Long phoneId) {
		Optional<Phone> op = phoneRepository.findById(phoneId);
		Phone p = op.get();
		return p;
	}
	
	// Delete
	@CacheEvict(value = "area", allEntries = true)
	public void delete(Phone phone) {
		phoneRepository.delete(phone);
	}
	
	@CacheEvict(value = "area", allEntries = true)
    public void deleteOne(Phone phone) {
    	phoneRepository.deleteOne(phone);
    }
	
	public boolean phoneInsertDuplicationChk(String phonenumber) {
		if (phoneRepository.findPhoneByPhonenumber(phonenumber) == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean phoneUpdateDuplicationChk(String phonenumber, Long phup) {
		
		if (phoneRepository.findPhoneByPhonenumber(phonenumber) == null || phoneRepository.selectOne(phup).getPhonenumber().equals(phonenumber)) {
			return true;
		} else {
			return false;
		}
	}
}
