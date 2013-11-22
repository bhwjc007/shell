/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * InterfaceName: shell.framework.dao.IMybatisBaseDao.java
 * CreatedTime: 13-1-22 下午2:12
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */

package shell.framework.dao;

import shell.framework.dao.support.DaoException;
import shell.framework.dao.support.VOResult;

/**
 * <p> 通用持久层Mybatis实现接口定义 </p>
 *
 * @author changming.Y
 * @version 1.0   13-1-22 下午2:12
 */
public interface IMybatisBaseDao {

    public VOResult query() throws DaoException;




}
