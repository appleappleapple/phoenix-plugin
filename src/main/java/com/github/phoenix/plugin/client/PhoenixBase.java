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
     * 插入或更新单条记录（phoenix中插入与更新使用相同命令）
     * 注意如果有字段为null会忽略不插入
     *
     * @param data
     * @author linbingwen
     * @since 2016年8月31日
     */
    public <T> void save(T data);

    /**
     * 插入或更新多条记录（phoenix中插入与更新使用相同命令）
     * 注意如果有字段为null会忽略不插入
     *
     * @param data
     * @author linbingwen
     * @since 2016年8月31日
     */
    public <T> void save(List<T> datas);


    /**
     * 根据类的类型找到对应的表的所有数据
     *
     * @param clazz 表对应的vo类或domain类
     * @return
     * @author linbingwen
     * @since 2016年8月31日
     */
    public <T> List<T> findAll(Class<T> clazz);

    /**
     * 根据类的类型和查询条件找到对应的表的所有数据
     *
     * @param clazz 表对应的vo类或domain类
     * @param map   查询条件
     * @return
     * @author linbingwen
     * @since 2016年8月31日
     */
    public <T> List<T> findByCondition(Class<T> clazz, Map<String, Object> map);


    /**
     * 根据condition 的where语句来查
     *
     * @param clazz
     * @param sql
     * @return
     * @author linbingwen
     * @since 2016年8月31日
     */
    public <T> List<T> findByCondition(Class<T> clazz, String condition);


    /**
     * 执行SQL, 也适应于drop table, create table, alter table等ddl操作
     *
     * @param sql
     * @throws Exception
     */
    public void execute(String sql) throws Exception;

    public <T> void upsert(T data) throws Exception;

    public <T> void upsert(List<T> datas) throws Exception;

    public <T> void upsertIgnoreNull(T data) throws Exception;

    public <T> void upsertIgnoreNull(List<T> datas) throws Exception;


    /**
     * 根据指定参数criteria构造过滤条件，获取符合条件的记录。
     *
     * @param clazz
     * @param criteria
     * @param <T>
     * @return
     */
    public <T> List<T> find(Class<T> clazz, Criteria criteria);


    /**
     * 针对参数化SQL，把map中的值填充到对应的位置，然后执行查询。
     *
     * @param sql   executable sql, such as: "SELECT A.NAME,A.TYPE FROM TABLEXX
     *              WHERE A.NAME = ? "
     * @param map   Map object, which loads the prameter values for the
     *              sql.
     * @param clazz the Class object of BaseEntity which loads the query results.
     * @param
     * @return
     * @throws Exception
     */
    public <T> List<T> find(String sql, Map<Integer, Object> map, Class<T> clazz) throws Exception;

}
