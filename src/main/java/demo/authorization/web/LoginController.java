/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: LoginController.java $
 * $LastChangedDate: 2012-5-15 下午3:11:58 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.authorization.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import shell.framework.authorization.service.LoginService;
import shell.framework.core.DefaultBeanFactory;
import shell.framework.core.SystemParam;

/**
 * <p> 系统登录控制器 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-5-15 下午3:11:58 $
 */
@Controller
public class LoginController {

	@RequestMapping("/login")
	public String login(String userCode,String password,HttpServletRequest request){
		LoginService loginService = (LoginService)DefaultBeanFactory.getBean(LoginService.BEAN_ID);
		if(userCode!=null && password!=null){
			if(loginService.login(userCode, password, request)){
				return "web/defaultFrame/mainFrame";
			}
		}else{
			//浏览器对应服务端session中已经存在用户登录信息，忽略掉登录验证过程
			if(request.getSession().getAttribute(SystemParam.SESSIOIN_ID_LOGIN_INFO)!=null){
				return "web/defaultFrame/mainFrame";
			}
		}
		return "redirect:index.jsp";
	}
	
	
}
