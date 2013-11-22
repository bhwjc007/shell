package shell.framework.cache;

/**
 * <p> 制造cache的统一工厂，根据注入的具体缓存策略来制造 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-4-10 下午3:21:31 $
 */
public class CacheFactory {

	public static String CACHEFACTORY_BEAN_ID = "cacheFactory";
	
	/**
	 * 具体缓存策略
	 */
	private CacheProvider cacheProvider;
	

	/**
	 * @return the cacheProvider
	 */
	public CacheProvider getCacheProvider() {
		return cacheProvider;
	}


	/**
	 * @param cacheProvider the cacheProvider to set
	 */
	public void setCacheProvider(CacheProvider cacheProvider) {
		this.cacheProvider = cacheProvider;
	}

	
	/**
	 * 制造cache
	 * @param reginName 缓存业务名称
	 * @return the Cache 
	 */
	public Cache buildCache(String regionName){
		return cacheProvider.buildCache(regionName);
	}
}
