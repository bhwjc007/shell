/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: SystemCacheInitializer.java $
 * $LastChangedDate: 2012-11-20 下午4:22:06 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.cache;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import shell.framework.cache.support.CacheUtil;
import shell.framework.core.SystemParam;
import shell.framework.dao.IJdbcBaseDao;
import shell.framework.model.TblSysDepartment;
import shell.framework.model.TblSysUser;
import shell.framework.util.PopulateUtil;

/**
 * <p> 系统缓存初始化器 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-11-20 下午4:22:06 $
 */
public class SystemCacheInitializer {
	
	private IJdbcBaseDao jdbcBaseDao;
	
	/**
	 * @return the jdbcBaseDao
	 */
	public IJdbcBaseDao getJdbcBaseDao() {
		return jdbcBaseDao;
	}

	/**
	 * @param jdbcBaseDao the jdbcBaseDao to set
	 */
	public void setJdbcBaseDao(IJdbcBaseDao jdbcBaseDao) {
		this.jdbcBaseDao = jdbcBaseDao;
	}
	
	
	/**
	 * 初始化各个缓存数据
	 */
	@SuppressWarnings("all")
	protected void initial(){
		System.out.println("Begin Loading Cache Data.....");
		
		//this.userInitial();
		this.departmentInitial();
		this.userRoleInitial();
		
		for(String cacheName : CacheUtil.getCacheNames()){
			System.out.println("CacheName=> [" + cacheName + "] : CacheSize=> [" + CacheUtil.getKeys(cacheName).size()+"] **************************************");
//			for (Iterator iterator = CacheUtil.getKeys(cacheName).iterator(); iterator.hasNext();) {
//				Object key = (Object) iterator.next();
//				Object value = CacheUtil.getValue(cacheName, key);
//				System.out.println(key.toString() + " : [" + (value==null ? "" : value.toString())+"]");
//			}
		}
	}
	
	/**
	 * 系统用户全部数据，放入缓存“userCache”内
	 * 缓存形如： <用户登录id,用户实例> => <usercode,user>
	 */
	@SuppressWarnings("all")
	public void userInitial(){
		String sql = "select * from TBL_SYS_USER where IS_VALID='"+ SystemParam.IS_VALID + "'";
		List<?> userList = jdbcBaseDao.query(sql,new RowMapper<Object>() {
			
			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
			 */
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				TblSysUser user = new TblSysUser();	
				Map<String,String> propertyMap = new HashMap<String,String>();
				propertyMap.put("createdTime" , "CREATE_TIME");
				propertyMap.put("updatedTime" , "UPDATE_TIME");
				PopulateUtil.populate(user, rs ,propertyMap);
				CacheUtil.putValue("userCache", user.getUserCode(), user);
				return user;
			}
		});
	}
	
	/**
	 * 系统所有部门数据放入缓存“departmentCache”内
	 * 缓存形如：<部门id,部门对象实例>  =>  <department_id,department>
	 */
	@SuppressWarnings("all")
	public void departmentInitial(){
		String sql = "select ID,DEPARTMENT_NAME,DEPARTMENT_TYPE,PARENT_ID,ORDER_NO from TBL_SYS_DEPARTMENT where IS_VALID='"+ SystemParam.IS_VALID + "'";
		List<?> departmentList = this.jdbcBaseDao.query(sql, new RowMapper<Object>(){
			
			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
			 */
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				TblSysDepartment department = new TblSysDepartment();
				Map<String,String> propertyMap = new HashMap<String,String>();
				propertyMap.put("departmentName", "DEPARTMENT_NAME");
				propertyMap.put("departmentType", "DEPARTMENT_TYPE");
				propertyMap.put("organizationID", "ORGANIZATION_ID");
				propertyMap.put("parentID", "PARENT_ID");
				propertyMap.put("orderID", "ORDER_NO");
				propertyMap.put("isValid", "IS_VALID");
				propertyMap.put("isVD", "IS_VD");
				PopulateUtil.populate(department,rs,propertyMap);
				CacheUtil.putValue("departmentCache", department.getId(), department);
				return department;
			}
		});
	}	
	
	
	/**
	 * 用户角色关系放入缓存“userRoleCache”中
	 * 取直接分配给用户的角色与分配给用户所在部分的角色进的并集，放入缓存中(只存角色id的list)
	 * 目前@SHELL框架中只针对用户和其所在的部门进行角色分配
	 * 缓存形如： <系统用户唯一id,用户角色与所在部门角色的ID并集>  => <user_id,roleIDs>  
	 */
	@SuppressWarnings("all")
	public void userRoleInitial(){
		//计算直接给用户分配的角色
		String sql = "select * from TBL_SYS_USER_ROLE";
		List<?> userRoleList = this.jdbcBaseDao.query(sql, new RowMapper<Object>(){
			String tempUserID = "";
			List<String> roleIDList = null;
			
			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
			 */
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				if(!tempUserID.equals(rs.getString("USER_ID"))){
					tempUserID = rs.getString("USER_ID");
					roleIDList = new ArrayList<String>();
					CacheUtil.putValue(CacheUtil.USER_ROLE_CACHE,rs.getString("USER_ID"),roleIDList);
				}
                Object _roleIdList = CacheUtil.getValue(CacheUtil.USER_ROLE_CACHE,rs.getString("USER_ID"));
                if(_roleIdList!=null){
                    ((List<String>)_roleIdList).add(rs.getString("ROLE_ID"));
                }
				return null;
			}
		});
		//计算给用户所在的部门分配的角色
		String depSQL = "select ud.USER_ID,rd.ROLE_ID from TBL_SYS_USER_DEPARTMENT ud join TBL_SYS_ROLE_DEPARTMENT rd on rd.DEPARTMENT_ID=ud.DEPARTMENT_ID";
		List<?> userDepRoleList = this.jdbcBaseDao.query(depSQL, new RowMapper<Object>(){
			String tempUserID = "";
			List<String> roleIDList = null;
			
			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
			 */
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				if(!tempUserID.equals(rs.getString("USER_ID"))){
					tempUserID = rs.getString("USER_ID");
					//如果用户对应的角色已经存在缓存中，就从缓存中获取对应的角色
					if(CacheUtil.getValue(CacheUtil.USER_ROLE_CACHE, tempUserID)!=null){
						roleIDList = (List)CacheUtil.getValue(CacheUtil.USER_ROLE_CACHE, tempUserID);
					//如果用户对应的角色不再缓存中，就加入缓存中
					}else{
						roleIDList = new ArrayList<String>();
						CacheUtil.putValue(CacheUtil.USER_ROLE_CACHE,rs.getString("USER_ID"),roleIDList);
					}
				}
				//如果给用户分配的角色中已经包含了用户所在部门所拥有的角色，就不再合并角色，否则合并
				if(!roleIDList.contains(rs.getString("ROLE_ID"))){
					roleIDList.add(rs.getString("ROLE_ID"));
				}
				return null;
			}
		});
	}
	
}
