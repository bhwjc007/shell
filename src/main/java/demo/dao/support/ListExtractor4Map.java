/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: ListExtractor4Map.java $
 * $LastChangedDate: 2012-4-18 下午9:07:13 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.dao.support;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;


/**
 * <p> 结果集提取器，结果的数据结构是list中存储map结构 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-4-18 下午9:07:13 $
 */
public class ListExtractor4Map implements ResultSetExtractor<Object> {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.ResultSetExtractor#extractData(java.sql.ResultSet)
	 */
	public Object extractData(ResultSet rs) throws SQLException,DataAccessException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnNum = rsmd.getColumnCount();
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		while(rs.next()){
			Map<String,Object> map4Row = new HashMap<String,Object>();
			for(int i=1;i<=columnNum;i++){
				map4Row.put(rsmd.getColumnName(i), rs.getObject(i));
			}
			result.add(map4Row);
		}
		return result;
	}
}
