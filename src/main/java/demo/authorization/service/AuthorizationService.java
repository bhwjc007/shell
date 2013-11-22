/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: AuthorizationService.java $
 * $LastChangedDate: 2012-4-29 下午9:45:06 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.authorization.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.omg.CORBA.portable.StreamableValue;
import shell.framework.dao.IJdbcBaseDao;


/**
 * <p> 系统鉴权服务，不需要基于接口方式，业务类直接调用即可 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-4-29 下午9:45:06 $
 */
public class AuthorizationService {

	private IJdbcBaseDao jdbcBaseDao;
	
	public static String BEAN_ID = "authorizationService";
	
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
	 * 获取指定角色的所有权限
	 * @param roleIDs 角色集合
	 * @return 包括权限id的list集合
	 */
	public List<String> getAllAuthorities(List<String> roleIDs){
		
		return null;
	}

	
	/**
	 * 根据指定角色，判断是否拥有某个权限资源的访问权限
	 * @param roleIDList 角色ID集合
	 * @param funcValue 权限资源值
	 * @param funcID 权限资源ID
	 * @return true-拥有 false-没有
	 */
	public boolean hasAuthority(List<String> roleIDList,String funcValue,String funcID){
        StringBuilder sql;
        String roleIds = "";
        boolean  hasAuth = false;
        if(roleIDList==null || roleIDList.size()==0){
            return false;
        }
        for (String roleID : roleIDList) {
            roleIds = roleIds + "'" + roleID + "',";
        }
        roleIds = roleIds.substring(0, roleIds.length()-1);
        sql = new StringBuilder("select * from (select * from TBL_SYS_AUTHORITY" + " where ROLE_ID in ("+roleIds+")");
        if(funcID!=null && !"".equals(funcID)){
			sql.append(" and FUNCTION_ID='").append(funcID).append("'");
		}
		sql.append(") auth");
        if(funcValue!=null && !"".equals(funcValue)){
            sql.append(" join TBL_SYS_FUNCTION func on auth.FUNCTION_ID=func.ID and func.FUNCTION_URL <> '' ");
        }
        
        //TODO 需要优化，不会每次访问权限资源都要查询数据库，应该放入缓存中
		List<?> resultList = jdbcBaseDao.query(sql.toString());

        if(resultList!=null && resultList.size()>0){
            for(Object rowsMap : resultList) {
                //PAGE类型资源权限
                if(funcID!=null && !"".equals(funcID)){
                    String _funcID = ((Map)rowsMap).get("FUNCTION_ID")==null ? "" : (String)((Map)rowsMap).get("FUNCTION_ID");
                    if(_funcID.equals(funcID)) {
                        hasAuth = true;
                        break;
                    }
                }

                //URL类型资源权限
                if(funcValue!=null && !"".equals(funcValue)){
                    String functionValuePattern =  (String)((Map)rowsMap).get("FUNCTION_URL");
                    //匹配权限资源值与ACL表中的设定权限资源值（目前只针对形如/xxx/xxx/*进行匹配）
                    if (functionValuePattern!=null){
                        if(functionValuePattern.endsWith("*")){
                            functionValuePattern = functionValuePattern.substring(0,functionValuePattern.length()-1);
                        }
                        if(funcValue.startsWith(functionValuePattern)){
                            hasAuth = true;
                            break;
                        }
                    }
                }

                //COMMAND类型资源权限
                //TODO  COMMAND类型资源权限验证
            }
        }
        return hasAuth;
	}
	
	
	/**
	 * 根据指定角色，判断是否拥有某个权限资源的访问权限
	 * @param roleIDs 角色ID结合
	 * @param funcValue 权限资源值
	 * @return true-拥有 false-没有
	 */
	public boolean hasAuthority(List<String> roleIDs,String funcValue){
		return this.hasAuthority(roleIDs, funcValue, null);
	}
	
	
	/**
	 * 判断指定用户是否拥有某个权限资源的访问权限 - 扩展功能
	 * @param userCode 用户登录ID
	 * @param funcValue 权限资源值
	 * @return true-拥有 false-没有
	 */
	public boolean hasAuthority(String userCode,String funcValue){
        return true;
	}
	
}
