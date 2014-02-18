package shell.framework.cache;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


/**
 * <p> net.sf.ehcache.Ehcache 包装类 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-4-10 下午2:57:11 $
 */
public class EhCache implements Cache {

	private Logger logger = Logger.getLogger(EhCache.class);
	private CacheManager cacheManager = null;
	private net.sf.ehcache.Cache cache = null;
	
	/**
	 * 无参数构造函数
	 * Class Constructor
	 */
	public EhCache(){}
	
	/**
	 * 构造函数，创建cache对象
	 * Class Constructor
	 * @param regionName
	 */
	public EhCache(String regionName){
		try{
			cacheManager = CacheManager.create();
			cache = cacheManager.getCache(regionName);
			if(cache==null){
				System.out.println("--------default setting :" + regionName);
				logger.warn("Could not find configuration for " + regionName + ". Configuring using the defaultCache settings.");
				cacheManager.addCache(regionName);
				cache = cacheManager.getCache(regionName);
			}
		}catch(CacheException e){
			logger.error(e.getMessage());
			throw new CacheException(e);
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see shell.framework.cache.Cache#get(java.lang.Object)
	 */
	public Object get(Object key)  throws shell.framework.cache.support.CacheException {
		try{
			Element element = cache.get((Serializable)key);
			if(element!=null){
				return element.getValue();
			}
			logger.warn("element of the key[" + key + "] is null");
			return null;
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new shell.framework.cache.support.CacheException(e);
		}
	}


	/* (non-Javadoc)
	 * @see shell.framework.cache.Cache#getKeys()
	 */
	@SuppressWarnings("unchecked")
	public List<String> getKeys() throws shell.framework.cache.support.CacheException {
		try{
			return cache.getKeys();
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new shell.framework.cache.support.CacheException(e);
		}
	}


	/* (non-Javadoc)
	 * @see shell.framework.cache.Cache#put(java.lang.Object, java.lang.Object)
	 */
	public void put(Object key, Object value) throws shell.framework.cache.support.CacheException {
		try{
			Element element = new Element(key,value);
			cache.put(element);
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new shell.framework.cache.support.CacheException(e);
		}
	}


	/* (non-Javadoc)
	 * @see shell.framework.cache.Cache#remove(java.lang.Object)
	 */
	public void remove(Object key)	throws shell.framework.cache.support.CacheException {
		try{
			cache.remove((Serializable)key);
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new shell.framework.cache.support.CacheException(e);
		}
	}


	/* (non-Javadoc)
	 * @see shell.framework.cache.Cache#destroy()
	 */
	public void destroy() throws shell.framework.cache.support.CacheException {
		try{
			if(cacheManager==null){
				cacheManager = CacheManager.create();
			}
			cacheManager.removeCache(cache.getName());
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new shell.framework.cache.support.CacheException(e);
		}
	}


	/* (non-Javadoc)
	 * @see shell.framework.cache.Cache#clear()
	 */
	public void clear() throws shell.framework.cache.support.CacheException {
		try{
			if(cacheManager==null){
				cacheManager = CacheManager.create();
			}
			cacheManager.removalAll();
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new shell.framework.cache.support.CacheException(e);
		}
	}


	/* (non-Javadoc)
	 * @see shell.framework.cache.Cache#getCacheNames()
	 */
	public String[] getCacheNames() throws shell.framework.cache.support.CacheException {
		try{
			String[] cacheName = null;
			if(cacheManager==null){
				cacheManager = CacheManager.create();
			}
			cacheName = cacheManager.getCacheNames();
			return cacheName;
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new shell.framework.cache.support.CacheException(e);
		}
	}
	
	
	
}
