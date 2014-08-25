package com.tm.broadband.util.test;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ModelTest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private Integer age;
	private String job;
	private Double sal;
	private String gendar;
	private boolean active;
	private List<ModelTest> all;
	private List<Object> objs;
	private ModelTest mt;
	private Date birth;

	public ModelTest getMt() {
		return mt;
	}

	public void setMt(ModelTest mt) {
		this.mt = mt;
	}

	public List<Object> getObjs() {
		return objs;
	}

	public void setObjs(List<Object> objs) {
		this.objs = objs;
	}

	public List<ModelTest> getAll() {
		return all;
	}

	public void setAll(List<ModelTest> all) {
		this.all = all;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Double getSal() {
		return sal;
	}

	public void setSal(Double sal) {
		this.sal = sal;
	}

	public String getGendar() {
		return gendar;
	}

	public void setGendar(String gendar) {
		this.gendar = gendar;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

}
