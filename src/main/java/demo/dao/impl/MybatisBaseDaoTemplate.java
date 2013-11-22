/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * ClassName: shell.framework.dao.impl.MybatisBaseDaoTemplate.java
 * CreatedTime: 13-1-22 下午2:18
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.dao.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import shell.framework.dao.IMybatisBaseDao;
import shell.framework.dao.support.DaoException;
import shell.framework.dao.support.VOResult;

/**
 * <p> 通用持久层mybatis实现
 *     封装org.mybatis.spring.SqlSessionTemplate
 * </p>
 *
 * @author changming.Y
 * @version 1.0   13-1-22 下午2:18
 */
public class MybatisBaseDaoTemplate extends SqlSessionTemplate implements IMybatisBaseDao {

    /**
     * constructor 父类需要从构造函数注入SqlSessionFactory对象
     * @param sqlSessionFactory  sqlSessionFactory
     */
    public MybatisBaseDaoTemplate(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public VOResult query() throws DaoException {
        return null;  //TODO something.
    }



}
