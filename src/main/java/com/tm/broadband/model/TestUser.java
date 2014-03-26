package com.tm.broadband.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.BindingResult;

public class TestUser implements Serializable {

	private static final long serialVersionUID = 1L;

	public TestUser() {
		// TODO Auto-generated constructor stub
	}

	private Integer id;
	@NotEmpty
	@Size(min = 3, max=20)
	private String username;
	@NotEmpty
	@Size(min = 3, max=20)
	private String password;
	private Double price;
	private Date date;
	private int age;

	private BindingResult result;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public BindingResult getResult() {
		return result;
	}

	public void setResult(BindingResult result) {
		this.result = result;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
