package kr.co.uclick.entity;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jetbrains.annotations.NotNull;

@Entity
@TableGenerator(name="phone", table="pk_sequence", allocationSize=1)
// Table명 : phone, id증가참조테이블 : pk_sequence, 증가사이즈 : 1
@Table(name = "phone")
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Phone {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "phone")
	private Long id;
	
	@Column(name="phonenumber", length=11)
	@NotNull
	private String phonenumber;
	
	@Column(name="telecom", length=10)
	@NotNull
	private String telecom;
	
	// Getter Setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getTelecom() {
		return telecom;
	}

	public void setTelecom(String telecom) {
		this.telecom = telecom;
	}

	// 외래키
	@ManyToOne(cascade = CascadeType.MERGE)
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
