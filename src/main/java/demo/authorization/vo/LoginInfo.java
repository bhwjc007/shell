/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: UserInfo.java $
 * $LastChangedDate: 2012-5-8 上午11:14:32 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.authorization.vo;

import java.util.ArrayList;
import java.util.List;
import shell.framework.model.TblSysUser;

/**
 * <p> 系统用户登录信息 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-5-8 上午11:14:32 $
 */
public class LoginInfo {
	
	//登录用户
	private TblSysUser user;
	//登录ip
	private String loginIP;
	//登录主机
	private String loginHost;
	//登录时间
	private String loginTime;
	//登录sessionid
	private String sessionID;
	//登录url地址
	private String url;
	//登录用户拥有的最终全部角色ID列表（合并后的角色）
	private List<String> roleList = new ArrayList<String>();
	
	/**
	 * @return the sessionID
	 */
	public String getSessionID() {
		return sessionID;
	}
	/**
	 * @param sessionID the sessionID to set
	 */
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the loginIP
	 */
	public String getLoginIP() {
		return loginIP;
	}
	/**
	 * @param loginIP the loginIP to set
	 */
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	/**
	 * @return the loginHost
	 */
	public String getLoginHost() {
		return loginHost;
	}
	/**
	 * @param loginHost the loginHost to set
	 */
	public void setLoginHost(String loginHost) {
		this.loginHost = loginHost;
	}
	/**
	 * @return the loginTime
	 */
	public String getLoginTime() {
		return loginTime;
	}
	/**
	 * @param loginTime the loginTime to set
	 */
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	/**
	 * @return the user
	 */
	public TblSysUser getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(TblSysUser user) {
		this.user = user;
	}
	
	/**
	 * @param roleList the roleList to set
	 */
	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}
	
	/**
	 * @return the roleList
	 */
	public List<String> getRoleList() {
		return roleList;
	}
	
}
