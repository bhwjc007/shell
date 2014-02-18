/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: OsCache.java $
 * $LastChangedDate: 2012-4-12 下午4:03:19 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.cache;

import java.util.List;

import org.apache.log4j.Logger;

import shell.framework.cache.support.CacheException;

/**
 * <p>  </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-4-12 下午4:03:19 $
 */
public class OsCache implements Cache {

	private Logger logger = Logger.getLogger(OsCache.class);
	
	/**
	 * 构造函数
	 * Class construtor
	 * @param regionName
	 */
	public OsCache(String regionName){
		
	}
	
	/* (non-Javadoc)
	 * @see shell.framework.cache.Cache#get(java.lang.Object)
	 */
	public Object get(Object key) throws CacheException {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see shell.framework.cache.Cache#getKeys()
	 */
	public List<String> getKeys() throws CacheException {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see shell.framework.cache.Cache#put(java.lang.Object, java.lang.Object)
	 */
	public void put(Object key, Object value) throws CacheException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see shell.framework.cache.Cache#remove(java.lang.Object)
	 */
	public void remove(Object key) throws CacheException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see shell.framework.cache.Cache#destroy()
	 */
	public void destroy() throws CacheException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see shell.framework.cache.Cache#clear()
	 */
	public void clear() throws CacheException {
		// TODO Auto-generated method stub

	}


	/* (non-Javadoc)
	 * @see shell.framework.cache.Cache#getCacheNames()
	 */
	public String[] getCacheNames() throws CacheException {
		// TODO Auto-generated method stub
		return null;
	}

}
