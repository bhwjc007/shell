package shell.framework.cache;

/**
 * <p> cache制造工厂接口规范 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-4-10 下午2:49:57 $
 */
public interface CacheProvider {

	public Cache buildCache(String regionName);
}
