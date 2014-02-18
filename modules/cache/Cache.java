package shell.framework.cache;

import java.util.List;

import shell.framework.cache.support.CacheException;

public interface Cache {
	
	/**
	 * 取缓存值
	 * @param key 缓存中的键
	 * @return 值对象
	 * @throws CacheException
	 */
	public Object get(Object key) throws CacheException;
	
	/**
	 * 获取cache所有key数组
	 * @return 缓存key数组
	 * @throws CacheException
	 */
	public List<String> getKeys() throws CacheException;
	
	/**
	 * 放入缓存数据
	 * @param key 
	 * @param value
	 * @throws CacheException
	 */
	public void put(Object key,Object value) throws CacheException;
	
	/**
	 * 删除与key对应的缓存值
	 * @param key
	 * @throws CacheException
	 */
	public void remove(Object key) throws CacheException;
	
	/**
	 * 销毁当前缓存对象
	 * @throws CacheException
	 */
	public void destroy()  throws CacheException;
	
	/**
	 * 清除当前缓存策略下的所有缓存对象
	 * @throws CacheException
	 */
	public void clear() throws CacheException;
	
	/**
	 * 获取当前缓存策略下的所有缓存对象名称
	 * @return
	 * @throws CacheException
	 */
	public String[] getCacheNames() throws CacheException;

}
