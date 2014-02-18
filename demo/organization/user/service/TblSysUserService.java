/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: UserService.java $
 * $LastChangedDate: 2012-5-17 下午4:22:37 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.organization.user.service;

import java.io.Serializable;
import java.util.List;

import shell.framework.dao.support.VOResult;
import shell.framework.model.TblSysUser;
import shell.framework.organization.user.vo.TblSysUserDetailVO;
import shell.framework.organization.user.vo.TblSysUserVO;

/**
 * <p> 系统用户管理服务类 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-5-17 下午4:22:37 $
 */
public interface TblSysUserService {

	/**
	 * 分页查询
	 * @param currentPage 当前查询起始页码
	 * @param pageSize 每页显示记录条数
	 * @param userVO 系统用户值对象
	 * @return 分页对象
	 */
	public VOResult findByPagination(int currentPage , int pageSize, TblSysUserVO userVO);
	
	
	/**
	 * 查询单个系统用户记录，根据记录id
	 * @param id 记录唯一id
	 * @return 系统用户对象
	 */
	public TblSysUser findUserByID(Serializable id);
	
	
	/**
	 * 查询单个系统用户，根据登录ID
	 * @param userCode
	 * @return 系统用户对象 
	 */
	public TblSysUser findUserByUserCode(String userCode);
	
	
	/**
	 * 根据系统用户ID删除用户，支持批量删除
	 * @param userVO 系统用户值对象 ，删除时使用id字段值存储所有id值，以“-”分割
	 * 形如 id1-id2-id3-id4-id5-id6-id7
	 * @return 受影响记录个数
	 */
	public int deleteByID(TblSysUserVO userVO);
	
	/**
	 * 增加系统用户
	 * @param userVO 系统用户值对象
	 * @return 受影响记录数
	 */
	public int add(TblSysUserVO userVO);
	
	/**
	 * 更新系统用户
	 * @param userVO 系统用户值对象
	 * @return 受影响记录数
	 */
	public int update(TblSysUserVO userVO);
	
	/**
	 * 系统用户详细信息显示
	 * @param id
	 * @return
	 */
	public TblSysUserDetailVO show(Serializable id);
	
	/**
	 * 索引指定系统用户分配的角色
	 * @param currentPage
	 * @param pageSize
	 * @param userVO 系统用户值对象
	 * @return 分页对象
	 */
	public VOResult findAssignRoleByPagination(int currentPage,int pageSize,TblSysUserVO userVO);
	
	/**
	 * 索引所有已经分配给指定用户的角色 
	 * @param currentPage
	 * @param pageSize
	 * @param userVO
	 * @return
	 */
	public VOResult findUnAssignRoleByPagination(int currentPage,int pageSize,TblSysUserVO userVO);
	
	
	/**
	 * 分配系统角色给指定用户
	 * @param userVO 系统用户值对象
	 * @return 更新记录数
	 */
	public int assignSysRole(TblSysUserVO userVO);
	
	/**
	 * 回收指定系统用户的已经分配的角色
	 * @param userVO
	 * @return
	 */
	public int unAssignSysRole(TblSysUserVO userVO);
	
	/**
	 * 索引所有系统用户
	 * @return
	 */
	public List<?> findAllUser();
}
