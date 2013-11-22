package shell.framework.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import shell.framework.dao.support.DaoException;
import shell.framework.dao.support.VOResult;

/**
 * 通用持久层的hibernate实现接口
 * @author yangchangming
 *
 */
public interface IHibernateBaseDao {

	/**
	 * 保存游离态实例
	 * @param entity 游离态对象
	 * @throws DaoException
	 */
	public void save(Object entity) throws DaoException;
	
	/**
	 * 删除持久态实例
	 * @param entity 持久态对象
	 * @throws DaoException
	 */
	public void delete(Object entity) throws DaoException;
	
	/**
	 * 删除持久态实例
	 * @param clazz 实例类型
	 * @param id 实例id
	 * @throws DaoException
	 */
	public void delete(Class<?> clazz,Serializable id) throws DaoException;
	
	/**
	 * 删除集合内的所有持久态实例
	 * @param entities 持久态对象集合
	 * @throws DaoException
	 */
	@SuppressWarnings("rawtypes")
	public void deleteAll(Collection entities) throws DaoException;
	
	/**
	 * 删除指定类型的持久态实例(先加载为持久太实例，后再删除)
	 * @param clazz 实例类型
	 * @throws DaoException
	 */
	@SuppressWarnings("rawtypes")
	public void deleteAll(Class clazz) throws DaoException;
	
	/**
	 * 保存或更新对象（游离或者脱管态）
	 * @param entity 游离或者脱管态对象
	 * @throws DaoException
	 */
	public void saveOrUpdate(Object entity) throws DaoException;
	
	/**
	 * 保存或更新集合中所有对象（游离或者脱管态）
	 * @param entities 游离或者脱管态对象的集合
	 * @throws DaoException
	 */
	@SuppressWarnings("rawtypes")
	public void saveOrUpdate(Collection entities) throws DaoException;
	
	/**
	 * 更新脱管态或持久态实例-除非对象跨session，否则一般不会显式调用
	 * @param entity 持久态或脱管态对象
	 * @throws DaoException
	 */
	public void update(Object entity) throws DaoException;
	
	/**
	 * 根据对象类型和id，查询对象
	 * @param id 主键
	 * @param clazz 对象类型
	 * @return 对象实例
	 * @throws DaoException
	 */
	public Object load(Serializable id , Class<?> clazz) throws DaoException;
	
	/**
	 * 根据hql查询语句查询对象
	 * @param queryString hql查询语句
	 * @return 对象集合
	 * @throws DaoException
	 */
	public List<?> find(String queryString) throws DaoException;
	
	/**
	 * 根据带参数的hql查询语句查询对象
	 * @param queryString 带参数(?)的hql查询语句
	 * @param params hql查询语句参数值数组
	 * @return 对象集合
	 * @throws DaoException
	 */
	public List<?> find(String queryString , Object[] params) throws DaoException;
	
	/**
	 * 根据对象类型查询对象
	 * @param clazz 查询对象类型
	 * @return 对象集合
	 * @throws DaoException
	 */
	public List<?> findAll(Class<?> clazz) throws DaoException;
	
	/**
	 * 根据hql查询结果，分页查询
	 * @param queryString 查询hql 形如[from Page]
	 * @param currentPage 当前查询页码
	 * @param pageSize 查询的记录数
	 * @return VOResult 分页对象，封装了结果集
	 * @throws DaoException
	 */
	public VOResult find(String queryString , int currentPage , int pageSize) throws DaoException;
	
	/**
	 * 根据带参数的hql查询语句进行分页查询
	 * @param queryString 带参数的hql查询语句，形如 [from Page where id = ? and title = ?]
	 * @param params 参数值
	 * @param currentPage 当前查询页码
	 * @param pageSize 查询的记录数
	 * @return VOResult 分页对象，封装了结果集
	 * @throws DaoException
	 */
	public VOResult find(String queryString , Object[] params , int currentPage , int pageSize) throws DaoException;
}
