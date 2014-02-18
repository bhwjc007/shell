/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: AuthorizationPostProcessor.java $
 * $LastChangedDate: 2012-12-3 上午10:42:15 $
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
import org.apache.log4j.Logger;
import shell.framework.authorization.service.AuthorizationService;
import shell.framework.authorization.support.AuthorizationException;
import shell.framework.authorization.vo.LoginInfo;
import shell.framework.core.DefaultBeanFactory;
import shell.framework.core.SystemParam;

/**
 * <p> 鉴权前置处理器 通过过滤器实现，通过截取URL，
 * 	   匹配ACL表来判断是否有权限访问该URL </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-12-3 上午10:42:15 $
 */
public class AuthorizationPostProcessor implements Filter {

	private Logger logger = Logger.getLogger(AuthorizationPostProcessor.class);
	
	private String forbidPage = "/common/forbiddenAccess.jsp";
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		if(filterConfig.getInitParameter("forbidPage")!=null && !"".equals(filterConfig.getInitParameter("forbidPage"))){
			forbidPage = filterConfig.getInitParameter("forbidPage");
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		AuthorizationService authService = (AuthorizationService)DefaultBeanFactory.getBean(AuthorizationService.BEAN_ID);
		LoginInfo loginInfo = req.getSession().getAttribute(SystemParam.SESSIOIN_ID_LOGIN_INFO)==null ? null : (LoginInfo)req.getSession().getAttribute(SystemParam.SESSIOIN_ID_LOGIN_INFO);
		//loginFilter验证过后，session仍然没有登录信息，认为是系统漏洞，直接报错,不应该在跳转到登录页面
		if(loginInfo==null){
			logger.error("USER IS NOT LOGINING!");
			throw new AuthorizationException("USER IS NOT LOGINING!");
		}
		if(!authService.hasAuthority(loginInfo.getRoleList(), req.getRequestURI())){
			this.forbidAccess(req,resp);
			return;
		}
		filterChain.doFilter(req, resp);
	}

	
	/**
	 * 重定向到禁止访问页面
	 * @param request
	 * @param response
	 */
	protected void forbidAccess(HttpServletRequest request,HttpServletResponse response){
		try {
			response.sendRedirect(request.getContextPath()+forbidPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
