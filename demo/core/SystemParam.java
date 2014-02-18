/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: SystemParam.java $
 * $LastChangedDate: 2012-6-18 上午9:54:43 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.core;

/**
 * <p> 系统参数 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-6-18 上午9:54:43 $
 */
public interface SystemParam {

	//分页查询，每页显示记录个数
	public static int PAGE_SIZE = 10;
	//记录是否有效
	public static String IS_VALID = "T";  // 有效
	public static String IN_VALID = "F";  // 无效
	//登录SESSIONID
	public static String SESSIOIN_ID_LOGIN_INFO = "loginInfo";
	
	//权限资源类别
	public static String AUTHORITY_TYPE_PAGE_FUNMENU = "00"; //页面功能菜单
	public static String AUTHORITY_TYPE_PAGE_COMMAND = "01"; //页面操作按钮
	public static String AUTHORITY_TYPE_URL = "02"; 		 //URL
	public static String AUTHORITY_TYPE_CLASS = "03";	     //代码类或方法
	//权限资源操作
	public static int AUTHORITY_OPER_READ_ONLY = 0; 		 //只读操作-默认值
	
	//权限资源树根节点id
	public static String AUTHORITY_TREE_ROOT_ID = "PAGE.FUN.MENU.ROOT";
	
	
}
