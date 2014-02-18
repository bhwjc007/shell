/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: TblSysDepartmentService.java $
 * $LastChangedDate: 2012-6-26 下午5:30:26 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.organization.department.service;

import java.io.Serializable;

import shell.framework.dao.support.VOResult;
import shell.framework.model.TblSysDepartment;
import shell.framework.organization.department.vo.TblSysDepartmentVO;
import shell.framework.organization.user.vo.TblSysUserVO;

/**
 * <p> 系统部门服务类接口定义 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-6-26 下午5:30:26 $
 */
public interface TblSysDepartmentService {

	/**
	 * 分页查询
	 * @param currentPage 当前查询起始页码
	 * @param pageSize 每页显示记录条数
	 * @param departmentVO 系统部门值对象
	 * @return 分页对象
	 */
	public VOResult findByPagination(int currentPage , int pageSize, TblSysDepartmentVO departmentVO);
	
	/**
	 * 查询单个系统部门记录，根据记录id
	 * @param id 记录唯一id
	 * @return 系统部门对象
	 */
	public TblSysDepartment findDepartmentByID(Serializable id);
	
	/**
	 * 根据系统部门ID删除部门，支持批量删除，部门下存在用户和岗位的默认不删除，也不提示
	 * @param departmentVO 系统部门值对象 ，删除时使用id字段值存储所有id值，以“-”分割
	 * 形如 id1-id2-id3-id4-id5-id6-id7
	 * @return 受影响记录个数
	 */
	public int deleteByID(TblSysDepartmentVO departmentVO);
	
	/**
	 * 增加系统部门
	 * @param departmentVO 系统部门值对象
	 * @return 受影响记录数
	 */
	public int add(TblSysDepartmentVO departmentVO);
	
	/**
	 * 更新系统部门
	 * @param departmentVO 系统部门值对象
	 * @return 受影响记录数
	 */
	public int update(TblSysDepartmentVO departmentVO);
	
	/**
	 * 查询部门下所有的岗位信息
	 * @param currentPage 当前页码
	 * @param pageSize 每页记录条数
	 * @param departmentId 部门ID
	 * @return 分页对象-存储岗位对象
	 */
	public VOResult findPositionByPagination(int currentPage , int pageSize, Serializable departmentId);
	
	/**
	 * 给部门设立岗位，支持批量设置
	 * @param departmentId 部门ID
	 * @param positionIds 岗位id-数组
	 */
	public int assignPosition(final String departmentId , String[] positionIds);
	
	/**
	 * 给部门分配系统用户，支持批量设置
	 * @param departmentId 部门id
	 * @param sysUserIds 用户id组 - 数组
	 * @return
	 */
	public int assignSysUser(TblSysDepartmentVO departmentVO);
	
	/**
	 * 给部门分配角色，支持批量分配
	 * @param departmentVO
	 * @return
	 */
	public int assignSysRole(TblSysDepartmentVO departmentVO);
	
	/**
	 * 回收指定部门下系统用户
	 * @param departmentId 部门id
	 * @param sysUserIds 用户id数组
	 * @return
	 */
	public int unassignSysUser(final String departmentId , String[] sysUserIds);
	
	/**
	 * 回收指定部门下岗位
	 * @param departmentId 部门id
	 * @param positionIds 岗位id数组
	 * @return
	 */
	public int unassignPosition(final String departmentId , String[] positionIds);
	
	
	/**
	 * 回收部门的角色
	 * @param departmentVO
	 * @return 回收的角色记录数
	 */
	public int unAssignSysRole(TblSysDepartmentVO departmentVO);
	
	/**
	 * 分页查询部门人员
	 * @param currentPage
	 * @param pageSize
	 * @param departmentId 部门id
	 * @return 分页对象-人员
	 */
	public VOResult findUserByPagination(int currentPage , int pageSize, TblSysDepartmentVO departmentVO);
	
	
	/**
	 * 分页查询指定部门分配的角色
	 * @param currentPage
	 * @param pageSize
	 * @param departmentVO
	 * @return 分页对象-角色
	 */
	public VOResult findRoleByPagination(int currentPage , int pageSize, TblSysDepartmentVO departmentVO);
	
	
	/**
	 * 查询未分配给部门的系统用户
	 * @param currentPage
	 * @param pageSize
	 * @param departmentId 部门id
	 * @return 分页对象-人员
	 */
	public VOResult findUserByUnbindDepartment(int currentPage , int pageSize, TblSysDepartmentVO departmentVO);
	
	
	/**
	 * 查询未分配给部门的系统角色
	 * @param currentPage
	 * @param pageSize
	 * @param departmentVO
	 * @return
	 */
	public VOResult findRoleByUnAssignDepartment(int currentPage , int pageSize, TblSysDepartmentVO departmentVO);
	
	
	/**
	 * 按照数组次序重新给部门进行排序
	 * @param departmentIds 部门id数组
	 */
	public void reOrder(String[] departmentIds);
	
	/**
	 * 部门下是否存在用户
	 * @param departmentID
	 * @return
	 */
	public boolean hasSysUser(String departmentID);
	
	/**
	 * 部门下是否存在岗位
	 * @param departmentID
	 * @return
	 */
	public boolean hasSysPosition(String departmentID);
	
}
