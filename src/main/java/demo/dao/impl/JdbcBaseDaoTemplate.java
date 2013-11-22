package shell.framework.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.JdbcUtils;
import shell.framework.dao.IJdbcBaseDao;
import shell.framework.dao.support.DaoException;
import shell.framework.dao.support.ListExtractor4Bean;
import shell.framework.dao.support.ListExtractor4Map;
import shell.framework.dao.support.VOResult;

/**
 * 
 * <p> 通用持久层JDBC实现 , 支持用户自定义结果集提取器</p>
 * <p> 自定义结果集提取器必须继承 ResultSetExtractor </p>
 * <p> 支持2种结果集解析方式：ResultSetExtractor 和 RowMapper </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-4-16 上午9:48:06 $
 */
public class JdbcBaseDaoTemplate extends JdbcDaoSupport implements IJdbcBaseDao {
	
	private Logger logger = Logger.getLogger(JdbcBaseDaoTemplate.class);

	/* (non-Javadoc)
	 * @see shell.framework.dao.IJdbcBaseDao#query(java.lang.String, org.springframework.jdbc.core.ResultSetExtractor)
	 */
	@SuppressWarnings("all")
	public List<?> query(String sql, ResultSetExtractor<?> extractor) throws DaoException {
		try{
			JdbcTemplate jt = this.getJdbcTemplate();
			if(extractor == null){
				//缺省结果集抽取器ListExtractor4Map
				return (List)jt.query(sql, new ListExtractor4Map());
			}else{
				return (List)jt.query(sql, extractor);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new DaoException(e);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.dao.IJdbcBaseDao#query(java.lang.String, java.lang.Object[], org.springframework.jdbc.core.ResultSetExtractor)
	 */
	public List<?> query(String sql, Object[] params, ResultSetExtractor<?> extractor) throws DaoException {
		try{
			JdbcTemplate jt = this.getJdbcTemplate();
			if(extractor == null){
				//缺省结果集抽取器ListExtractor4Map
				return (List<?>)jt.query(sql, params, new ListExtractor4Map());
			}else{
				return (List<?>)jt.query(sql, params, extractor);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new DaoException(e);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.dao.IJdbcBaseDao#query(java.lang.String, java.lang.Class, org.springframework.jdbc.core.ResultSetExtractor)
	 */
	@SuppressWarnings("all")
	public List<?> query(String sql, Class<?> beanClazz,ResultSetExtractor<?> extractor) throws DaoException {
		try{
			JdbcTemplate jt = this.getJdbcTemplate();
			if(extractor==null){
				//缺省结果集抽取器ListExtractor4Bean
				return (List)jt.query(sql, new ListExtractor4Bean(beanClazz));
			}else{
				return (List)jt.query(sql, extractor);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new DaoException(e);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.dao.IJdbcBaseDao#query(java.lang.String, java.lang.Object[], java.lang.Class, org.springframework.jdbc.core.ResultSetExtractor)
	 */
	@SuppressWarnings("all")
	public List<?> query(String sql, Object[] params, Class<?> beanClazz,ResultSetExtractor<?> extractor) throws DaoException{
		try{
			JdbcTemplate jt = this.getJdbcTemplate();
			if(extractor==null){
				//缺省的结果集抽取器实现ListExtractor4Bean
				return (List)jt.query(sql, params , new ListExtractor4Bean(beanClazz));
			}else{
				return (List)jt.query(sql, params, extractor);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new DaoException(e);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.dao.IJdbcBaseDao#query(java.lang.String, org.springframework.jdbc.core.RowMapper)
	 */
	public List<?> query(String sql) throws DaoException {
		JdbcTemplate jt = this.getJdbcTemplate();
		return (List<?>)jt.query(sql, new RowMapper<Object>(){
			
			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
			 */
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map<String,Object> rowsMap = new HashMap<String,Object>();
				int columnNum = rs.getMetaData().getColumnCount();
				for(int i=1;i<=columnNum;i++){
					rowsMap.put(rs.getMetaData().getColumnName(i), JdbcUtils.getResultSetValue(rs, i));
				}
				
				return rowsMap;
			}
		});	
	}
	
	
	
	/* (non-Javadoc)
	 * @see shell.framework.dao.IJdbcBaseDao#query(java.lang.String, java.lang.Class)
	 */
	public List<?> query(String sql, final Class<?> beanClazz) throws DaoException {
		return query(sql, beanClazz, null);
	}
	
	
	
	/* (non-Javadoc)
	 * @see shell.framework.dao.IJdbcBaseDao#query(java.lang.String, java.lang.Class)
	 */
//	public List<?> query(String sql, final Class<?> beanClazz) throws DaoException {
//		JdbcTemplate jt = this.getJdbcTemplate();
//		if(beanClazz==null){
//			logger.error("class of the bean can not be null!");
//			throw new DaoException("class of the bean can not be null!");
//		}
//		return jt.query(sql, new RowMapper<Object>(){
//			
//			
//			/* (non-Javadoc)
//			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
//			 */
//			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//					
//				long startMillis = 0;
//				startMillis = System.currentTimeMillis();
//				
//				try {
//					Object bean = beanClazz.newInstance();
//					Field[] fields  = beanClazz.getDeclaredFields();
//					for(Field field : fields){
//						field.getName().toLowerCase();
//						
//						// TODO 字符串序列的比较算法....
//					}
//					
//					ResultSetMetaData rsdm = rs.getMetaData();
//					int columnCount = rsdm.getColumnCount();
//					for(int i=1;i<=columnCount;i++){
//						String columnName = JdbcUtils.lookupColumnName(rsdm, i);
//						columnName.toLowerCase();
//						Object columnValue = JdbcUtils.getResultSetValue(rs, i);
//						
//						
//					}
//					
//					
//					
//					long endMillis = 0;
//					endMillis = System.currentTimeMillis();
//					long spendMillis = endMillis - startMillis;
//					System.out.println("**********The time of converting single row [ " + rowNum + "] to javaBean is " + spendMillis + " millis");
//					
//				return bean;
//
//				} catch (InstantiationException e) {
//					logger.error(e.getMessage() + "| bean instance failure!");
//					throw new DaoException(e);
//				} catch (IllegalAccessException e) {
//					logger.error(e.getMessage() + "| bean instance failure!");
//					throw new DaoException(e);
//				}
//			}
//		});
//	}
	
	
	
	/* (non-Javadoc)
	 * @see shell.framework.dao.IJdbcBaseDao#query(java.lang.String, java.lang.Object[], java.lang.Class)
	 */
	public List<?> query(String sql, Object[] params,final Class<?> beanClazz) throws DaoException {
		return query(sql, params, beanClazz,null);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see shell.framework.dao.IJdbcBaseDao#query(java.lang.String, java.lang.Object[], java.lang.Class)
	 */
//	public List<?> query(String sql, Object[] params,final Class<?> beanClazz) throws DaoException {
//		JdbcTemplate jt = this.getJdbcTemplate();
//		if(beanClazz==null){
//			logger.error("class of the bean can not be null!");
//			throw new DaoException("class of the bean can not be null!");
//		}
//		
//		return jt.query(sql, params, new RowMapper<Object>(){
//			
//			/* (non-Javadoc)
//			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
//			 */
//			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//				// TODO 结果集记录到javabean的自动映射，可以考虑使用PopulateUtil工具
//				// TODO  也考虑使用上面的方法来实现bean属性和结果集记录字段的映射
//				return null;
//			}
//		});
//	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.dao.IJdbcBaseDao#query(java.lang.String, org.springframework.jdbc.core.RowMapper)
	 */
	public List<?> query(String sql, RowMapper<?> rowMapper) throws DaoException {
		JdbcTemplate jt = this.getJdbcTemplate();
		return jt.query(sql, rowMapper);
	}	
	
	
	/* (non-Javadoc)
	 * @see shell.framework.dao.IJdbcBaseDao#query(java.lang.String, java.lang.Object[], org.springframework.jdbc.core.RowMapper)
	 */
	public List<?> query(String sql, Object[] params, RowMapper<?> rowMapper) throws DaoException {
		JdbcTemplate jt = this.getJdbcTemplate();
		return jt.query(sql, params, rowMapper);
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.dao.IJdbcBaseDao#query(java.lang.String, org.springframework.jdbc.core.RowMapper, int, int)
	 */
	public VOResult query(String sql, RowMapper<?> rowMapper, int currentPage, int pageSize) 
			throws DaoException {
		if(currentPage<=0){
			currentPage = 1;
		}
		JdbcTemplate jt = this.getJdbcTemplate();
		List<?> resultList = jt.query(_buildPageSql(sql,currentPage,pageSize), rowMapper);
		
		int totalRows = _getCount(sql);
		VOResult voResult = new VOResult(resultList);
		voResult.setTotalRows(totalRows);
		voResult.setPageSize(pageSize);
		voResult.setCurrentPage(currentPage);
		return voResult;
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.dao.IJdbcBaseDao#query(java.lang.String, java.lang.Object[], org.springframework.jdbc.core.RowMapper, int, int)
	 */
	public VOResult query(String sql, Object[] params, RowMapper<?> rowMapper, int currentPage, int pageSize) 
			throws DaoException {
		if(currentPage<=0){
			currentPage = 1;
		}
		JdbcTemplate jt = this.getJdbcTemplate();
		List<?> resultList  = jt.query(_buildPageSql(sql,currentPage,pageSize),params,rowMapper);
		
		int totalRows = _getCount(sql,params);
		VOResult voResult = new VOResult(resultList);
		voResult.setTotalRows(totalRows);
		voResult.setPageSize(pageSize);
		voResult.setCurrentPage(currentPage);
		return voResult;
	}
	
	
	/**
	 * 获得总记录数
	 * @param sql
	 * @return
	 * @throws DaoException
	 */
	private int _getCount(String sql) throws DaoException{
		return this._getCount(sql, null);
	}
	
	
	/**
	 * 获得总记录数
	 * @param sql
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	private int _getCount(String sql , Object[] params) throws DaoException{
		int count = 0;
		JdbcTemplate jt = this.getJdbcTemplate();
		int index = sql.toLowerCase().indexOf("from");
		if(index==-1){
			logger.error("SQL is spelling error!" + sql);
			throw new DaoException("SQL is spelling error!");
		}
		String pageSQL = "select count(1) " + sql.substring(index);
		
		logger.info("Executing SQL count [" + pageSQL + "]");
		
		if(params!=null){
			count = jt.queryForInt(pageSQL, params);
		}else{
			count = jt.queryForInt(pageSQL);
		}
		return count;
	}
	
	
	/**
	 * 构建mysql分页sql
	 * @param sql 静态sql
	 * @param currentPage 当前页码
	 * @param pageSize 每页记录数
	 * @return
	 */
	private String _buildPageSql(String sql , int currentPage , int pageSize){
		StringBuffer sb = new StringBuffer(sql);
		sb.append(" limit ");
		sb.append((currentPage-1)*pageSize);
		sb.append(" , ");
		sb.append(pageSize);
		logger.info("Executing SQL query [" + sb.toString() + "]");
		return sb.toString();
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.dao.IJdbcBaseDao#update(java.lang.String)
	 */
	public int update(String sql) throws DaoException {
		return this.update(sql, null);
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.dao.IJdbcBaseDao#update(java.lang.String, java.lang.Object[])
	 */
	public int update(String sql, Object[] params) throws DaoException {
		try{
			JdbcTemplate jt = this.getJdbcTemplate();
			logger.info("Executing SQL ["  + sql +"]");
			if(params==null){
				return jt.update(sql);
			}else{
				return jt.update(sql, params);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.dao.IJdbcBaseDao#batchUpdate(java.lang.String, java.util.List)
	 */
	public int[] batchUpdate(String sql, final List<?> Objects , BatchPreparedStatementSetter setter) throws DaoException {
		try{
			JdbcTemplate jt = this.getJdbcTemplate();
			logger.info("Batch Executing SQL ["  + sql +"]");
			return jt.batchUpdate(sql, setter);
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new DaoException(e);
		}
	}
}
