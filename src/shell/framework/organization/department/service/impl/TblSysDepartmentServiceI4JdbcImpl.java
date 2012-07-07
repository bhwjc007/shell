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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import shell.framework.core.SystemParam;
import shell.framework.dao.IJdbcBaseDao;
import shell.framework.dao.support.VOResult;
import shell.framework.model.TblSysDepartment;
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
			idList.add(id);
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
	 * @see shell.framework.organization.department.service.TblSysDepartmentService#findUserByPagination(int, int, java.io.Serializable)
	 */
	public VOResult findUserByPagination(int currentPage, int pageSize,	Serializable departmentId) {
		String sql = "select * from TBL_SYS_USER user , TBL_SYS_USER_DEPARTMENT user_department where user.ID=user_department.USER_ID and " +
				" user_department.DEPARTMENT_ID='" + departmentId + "' and user.IS_VALID= 'T' ";
		
		VOResult voResult = jdbcBaseDao.query(sql, new RowMapper<Object>(){
			
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

}
