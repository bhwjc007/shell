/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: CacheException.java $
 * $LastChangedDate: 2012-4-12 下午2:27:18 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.cache.support;

/**
 * <p> 缓存异常 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-4-12 下午2:27:18 $
 */
@SuppressWarnings("serial")
public class CacheException extends RuntimeException {

	public CacheException(String message) {
		super(message);
	}
	
	public CacheException(Throwable throwable){
		super(throwable);
	}
	
	public CacheException(String message,Throwable throwable){
		super(message, throwable);
	}
}
