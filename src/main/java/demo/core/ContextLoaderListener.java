package shell.framework.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * servlet容器启动加载此监听器，该监听器负责启动spring
 * 此监听器本身即是应用上下文加载器，因为继承了ContextLoader
 * @author yangchangming
 *
 */
public class ContextLoaderListener extends ContextLoader implements ServletContextListener {

	/*
	 * spring上下文加载器
	 */
	private ContextLoader contextLoader = null;
	
	/*
	 * web应用上下文
	 */
	private WebApplicationContext wac = null;
	
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent event) {
		String beanName = "pilot";
	
		if(this.contextLoader==null){
			this.contextLoader = this;
		}
		System.out.println("\n\nLoading Default Web Application Context ...\n");
		//初始化web应用上下文-这种方式要求web.xml中已经配置好spring配置文件[classpath:applicationContext.xml]
		wac = this.contextLoader.initWebApplicationContext(event.getServletContext());
		//通过这里给DefaultBeanFactory注入bean的IOC容器（beanfactory）
		DefaultBeanFactory.getInstance(wac, event.getServletContext());
		
	    System.out.println("* * * * * * * * * * * * * * * * * * * *\n" +
	                           "* * * * * * * * * * * * * * * * * * * *\n" +
	                           "* * * * * * * * * * * * * * * * * * * *\n" +
	                           "* * * * * * * * * * * * * * * * * * * *\n" +
	                           "* * * * * * * * * * * * * * * * * * * *\n" +
	                           "* * * -> " +
	                           "@SHELL" + " say: " + DefaultBeanFactory.getBean(beanName) +
	                           "  * * *\n" +
	                           "* * * * * * * * * * * * * * * * * * * *\n" +
	                           "* * * * * * * * * * * * * * * * * * * *\n" +
	                           "* * * * * * * * * * * * * * * * * * * *\n" +
	                           "* * * * * * * * * * * * * * * * * * * *\n" +
	                           "* * * * * * * * * * * * * * * * * * * *\n");
	    System.out.println("Welcome @SHELL framework tour, enjoy!\n");
	    
	}

}
