package shell.framework.dao;

import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import shell.framework.dao.support.DaoException;
import shell.framework.dao.support.VOResult;

/**
 * 
 * <p> 通用持久层JDBC实现,支持用户自定义结果集提取器 </p>
 * <p> 自定义结果集提取器必须继承 ResultSetExtractor </p>
 * <p> 支持2种结果集解析方式：ResultSetExtractor 和 RowMapper </p>	
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-4-16 上午9:48:43 $
 */
public interface IJdbcBaseDao {

	/**
	 * 根据静态sql查询，会生成jdbc的Statement，而不是prepared statement
	 * @param sql 静态sql
	 * @param extractor 结果集提取器，缺省使用ListExtractor4Map
	 * @return 对象list
	 * @throws DaoException
	 */
	public List<?> query(String sql , ResultSetExtractor<?> extractor) throws DaoException ;
	
	/**
	 * 根据给定的sql创建一个prepared statement，执行sql
	 * @param sql 执行的sql查询-带占位符的sql
	 * @param params  绑定到sql查询上的参数
	 * @param extractor 结果集提取器，缺省使用ListExtractor4Map
	 * @return
	 * @throws DaoException
	 */
	public List<?> query(String sql , Object[] params , ResultSetExtractor<?> extractor) throws DaoException ;
	
	/**
	 * 根据静态sql查询，会生成jdbc的Statement，而不是prepared statement
	 * 结果集row属性转换为bean属性
	 * @param sql 静态sql
	 * @param beanClazz bean对象class属性
	 * @param extractor 结果集抽取器，会将rs属性提取为bean属性，并返回存储bean对象的list，缺省使用ListExtractor4Bean
	 * @return list对象，存储bean对象
	 * @throws DaoException
	 */
	public List<?> query(String sql , Class<?> beanClazz , ResultSetExtractor<?> extractor) throws DaoException ;
	
	/**
	 * 根据给定的sql创建一个prepared statement，执行sql
	 * 结果集row属性转换为bean属性
	 * @param sql 带占位符的sql
	 * @param params 查询sql的参数值
	 * @param beanClazz bena对象class属性
	 * @param extractor 结果集抽取器，会将rs属性提取为bean属性，并返回存储bean对象的list，缺省使用ListExtractor4Bean
	 * @return 存储bean对象的list
	 * @throws DaoException
	 */
	public List<?> query(String sql, Object[] params , Class<?> beanClazz , ResultSetExtractor<?> extractor)
			throws DaoException;
	
	/**
	 * 默认使用RowMapper类来映射每行记录到map中
	 * 灵活获取结果集方式，可以自由使用返回的结果集list，不支持自定义结果集提取器
	 * 默认的RowMapper会将结果集每行记录转为一个map，形式为rowsMap<字段名，字段值>
	 * @param sql 静态sql
	 * @return 存储map<字段名，字段值>的list对象
	 * @throws DaoException
	 */
	public List<?> query(String sql) throws DaoException ;
	
	/**
	 * 使用ResultSetExtractor类来映射结果集中的每行记录到javabean中
	 * 不支持自定义结果集提取器,使用默认结果集提取器[ListExtractor4Bean]来解析结果集
	 * @param sql 静态sql
	 * @param beanClazz javabean的class类型
	 * @return 存储javabean的list对象
	 * @throws DaoException
	 */
	public List<?> query(String sql , Class<?> beanClazz) throws DaoException;
	
	/**
	 * 使用ResultSetExtractor类来映射结果集中的每行记录到javabean中
	 * 不支持自定义结果集提取器,使用默认结果集提取器[ListExtractor4Bean]来解析结果集
	 * @param sql 带占位符的sql
	 * @param params sql参数值数组
	 * @param beanClazz javabean的class类型 
	 * @return 存储javabean的list对象
	 * @throws DaoException
	 */
	public List<?> query(String sql , Object[] params, Class<?> beanClazz) throws DaoException;
	
	/**
	 * 自定义rowmapper实现类，要求要有意义的记录映射实现rowmapper
	 * @param sql 静态sql
	 * @param rowMapper 自定义的rowmapper实现类,示例代码如下：
	 * 
	 * List<?> resultList = jbdt.query(sql, new RowMapper<Object>(){
	 *	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	 *			TblSysUser user = new TblSysUser();
	 *
	 *			//定义同义不同名的属性映射map
	 *			Map<String,String> propertyMap = new HashMap<String,String>();
	 *			propertyMap.put("createdTime" , "CREATE_TIME");
	 *			propertyMap.put("updatedTime" , "UPDATE_TIME");
	 *			PopulateUtil.populate(user, rs ,propertyMap);
	 *			
	 *			return user;
	 *		}
	 *	});
	 *
	 * @return 存储javabean的list对象
	 * @throws DaoException
	 */
	public List<?> query(String sql, RowMapper<?> rowMapper) throws DaoException;
	
	/**
	 * 自定义rowmapper实现类，要求要有意义的记录映射实现rowmapper
	 * @param sql 带占位符的sql
	 * @param params sql参数值
	 * @param rowMapper 自定义的rowmapper实现类，示例代码如下：
	 * 
	 * List<?> resultList = jbdt.query(sql, new Object[]{userCode,password}, new RowMapper<Object>(){
	 *	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	 *			TblSysUser user = new TblSysUser();
	 *
	 *			//定义同义不同名的属性映射map
	 *			Map<String,String> propertyMap = new HashMap<String,String>();
	 *			propertyMap.put("createdTime" , "CREATE_TIME");
	 *			propertyMap.put("updatedTime" , "UPDATE_TIME");
	 *			PopulateUtil.populate(user, rs ,propertyMap);
	 *			
	 *			return user;
	 *		}
	 *	});
	 *
	 * @return 存储javabean的list对象
	 * @throws DaoException
	 */
	public List<?> query(String sql , Object[] params, RowMapper<?> rowMapper) throws DaoException;

	/**
	 * 分页查询 - 只支持mysql
	 * @param sql 静态sql
	 * @param rowMapper 自定义结果集抽取器，示例代码如上所示
	 * @param currentPage 当前页
	 * @param pageSize 每页记录数
	 * @return 分页对象
	 * @throws DaoException
	 */
	public VOResult query(String sql , RowMapper<?> rowMapper , int currentPage , int pageSize) throws DaoException;
	
	/**
	 * 分页查询 - 只支持mysql
	 * @param sql 带占位符的sql
	 * @param values sql参数数组
	 * @param rowMapper 自定义结果集抽取器，示例代码如上所示
	 * @param currentPage 当前页
	 * @param pageSize 每页记录数
	 * @return 分页对象
	 * @throws DaoException
	 */
	public VOResult query(String sql , Object[] params , RowMapper<?> rowMapper , int currentPage , int pageSize) 
			throws DaoException;
	
	/**
	 * 执行单个sql操作，如 insert delete update
	 * @param sql 静态sql
	 * @return
	 * @throws DaoException
	 */
	public int update(String sql) throws DaoException;
	
	/**
	 * 执行单个sql操作，如 insert delete update
	 * @param sql 带占位符的sql
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public int update(String sql , Object[] params) throws DaoException;
	
	/**
	 * 批量更新操作 如 insert delete update
	 * @param sql [insert into login values(?,?,?)] 
	 * @param Objects 
	 * @param setter 批处理statement预处理器,设置参数值等业务逻辑 ，如下面代码示例
	 *  BatchPreparedStatementSetter setter=null; 
	 *  //使用匿名内部类，实现BatchPreparedStatementSetter接口，实现批量更新，persons代表批量更新的对象
	 *  setter=new BatchPreparedStatementSetter(){
	 * 	 public int getBatchSize(){
	 *  		return persons.size();   
     *   }   
     *   public void setValues(PreparedStatement ps,int index) throws SQLException{   
     *       Person person=(Person)persons.get(index);   
     *       ps.setString(1,person.getName());   
     *       ps.setString(2,person.getPassword());   
     *       ps.setString(3,person.getAddress());   
     *   }   
     *  };   
	 * @return 受影响记录数的个数数组
	 * @throws DaoException
	 */
	public int[] batchUpdate(String sql , final List<?> Objects ,BatchPreparedStatementSetter setter) throws DaoException;

}
