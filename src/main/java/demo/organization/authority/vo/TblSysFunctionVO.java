/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: TblSysFunction.java $
 * $LastChangedDate: 2012-10-23 下午4:24:06 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.organization.authority.vo;

/**
 * <p> 系统权限值对象 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-10-23 下午4:24:06 $
 */
public class TblSysFunctionVO {

	private String id;
	private String functionName;
	private String functionURL;
    private String functionType;
	private String isMenu;
	private String parentFunctionID;
	private int orderNO;
	private String remark;
	private String createTime;
	private String updateTime;
	private String creator;
	private int lft;
	private int rgt;


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}
	/**
	 * @param functionName the functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    /**
	 * @return the functionURL
	 */
	public String getFunctionURL() {
		return functionURL;
	}
	/**
	 * @param functionURL the functionURL to set
	 */
	public void setFunctionURL(String functionURL) {
		this.functionURL = functionURL;
	}
	/**
	 * @return the isMenu
	 */
	public String getIsMenu() {
		return isMenu;
	}
	/**
	 * @param isMenu the isMenu to set
	 */
	public void setIsMenu(String isMenu) {
		this.isMenu = isMenu;
	}
	/**
	 * @return the parentFunctionID
	 */
	public String getParentFunctionID() {
		return parentFunctionID;
	}
	/**
	 * @param parentFunctionID the parentFunctionID to set
	 */
	public void setParentFunctionID(String parentFunctionID) {
		this.parentFunctionID = parentFunctionID;
	}
	/**
	 * @return the orderNO
	 */
	public int getOrderNO() {
		return orderNO;
	}
	/**
	 * @param orderNO the orderNO to set
	 */
	public void setOrderNO(int orderNO) {
		this.orderNO = orderNO;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * @return the lft
	 */
	public int getLft() {
		return lft;
	}
	/**
	 * @param lft the lft to set
	 */
	public void setLft(int lft) {
		this.lft = lft;
	}
	/**
	 * @return the rgt
	 */
	public int getRgt() {
		return rgt;
	}
	/**
	 * @param rgt the rgt to set
	 */
	public void setRgt(int rgt) {
		this.rgt = rgt;
	}
	
}
