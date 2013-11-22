package shell.framework.core;

import javax.servlet.ServletContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 缺省bean工厂，所有bean的获取都通过该类-使用单利模式实现
 * 该情景必须在业务层中使用，不得在spring实例化过程中调用该类获取bean
 * 会出现死循环
 * 如：DefaultBeanFactory.getBean("beanName")
 * @author yangchangming
 *
 */
@SuppressWarnings("unused")
public class DefaultBeanFactory {
	
	//spring资源配置文件路径
	private static final String CONTEXT_CONFIG_LOCATION = "classpath:applicationContext.xml";
	private static BeanFactory beanFactory = null;
	private static ServletContext sc = null;
	private static DefaultBeanFactory instance = null;
	private DefaultBeanFactory(){}
	
	public static DefaultBeanFactory getInstance(BeanFactory beanFactory , ServletContext sc){
		DefaultBeanFactory.beanFactory = beanFactory;
		DefaultBeanFactory.sc = sc;
		if(instance==null){
			instance = new DefaultBeanFactory();
		}
		return instance;
	}
	
	/**
	 * 系统获取bean统一入口
	 * @param beanName bean资源配置名称
	 * @return
	 */
	public static Object getBean(String beanName){
		return getBeanFactory().getBean(beanName);
	}
	
	/**
	 * 获取beanFactory(ClassPathXmlApplicationContext)
	 * @return the beanFactory
	 */
	private static BeanFactory getBeanFactory() {
		if(beanFactory==null){
			//当前servlet容器已经启动成功，servletContext不为空
			if(sc!=null){
				//从当前web应用的servletContext中获取applicationContext（beanFactory）--ROOT
				beanFactory = WebApplicationContextUtils.getWebApplicationContext(sc);
			}else{
				//servlet容器启动失败，就手动创建applicationContext，并在servletContext中进行注册
				//注意：在spring初始实例话的bean中，不要调用BeanFactory.getBean()方法，这里容易造成循环加载spring bean实例，出现死循环
				beanFactory = new ClassPathXmlApplicationContext(CONTEXT_CONFIG_LOCATION);
			}
		}
		// 如果beanFactory不是web类型的applicationContext，就将其注册到servletContext中，此处会失败-类型转换错误
		//WebApplicationContextUtils.registerEnvironmentBeans((ConfigurableListableBeanFactory)beanFactory, sc);
		return DefaultBeanFactory.beanFactory;
	}
	
	@Override
	public String toString() {
		return "三体文明来临！ :)";
	}
	
	
	
}
