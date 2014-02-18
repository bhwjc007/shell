/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: TblSysAgentCode.java $
 * $LastChangedDate: 2012-8-30 上午11:57:31 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.organization.agentcode.vo;

import shell.framework.model.TblSysAgentCode;
import shell.framework.organization.user.vo.TblSysUserVO;

/**
 * <p> 工号 值对象 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-8-30 上午11:57:31 $
 */
public class TblSysAgentCodeVO {

	/**
	 * 系统工号
	 */
	private TblSysAgentCode sysAgentCode;
	
	/**
	 * 系统用户
	 */
	private TblSysUserVO sysUser;
	
	
	/**
	 * @return the sysAgentCode
	 */
	public TblSysAgentCode getSysAgentCode() {
		return sysAgentCode;
	}
	/**
	 * @param sysAgentCode the sysAgentCode to set
	 */
	public void setSysAgentCode(TblSysAgentCode sysAgentCode) {
		this.sysAgentCode = sysAgentCode;
	}
	/**
	 * @return the sysUser
	 */
	public TblSysUserVO getSysUser() {
		return sysUser;
	}
	/**
	 * @param sysUser the sysUser to set
	 */
	public void setSysUser(TblSysUserVO sysUser) {
		this.sysUser = sysUser;
	}
	
	
	
}
