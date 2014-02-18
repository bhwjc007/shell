/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: TblSysRoleVO.java $
 * $LastChangedDate: 2012-9-23 下午1:17:44 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.organization.role.vo;

import shell.framework.model.TblSysFunction;
import shell.framework.model.TblSysRole;

/**
 * <p> 系统角色值对象 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-9-23 下午1:17:44 $
 */
public class TblSysRoleVO {

	public TblSysRole role;

	public TblSysFunction function;
	
	/**
	 * @return the role
	 */
	public TblSysRole getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(TblSysRole role) {
		this.role = role;
	}

	/**
	 * @return the function
	 */
	public TblSysFunction getFunction() {
		return function;
	}

	/**
	 * @param function the function to set
	 */
	public void setFunction(TblSysFunction function) {
		this.function = function;
	}
	
	
	
}
