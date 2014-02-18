/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: AuthorizationUtil.java $
 * $LastChangedDate: 2012-11-16 下午3:15:48 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.authorization.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import shell.framework.model.TblSysRole;

/**
 * <p> 权限工具类 - give up </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-11-16 下午3:15:48 $
 */
public class AuthorizationUtil {

	/**
	 * 对2个角色list取并集
	 * @param rolesA 角色集合A
	 * @param rolesB 角色集合B
	 * @return 角色LIST集合
	 */
	public static  List<TblSysRole> mergeRole(List<TblSysRole> rolesA,List<TblSysRole> rolesB){
		List<TblSysRole> finalRoles = new ArrayList<TblSysRole>();
		if(rolesA!=null && rolesA.size()==0 && rolesB!=null && rolesB.size()==0){
			return finalRoles;
		}
		TblSysRole tblSysRoleA = null;
		TblSysRole tblSysRoleB = null;
		for (Iterator<TblSysRole> iterator = rolesA.iterator(); iterator.hasNext();) {
			tblSysRoleA = (TblSysRole) iterator.next();
			boolean isEqual = false;
			for (Iterator<TblSysRole> iterator2 = rolesB.iterator(); iterator2.hasNext();) {
				tblSysRoleB = (TblSysRole) iterator2.next();
				if(tblSysRoleA.getId().equals(tblSysRoleB.getId())){
					isEqual =true;
					break;
				}
			}
			if(!isEqual){
				finalRoles.add(tblSysRoleA);
			}
		}
		finalRoles.addAll(rolesB);
		return finalRoles;
	}
}
