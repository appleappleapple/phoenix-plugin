package com.github.phoenix.plugin.client.test;

/**
 * 
 * 功能概要：测试用VO类
 * 
 * @author linbingwen
 * @since  2016年8月30日
 */
public class StudentLin extends MyBaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Integer stuId;

	private String newVisitCnt;

	private Integer score;
	
	private Integer age;

	private Integer classId;

	public Integer getStuId() {
		return stuId;
	}

	public Integer getAge() {
		return age;
	}



	public void setAge(Integer age) {
		this.age = age;
	}



	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}

	public String getNewVisitCnt() {
		return newVisitCnt;
	}

	public void setNewVisitCnt(String newVisitCnt) {
		this.newVisitCnt = newVisitCnt;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	
	
}
