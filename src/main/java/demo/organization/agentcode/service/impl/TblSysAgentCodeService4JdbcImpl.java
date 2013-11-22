/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: TblSysAgentCodeService4JdbcImpl.java $
 * $LastChangedDate: 2012-8-30 下午4:15:47 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.organization.agentcode.service.impl;

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
import shell.framework.model.TblSysAgentCode;
import shell.framework.model.TblSysUser;
import shell.framework.organization.agentcode.service.TblSysAgentCodeService;
import shell.framework.organization.agentcode.vo.TblSysAgentCodeVO;
import shell.framework.util.PopulateUtil;

/**
 * <p> 系统工号服务的jdbc实现 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-8-30 下午4:15:47 $
 */
public class TblSysAgentCodeService4JdbcImpl implements TblSysAgentCodeService {

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
	 * @see shell.framework.organization.agentcode.service.TblSysAgentCodeService#findByPagination(int, int, shell.framework.organization.agentcode.vo.TblSysAgentCodeVO)
	 */
	public VOResult findByPagination(int currentPage, int pageSize, TblSysAgentCodeVO agentCodeVO) {
		StringBuffer sql = new StringBuffer("select * from TBL_SYS_AGENTCODE agentcode");
		sql.append(" where agentcode.IS_VALID='" + SystemParam.IS_VALID +"'");
		//系统工号 模糊查询
		if(agentCodeVO.getSysAgentCode()!=null && agentCodeVO.getSysAgentCode().getAgentCode()!=null && !"".equals(
				agentCodeVO.getSysAgentCode().getAgentCode()) ){
			sql.append(" and agentcode.AGENTCODE like '%" + agentCodeVO.getSysAgentCode().getAgentCode().trim() + "%'");
		}
		
		return jdbcBaseDao.query(sql.toString(), new RowMapper<Object>(){
			
			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
			 */
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				TblSysAgentCode agentCode = new TblSysAgentCode();
				Map<String,String> propertyMap = new HashMap<String,String>();
				propertyMap.put("isValid", "IS_VALID");
				propertyMap.put("isVac", "IS_VAC");
				
				PopulateUtil.populate(agentCode,rs,propertyMap);
				return agentCode;
			}
			
		}, currentPage, pageSize);
	}

	/* (non-Javadoc)
	 * @see shell.framework.organization.agentcode.service.TblSysAgentCodeService#findByID(java.io.Serializable)
	 */
	public TblSysAgentCode findByID(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see shell.framework.organization.agentcode.service.TblSysAgentCodeService#add(shell.framework.organization.agentcode.vo.TblSysAgentCodeVO)
	 */
	public int add(TblSysAgentCodeVO agentCodeVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see shell.framework.organization.agentcode.service.TblSysAgentCodeService#update(shell.framework.organization.agentcode.vo.TblSysAgentCodeVO)
	 */
	public int update(TblSysAgentCodeVO agentCodeVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see shell.framework.organization.agentcode.service.TblSysAgentCodeService#deleteByID(shell.framework.organization.agentcode.vo.TblSysAgentCodeVO)
	 */
	public int deleteByID(TblSysAgentCodeVO agentCodeVO) {
		String sql = "delete from TBL_SYS_AGENTCODE where ID=?";
		final List<String> idList = new ArrayList<String>();
		String ids[] = agentCodeVO.getSysAgentCode().getId().split("-");
		for(String id : ids){
			if(!hasSysUser(id)){
				idList.add(id);
			}
		}
		int[] deleteNumbers = jdbcBaseDao.batchUpdate(sql, idList, new BatchPreparedStatementSetter() {
			/*
			 * (non-Javadoc)
			 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#setValues(java.sql.PreparedStatement, int)
			 */
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				ps.setString(1, idList.get(index));
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
	 * @see shell.framework.organization.agentcode.service.TblSysAgentCodeService#assignUser(java.lang.String, java.lang.String[])
	 */
	public int assignUser(TblSysAgentCodeVO agentCodeVO) {
		String sql = "update TBL_SYS_USER set AGENTCODE_ID=? where ID=? ";
		
		final String agentCodeID =  agentCodeVO.getSysAgentCode().getAgentCode();
		
		String sysUserIds[] = agentCodeVO.getSysUser().getId().split("-");
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
				ps.setString(1, agentCodeID);
				ps.setString(2, sysUser_id);
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
	 * @see shell.framework.organization.agentcode.service.TblSysAgentCodeService#unAssignUser(java.lang.String, java.lang.String[])
	 */
	public int unAssignUser(TblSysAgentCodeVO agentCodeVO) {
		String sql = "update TBL_SYS_USER set AGENTCODE_ID = NULL where ID=?";
		String sysUserIds[] = agentCodeVO.getSysUser().getId().split("-");
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
	 * @see shell.framework.organization.agentcode.service.TblSysAgentCodeService#findSysUserByPagination(int, int, shell.framework.organization.agentcode.vo.TblSysAgentCodeVO)
	 */
	public VOResult findSysUserByPagination(int currentPage, int pageSize, TblSysAgentCodeVO agentCodeVO) {

		StringBuffer sql = new StringBuffer("select * from TBL_SYS_USER user where user.IS_VALID = 'T' and user.AGENTCODE_ID = '" +
						   agentCodeVO.getSysAgentCode().getAgentCode().trim() + "'");
		//按照工号查询
		if(agentCodeVO.getSysUser()!=null && agentCodeVO.getSysUser().getFullName()!=null && !"".equals(
				agentCodeVO.getSysUser().getFullName()) ){
			sql.append(" and user.FULLNAME like '%" + agentCodeVO.getSysUser().getFullName().trim() +"%'");
		}
		
		VOResult voResult = jdbcBaseDao.query(sql.toString(), new RowMapper<Object>(){
			
			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
			 */
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				TblSysUser sysUser = new TblSysUser();
				Map<String,String> propertyMap = new HashMap<String,String>();
				propertyMap.put("isValid", "IS_VALID");
				propertyMap.put("isVac", "IS_VAC");
				PopulateUtil.populate(sysUser, rs, propertyMap);
				return sysUser;
			}
		}, currentPage, pageSize);
		return voResult;
	}

	/* (non-Javadoc)
	 * @see shell.framework.organization.agentcode.service.TblSysAgentCodeService#findSysUserOfUnAssignAgentCodeByPagination(int, int, shell.framework.organization.agentcode.vo.TblSysAgentCodeVO)
	 */
	public VOResult findSysUserOfUnAssignAgentCodeByPagination(int currentPage,
			int pageSize, TblSysAgentCodeVO agentCodeVO) {
		//"' and user.AGENTCODE_ID != '" + agentCodeVO.getSysAgentCode().getAgentCode().trim() + "'";
		StringBuffer sql = new StringBuffer("select * from TBL_SYS_USER user where user.IS_VALID='" + SystemParam.IS_VALID +
					 "' and user.AGENTCODE_ID is NULL") ;
		
		//系统用户全名--查询条件
		if(agentCodeVO.getSysUser()!=null && agentCodeVO.getSysUser().getFullName()!=null && 
					!"".equals(agentCodeVO.getSysUser().getFullName().trim())){
			sql.append(" and user.FULLNAME like '%" + agentCodeVO.getSysUser().getFullName().trim() + "%'");
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
	 * @see shell.framework.organization.agentcode.service.TblSysAgentCodeService#hasSysUser(java.lang.String)
	 */
	public boolean hasSysUser(String agentCodeID) {
		// TODO Auto-generated method stub
		return false;
	}

}
