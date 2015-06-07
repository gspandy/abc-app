package com.kingteller.bs.domain.login;

import java.util.Date;

//import org.codehaus.jackson.map.annotate.JsonSerialize;

//import com.fsj.spring.util.CustomDateSerializer;

/**
 * TUser entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -8032736954376548825L;
	private Integer id;
	private String password;
	private String name;
	private Integer age;
	private Date birthday;
	private Integer deptId;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(String password, String name, Integer age, Date birthday,
			Integer deptId) {
		this.password = password;
		this.name = name;
		this.age = age;
		this.birthday = birthday;
		this.deptId = deptId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	//@JsonSerialize(using = CustomDateSerializer.class)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getDeptId() {
		return this.deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

}
