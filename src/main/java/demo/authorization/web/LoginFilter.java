/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: LoginFilter.java $
 * $LastChangedDate: 2012-5-6 下午12:47:57 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.authorization.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import shell.framework.authorization.service.LoginService;
import shell.framework.core.DefaultBeanFactory;
import shell.framework.core.SystemParam;

/**
 * <p> 验证用户请求资源时是否已经登录 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-5-6 下午12:47:57 $
 */
public class LoginFilter implements Filter {
	private String loginPage = "/index.jsp";
	private Logger logger = Logger.getLogger(LoginFilter.class);
	
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {}

	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) 
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		//根据sessionID获取当前浏览器对应session
		HttpSession session = req.getSession();
		if(session==null){
			logger.warn("NO SESSION SPECIFIED!");
			//跳转到登录页面
			this.doLogin(req, resp, filterChain);
			return;
		}

		//session中没有登录信息
		if(session.getAttribute(SystemParam.SESSIOIN_ID_LOGIN_INFO)==null){
			//尝试从url串获取用户名和密码
			String usercode = req.getParameter("userCode");
			String pwd = req.getParameter("password");
			if(usercode!=null && pwd!=null && !usercode.trim().equals("") && !"".equals(pwd.trim())){
				LoginService loginService = (LoginService)DefaultBeanFactory.getBean(LoginService.BEAN_ID);
				//如果用户验证失败，转向登录页面
				if(!loginService.login((String)usercode,pwd,req)){
					this.doLogin(req, resp, filterChain);
					return;
				}
			}else{
				this.doLogin(req, resp, filterChain);
				return;
			}
		}
		filterChain.doFilter(req, resp);
	}

	
	/**
	 * 验证失败，转向登录页面
	 * @param request   http请求
	 * @param response  http应答
	 * @param filterChain   过滤器
	 */
    @SuppressWarnings("unused")
	public void doLogin(ServletRequest request, ServletResponse response, FilterChain filterChain){
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		try {
			//重定向到登录页面
			resp.sendRedirect(req.getContextPath()+loginPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		if(filterConfig.getInitParameter("loginPage")!=null && !"".equals(filterConfig.getInitParameter("loginPage"))){
			loginPage = filterConfig.getInitParameter("loginPage");
		}
	}

}
