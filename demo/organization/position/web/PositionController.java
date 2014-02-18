/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: PositionController.java $
 * $LastChangedDate: 2012-9-22 下午11:12:26 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.organization.position.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import shell.framework.dao.support.VOResult;
import shell.framework.organization.department.vo.TblSysDepartmentVO;

/**
 * <p> 岗位控制器 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-9-22 下午11:12:26 $
 */
@Controller
@RequestMapping("/web/organization/position/*")
public class PositionController {

	@RequestMapping(value="index")
	public ModelAndView index(HttpServletRequest request,@RequestParam(required=false) Integer currentPage ,TblSysDepartmentVO departmentVO){
		
		if(currentPage==null || currentPage<=0){
			currentPage = 1;
		}
		VOResult voResult = null;
		
		return new ModelAndView("web/organization/position/index","voResult",voResult);
	}
}
