/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: ListExtractor4Bean.java $
 * $LastChangedDate: 2012-4-19 上午9:18:55 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.dao.support;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.ResultSetDynaClass;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

//import com.sun.org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtils;

/**
 * <p> 结果集提取器，结果集属性转为bean属性，返回包含bean的list对象 
 * 	   该提取器针对
 * </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-4-19 上午9:18:55 $
 */
public class ListExtractor4Bean implements ResultSetExtractor<Object> {

	private Logger logger = Logger.getLogger(ListExtractor4Bean.class);
	
	private Class<?> beanClazz = null;
	
	/**
	 * Class Constructor
	 * @param beanClazz
	 */
	public ListExtractor4Bean(Class<?> beanClazz){
		if(beanClazz==null){
			logger.error("beanClazz can not  null!");
			throw new DaoException("beanClazz can not null!");
		}
		this.beanClazz = beanClazz;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
	 */
	@SuppressWarnings("all")
	public Object extractData(ResultSet rs) throws SQLException,DataAccessException {
		List resultList = new ArrayList();
		try {
			Object bean = null;
			ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
			Iterator rows = rsdc.iterator();
			while(rows.hasNext()){
				bean = beanClazz.newInstance();
				DynaBean rowBean = (DynaBean)rows.next();
				BeanUtils.copyProperties(rowBean, bean);
				resultList.add(bean);
			}
			
		} catch (InstantiationException e) {
			logger.error(e.getMessage() + "| bean instance failure!");
			throw new DaoException(e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage() + "| bean instance failure!");
			throw new DaoException(e);	
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage() + "| bean instance failure!");
			throw new DaoException(e);	
		}
		
		return resultList;
	}
}
