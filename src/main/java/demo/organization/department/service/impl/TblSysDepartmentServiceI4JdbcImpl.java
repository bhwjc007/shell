/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: TblSysDepartmentServiceImpl.java $
 * $LastChangedDate: 2012-6-28 下午8:59:32 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.organization.department.service.impl;

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
import shell.framework.model.TblSysDepartment;
import shell.framework.model.TblSysRole;
import shell.framework.model.TblSysUser;
import shell.framework.organization.department.service.TblSysDepartmentService;
import shell.framework.organization.department.vo.TblSysDepartmentVO;
import shell.framework.util.PopulateUtil;
import shell.framework.util.UUIDGenerator;

/**
 * <p> 系统部门服务实现类 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-6-28 下午8:59:32 $
 */
public class TblSysDepartmentServiceI4JdbcImpl implements TblSysDepartmentService {

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
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#findByPagination(int, int, shell.framework.organization.department.vo.TblSysDepartmentVO)
	 */
	public VOResult findByPagination(int currentPage, int pageSize,TblSysDepartmentVO departmentVO) {
		StringBuffer sql = new StringBuffer("select * from TBL_SYS_DEPARTMENT department");
		sql.append(" where department.IS_VALID = '" + SystemParam.IS_VALID + "'");
		//部门名称
		if(departmentVO.getDepartmentName()!=null && !"".equals(departmentVO.getDepartmentName()) ){
			sql.append(" and department.DEPARTMENT_NAME like '%" + departmentVO.getDepartmentName().trim() + "%'");
		}
		//部门类型	
		if(departmentVO.getDepartmentType()!=null && !"".equals(departmentVO.getDepartmentType()) ){
			sql.append(" and department.DEPARTMENT_TYPE = '" + departmentVO.getDepartmentType().trim() + "'");
		}
		//是否虚拟部门
		if(departmentVO.getIsVD()!=null && !"".equals(departmentVO.getIsVD()) ){
			sql.append(" and department.IS_VD = '" + departmentVO.getIsVD().trim() + "'");
		}
		
		VOResult voResult = jdbcBaseDao.query(sql.toString(), new RowMapper<Object>(){
			
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
				return department;
			}
			
		}, currentPage, pageSize);
		return voResult;
	}

	
	/* (non-Javadoc)
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#findDepartmentByID(java.io.Serializable)
	 */
	public TblSysDepartment findDepartmentByID(Serializable id) {
		String sql = "select * from TBL_SYS_DEPARTMENT department where department.ID = ?";
		List<?> resultList = jdbcBaseDao.query(sql,new Object[]{id} , new RowMapper<Object>(){
			
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
				PopulateUtil.populate(department, rs, propertyMap);
				return department;
			}
		});
		if(resultList==null || resultList.size()==0){
			throw new RuntimeException("NO DATA FROM DATABASE!");
		}
		return (TblSysDepartment)resultList.get(0);
	}

	
	/* (non-Javadoc)
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#deleteByID(shell.framework.organization.department.vo.TblSysDepartmentVO)
	 */
	public int deleteByID(TblSysDepartmentVO departmentVO) {
		String sql = "delete from TBL_SYS_DEPARTMENT where ID = ?";
		final List<String> idList = new ArrayList<String>();
		String ids[] = departmentVO.getId().split("-");
		for(String id : ids){
			if(!hasSysUser(id) && !hasSysPosition(id)){
				idList.add(id);
			}
		}
		
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
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#add(shell.framework.organization.department.vo.TblSysDepartmentVO)
	 */
	public int add(TblSysDepartmentVO departmentVO) {
		String sql = "insert into TBL_SYS_DEPARTMENT values (?,?,?,?,?,?,?,?,?,?,?,?)";
		return jdbcBaseDao.update(sql, new Object[]{UUIDGenerator.generate(),
					departmentVO.getDepartmentName(), departmentVO.getDepartmentType(),departmentVO.getOrganizationID(),
					departmentVO.getParentID(),departmentVO.getOrderID(),departmentVO.getIsValid(),departmentVO.getIsVD(),
					departmentVO.getRemark(),departmentVO.getCreateTime(),departmentVO.getUpdateTime(),departmentVO.getCreator()
				});
	}

	
	/* (non-Javadoc)
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#update(shell.framework.organization.department.vo.TblSysDepartmentVO)
	 */
	public int update(TblSysDepartmentVO departmentVO) {
		String sql = "update TBL_SYS_DEPARTMENT set DEPARTMENT_NAME=? , DEPARTMENT_TYPE=? , ORGANIZATION_ID=? ," +
				"PARENT_ID=? where ID = ?";
		return jdbcBaseDao.update(sql, new Object[]{departmentVO.getDepartmentName(),departmentVO.getDepartmentType(),
				departmentVO.getOrganizationID(),departmentVO.getParentID(),departmentVO.getId()});
	}

	
	/* (non-Javadoc)
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#findPositionByPagination(int, int, java.io.Serializable)
	 */
	public VOResult findPositionByPagination(int currentPage, int pageSize,	Serializable departmentId) {
		String sql = "select * from TBL_SYS_POSITION position,TBL_SYS_DEPARTMENT_POSITION deparment_position where " +
				"deparment_position.DEPARTMENT_ID = ? and " +
				"position.ID = department_position.POSITION_ID and position.IS_VALID = 'T' ";
		VOResult voResult = jdbcBaseDao.query(sql, new Object[]{departmentId}, new RowMapper<Object>(){
			
			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
			 */
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				TblSysDepartment department = new TblSysDepartment();
				Map<String,String> propertyMap = new HashMap<String,String>();
//				propertyMap.put("departmentName", "DEPARTMENT_NAME");
//				propertyMap.put("departmentType", "DEPARTMENT_TYPE");
//				propertyMap.put("organizationID", "ORGANIZATION_ID");
//				propertyMap.put("parentID", "PARENT_ID");
//				propertyMap.put("orderID", "ORDER_NO");
//				propertyMap.put("isValid", "IS_VALID");
//				propertyMap.put("isVD", "IS_VD");
				PopulateUtil.populate(department, rs, propertyMap);
				return department;
			}
		}, currentPage, pageSize);
		
		return voResult;
	}

	
	/* (non-Javadoc)
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#assignPosition(java.io.Serializable, java.lang.String[])
	 */
	public int assignPosition(final String departmentId, String[] positionIds) {
		String sql = "insert into TBL_SYS_DEPARTMENT_POSITION values (?,?)";
		final List<String> idList = new ArrayList<String>();
		for(String id : positionIds){
			idList.add(id);
		}
		
		int[] deleteNumbers = jdbcBaseDao.batchUpdate(sql, idList, new BatchPreparedStatementSetter() {
			
			/*
			 * (non-Javadoc)
			 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#setValues(java.sql.PreparedStatement, int)
			 */
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				String position_id = idList.get(index);
				ps.setString(1, departmentId);
				ps.setString(2, position_id);
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

	
	/* (non-Javadoc)
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#assignSysUser(java.lang.String, java.lang.String[])
	 */
	public int assignSysUser(TblSysDepartmentVO departmentVO) {
		String sql = "insert into TBL_SYS_USER_DEPARTMENT values (?,?)";
		final String departmentID =  departmentVO.getId();
		String sysUserIds[] = departmentVO.getUser().getId().split("-");
		final List<String> idList = new ArrayList<String>();
		for(String id : sysUserIds){
			idList.add(id);
		}
		
		int[] deleteNumbers = jdbcBaseDao.batchUpdate(sql, idList, new BatchPreparedStatementSetter() {
			
			/*
			 * (non-Javadoc)
			 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#setValues(java.sql.PreparedStatement, int)
			 */
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				String sysUser_id = idList.get(index);
				ps.setString(1, sysUser_id);
				ps.setString(2, departmentID);
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
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#assignSysRole(shell.framework.organization.department.vo.TblSysDepartmentVO)
	 */
	public int assignSysRole(TblSysDepartmentVO departmentVO) {
		String sql = "insert into TBL_SYS_ROLE_DEPARTMENT values (?,?)";
		final String departmentID =  departmentVO.getId();
		String sysRoleIds[] = departmentVO.getRole().getRole().getId().split("-");
		final List<String> idList = Arrays.asList(sysRoleIds);
		
		int[] deleteNumbers = jdbcBaseDao.batchUpdate(sql, idList, new BatchPreparedStatementSetter() {
			
			/*
			 * (non-Javadoc)
			 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#setValues(java.sql.PreparedStatement, int)
			 */
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				String sysRole_id = idList.get(index);
				ps.setString(1, sysRole_id);
				ps.setString(2, departmentID);
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
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#removeSysUser(java.lang.String, java.lang.String[])
	 */
	public int unassignSysUser(final String departmentId, String[] sysUserIds) {
		String sql = "delete user_department from TBL_SYS_USER_DEPARTMENT user_department where user_department.USER_ID=? and user_department.DEPARTMENT_ID=?";
		final List<String> idList = new ArrayList<String>();
		for(String id : sysUserIds){
			idList.add(id);
		}
		
		int[] deleteNumbers = jdbcBaseDao.batchUpdate(sql, idList, new BatchPreparedStatementSetter() {
			
			/*
			 * (non-Javadoc)
			 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#setValues(java.sql.PreparedStatement, int)
			 */
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				String sysUser_id = idList.get(index);
				ps.setString(1, sysUser_id);
				ps.setString(2, departmentId);
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
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#removePosition(java.lang.String, java.lang.String[])
	 */
	public int unassignPosition(final String departmentId, String[] positionIds) {
		String sql = "delete from TBL_SYS_DEPARTMENT_POSITION department_position where department_position.DEPARTMENT_ID=? and department_position.POSITION_ID=?";
		final List<String> idList = new ArrayList<String>();
		for(String id : positionIds){
			idList.add(id);
		}
		
		int[] deleteNumbers = jdbcBaseDao.batchUpdate(sql, idList, new BatchPreparedStatementSetter() {
			
			/*
			 * (non-Javadoc)
			 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#setValues(java.sql.PreparedStatement, int)
			 */
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				String position_id = idList.get(index);
				ps.setString(1, departmentId);
				ps.setString(2, position_id);
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
	
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#unAssignSysRole(java.lang.String, shell.framework.organization.department.vo.TblSysDepartmentVO)
	 */
	public int unAssignSysRole(TblSysDepartmentVO departmentVO) {
		String sql = "delete from TBL_SYS_ROLE_DEPARTMENT where ROLE_ID=? and DEPARTMENT_ID=?";
		final String departmentID =  departmentVO.getId();
		String sysRoleIds[] = departmentVO.getRole().getRole().getId().split("-");
		final List<String> idList = Arrays.asList(sysRoleIds);
		
		int[] deleteNumbers = jdbcBaseDao.batchUpdate(sql, idList, new BatchPreparedStatementSetter() {
			
			/*
			 * (non-Javadoc)
			 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#setValues(java.sql.PreparedStatement, int)
			 */
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				String sysRole_id = idList.get(index);
				ps.setString(1, sysRole_id);
				ps.setString(2, departmentID);
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
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#findUserByPagination(int, int, java.io.Serializable)
	 */
	public VOResult findUserByPagination(int currentPage, int pageSize,	TblSysDepartmentVO departmentVO) {
		StringBuffer sql = new StringBuffer("select * from TBL_SYS_USER user , TBL_SYS_USER_DEPARTMENT user_department where user.ID=user_department.USER_ID and " +
				" user_department.DEPARTMENT_ID='" + departmentVO.getId().trim() + "' and user.IS_VALID= 'T' ");
		
		if(departmentVO.getUser()!=null && departmentVO.getUser().getFullName()!=null && !"".equals(departmentVO.getUser().getFullName().trim())){
			sql.append(" and user.FULLNAME like '%" + departmentVO.getUser().getFullName().trim() + "%'");
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
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#findRoleByPagination(int, int, shell.framework.organization.department.vo.TblSysDepartmentVO)
	 */
	public VOResult findRoleByPagination(int currentPage, int pageSize,TblSysDepartmentVO departmentVO) {
		StringBuffer sql = new StringBuffer("select * from TBL_SYS_ROLE role , TBL_SYS_ROLE_DEPARTMENT rd where rd.DEPARTMENT_ID='"+ departmentVO.getId().trim() +"' and" +
				" role.ID=rd.ROLE_ID and role.IS_VALID='T'");
		if(departmentVO.getRole()!=null && departmentVO.getRole().getRole()!=null && !"".equals(departmentVO.getRole().getRole().getName().trim())){
			sql.append(" and role.NAME like '%"+ departmentVO.getRole().getRole().getName().trim() +"%'");
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
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#findUserByUnbindDepartment(int, int, java.io.Serializable)
	 */
	public VOResult findUserByUnbindDepartment(int currentPage, int pageSize, TblSysDepartmentVO departmentVO) {
		//只能分配未被分配给任何部门的用户，不能实现同一用户分配多个不同部门的情况
		StringBuffer sql = new StringBuffer("select * from TBL_SYS_USER user where user.ID not in (select USER_ID from TBL_SYS_USER_DEPARTMENT) " +
				"and user.IS_VALID= 'T' ");
		
		if(departmentVO.getUser()!=null && departmentVO.getUser().getFullName()!=null && !"".equals(departmentVO.getUser().getFullName().trim())){
			sql.append(" and user.FULLNAME like '%" + departmentVO.getUser().getFullName().trim() + "%'");
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
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#findRoleByUnAssignDepartment(int, int, shell.framework.organization.department.vo.TblSysDepartmentVO)
	 */
	public VOResult findRoleByUnAssignDepartment(int currentPage, int pageSize,TblSysDepartmentVO departmentVO) {
		//获取已经分配给当前部门以外的所有角色对象
		StringBuffer sql = new StringBuffer("select * from TBL_SYS_ROLE role where role.ID not in (select ROLE_ID from TBL_SYS_ROLE_DEPARTMENT where DEPARTMENT_ID='" + 
						   departmentVO.getId() +"') and role.IS_VALID = 'T' "  );
		if(departmentVO !=null && departmentVO.getRole()!=null && departmentVO.getRole().getRole()!=null && !"".equals(departmentVO.getRole().getRole().getName().trim())){
			sql.append(" and role.NAME like '%" + departmentVO.getRole().getRole().getName().trim() + "%'");
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
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#reOrder(java.lang.String[])
	 */
	public void reOrder(String[] departmentIds) {
		if(departmentIds.length==0){
			throw new RuntimeException("NO DATA ABOUT DEPARTMENT_ID!");
		}
		for(int i=0;i<departmentIds.length;i++){
			//TODO 排序
		}
		
		
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#hasSysUser(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public boolean hasSysUser(String departmentID) {
		String sql = "select count(*) COUNT from TBL_SYS_USER_DEPARTMENT user_department where user_department.DEPARTMENT_ID='" + departmentID +"'";
		List<?> resultList = jdbcBaseDao.query(sql);
		if(resultList==null || resultList.size()==0){
			throw new RuntimeException("NO DATA FROM DATABASE.");
		}
		Map<String,Object> valueMap = (Map<String,Object>)resultList.get(0);
		int count = ((Long)valueMap.get("COUNT")).intValue();
		return count==0 ? false : true  ;
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#hasSysPosition(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public boolean hasSysPosition(String departmentID) {
		String sql = "select count(*) COUNT from TBL_SYS_DEPARTMENT_POSITION department_position where department_position.DEPARTMENT_ID='" + departmentID +"'";
		List<?> resultList = jdbcBaseDao.query(sql);
		if(resultList==null || resultList.size()==0){
			throw new RuntimeException("NO DATA FROM DATABASE.");
		}
		Map<String,Object> valueMap = (Map<String,Object>)resultList.get(0);
		int count = ((Long)valueMap.get("COUNT")).intValue();
		return count==0 ? false : true  ;
	}
	
	
	
}
