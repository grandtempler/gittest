package kr.co.uclick.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jetbrains.annotations.NotNull;

@Entity
@TableGenerator(name="user", table="pk_sequence", allocationSize=1)
// Table명 : user, id증가참조테이블 : pk_sequence, 증가사이즈 : 1
@Table(name = "user")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class User {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "user")
	private Long id;

	@Column(name="name", length=20)
	@NotNull
	private String name;
	
	@Column(name="address1", length=150)
	@NotNull
	private String address1;
	
	@Column(name="address2", length=200)
	@NotNull
	private String address2;

	@Column(name="sex", length=5)
	@NotNull
	private String sex;
	
	@Column(name="age", length=5)
	@NotNull
	private String age;
	
	// Getter Setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	// 외래키
	@Cache(usage = CacheConcurrencyStrategy.NONE)
	@OneToMany(mappedBy="user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Phone> phoneList;

	// 외래키 Getter Setter
	public List<Phone> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(List<Phone> phoneList) {
		this.phoneList = phoneList;
	}

}
