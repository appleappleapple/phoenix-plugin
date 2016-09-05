package com.github.phoenix.plugin.client.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.github.phoenix.plugin.client.PhoenixClinet;

/**
 * 
 * 功能概要：PhoenixClinet测试类
 * 
 * @author linbingwen
 * @since  2016年8月31日
 */
public class PhoenixClinetTest {
	
	
	/**
	 * 批量插入测试
	 * @author linbingwen
	 * @since  2016年8月31日 
	 * @param phoenixClinet
	 */
	public static void saveTest1(PhoenixClinet phoenixClinet) {
		List<StudentLin> lins = new ArrayList<StudentLin>();
		Random random = new Random();
		for (int i = 1; i <= 100; i++) {
			StudentLin lin = new StudentLin();
			lin.setStuId(i);
			lin.setNewVisitCnt("lin" + i);
		    lin.setClassId(random.nextInt(100));
			lin.setScore(random.nextInt(100));
			lins.add(lin);
		}
		phoenixClinet.save(lins);
	}
	
	/**
	 * 全部查找测试
	 * @author linbingwen
	 * @since  2016年8月31日 
	 * @param phoenixClinet
	 */
	public static void findAllTest1(PhoenixClinet phoenixClinet) {
		List<StudentLin> list = phoenixClinet.findAll(StudentLin.class);
		System.out.print(list);
	}
	
	/**
	 * 按条件查找测试
	 * @author linbingwen
	 * @since  2016年8月31日 
	 * @param phoenixClinet
	 */
	public static void findByConditionTest(PhoenixClinet phoenixClinet) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("newVisitCnt", "lin10");
		List<StudentLin> list = phoenixClinet.findByCondition(StudentLin.class, map);
		System.out.print(list);
		
	}
	
	public static void findByConditionTest2(PhoenixClinet phoenixClinet) {
		String Condition = "where new_visit_cnt = 'lin1' and score = 22";
		List<StudentLin> list = phoenixClinet.findByCondition(StudentLin.class, Condition);
		System.out.print(list);
	}
	
	
	
	/**
	 * 批量插入测试
	 * @author linbingwen
	 * @since  2016年8月31日 
	 * @param phoenixClinet
	 */
	public static void saveTest(PhoenixClinet phoenixClinet) {
		List<PersonLin> lins = new ArrayList<PersonLin>();
		Random random = new Random();
		for (int i = 1; i <= 100; i++) {
			PersonLin lin = new PersonLin();
			lin.setStuid(i);
			lin.setAge(random.nextInt(50));
			lin.setClassid(i);
			lin.setName("675" + i);
			lin.setScore(random.nextInt(100));
			lins.add(lin);
		}
		phoenixClinet.save(lins);
	}
	
	/**
	 * 全部查找测试
	 * @author linbingwen
	 * @since  2016年8月31日 
	 * @param phoenixClinet
	 */
	public static void findAllTest(PhoenixClinet phoenixClinet) {
		List<PersonLin> list = phoenixClinet.findAll(PersonLin.class);
		System.out.print(list);
		
	}
	
	
	
	public static void main(String[] args) {
		PhoenixClinet phoenixClinet = new PhoenixClinet("uba");
		
		
		saveTest(phoenixClinet);
		
		findAllTest(phoenixClinet);
		
//		saveTest1(phoenixClinet);
//		
//		findAllTest1(phoenixClinet);
//		
//		findByConditionTest(phoenixClinet);
//		
//		findByConditionTest2(phoenixClinet);
	}

}
