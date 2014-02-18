/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: TblSysUserInterceptor.java $
 * $LastChangedDate: 2012-11-24 下午5:39:27 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.organization.user.service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * <p> 系统用户操作拦截器 ，添加如缓存、日志记录等切面操作的逻辑 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-11-24 下午5:39:27 $
 */
public class TblSysUserInterceptor implements MethodInterceptor {

	/* (non-Javadoc)
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if(invocation.getMethod().getName().equalsIgnoreCase("findByPagination")){
			System.out.println("...........Before . Method is " + invocation.getMethod().getName());
		}
		Object obj = invocation.proceed();
		System.out.println("..........AfterMethod Interceptor.");
		
		return obj;
	}

}
