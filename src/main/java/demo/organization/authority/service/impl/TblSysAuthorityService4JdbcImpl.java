/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * ClassName: shell.framework.organization.authority.service.impl.TblSysAuthorityService4JdbcImpl.java
 * CreatedTime: 13-1-14 下午1:37
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.organization.authority.service.impl;

import org.springframework.jdbc.core.RowMapper;
import shell.framework.dao.IJdbcBaseDao;
import shell.framework.dao.support.VOResult;
import shell.framework.model.TblSysAuthority;
import shell.framework.organization.authority.service.TblSysAuthorityService;
import shell.framework.organization.authority.vo.TblSysFunctionVO;
import shell.framework.util.PopulateUtil;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> 系统权限服务jdbc实现 </p>
 *
 * @author changming.Y
 * @version 1.0   13-1-14 下午1:37
 */
public class TblSysAuthorityService4JdbcImpl implements TblSysAuthorityService {

    private IJdbcBaseDao jdbcBaseDao;

    public IJdbcBaseDao getJdbcBaseDao() {
        return jdbcBaseDao;
    }

    public void setJdbcBaseDao(IJdbcBaseDao jdbcBaseDao) {
        this.jdbcBaseDao = jdbcBaseDao;
    }

    @Override
    public VOResult findByPagination(int currentPage, int pageSize, TblSysFunctionVO sysFunctionVO) {
        StringBuilder sql = new StringBuilder("select * from TBL_SYS_FUNCTION func where func.FUNCTION_TYPE<>'' ");
        if(sysFunctionVO!=null){
            //权限名称
            if(sysFunctionVO.getFunctionName()!=null && !"".equals(sysFunctionVO.getFunctionName())){
                sql.append(" and func.FUNCTION_NAME like '%").append(sysFunctionVO.getFunctionName().trim()).append("%'");
            }
            //权限类型
            if(sysFunctionVO.getFunctionType()!=null && !"".equals(sysFunctionVO.getFunctionType())){
                sql.append(" and func.FUNCTION_TYPE ='").append(sysFunctionVO.getFunctionType()).append("'");
            }
            //权限值（URL）
            if(sysFunctionVO.getFunctionURL()!=null && !"".equals(sysFunctionVO.getFunctionURL())){
                sql.append(" and func.FUNCTION_URL like '%").append(sysFunctionVO.getFunctionURL().trim()).append("%'");
            }
            sql.append(" order by func.ORDER_NO");
        }

        return jdbcBaseDao.query(sql.toString(),new RowMapper<Object>() {

            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                TblSysAuthority authority = new TblSysAuthority();
                Map<String,String> propertiesMap = new HashMap<String,String >();
                propertiesMap.put("functionName","FUNCTION_NAME");
                propertiesMap.put("functionURL","FUNCTION_URL");
                propertiesMap.put("functionType","FUNCTION_TYPE");
                propertiesMap.put("parentID","PARENT_FUN_ID");
                propertiesMap.put("orderNO","ORDER_NO");
                PopulateUtil.populate(authority,rs,propertiesMap);
                return authority;
            }

        },currentPage,pageSize);
    }

    @Override
    public TblSysAuthority findByID(Serializable id) {
        return null;
    }

    @Override
    public int add(TblSysFunctionVO sysFunctionVO) {
        return 0;
    }

    @Override
    public int update(TblSysFunctionVO sysFunctionVO) {
        return 0;
    }

    @Override
    public int deleteByID(TblSysFunctionVO sysFunctionVO) {
        return 0;
    }
}
