/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: TblSysRoleService4JdbcImpl.java $
 * $LastChangedDate: 2012-9-23 下午2:07:39 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.organization.role.service.impl;

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
import shell.framework.model.TblSysFunction;
import shell.framework.model.TblSysRole;
import shell.framework.organization.role.service.TblSysRoleService;
import shell.framework.organization.role.vo.TblSysRoleVO;
import shell.framework.util.MPTTTreeUtil;
import shell.framework.util.PopulateUtil;
import shell.framework.util.UUIDGenerator;

/**
 * <p> 系统角色服务jdbc实现 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-9-23 下午2:07:39 $
 */
public class TblSysRoleService4JdbcImpl implements TblSysRoleService {

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
	 * @see shell.framework.organization.role.service.TblSysRoleService#findByPagination(int, int, shell.framework.organization.role.vo.TblSysRoleVO)
	 */
	public VOResult findByPagination(int currentPage, int pageSize,	TblSysRoleVO sysRoleVO) {
		VOResult voResult = new VOResult();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from TBL_SYS_ROLE role where role.IS_VALID='"+SystemParam.IS_VALID+"'");
		if(sysRoleVO!=null && sysRoleVO.getRole()!=null && !"".equals(sysRoleVO.getRole().getName())) {
			sql.append(" and role.NAME like '%" + sysRoleVO.getRole().getName().trim() + "%'");
		}
		
		voResult = jdbcBaseDao.query(sql.toString(), new RowMapper<Object>(){
			
			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
			 */
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				TblSysRole sysRole = new TblSysRole();
				Map<String,String> propertyMap = new HashMap<String,String>();
				propertyMap.put("isValid", "IS_VALID");
				propertyMap.put("isVirtual", "IS_VIRTUAL");
				PopulateUtil.populate(sysRole,rs,propertyMap);
				return sysRole;
			}
			
		}, currentPage, pageSize);
		return voResult;
	}

	/* (non-Javadoc)
	 * @see shell.framework.organization.role.service.TblSysRoleService#findDepartmentByID(java.io.Serializable)
	 */
	public TblSysRole findRoleByID(Serializable id) {
		String sql = "select * from TBL_SYS_ROLE role where role.ID = ?";
		List<?> resultList = jdbcBaseDao.query(sql, new Object[]{id}, new RowMapper<Object>() {
			
			/* (non-Javadoc)
			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
			 */
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				TblSysRole sysRole = new TblSysRole();	
				Map<String,String> propertyMap = new HashMap<String,String>();
				propertyMap.put("isValid" , "IS_VALID");
				propertyMap.put("isVirtual" , "IS_VIRTUAL");
				PopulateUtil.populate(sysRole, rs ,propertyMap);
				return sysRole;
			}
		});
		
		if(resultList==null || resultList.size()==0){
			throw new RuntimeException("NO DATA FROM DATABASE!");
		}
		return (TblSysRole)resultList.get(0);
	}

	/* (non-Javadoc)
	 * @see shell.framework.organization.role.service.TblSysRoleService#deleteByID(shell.framework.organization.role.vo.TblSysRoleVO)
	 */
	public int deleteByID(TblSysRoleVO sysRoleVO) {
		String sql = "delete from TBL_SYS_ROLE where ID=?";
		String[] ids = sysRoleVO.getRole().getId().split("-");
		final List<String> idList = Arrays.asList(ids);
		
		int[] deleteNumbers = jdbcBaseDao.batchUpdate(sql, idList, new BatchPreparedStatementSetter() {
			
			/*
			 * (non-Javadoc)
			 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#setValues(java.sql.PreparedStatement, int)
			 */
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				String id = idList.get(index);
				ps.setString(1, id);
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
	 * @see shell.framework.organization.role.service.TblSysRoleService#add(shell.framework.organization.role.vo.TblSysRoleVO)
	 */
	public int add(TblSysRoleVO sysRoleVO) {
		String sql = "insert into TBL_SYS_ROLE values (?,?,?,?,?,?,?,?)";
		return jdbcBaseDao.update(sql, new Object[]{UUIDGenerator.generate(),sysRoleVO.getRole().getName(),SystemParam.IS_VALID,
									sysRoleVO.getRole().getIsVirtual(),sysRoleVO.getRole().getRemark(),null,
									null,sysRoleVO.getRole().getCreator()} );
	}

	/* (non-Javadoc)
	 * @see shell.framework.organization.role.service.TblSysRoleService#update(shell.framework.organization.role.vo.TblSysRoleVO)
	 */
	public int update(TblSysRoleVO sysRoleVO) {
		String sql = "update TBL_SYS_ROLE set NAME=?,IS_VALID=?,IS_VIRTUAL=?,REMARK=? where ID=?"; 
		return jdbcBaseDao.update(sql, new Object[]{sysRoleVO.getRole().getName(),sysRoleVO.getRole().getIsValid(),
				sysRoleVO.getRole().getIsVirtual(),sysRoleVO.getRole().getRemark(),sysRoleVO.getRole().getId()});
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.role.service.TblSysRoleService#findFunctionByRoleID(shell.framework.organization.role.vo.TblSysRoleVO)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Object,Object> findFunctionByRoleID(TblSysRoleVO sysRoleVO) {
		Map resultMap = new HashMap();
		List<?> funcAllList = new ArrayList(); 
		try {
			//根节点，不应该写死，应该从系统编码表（数据字典）中获取
			funcAllList = MPTTTreeUtil.retrieveAllTree(SystemParam.AUTHORITY_TREE_ROOT_ID, this.jdbcBaseDao);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String sql = "select ID,FUNCTION_NAME from TBL_SYS_FUNCTION , TBL_SYS_AUTHORITY where ID=FUNCTION_ID and ROLE_ID ='" +
                      sysRoleVO.getRole().getId() + "'";
		List<?> funcList = jdbcBaseDao.query(sql);
		List funcOfRoleList = new ArrayList();
		for(Object funcMap : funcList){
			if(funcMap instanceof Map){
				TblSysFunction funcVO = new TblSysFunction();
				Map functionMap = (HashMap)funcMap;
				funcVO.setId(functionMap.get("ID").toString());
				funcVO.setFunctionName(functionMap.get("FUNCTION_NAME").toString());
				funcOfRoleList.add(funcVO);
			}
		}
		
		resultMap.put("funcAllList", funcAllList);
		resultMap.put("funcOfRoleList", funcOfRoleList);
		return resultMap;
	}
	
	
	/* (non-Javadoc)
	 * @see shell.framework.organization.role.service.TblSysRoleService#saveFunctionsOfRole(shell.framework.organization.role.vo.TblSysRoleVO)
	 */
	@SuppressWarnings("unused")
	public int saveFunctionsOfRole(final TblSysRoleVO sysRoleVO) {
		String delSQL = "delete from TBL_SYS_AUTHORITY where ROLE_ID=?";
		String sql = "insert into TBL_SYS_AUTHORITY values (?,?,?,?)";
		
		//先删掉角色的所有权限点
		int delNum = jdbcBaseDao.update(delSQL, new Object[]{sysRoleVO.getRole().getId()});

		if(sysRoleVO.getFunction().getId()==null || "".equals(sysRoleVO.getFunction().getId()) ){
            return 0;
        }
        String[] functionIDs = sysRoleVO.getFunction().getId().split("-");
        final List<String> functionIdList = Arrays.asList(functionIDs);

		int[] updateNumbers = jdbcBaseDao.batchUpdate(sql, functionIdList, new BatchPreparedStatementSetter() {
			
			/*
			 * (non-Javadoc)
			 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#setValues(java.sql.PreparedStatement, int)
			 */
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				String functionID = functionIdList.get(index);
				ps.setString(1, sysRoleVO.getRole().getId());
				ps.setString(2, functionID);
				ps.setString(3, "");
				ps.setInt(4, SystemParam.AUTHORITY_OPER_READ_ONLY);	//权限点默认操作值，暂不起作用 0-读取
			}
			
			/*
			 * (non-Javadoc)
			 * @see org.springframework.jdbc.core.BatchPreparedStatementSetter#getBatchSize()
			 */
			public int getBatchSize() {
				return functionIdList.size();
			}
		});
		
		return updateNumbers.length;
	}


    @Override
    public VOResult findURLAuthorityByPagination(int currentPage,int pageSize,TblSysRoleVO sysRoleVO) {
        String sql = "select func.ID,func.FUNCTION_NAME,func.FUNCTION_URL from TBL_SYS_FUNCTION func , TBL_SYS_AUTHORITY auth " +
                     "where func.ID=auth.FUNCTION_ID and auth.ROLE_ID='" + sysRoleVO.getRole().getId() + "' and func.FUNCTION_TYPE='" + SystemParam.AUTHORITY_TYPE_URL + "'";
        return jdbcBaseDao.query(sql,new RowMapper<Object>() {

            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                TblSysFunction function = new TblSysFunction();
                Map<String,String> propertiesMap = new HashMap<String,String>();
                propertiesMap.put("functionName","FUNCTION_NAME");
                propertiesMap.put("functionURL","FUNCTION_URL");
                PopulateUtil.populate(function,rs,propertiesMap);
                return function;
            }

        },currentPage,pageSize);
    }


    @Override
    public VOResult findUnAssignURLAuthorityByPagination(int currentPage,int pageSize,TblSysRoleVO sysRoleVO) {
        String sql = "select ID,FUNCTION_NAME,FUNCTION_URL,FUNCTION_TYPE from TBL_SYS_FUNCTION where id not in (select FUNCTION_ID from TBL_SYS_AUTHORITY where ROLE_ID=?) " +
                "and FUNCTION_TYPE='" + SystemParam.AUTHORITY_TYPE_URL + "'";

        return jdbcBaseDao.query(sql,new Object[]{sysRoleVO.getRole().getId()},new RowMapper<Object>() {
                    @Override
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                        TblSysFunction function = new TblSysFunction();
                        Map<String,String> propertiesMap = new HashMap<String,String>();
                        propertiesMap.put("functionName","FUNCTION_NAME");
                        propertiesMap.put("functionURL","FUNCTION_URL");
                        propertiesMap.put("functionType","FUNCTION_TYPE");
                        PopulateUtil.populate(function,rs,propertiesMap);
                        return function;
                    }
                },currentPage,pageSize);
    }


    @Override
    public int assignURLAuthority(final TblSysRoleVO sysRoleVO) {
        String sql = "insert into TBL_SYS_AUTHORITY values (?,?,?,?) ";
        if(sysRoleVO.getFunction().getId()==null || "".equals(sysRoleVO.getFunction().getId()) ){
            return 0;
        }
        String[] functionIDs = sysRoleVO.getFunction().getId().split("-");
        final List<String> functionIdList = Arrays.asList(functionIDs);

        int[] updateNum = jdbcBaseDao.batchUpdate(sql,functionIdList,new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {
                String funID = functionIdList.get(index);
                ps.setString(1,sysRoleVO.getRole().getId());
                ps.setString(2,funID);
                ps.setString(3,SystemParam.AUTHORITY_TYPE_URL);
                ps.setInt(4, SystemParam.AUTHORITY_OPER_READ_ONLY); //权限点默认操作值，暂不起作用 0-读取
            }

            @Override
            public int getBatchSize() {
                return functionIdList.size();
            }
        });
        return updateNum.length;
    }


    @Override
    public int unAssignURLAuthority(final TblSysRoleVO sysRoleVO) {
        String sql = "delete from TBL_SYS_AUTHORITY where ROLE_ID=? and FUNCTION_ID=?";
        if(sysRoleVO.getFunction().getId()==null || "".equals(sysRoleVO.getFunction().getId()) ){
            return 0;
        }
        String[] functionIDs = sysRoleVO.getFunction().getId().split("-");
        final List<String> functionIdList = Arrays.asList(functionIDs);

        int[] updateNum = jdbcBaseDao.batchUpdate(sql,functionIdList,new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {
                String funID = functionIdList.get(index);
                ps.setString(1,sysRoleVO.getRole().getId());
                ps.setString(2,funID);
            }

            @Override
            public int getBatchSize() {
                return functionIdList.size();
            }
        });
        return updateNum.length;
    }
}
