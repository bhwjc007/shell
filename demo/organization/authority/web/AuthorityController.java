/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * ClassName: shell.framework.organization.authority.web.AuthorityController.java
 * CreatedTime: 13-1-13 下午10:15
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.organization.authority.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import shell.framework.core.DefaultBeanFactory;
import shell.framework.core.SystemParam;
import shell.framework.dao.support.VOResult;
import shell.framework.organization.authority.service.TblSysAuthorityService;
import shell.framework.organization.authority.vo.TblSysFunctionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> 系统权限控制器 </p>
 *
 * @author changming.Y
 * @version 1.0   13-1-13 下午10:15
 */
@Controller
@RequestMapping("/web/organization/authority/*")
public class AuthorityController {

    /**
     * 索引所有的权限，包括各种类型
     * @param request   http请求
     * @param currentPage 当前页码
     * @param sysFunctionVO   系统权限值对象
     * @return  视图模型
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "index")
    public ModelAndView index(HttpServletRequest request,@RequestParam(required=false) Integer currentPage,TblSysFunctionVO sysFunctionVO){
        TblSysAuthorityService authorityService = (TblSysAuthorityService)DefaultBeanFactory.getBean("tblSysAuthorityService");
        if(currentPage==null || currentPage<=0){
            currentPage = 1;
        }
        VOResult voResult = authorityService.findByPagination(currentPage, SystemParam.PAGE_SIZE,sysFunctionVO);
        return new ModelAndView("web/organization/authority/index","voResult",voResult);
    }



}
