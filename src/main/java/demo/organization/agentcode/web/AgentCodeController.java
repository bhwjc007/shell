/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: AgentCodeController.java $
 * $LastChangedDate: 2012-8-30 上午11:36:12 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.organization.agentcode.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import shell.framework.core.DefaultBeanFactory;
import shell.framework.core.SystemParam;
import shell.framework.dao.support.VOResult;
import shell.framework.model.TblSysAgentCode;
import shell.framework.organization.agentcode.service.TblSysAgentCodeService;
import shell.framework.organization.agentcode.vo.TblSysAgentCodeVO;

/**
 * <p> 系统工号控制器 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-8-30 上午11:36:12 $
 */
@Controller
@RequestMapping("/web/organization/agentCode/*")
@SuppressWarnings("unused")
public class AgentCodeController {
	
	/**
	 * 索引所有的系统工号
	 * @param request
	 * @param currentPage
	 * @param sysAgentCodeVO 系统工号值对象
	 * @return
	 */
	@RequestMapping(value="index")
	public ModelAndView index(HttpServletRequest request , @RequestParam(required=false) Integer currentPage,
			TblSysAgentCodeVO sysAgentCodeVO){
		TblSysAgentCodeService sysAgentCodeService  = (TblSysAgentCodeService)DefaultBeanFactory.getBean("tblSysAgentCodeService");
		if(currentPage==null || currentPage<=0){
			currentPage = 1;
		}
		VOResult voResult = sysAgentCodeService.findByPagination(currentPage, SystemParam.PAGE_SIZE, sysAgentCodeVO);
		return new ModelAndView("web/organization/agentcode/index","voResult",voResult);
	}

	
	/**
	 * 索引指定工号下绑定的系统用户
	 * @param request
	 * @param currentPage
	 * @param sysAgentCodeVO
	 * @return
	 */
	@RequestMapping(value="userIndex")
	public ModelAndView userIndex(HttpServletRequest request,@RequestParam(required=false) Integer currentPage,TblSysAgentCodeVO sysAgentCodeVO){
		TblSysAgentCodeService sysAgentCodeService  = (TblSysAgentCodeService)DefaultBeanFactory.getBean("tblSysAgentCodeService");
		if(currentPage==null || currentPage<=0){
			currentPage = 1;
		}
		VOResult voResult = sysAgentCodeService.findSysUserByPagination(currentPage, SystemParam.PAGE_SIZE, sysAgentCodeVO);
		return new ModelAndView("web/organization/agentcode/userIndex","voResult",voResult);
	}
	
	
	/**
	 * 删除系统工号
	 * @param request
	 * @param sysAgentCodeVO
	 * @return
	 */
	@RequestMapping(value="delete")
	public ModelAndView delete(HttpServletRequest request,TblSysAgentCodeVO sysAgentCodeVO){
		TblSysAgentCodeService sysAgentCodeService  = (TblSysAgentCodeService)DefaultBeanFactory.getBean("tblSysAgentCodeService");
		int rowNums = 0;
		if(sysAgentCodeVO.getSysAgentCode().getId()!=null && !"".equals(sysAgentCodeVO.getSysAgentCode().getId().trim())){
			rowNums = sysAgentCodeService.deleteByID(sysAgentCodeVO);
		}
		request.getSession().setAttribute("SYS_MESSAGE_TYPE","success");
		request.getSession().setAttribute("SYS_MESSAGE_VALUE", "成功删除"+rowNums+"条记录.");
		return new ModelAndView("redirect:/web/organization/agentCode/index.action");
	}
	
	
	/**
	 * 索引所有未绑定工号用户
	 * @param request
	 * @param currentPage
	 * @param sysAgentCodeVO
	 * @return
	 */
	@RequestMapping(value="unbindUserIndex")
	public ModelAndView unbindUserIndex(HttpServletRequest request,@RequestParam(required=false) Integer currentPage,TblSysAgentCodeVO sysAgentCodeVO) {
		TblSysAgentCodeService sysAgentCodeService  = (TblSysAgentCodeService)DefaultBeanFactory.getBean("tblSysAgentCodeService");
		if(currentPage==null || currentPage<=0){
			currentPage = 1;
		}
		VOResult voResult = sysAgentCodeService.findSysUserOfUnAssignAgentCodeByPagination(currentPage, SystemParam.PAGE_SIZE, sysAgentCodeVO);
		return new ModelAndView("web/organization/agentcode/agentCodeUserAdd","voResult",voResult);
	}
	
	
	/**
	 * 加载更新页面
	 * @param request
	 * @param sysAgentCodeVO
	 * @return
	 */
	@RequestMapping(value="preUpdate")
	public ModelAndView preUpdate(HttpServletRequest request,TblSysAgentCodeVO sysAgentCodeVO){
		TblSysAgentCodeService sysAgentCodeService  = (TblSysAgentCodeService)DefaultBeanFactory.getBean("tblSysAgentCodeService");
		TblSysAgentCode tblSysAgentCode = null;
		if(sysAgentCodeVO.getSysAgentCode().getId()!=null && !"".equals(sysAgentCodeVO.getSysAgentCode().getId())){
			tblSysAgentCode = sysAgentCodeService.findByID(sysAgentCodeVO.getSysAgentCode().getId());
		}
		return new ModelAndView("web/organization/agentcode/agentCodeUpdates","tblSysAgentCode",tblSysAgentCode);
	}
	
	
	/**
	 * 给工号绑定用户
	 * @param request
	 * @param sysAgentCodeVO
	 * @return
	 */
	@RequestMapping(value="assignSysUser")
	public ModelAndView assignSysUser(HttpServletRequest request,TblSysAgentCodeVO sysAgentCodeVO){
		TblSysAgentCodeService sysAgentCodeService  = (TblSysAgentCodeService)DefaultBeanFactory.getBean("tblSysAgentCodeService");
		int rowNums = 0;
		if(sysAgentCodeVO.getSysUser().getId()!=null && !"".equals(sysAgentCodeVO.getSysUser().getId())){
			rowNums = sysAgentCodeService.assignUser(sysAgentCodeVO);
		}
		return new ModelAndView("common/ok");
	}
	
	
	/**
	 * 解除系统工号绑定的系统用户
	 * @param request
	 * @param sysAgentCodeVO
	 * @return
	 */
	@RequestMapping(value="unAssignSysUser")
	public ModelAndView unAssignSysUser(HttpServletRequest request , TblSysAgentCodeVO sysAgentCodeVO) {
		TblSysAgentCodeService sysAgentCodeService  = (TblSysAgentCodeService)DefaultBeanFactory.getBean("tblSysAgentCodeService");
		int rowNums = 0;
		if(sysAgentCodeVO.getSysUser()!=null && sysAgentCodeVO.getSysUser().getId()!=null && 
				!"".equals(sysAgentCodeVO.getSysUser().getId())) {
			rowNums = sysAgentCodeService.unAssignUser(sysAgentCodeVO);
		}
		return new ModelAndView("redirect:/web/organization/agentCode/index.action");
	}
	
	
	
	
	
	
	
	
}
