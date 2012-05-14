/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: LoginService.java $
 * $LastChangedDate: 2012-4-29 下午9:45:06 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.authorization.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import shell.framework.authorization.support.AuthorizationException;
import shell.framework.authorization.vo.UserInfo;
import shell.framework.core.DefaultBeanFactory;
import shell.framework.dao.impl.JdbcBaseDaoTemplate;
import shell.framework.model.TblSysUser;
import shell.framework.util.PopulateUtil;

/**
 * <p> 系统权限之登录服务，不需要基于接口方式，业务类直接调用即可 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-4-29 下午9:45:06 $
 */
public class LoginService {

	private Logger logger = Logger.getLogger(LoginService.class);
	
	public static String BEAN_ID = "loginService";
	
	
	/**
	 * 用户登录
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
			throw new RuntimeException("NO USERCODE OR PASSWORD SPECIFIED !");
		}
		
		List<?> resultList = this.checkUser(userCode, password);
		
		if (resultList==null || resultList.size()==0){
			String message = "userCode or password is mismatching!";
			return false;
		}else{
			TblSysUser user = (TblSysUser)resultList.get(0);
			this.updateSession(user, request);
			return true;
		}
	}
	
	
	
	public void logout() throws AuthorizationException{
	}
	
	
	/**
	 * 验证是否存在该用户
	 * @param userCode 用户ID
	 * @param password 密码，明文
	 * @return
	 */
	public List<?> checkUser(String userCode,String password) {
		String sql = "select * from TBL_SYS_USER user where user.USERCODE=? and user.PASSWORD=?";
		
		JdbcBaseDaoTemplate jbdt = (JdbcBaseDaoTemplate)DefaultBeanFactory.getBean("baseDaoTemplate.Jdbc");
		//password要进行加密
		List<?> resultList = jbdt.query(sql, new Object[]{userCode,password}, new RowMapper<Object>(){
			
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
		});
		
		return resultList;
	}
	
	
	/**
	 * 更新session中的用户登录信息
	 * @param user 当前登录用户
	 * @param request
	 */
	public void updateSession(TblSysUser user , HttpServletRequest request){
		if(request==null){
			logger.warn("THE REQUEST IS NOT COME FROM HTTP!");
			return;
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUser(user);
		userInfo.setLoginHost(request.getRemoteHost());
		userInfo.setLoginIP(request.getRemoteAddr());
		//需要使用日期工具解析成字符串
		userInfo.setLoginTime(String.valueOf(System.currentTimeMillis()));
		userInfo.setSessionID(request.getRequestedSessionId());
		userInfo.setUrl(request.getRequestURI());
		
		HttpSession session = request.getSession(true);
		if(session.getAttribute(user.getUserCode())!=null){
			session.removeAttribute(user.getUserCode());
		}
		session.setAttribute(user.getUserCode(), userInfo);
	}
	
}
