/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: CacheException.java $
 * $LastChangedDate: 2012-4-12 下午2:27:18 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.authorization.support;

/**
 * <p> 权限异常 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-4-12 下午2:27:18 $
 */
@SuppressWarnings("serial")
public class AuthorizationException extends RuntimeException {

	public AuthorizationException(String message) {
		super(message);
	}
	
	public AuthorizationException(Throwable throwable){
		super(throwable);
	}
	
	public AuthorizationException(String message,Throwable throwable){
		super(message, throwable);
	}
}
