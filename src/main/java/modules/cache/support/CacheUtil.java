/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: CacheUtil.java $
 * $LastChangedDate: 2012-4-12 下午2:40:39 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.cache.support;

import java.util.List;
import org.apache.log4j.Logger;
import shell.framework.cache.Cache;
import shell.framework.cache.CacheFactory;

/**
 * <p> Cache工具类 ，供业务层调用 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-4-12 下午2:40:39 $
 */
public class CacheUtil {
	
	private Logger logger = Logger.getLogger(CacheUtil.class);
	private List<String> cacheRegionList;
	private static CacheFactory cacheFactory;

	public static String USER_CACHE = "userCache";
	public static String LOGIN_INFO_CACHE = "loginInfoCache";
	public static String USER_ROLE_CACHE = "userRoleCache";
	public static String DEPARTMENT_CACHE = "departmentCache";
	public static String FUNCTION_CACHE = "functionCache";

	
	/**
	 * @return the cacheFactory
	 */
	public static CacheFactory getCacheFactory() {
		if(cacheFactory==null){
			throw new CacheException("CacheFacotry is null!");
		}
		return cacheFactory;
	}

	/**
	 * @param cacheFactory the cacheFactory to set
	 */
	@SuppressWarnings("all")
	public void setCacheFactory(CacheFactory cacheFactory) {
		this.cacheFactory = cacheFactory;
	}

	/**
	 * @param cacheRegionList the cacheRegionList to set
	 */
	public void setCacheRegionList(List<String> cacheRegionList) {
		this.cacheRegionList = cacheRegionList;
	}
	
	/**
	 * 获取缓存对象值
	 * @param regionName 业务缓存名称
	 * @param key 缓存键名称
	 * @return
	 */
	public static Object getValue(String regionName,Object key) throws CacheException {
		return getCache(regionName).get(key);
	}
	
	
	/**
	 * 获取缓存策略下的所有缓存对象的名称
	 * @return 缓存对象名称数组
	 */
	public static String[] getCacheNames(){
		return getCache(null).getCacheNames();
	}
	
	/**
	 * 将数据存入指定缓存
	 * @param regionName 业务缓存名称
	 * @param key 键
	 * @param value 值
	 */
	public static void putValue(String regionName , Object key , Object value) throws CacheException {
		getCache(regionName).put(key, value);
	}
	
	/**
	 * 删除指定缓存中的对象值
	 * @param regionName
	 * @param key 缓存中的指定键
	 */
	public static void removeValue(String regionName , Object key) throws CacheException {
		getCache(regionName).remove(key);
	}
	
	/**
	 * 清除当前缓存策略下的所有缓存对象
	 */
	public static void clearCaches() throws CacheException {
		getCache(null).clear();
	}
	
	/**
	 * 销毁指定的cache
	 * @param regionName 业务cache名称
	 */
	public static void destoryCache(String regionName) throws CacheException {
		getCache(regionName).destroy();
	}
	
	/**
	 * 获取指定缓存中所有的对象key值
	 * @param regionName 业务缓存对象
	 * @return 存储key值的list对象
	 */
	public static List<String> getKeys(String regionName) throws CacheException {
		return getCache(regionName).getKeys();
	}
	
	/**
	 * 刷新指定缓存
	 * @param regionName 缓存名称
	 */
	public static void refreshCache(String regionName){
		//TODO 刷新指定缓存
	}
	
	/**
	 * 获取cache对象，根据指定业务名称
	 * @param regionName 业务名称，如loginCache orgnizationCache
	 * @return
	 */
	protected static Cache getCache(String regionName) throws CacheException {
		Cache cache = getCacheFactory().buildCache(regionName);
		return cache;
	}
	
	
	/**
	 * spring启动时，初始化加载各种业务cache空间（此时并没有数据填充cache空间）
	 */
	protected void initial() throws CacheException {
		try{
			if(cacheRegionList!=null && cacheRegionList.size()>0){
				for(int i=0;i<cacheRegionList.size();i++){
					getCacheFactory().buildCache(cacheRegionList.get(i));
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage() + "Cache initialization failure！");
			throw new CacheException(e);
		}
	}
	
	
}
