/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: TblSysUserServiceImpl.java $
 * $LastChangedDate: 2012-5-17 下午4:44:14 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.organization.user.service.impl;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import shell.framework.core.SystemParam;
import shell.framework.dao.IJdbcBaseDao;
import shell.framework.dao.support.VOResult;
import shell.framework.model.TblSysRole;
import shell.framework.model.TblSysUser;
import shell.framework.organization.user.service.TblSysUserService;
import shell.framework.organization.user.vo.TblSysUserDetailVO;
import shell.framework.organization.user.vo.TblSysUserVO;
import shell.framework.util.PopulateUtil;
import shell.framework.util.UUIDGenerator;

/**
 * <p> 系统用户管理服务类JDBC实现 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-5-17 下午4:44:14 $
 */
public class TblSysUserService4JdbcImpl implements TblSysUserService {

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

	/* (non-Javadoc)
	 * @see shell.framework.organization.user.service.TblSysUserService#listAll()
	 */
	public VOResult findByPagination(int currentPage , int pageSize , TblSysUserVO userVO) {
		StringBuffer sql = new StringBuffer("select * from TBL_SYS_USER user");
		if(userVO!=null){
			sql.append(" where 1=1");
			//系统用户全称
			if(userVO.getFullName()!=null && !"".equals(userVO.getFullName())){
				sql.append(" and user.FULLNAME like '%" + userVO.getFullName().trim() +"%'");
			}
			//系统用户登录ID
			if(userVO.getUserCode()!=null && !"".equals(userVO.getUserCode())){
				sql.append(" and user.USERCODE='" + userVO.getUserCode().trim() +"'");
			}
			//系统用户手机号
			if(userVO.getTelephone()!=null && !"".equals(userVO.getTelephone())){
				sql.append(" and user.TELEPHONE='" + userVO.getTelephone().trim() +"'");
			}
		}
		
		VOResult voResult = jdbcBaseDao.query(sql.toString(), new RowMapper<Object>(){

			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
			 */
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				TblSysUser user = new TblSysUser();	
				Map<String,String> propertyMap = new HashMap<String,String>();
				propertyMap.put("createdTime" , "CREATE_TIME");
				propertyMap.put("updatedTime" , "UPDATE_TIME");
				PopulateUtil.populate(user, rs ,propertyMap);
				return user;
			}
		}, currentPage, pageSize);
		
		return voResult;
	}

	
	/* (non-Javadoc)
	 * @see shell.framework.organization.user.service.TblSysUserService#findUserByID(java.io.Serializable)
	 */
	public TblSysUser findUserByID(Serializable id) {
		String sql = "select * from TBL_SYS_USER user where user.ID = ?";
		List<?> resultList = jdbcBaseDao.query(sql, new Object[]{id}, new RowMapper<Object>() {
			
			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
			 */
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				TblSysUser user = new TblSysUser();	
				Map<String,String> propertyMap = new HashMap<String,String>();
				propertyMap.put("createdTime" , "CREATE_TIME");
				propertyMap.put("updatedTime" , "UPDATE_TIME");
				PopulateUtil.populate(user, rs ,propertyMap);
				return user;
			}
		});
		
		if(resultList==null || resultList.size()==0){
			throw new RuntimeException("NO DATA FROM DATABASE!");
		}
		return (TblSysUser)resultList.get(0);
	}

	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.user.service.TblSysUserService#findUserByUserCode(java.lang.String)
	 */
	public TblSysUser findUserByUserCode(String userCode) {
		String sql = "select * from TBL_SYS_USER user where user.USERCODE=?";
		List<?> resultList = jdbcBaseDao.query(sql, new Object[]{userCode}, new RowMapper<Object>(){
			
			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
			 */
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				TblSysUser user = new TblSysUser();
				
				Map<String,String> propertyMap = new HashMap<String,String>();
				propertyMap.put("createdTime" , "CREATE_TIME");
				propertyMap.put("updatedTime" , "UPDATE_TIME");
				PopulateUtil.populate(user, rs ,propertyMap);
				return user;
			}
		});
		return (resultList==null || resultList.size()==0) ? null : (TblSysUser)resultList.get(0);
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.user.service.TblSysUserService#findAllUser()
	 */
	public List<?> findAllUser() {
		String sql = "select * from TBL_SYS_USER";
		List<?> resultList = jdbcBaseDao.query(sql,new RowMapper<Object>() {
			
			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
			 */
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				TblSysUser user = new TblSysUser();	
				Map<String,String> propertyMap = new HashMap<String,String>();
				propertyMap.put("createdTime" , "CREATE_TIME");
				propertyMap.put("updatedTime" , "UPDATE_TIME");
				PopulateUtil.populate(user, rs ,propertyMap);
				return user;
			}
		});
		return resultList;
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.user.service.TblSysUserService#delete(shell.framework.organization.user.vo.TblSysUserVO)
	 */
	public int deleteByID(TblSysUserVO userVO) {
		String sql = "delete from TBL_SYS_USER where ID = ?";
		final List<String> idList = new ArrayList<String>();
		String ids[] = userVO.getId().split("-");
		for(String id : ids){
			idList.add(id);
		}
		
		//TODO 删除时 是假删除 同时不能删除已经被关联的用户，比如已经和部门关联，和工号关联，或者同时解除他们的关联关系
		
		int[] deleteNumbers = jdbcBaseDao.batchUpdate(sql, idList, new BatchPreparedStatementSetter(){
			
			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#getBatchSize()
			 */
			public int getBatchSize() {
				return idList.size();
			}
			
			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#setValues(java.sql.PreparedStatement, int)
			 */
			public void setValues(PreparedStatement ps, int index)	throws SQLException {
				String id = idList.get(index);
				ps.setString(1, id);
			}
		});
		
		return deleteNumbers.length;
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.user.service.TblSysUserService#add(shell.framework.organization.user.vo.TblSysUserVO)
	 */
	public int add(TblSysUserVO userVO) {
		String sql = "insert into TBL_SYS_USER values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return jdbcBaseDao.update(sql, new Object[]{UUIDGenerator.generate(),
													 userVO.getUserCode(), userVO.getPassword(),userVO.getPasswordDuration(),
													 userVO.getFullName(), userVO.getAddress(),userVO.getSex(),
													 userVO.getTelephone(),userVO.getMobile(),userVO.getEducation(),
													 userVO.getEmail(),userVO.getPostCode(),userVO.getPhoto(),
													 null,userVO.getStatus(),SystemParam.IS_VALID,userVO.getHireDate(),
													 userVO.getBirthday(),userVO.getRemark(), null,null,null
													});
	}
	
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.user.service.TblSysUserService#update(shell.framework.organization.user.vo.TblSysUserVO)
	 */
	public int update(TblSysUserVO userVO) {
		String sql = "update TBL_SYS_USER set USERCODE=?,PASSWORD=?,PASSWORDDURATION=?,FULLNAME=?,ADDRESS=?,SEX=?," +
					 "TELEPHONE=?,MOBILE=?,EDUCATION=?,EMAIL=?,POSTCODE=?,PHOTO=?,STATUS=?,HIREDATE=?,BIRTHDAY=?,REMARK=? where ID=?";
		return jdbcBaseDao.update(sql, new Object[]{userVO.getUserCode(),userVO.getPassword(),userVO.getPasswordDuration(),
													userVO.getFullName(),userVO.getAddress(), userVO.getSex(),userVO.getTelephone(),
													userVO.getMobile(),userVO.getEducation(),userVO.getEmail(),userVO.getPostCode(),
													userVO.getPhoto(),userVO.getStatus(),userVO.getHireDate(),userVO.getBirthday(),
													userVO.getRemark(),userVO.getId()} );
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.user.service.TblSysUserService#show(java.io.Serializable)
	 */
	public TblSysUserDetailVO show(Serializable id) {

		String sql = "select user.*,dept.DEPARTMENT_NAME,dept.DEPARTMENT_TYPE,dept.ORGANIZATION_ID,pos.POSITION_NAME " +
					 "from TBL_SYS_USER user join TBL_SYS_USER user1 on user.ID=user1.ID and user.ID=? " +
				     "left join TBL_SYS_USER_DEPARTMENT userDep on user.ID=userDep.USER_ID " +
				     "left join TBL_SYS_DEPARTMENT dept on userDep.DEPARTMENT_ID=dept.ID " +
				     "left join TBL_SYS_USER_POSITION userPos on user.ID=userPos.USER_ID " +
				     "left join TBL_SYS_POSITION pos on userPos.POSITION_ID=pos.ID";
		
		
		List<?> resultList = this.jdbcBaseDao.query(sql, new Object[]{id}, new RowMapper<Object>(){

			/*
			 * (non-Javadoc)
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
			 */
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				TblSysUserDetailVO sysUserDetailVO = new TblSysUserDetailVO();
				Map<String,String> propertiesMap = new HashMap<String,String>();
				propertiesMap.put("departmentName", "DEPARTMENT_NAME");
				propertiesMap.put("departmentType", "DEPARTMENT_TYPE");
				propertiesMap.put("organizationID", "ORGANIZATION_ID");
				propertiesMap.put("positionName", "POSITION_NAME");
				propertiesMap.put("roleName", "NAME");
				PopulateUtil.populate(sysUserDetailVO, rs, propertiesMap);
				return sysUserDetailVO;
			}
			
		});
		
		if(resultList==null || resultList.size()==0){
			throw new RuntimeException("NO DATA FROM DATABASE!");
		}
		return (TblSysUserDetailVO)resultList.get(0);
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.user.service.TblSysUserService#findRoleByPagination(int, int, shell.framework.organization.user.vo.TblSysUserVO)
	 */
	public VOResult findAssignRoleByPagination(int currentPage, int pageSize, TblSysUserVO userVO) {
		StringBuffer sql = new StringBuffer("select r.* from TBL_SYS_ROLE r inner join TBL_SYS_USER_ROLE ur on " +
				     "r.ID=ur.ROLE_ID and r.IS_VALID = 'T' and ur.USER_ID='" + userVO.getId().trim() + "'");
		if(userVO.getRole()!=null && userVO.getRole().getName()!=null && userVO.getRole().getName().trim()!=null && !"".equals(userVO.getRole().getName().trim()) ){
			sql.append(" and r.NAME like '%" + userVO.getRole().getName().trim() + "%'");
		}
		
		VOResult voResult = jdbcBaseDao.query(sql.toString(), new RowMapper<Object>(){
			
			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
			 */
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				TblSysRole sysRole = new TblSysRole();
				Map<String,String> propertyMap = new HashMap<String,String>();
				propertyMap.put("isValid" , "IS_VALID");
				propertyMap.put("isVirtual" , "IS_VIRTUAL");
				PopulateUtil.populate(sysRole, rs, propertyMap);
				return sysRole;
			}
		}, currentPage, pageSize);
		return voResult;
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.user.service.TblSysUserService#findUnAssignRoleByPagination(int, int, shell.framework.organization.user.vo.TblSysUserVO)
	 */
	public VOResult findUnAssignRoleByPagination(int currentPage, int pageSize,	TblSysUserVO userVO) {
		//获取已经分配给当前用户以外的所有角色对象
		StringBuffer sql = new StringBuffer("select * from TBL_SYS_ROLE role where role.ID not in " +
											"(select ROLE_ID from TBL_SYS_USER_ROLE where USER_ID='" + 
											userVO.getId() +"') and role.IS_VALID = 'T' "  );
		if(userVO !=null && userVO.getRole()!=null && userVO.getRole().getName()!=null && !"".equals(userVO.getRole().getName().trim())){
			sql.append(" and role.NAME like '%" + userVO.getRole().getName().trim() + "%'");
		}
		VOResult voResult = jdbcBaseDao.query(sql.toString(), new RowMapper<Object>(){
			
			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
			 */
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				TblSysRole role =  new TblSysRole();
				Map<String,String> propertyMap = new HashMap<String,String>();
				propertyMap.put("isValid" , "IS_VALID");
				propertyMap.put("isVirtual" , "IS_VIRTUAL");
				PopulateUtil.populate(role, rs ,propertyMap);
				return role;
			}
			
		}, currentPage, pageSize);
		return voResult;
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.user.service.TblSysUserService#assignSysRole(shell.framework.organization.user.vo.TblSysUserVO)
	 */
	public int assignSysRole(TblSysUserVO userVO) {
		String sql = "insert into TBL_SYS_USER_ROLE values (?,?)";
		final String userID = userVO.getId();
		String[] roleIDs = userVO.getRole().getId().split("-");
		final List<String> roleIDList = Arrays.asList(roleIDs);
		
		int[] deleteNumbers = jdbcBaseDao.batchUpdate(sql, roleIDList, new BatchPreparedStatementSetter() {
			/*
			 * (non-Javadoc)
			 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#setValues(java.sql.PreparedStatement, int)
			 */
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				ps.setString(1, userID);	
				ps.setString(2, roleIDList.get(index));
			}
			
			/*
			 * (non-Javadoc)
			 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#getBatchSize()
			 */
			public int getBatchSize() {
				return roleIDList.size();
			}
		});
		return deleteNumbers.length;
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.user.service.TblSysUserService#unAssignSysRole(shell.framework.organization.user.vo.TblSysUserVO)
	 */
	public int unAssignSysRole(TblSysUserVO userVO) {
		String sql = "delete from TBL_SYS_USER_ROLE where USER_ID=? and ROLE_ID=?";
		final String userID =  userVO.getId();
		String sysRoleIds[] = userVO.getRole().getId().split("-");
		final List<String> idList = Arrays.asList(sysRoleIds);
		
		int[] deleteNumbers = jdbcBaseDao.batchUpdate(sql, idList, new BatchPreparedStatementSetter() {
			
			/*
			 * (non-Javadoc)
			 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#setValues(java.sql.PreparedStatement, int)
			 */
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				String sysRole_id = idList.get(index);
				ps.setString(1, userID);
				ps.setString(2, sysRole_id);
			}
			
			/*
			 * (non-Javadoc)
			 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#getBatchSize()
			 */
			public int getBatchSize() {
				return idList.size();
			}
		});
		return deleteNumbers.length;
	}
	
}
