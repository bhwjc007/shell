/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: OsCacheProvider.java $
 * $LastChangedDate: 2012-4-10 下午2:52:06 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.cache;

/**
 * <p> ehcache对应的制造工厂，由他来制造具体的ehcache实例 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-4-10 下午3:29:13 $
 */
@SuppressWarnings("all")
public class EhCacheProvider implements CacheProvider {

	/* (non-Javadoc)
	 * @see shell.framework.cache.CacheProvider#buildCache(java.lang.String)
	 */
	public Cache buildCache(String regionName) {
		if(regionName==null){
			return new EhCache();
		}else{
			return new EhCache(regionName);
		}
	}

	
}
