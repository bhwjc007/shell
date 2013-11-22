/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: LoginService.java $
 * $LastChangedDate: 2012-12-4 上午10:12:21 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.authorization.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import shell.framework.authorization.support.AuthorizationException;
import shell.framework.authorization.vo.LoginInfo;
import shell.framework.cache.support.CacheUtil;
import shell.framework.core.DefaultBeanFactory;
import shell.framework.core.SystemParam;
import shell.framework.model.TblSysUser;
import shell.framework.organization.user.service.TblSysUserService;

/**
 * <p> 系统登录服务，不需要基于接口方式，业务类直接调用即可 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-12-4 上午10:12:21 $
 */
public class LoginService {

	private Logger logger = Logger.getLogger(LoginService.class);
	
	public static String BEAN_ID = "loginService";
	
	
	/**
	 * 用户登录验证
	 * @param userCode 用户ID
	 * @param password 密码-明文
	 * @param request http请求
	 * @return
	 * @throws AuthorizationException
	 */
	@SuppressWarnings("unused")
	public boolean login(String userCode,String password,HttpServletRequest request) throws AuthorizationException {
		if(userCode==null || password==null){
			logger.warn("NO USERCODE OR PASSWORD SPECIFIED !");
			throw new AuthorizationException("NO USERCODE OR PASSWORD SPECIFIED !");
		}
		TblSysUser userObj = checkUser(userCode);
		if(userObj!=null){
			//核对密码 
			//TODO password要进行加密
			if(password.equals(userObj.getPassword())){
				this.updateSession(userObj, request);
				return true;
			}
		}
		String message = "userCode or password is mismatching!";
		return false;
	}
	
	
	/**
	 * 用户注销
	 * 1.session中用户登录数据
	 * 2.缓存中的用户权限数据
	 * @param userID 系统用户记录ID
	 * @throws AuthorizationException
	 */
	public void logout(String userID , HttpServletRequest request) throws AuthorizationException{
		request.getSession().removeAttribute(SystemParam.SESSIOIN_ID_LOGIN_INFO);
	}
	
	
	/**
	 * 验证是否存在该用户,并更新缓存中的用户信息
	 * @param userCode 用户登录ID
	 * @return 系统用户对象
	 */
	protected TblSysUser checkUser(String userCode) {
		TblSysUserService sysUserService = (TblSysUserService)DefaultBeanFactory.getBean("tblSysUserService");
		TblSysUser userObj = null;
		Object user = CacheUtil.getValue(CacheUtil.USER_CACHE, userCode);
		//缓存中存在
		if(user!=null && user instanceof TblSysUser){
			userObj = (TblSysUser)user;
		//从数据库中获取
		}else{
			userObj = sysUserService.findUserByUserCode(userCode);
			//更新userCache缓存 
			if(userObj!=null && CacheUtil.getValue(CacheUtil.USER_CACHE,userCode)==null ){
				CacheUtil.putValue(CacheUtil.USER_CACHE, userCode, userObj);
			}
		}
		return userObj;
	}
	
	
	/**
	 * 更新session中的用户登录信息
	 * 
	 * @param user 当前登录用户
	 * @param request request请求
	 */
	@SuppressWarnings("all")
	public void updateSession(TblSysUser user , HttpServletRequest request){
		if(request==null){
			logger.warn("THE REQUEST IS NOT COME FROM HTTP!");
			return;
		}
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setUser(user);
		loginInfo.setLoginHost(request.getRemoteHost());
		loginInfo.setLoginIP(request.getRemoteAddr());
		//TODO 需要使用日期工具解析成时间字符串
		loginInfo.setLoginTime(String.valueOf(System.currentTimeMillis()));
		loginInfo.setSessionID(request.getRequestedSessionId());
		loginInfo.setUrl(request.getRequestURI());
		//从缓存中获得用户角色数据
		List<String> roleList = (List)CacheUtil.getValue(CacheUtil.USER_ROLE_CACHE, user.getId());
		loginInfo.setRoleList(roleList);
		//每个用户登录信息放入session，根据sessionID区分
		HttpSession session = request.getSession(true);
		session.removeAttribute(SystemParam.SESSIOIN_ID_LOGIN_INFO);
		session.setAttribute(SystemParam.SESSIOIN_ID_LOGIN_INFO, loginInfo);
	}
	
}
