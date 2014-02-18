package shell.framework.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import shell.framework.dao.IHibernateBaseDao;
import shell.framework.dao.support.DaoException;
import shell.framework.dao.support.VOResult;

/**
 * 通用持久层的hibernate具体实现
 * @author yangchangming
 *
 */
public class HibernateBaseDaoTemplate extends HibernateDaoSupport implements
		IHibernateBaseDao {
	
	private static Logger logger = Logger.getLogger(HibernateBaseDaoTemplate.class);
	
	/*
	 * (non-Javadoc)
	 * @see shell.framework.dao.IHibernateDao#create(java.lang.Object)
	 */
	public void save(Object entity) throws DaoException {
		try{
			this.getHibernateTemplate().save(entity);
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new DaoException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see shell.framework.dao.IHibernateDao#delete(java.lang.Object)
	 */
	public void delete(Object entity) throws DaoException {
		try{
			this.getHibernateTemplate().delete(entity);
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new DaoException(e);
		}
	}

	/* (non-Javadoc)
	 * @see shell.framework.dao.IHibernateBaseDao#delete(java.lang.Class, java.io.Serializable)
	 */
	public void delete(Class<?> clazz, Serializable id) throws DaoException {
		HibernateTemplate ht = this.getHibernateTemplate();
		try{
			Object entity = ht.load(clazz, id);
			ht.delete(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new DaoException(e);
		}
	}

	/* (non-Javadoc)
	 * @see shell.framework.dao.IHibernateDao#deleteAll(java.util.Collection)
	 */
	@SuppressWarnings("rawtypes")
	public void deleteAll(Collection entities) throws DaoException {
		try{
			this.getHibernateTemplate().deleteAll(entities);
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new DaoException(e);
		}
	}

	/* (non-Javadoc)
	 * @see shell.framework.dao.IHibernateDao#deleteAll(java.lang.Class)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void deleteAll(Class entityClazz) throws DaoException {
		List<Object> resultList = null;
		try{
			resultList = this.getHibernateTemplate().loadAll(entityClazz);
			deleteAll(resultList);
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new DaoException(e);
		}
	}

	/* (non-Javadoc)
	 * @see shell.framework.dao.IHibernateDao#saveOrUpdate(java.lang.Object)
	 */
	public void saveOrUpdate(Object entity) throws DaoException {
		try{
			this.getHibernateTemplate().saveOrUpdate(entity);
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new DaoException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see shell.framework.dao.IHibernateBaseDao#saveOrUpdate(java.util.Collection)
	 */
	@SuppressWarnings("rawtypes")
	public void saveOrUpdate(Collection entities) throws DaoException {
		try{
			this.getHibernateTemplate().saveOrUpdateAll(entities);
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new DaoException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see shell.framework.dao.IHibernateDao#update(java.lang.Object)
	 */
	public void update(Object entity) throws DaoException {
		try{
			this.getHibernateTemplate().update(entity);
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new DaoException(e);
		}
	}

	/* (non-Javadoc)
	 * @see shell.framework.dao.IHibernateBaseDao#load(java.io.Serializable, java.lang.Class)
	 */
	public Object load(Serializable id, Class<?> clazz) throws DaoException {
		Object obj = null;
		try{
			//查不到数据时，get不会报异常，load会报异常
			obj = this.getHibernateTemplate().get(clazz, id);
			return obj;
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new DaoException(e);
		}
	}

	/* (non-Javadoc)
	 * @see shell.framework.dao.IHibernateBaseDao#find(java.lang.String)
	 */
	public List<?> find(String queryString) throws DaoException {
		List<?> resultList = null;
		try{
			resultList = this.getHibernateTemplate().find(queryString);
			return resultList==null?null:resultList ;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new DaoException(e);
		}
	}

	/* (non-Javadoc)
	 * @see shell.framework.dao.IHibernateBaseDao#find(java.lang.String, java.lang.Object[])
	 */
	public List<?> find(String queryString, Object[] params) throws DaoException {
		List<?> resultList = null;
		try{
			if(params.length==0){
				throw new DaoException("查询语句参数值不能为空！");
			}
			resultList = this.getHibernateTemplate().find(queryString, params);
			return resultList==null?null:resultList ;
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new DaoException(e);
		}
	}

	/* (non-Javadoc)
	 * @see shell.framework.dao.IHibernateBaseDao#findAll(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public List<?> findAll(Class<?> clazz) throws DaoException {
		List<Object> resultList = null;
		try{
			resultList = this.getHibernateTemplate().find("from "+ clazz.getName());
			return resultList==null ? null : resultList;
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new DaoException(e);
		}
	}

	/* (non-Javadoc)
	 * @see shell.framework.dao.IHibernateBaseDao#find(java.lang.String, int, int)
	 */
	public VOResult find(final String queryString, final int currentPage, final int pageSize)
			throws DaoException {
		return (VOResult)this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				return buildVOResult(query,session,null,pageSize,currentPage);
			}
		});
	}

	/* (non-Javadoc)
	 * @see shell.framework.dao.IHibernateBaseDao#find(java.lang.String, java.lang.Object[], int, int)
	 */
	public VOResult find(final String queryString,final Object[] params, final int currentPage,
			final int pageSize) throws DaoException {
		return (VOResult)this.getHibernateTemplate().execute(new HibernateCallback<Object>(){
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				if(params!=null && params.length>0){
					for(int position=0;position<params.length;position++){
						query.setParameter(position, params[position]);
					}
				}else{
					return find(queryString, currentPage, pageSize);
				}
				return buildVOResult(query,session,params,pageSize,currentPage);
			}
		});
	}
	
	/**
	 * 构建分页对象
	 * @param query
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected VOResult buildVOResult(Query query ,Session session,Object[] params,int pageSize ,int currentPage){
		List<Object> resultList = null; 
		int totalRows = 0;
		totalRows = _count(query,session,params);
		
		query.setFirstResult((currentPage-1)*pageSize);
		query.setMaxResults(pageSize);
		resultList = query.list();
		
		VOResult voResult = new VOResult(resultList);
		voResult.setTotalRows(totalRows);
		voResult.setCurrentPage(currentPage);
		voResult.setPageSize(pageSize);
		
		return voResult;
	}
	
	
	/**
	 * 查询总记录数
	 * @param query
	 * @param session
	 * @return 总记录数
	 */
	private int _count(Query query,Session session,Object[] params){
		String queryStr = "";
		queryStr = query.getQueryString();
		String temp = queryStr.substring(queryStr.toLowerCase().indexOf("from"));
		queryStr = "SELECT COUNT(*) " + temp;
		Query _query = session.createQuery(queryStr);
		if(params!=null){
			for(int position=0;position<params.length;position++){
				_query.setParameter(position, params[position]);
			}
		}
		return ((Integer)_query.uniqueResult()).intValue();
	}
}
