package com.github.phoenix.plugin.client.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.github.phoenix.plugin.client.PhoenixClient;

/**
 * 
 * 功能概要：PhoenixClinet测试类
 * 
 * @author linbingwen
 * @since  2016年8月31日
 */
public class PhoenixClinetTest {
	
	private static PhoenixClient phoenixClient = new PhoenixClient("uba");
	
	@Test
	public void findBySqlTest() {
		try {
		 List<StudentLin> list = phoenixClient.find(StudentLin.class, "select * from uba.STUDENT_LIN");
		 System.out.println(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test 
	public void upsertWithNullTest() {
		List<StudentLin> lins = new ArrayList<StudentLin>();
		Random random = new Random();
		for (int i = 1; i <= 100; i++) {
			StudentLin lin = new StudentLin();
			lin.setStuId(i);
			lin.setNewVisitCnt("apple" + i);
		    lin.setClassId(null);
			lin.setScore(null);
			lins.add(lin);
		}
		try {
			phoenixClient.upsert(lins);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void upsertIgnoreNullTest() {
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
		try {
			phoenixClient.upsertIgnoreNull(lins);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void
	
////	@Test
////	public void test() {
////		
////	}
//	
//	
//	/**
//	 * 批量插入测试
//	 * @author linbingwen
//	 * @since  2016年8月31日 
//	 * @param phoenixClinet
//	 */
//	public static void saveTest1(PhoenixClient phoenixClinet) {
//		List<StudentLin> lins = new ArrayList<StudentLin>();
//		Random random = new Random();
//		for (int i = 1; i <= 100; i++) {
//			StudentLin lin = new StudentLin();
//			lin.setStuId(i);
//			lin.setNewVisitCnt("lin" + i);
//		    lin.setClassId(random.nextInt(100));
//			lin.setScore(random.nextInt(100));
//			lins.add(lin);
//		}
////		phoenixClinet.save(lins);
//	}
//	
//	/**
//	 * 全部查找测试
//	 * @author linbingwen
//	 * @since  2016年8月31日 
//	 * @param phoenixClinet
//	 */
//	public static void findAllTest1(PhoenixClient phoenixClinet) {
////		List<StudentLin> list = phoenixClinet.findAll(StudentLin.class);
//		System.out.print(list);
//	}
//	
//	/**
//	 * 按条件查找测试
//	 * @author linbingwen
//	 * @since  2016年8月31日 
//	 * @param phoenixClinet
//	 */
//	public static void findByConditionTest(PhoenixClient phoenixClinet) {
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("newVisitCnt", "lin10");
//		List<StudentLin> list = phoenixClinet.findByCondition(StudentLin.class, map);
//		System.out.print(list);
//		
//	}
//	
//	public static void findByConditionTest2(PhoenixClient phoenixClinet) {
//		String Condition = "where new_visit_cnt = 'lin1' and score = 22";
//		List<StudentLin> list = phoenixClinet.findByCondition(StudentLin.class, Condition);
//		System.out.print(list);
//	}
//	
//	
//	
//	/**
//	 * 批量插入测试
//	 * @author linbingwen
//	 * @since  2016年8月31日 
//	 * @param phoenixClinet
//	 */
//	public static void saveTest(PhoenixClient phoenixClinet) {
//		List<PersonLin> lins = new ArrayList<PersonLin>();
//		Random random = new Random();
//		for (int i = 1; i <= 100; i++) {
//			PersonLin lin = new PersonLin();
//			lin.setStuid(i);
//			lin.setAge(random.nextInt(50));
//			lin.setClassid(i);
//			lin.setName("675" + i);
//			lin.setScore(random.nextInt(100));
//			lins.add(lin);
//		}
//		phoenixClinet.save(lins);
//	}
//	
//	/**
//	 * 全部查找测试
//	 * @author linbingwen
//	 * @since  2016年8月31日 
//	 * @param phoenixClinet
//	 */
//	public static void findAllTest(PhoenixClient phoenixClinet) {
//		List<PersonLin> list = phoenixClinet.findAll(PersonLin.class);
//		System.out.print(list);
//		
//	}
//	
//	
//	
//	public static void main(String[] args) {
//		PhoenixClient phoenixClinet = new PhoenixClient("uba");
//		
//		
//		saveTest(phoenixClinet);
//		
//		findAllTest(phoenixClinet);
//		
////		saveTest1(phoenixClinet);
////		
////		findAllTest1(phoenixClinet);
////		
////		findByConditionTest(phoenixClinet);
////		
////		findByConditionTest2(phoenixClinet);
//	}

}
