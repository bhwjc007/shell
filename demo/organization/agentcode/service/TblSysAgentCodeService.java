/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: TblSysAgentCodeService.java $
 * $LastChangedDate: 2012-8-30 下午3:39:07 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.organization.agentcode.service;

import java.io.Serializable;

import shell.framework.dao.support.VOResult;
import shell.framework.model.TblSysAgentCode;
import shell.framework.organization.agentcode.vo.TblSysAgentCodeVO;

/**
 * <p> 系统工号服务接口定义 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-8-30 下午3:39:07 $
 */
public interface TblSysAgentCodeService {

	/**
	 * 分页查询系统工号
	 * @param currentPage
	 * @param pageSize
	 * @param agentCodeVO 工号值对象
	 * @return
	 */
	public VOResult findByPagination(int currentPage,int pageSize,TblSysAgentCodeVO agentCodeVO);
	
	/**
	 * 根据id查询系统工号
	 * @param id 工号id
	 * @return
	 */
	public TblSysAgentCode findByID(Serializable id);
	
	/**
	 * 新增系统工号
	 * @param agentCodeVO
	 * @return
	 */
	public int add(TblSysAgentCodeVO agentCodeVO);
	
	/**
	 * 更新系统工号
	 * @param agentCodeVO
	 * @return
	 */
	public int update(TblSysAgentCodeVO agentCodeVO);
	
	/**
	 * 删除系统工号，根据工号id
	 * @param agentCodeVO
	 * @return
	 */
	public int deleteByID(TblSysAgentCodeVO agentCodeVO);
	
	/**
	 * 分配指定系统工号给系统用户
	 * @param agentCodeID 系统工号
	 * @param userIds 系统用户id数组
	 * @return
	 */
	public int assignUser(TblSysAgentCodeVO agentCodeVO);
	
	/**
	 * 回收系统用户的工号
	 * @param agentCodeID
	 * @param userIds
	 * @return
	 */
	public int unAssignUser(TblSysAgentCodeVO agentCodeVO);
	
	/**
	 * 分页查询指定工号下系统用户
	 * @param currentPage
	 * @param pageSize
	 * @param agentCodeVO 工号值对象
	 * @return
	 */
	public VOResult findSysUserByPagination(int currentPage,int pageSize,TblSysAgentCodeVO agentCodeVO);
	
	/**
	 * 分页查询未绑定工号的系统用户
	 * @param currentPage
	 * @param pageSize
	 * @param agentCodeVO 工号值对象
	 * @return
	 */
	public VOResult findSysUserOfUnAssignAgentCodeByPagination(int currentPage,int pageSize,TblSysAgentCodeVO agentCodeVO);
	
	/**
	 * 工号下是否存在系统用户
	 * @param agentCodeID 工号
	 * @return
	 */
	public boolean hasSysUser(String agentCodeID);
	
	
}
