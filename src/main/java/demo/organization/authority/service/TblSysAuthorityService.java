/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * InterfaceName: shell.framework.organization.authority.service.TblSysAuthorityService.java
 * CreatedTime: 13-1-14 下午1:18
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */

package shell.framework.organization.authority.service;

import shell.framework.dao.support.VOResult;
import shell.framework.model.TblSysAuthority;
import shell.framework.organization.authority.vo.TblSysFunctionVO;
import java.io.Serializable;

/**
 * <p> 系统权限服务接口 </p>
 *
 * @author changming.Y
 * @version 1.0   13-1-14 下午1:18
 */
public interface TblSysAuthorityService {

    /**
     * 分页索引所有的系统权限
     * @param currentPage 当前页码
     * @param pageSize    每页显示记录条数
     * @param sysFunctionVO 系统权限值对象
     * @return 分页对象
     */
    public VOResult findByPagination(int currentPage,int pageSize,TblSysFunctionVO sysFunctionVO);

    /**
     * 查找权限
     * @param id 权限id
     * @return   权限对象
     */
    public TblSysAuthority findByID(Serializable id);

    /**
     * 新增权限对象
     * @param sysFunctionVO  系统权限值对象
     * @return 更新记录条数
     */
    public int add(TblSysFunctionVO sysFunctionVO);

    /**
     * 更新系统权限
     * @param sysFunctionVO 系统权限值对象
     * @return   更新记录条数
     */
    public int update(TblSysFunctionVO sysFunctionVO);

    /**
     * 删除系统权限
     * @param sysFunctionVO  更新记录条数
     * @return  删除记录条数
     */
    public int deleteByID(TblSysFunctionVO sysFunctionVO);



}
