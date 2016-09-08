package com.github.phoenix.plugin.client;

import com.github.phoenix.plugin.criterion.Criteria;

import java.util.List;
import java.util.Map;

/**
 * 功能概要：phoenix操作hbase接口类
 *
 * @author linbingwen
 * @since 2016年8月30日
 */
public interface PhoenixBase {

    /**
     * 执行SQL, 也适应于drop table, create table, alter table等ddl操作
     * @author linbingwen
     * @param sql
     * @throws Exception drop table, create table, alter table等ddl操作
     * @return 执行成功返回true
     */
    public boolean execute(String sql) throws Exception;

    /**
     * 插入或更新单条记录
     * 注意:如果有字段为null,数据库对应字段会设置为null
     * @author linbingwen
     * @since  2016年9月8日 
     * @param data
     * @throws Exception
     */
    public <T> void upsert(T data) throws Exception;

    /**
     * 插入或更新多条记录
     * 注意:如果有字段为null,数据库对应字段会设置为null
     * @author linbingwen
     * @since  2016年9月8日 
     * @param datas
     * @throws Exception
     */
    public <T> void upsert(List<T> datas) throws Exception;

    /**
     * 插入或更新单条记录
     * 注意:如果有字段为null,这个字段会忽略不插入
     * @author linbingwen
     * @since  2016年9月8日 
     * @param data
     * @throws Exception
     */
    public <T> void upsertIgnoreNull(T data) throws Exception;

    /**
     * 插入或更新单条记录（phoenix中插入与更新使用相同命令）
     * 注意:如果有字段为null,这个字段会忽略不插入
     * @author linbingwen
     * @since  2016年9月8日 
     * @param datas
     * @throws Exception
     */
    public <T> void upsertIgnoreNull(List<T> datas) throws Exception;
    
    /**
     * 根据指定sql查询
     * 注意：传入sql形如 select * from 表名 where 条件,也支持关联表查询
     * @author linbingwen
     * @since  2016年9月8日 
     * @param clazz 查询结果对应的属性名所在的类
     * @param sql select 语句
     * @return
     */
    public <T> List<T> find(Class<T> clazz, String sql) throws Exception;

    /**
     * 根据指定参数criteria构造过滤条件，获取符合条件的记录。
     * @author linbingwen
     * @param clazz
     * @param criteria
     * @param <T>
     * @return
     */
    public <T> List<T> find(Class<T> clazz, Criteria criteria) throws Exception;


}
