# phoenix-plugin使用说明   
## 一、概述
Phoenix-Plugin 是一个使用jdbc封装了操作Hbase的工具类.可以快速的进行增删改查操作
## 二、环境 
开发环境：Eclipse luna + windows7  
编译环境：JDK7 + maven

## 三、使用配置

### 1、pom文件引入  
```
<dependency>  
  <groupId>com.github</groupId>  
  <artifactId>phoenix-plugin</artifactId>  
  <version>0.0.1-SNAPSHOT</version>  
</dependency>  
```
### 2、配置数据源
在maven的工程中，在目录src/main/resources添加一个名为hbase-phoenix.xml的文件。如下目录:
```
src
     --main
           --resources
                 --hbase-phoenix.xm
```
示例内容如下：  
```
<?xml version="1.0" encoding="UTF-8"?>

<!-- id :编号, 不能重复 -->
<!-- driver:表示驱动 -->
<!-- url:表示连接的地址 -->
<!-- schema:表示连接的地址 -->
<config>
	<datasources>
		<datasource>
			<id>uba</id>
			<driver>${phoenix.driver}</driver>
			<url>${phoenix.url}</url>
			<schema>uba</schema>
		</datasource>


		<!--<datasource> -->
		<!--<id>tt</id> -->
		<!--<driver>org.apache.phoenix.jdbc.PhoenixDriver</driver> -->
		<!--<url>jdbc:phoenix:sach01-002,sach01-003,sach01-004</url> -->
		<!--<schema>test</schema> -->
		<!--</datasource> -->

	</datasources>
</config>
```
注意：Phoenix-Plugin支持多数据源的配置。由于url无法指定使用哪个库，这里用schema代表库名。每一个id对应一个schema（id名可以随便定义，但不要定义两个不同的schema对应到同一个id，这样会以最后一个为基准）

## 四、增删查改操作

### 1、增和改操作
#### （1）接口
```
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
 ```   
#### （2）使用方法
在上面的操作都做了之后就可以到这里。一定要确保表已存在、字段也有！！所以实例化连接对象
```
	PhoenixClinet phoenixClinet = new PhoenixClinet("uba");
```
注意：uba代表上面xml文件中配置的id。根据不同的id连接到不同的数据源
##### a、使用upsert接口
由于phoenix中删除和修改都是使用upsert命令。所以增和改操作调用同一个接口。如下
```
		List<StudentLin> lins = new ArrayList<StudentLin>();
		Random random = new Random();
		for (int i = 1; i <= 100; i++) {
			StudentLin lin = new StudentLin();
			lin.setStuId(i);
			lin.setNewVisitCnt("lin" + i);
		        lin.setClassId(random.nextInt(100));
			//lin.setScore(random.nextInt(100)); 这个字段会设置为Null插入
			lins.add(lin);
		}
		phoenixClinet.upsert(lins);
```
upsert接口支持list对象和单个对象存放
##### b、使用upsertIgnoreNull接口
```
		List<StudentLin> lins = new ArrayList<StudentLin>();
		Random random = new Random();
		for (int i = 1; i <= 100; i++) {
			StudentLin lin = new StudentLin();
			lin.setStuId(i);
			lin.setNewVisitCnt("lin" + i);
		        lin.setClassId(random.nextInt(100));
			//lin.setScore(random.nextInt(100)); 这个字段会忽略插入
			lins.add(lin);
		}
		phoenixClinet.upsertIgnoreNull(lins);
```
#### upsert和upsertIgnoreNull的区别：
如果StudentLin有字段为Null,如score。那么upsertIgnoreNull会直接忽略这个字段。
而upsert会将其设置为null.

#### 一定要确保表名要和类名对应(陀峰法和下划线对应)
如：
```
      java 类名           表名
       PersonLin     person_lin
```
#### 一定要确保表中字段和类中字段对应(陀峰法和下划线对应)
如：
```
     java 字段名            表中字段名
       stuId                    stu_id
        score                   score
       newVisitCnt      new_visit_cnt
```
### 2、查找
#### （1）find接口
```
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
```
#### （2）使用方法
```
	public void findBySqlTest() {
		try {
               String sql = "select * from uba.STUDENT_LIN";
		 List<StudentLin> list = phoenixClient.find(StudentLin.class, sql);
		 System.out.println(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
```
### 3、直接执行sql语句（如create/drop）
#### (1)接口
```
    /**
     * 执行SQL, 也适应于drop table, create table, alter table等ddl操作
     * @author linbingwen
     * @param sql
     * @throws Exception drop table, create table, alter table等ddl操作
     * @return 执行成功返回true
     */
    public boolean execute(String sql) throws Exception;
```    
#### （2）使用方法
```
	public void executeTest() {
		  // String sql = "CREATE TABLE uba.lin_test (id varchar PRIMARY KEY,account varchar ,passwd varchar)";
		   String sql = "drop table uba.lin_test";
		   try {
			phoenixClient.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
```





    


