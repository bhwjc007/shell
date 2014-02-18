/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: UserController.java $
 * $LastChangedDate: 2012-5-15 下午4:41:28 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.organization.user.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import shell.framework.core.DefaultBeanFactory;
import shell.framework.core.SystemParam;
import shell.framework.dao.support.VOResult;
import shell.framework.model.TblSysUser;
import shell.framework.organization.department.service.TblSysDepartmentService;
import shell.framework.organization.user.service.TblSysUserService;
import shell.framework.organization.user.vo.TblSysUserDetailVO;
import shell.framework.organization.user.vo.TblSysUserVO;

/**
 * <p> 人员管理控制器 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-5-15 下午4:41:28 $
 */
@Controller
@RequestMapping("/web/organization/user/*")
public class UserController implements SystemParam {
	
	/**
	 * 索引所有的系统用户，分页查询
	 * @param request http请求
	 * @param currentPage 当前页
	 * @param userVO 系统用户值对象
	 * @return 跳转的url
	 */
	@RequestMapping(value="index")
	public ModelAndView index(HttpServletRequest request ,@RequestParam(required=false) Integer currentPage , TblSysUserVO userVO){
		TblSysUserService tblSysUserService = (TblSysUserService)DefaultBeanFactory.getBean("tblSysUserService");
		if(currentPage==null || currentPage<=0){
			currentPage = 1;
		}
		VOResult voResult = tblSysUserService.findByPagination(currentPage, PAGE_SIZE, userVO);
		return new ModelAndView("web/organization/user/index","voResult",voResult);
	}
	
	
	/**
	 * 删除系统用户，支持批量删除
	 * @param request
	 * @param userVO 系统用户值对象 ，删除时使用id字段值存储所有id值，以“-”分割
	 * 形如 id1-id2-id3-id4-id5-id6-id7
	 * @return 跳转链接
	 */
	@RequestMapping(value="delete")
	public ModelAndView delete(HttpServletRequest request , TblSysUserVO userVO){
		TblSysUserService tblSysUserService = (TblSysUserService)DefaultBeanFactory.getBean("tblSysUserService");
		int rowNums = 0;
		if(userVO.getId()!=null && !"".equals(userVO.getId().trim())){
			rowNums = tblSysUserService.deleteByID(userVO);
		}
		return new ModelAndView("redirect:/web/organization/user/index.action");
	}
	
	
	/**
	 * 增加系统用户
	 * @param request
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value="add")
	public ModelAndView add(HttpServletRequest request , TblSysUserVO userVO){
		TblSysUserService tblSysUserService = (TblSysUserService)DefaultBeanFactory.getBean("tblSysUserService");
		int rowNums = 0;
		if(userVO!=null && !"".equals(userVO.getUserCode()) && !"".equals(userVO.getPassword()) && !"".equals(userVO.getFullName())  ){
			rowNums = tblSysUserService.add(userVO);
		}
		return new ModelAndView("common/ok");
	}
	
	
	/**
	 * 加载更新页面
	 * @param request
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value="preUpdate",method=RequestMethod.GET)
	public ModelAndView preUpdate(HttpServletRequest request , TblSysUserVO userVO){
		TblSysUserService tblSysUserService = (TblSysUserService)DefaultBeanFactory.getBean("tblSysUserService");
		TblSysUser sysUser = null;
		if(userVO.getId()!=null && !"".equals(userVO.getId())){
			sysUser  = tblSysUserService.findUserByID(userVO.getId());
		}
		return new ModelAndView("web/organization/user/userUpdate","tblSysUser",sysUser);
	}
	
	
	/**
	 * 更新系统用户
	 * @param request
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value="update")
	public ModelAndView update(HttpServletRequest request , TblSysUserVO userVO){
		TblSysUserService tblSysUserService = (TblSysUserService)DefaultBeanFactory.getBean("tblSysUserService");
		int rowNums = 0;
		rowNums = tblSysUserService.update(userVO);
		return new ModelAndView("common/ok");
	}
	
	
	/**
	 * 展示系统用户详细信息
	 * @param request
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value="show")
	public ModelAndView show(HttpServletRequest request , TblSysUserVO userVO){
		TblSysUserService tblSysUserService = (TblSysUserService)DefaultBeanFactory.getBean("tblSysUserService");
		TblSysUserDetailVO sysUser = null;
		if(userVO.getId()!=null && !"".equals(userVO.getId())){
			sysUser  = tblSysUserService.show(userVO.getId());
		}
		return new ModelAndView("web/organization/user/userDetail","tblSysUser",sysUser);
	}
	
	
	/**
	 * 索引所有已经分配给指定用户的角色
	 * @param request
	 * @param currentPage
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value="roleIndex")
	public ModelAndView roleIndex(HttpServletRequest request ,@RequestParam(required=false) Integer currentPage , 
							   TblSysUserVO userVO){
		TblSysUserService tblSysUserService = (TblSysUserService)DefaultBeanFactory.getBean("tblSysUserService");
		if(currentPage==null || currentPage<=0){
			currentPage = 1;
		}
		VOResult voResult = tblSysUserService.findAssignRoleByPagination(currentPage, SystemParam.PAGE_SIZE, userVO);
		return new ModelAndView("web/organization/user/roleIndex","voResult",voResult);
	}
	
	
	/**
	 * 索引所有未分配给指定系统用户的角色
	 * @param request
	 * @param currentPage
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value="unAssignRoleIndex")
	public ModelAndView unAssignRoleIndex(HttpServletRequest request ,@RequestParam(required=false) Integer currentPage,
								TblSysUserVO userVO){
		TblSysUserService tblSysUserService = (TblSysUserService)DefaultBeanFactory.getBean("tblSysUserService");
		if(currentPage==null || currentPage<=0){
			currentPage = 1;
		}
		VOResult voResult = tblSysUserService.findUnAssignRoleByPagination(currentPage, SystemParam.PAGE_SIZE, userVO);
		return new ModelAndView("web/organization/user/userRoleAdd","voResult",voResult);
	}
	
	
	/**
	 * 分配系统角色给指定用户
	 * @param request
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value="assignSysRole")
	public ModelAndView assignSysRole(HttpServletRequest request , TblSysUserVO userVO){
		TblSysUserService tblSysUserService = (TblSysUserService)DefaultBeanFactory.getBean("tblSysUserService");
		int rowNums = 0;
		rowNums = tblSysUserService.assignSysRole(userVO);
		return new ModelAndView("common/ok");
	}
	
	
	/**
	 * 回收指定用户已经分配的角色
	 * @param request
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value="unAssignSysRole")
	public ModelAndView unAssignSysRole(HttpServletRequest request , TblSysUserVO userVO){
		TblSysUserService tblSysUserService = (TblSysUserService)DefaultBeanFactory.getBean("tblSysUserService");
		int rowNums = 0;
		rowNums = tblSysUserService.unAssignSysRole(userVO);
		return new ModelAndView("redirect:/web/organization/user/roleIndex.action");
	}
}