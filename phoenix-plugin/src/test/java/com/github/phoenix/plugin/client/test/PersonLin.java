package com.github.phoenix.plugin.client.test;

/**
 * 
 * 功能概要：测试用VO类,请先确保表已存在，并且字段名以‘-’对应（如java中字段vistorDay对应表中vistor_day）
 * 
 * @author linbingwen
 * @since  2016年8月30日
 */
public class PersonLin extends MyBaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Integer stuid;

	private String name;

	private Integer age;

	private Integer score;

	private Integer classid;
	
	public Integer getStuid() {
		return stuid;
	}

	public void setStuid(Integer stuid) {
		this.stuid = stuid;
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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getClassid() {
		return classid;
	}

	public void setClassid(Integer classid) {
		this.classid = classid;
	}
	
	
}
