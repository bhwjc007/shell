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
 * <p> oscache的制造工厂，由他来制造具体的oscache实例 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-4-10 下午2:52:06 $
 */
public class OsCacheProvider implements CacheProvider {

	/* (non-Javadoc)
	 * @see shell.framework.cache.CacheProvider#buildCache(java.lang.String)
	 */
	public Cache buildCache(String regionName) {
		return new OsCache(regionName);
	}

}
