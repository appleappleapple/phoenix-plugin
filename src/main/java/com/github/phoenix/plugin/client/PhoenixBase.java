package com.github.phoenix.plugin.client;

import java.util.List;
import java.util.Map;

/**
 * 
 * 功能概要：phoenix操作hbase接口类
 * 
 * @author linbingwen
 * @since  2016年8月30日
 */
public interface PhoenixBase {
	
	/**
	 * 插入或更新单条记录（phoenix中插入与更新使用相同命令）
	 * 注意如果有字段为null会忽略不插入
	 * @author linbingwen
	 * @since  2016年8月31日 
	 * @param data
	 */
    public <T> void save(T data); 
	
	/**
	 * 插入或更新多条记录（phoenix中插入与更新使用相同命令）
	 * 注意如果有字段为null会忽略不插入
	 * @author linbingwen
	 * @since  2016年8月31日 
	 * @param data
	 */
    public <T> void save(List<T> datas); 
    
    /**
     * 根据类的类型找到对应的表的所有数据
     * @author linbingwen
     * @since  2016年8月31日 
     * @param clazz 表对应的vo类或domain类
     * @return
     */
    public <T> List<T> findAll(Class<T> clazz);
    
    /**
     * 根据类的类型和查询条件找到对应的表的所有数据
     * @author linbingwen
     * @since  2016年8月31日 
     * @param clazz 表对应的vo类或domain类
     * @param map 查询条件
     * @return
     */
    public <T> List<T> findByCondition(Class<T> clazz,Map<String,Object> map);
    
    /**
     * 根据condition 的where语句来查
     * @author linbingwen
     * @since  2016年8月31日 
     * @param clazz
     * @param sql
     * @return
     */
    public <T> List<T> findByCondition(Class<T> clazz,String condition);
    
}
