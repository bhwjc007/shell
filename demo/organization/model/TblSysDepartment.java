/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: TblSysDepartment.java $
 * $LastChangedDate: 2012-6-26 下午5:36:26 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.model;

import java.io.Serializable;

/**
 * <p> 系统部门实体类 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-6-26 下午5:36:26 $
 */
public class TblSysDepartment implements Serializable{

	private static final long serialVersionUID = -4884689673220678425L;
	
	private String id ;
	private String departmentName;
	private String departmentType;
	//所在组织ID
	private String organizationID;
	//父节点ID
	private String parentID;
	//排序ID
	private int orderID;
	//是否有效
	private String isValid;
	//是否虚拟部门
	private String isVD;
	private String remark;
	private String createTime;
	private String updateTime;
	private String creator;
	
	
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
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}
	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	/**
	 * @return the departmentType
	 */
	public String getDepartmentType() {
		return departmentType;
	}
	/**
	 * @param departmentType the departmentType to set
	 */
	public void setDepartmentType(String departmentType) {
		this.departmentType = departmentType;
	}
	/**
	 * @return the organizationID
	 */
	public String getOrganizationID() {
		return organizationID;
	}
	/**
	 * @param organizationID the organizationID to set
	 */
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	/**
	 * @return the parentID
	 */
	public String getParentID() {
		return parentID;
	}
	/**
	 * @param parentID the parentID to set
	 */
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
	/**
	 * @return the orderID
	 */
	public int getOrderID() {
		return orderID;
	}
	/**
	 * @param orderID the orderID to set
	 */
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	/**
	 * @return the isValid
	 */
	public String getIsValid() {
		return isValid;
	}
	/**
	 * @param isValid the isValid to set
	 */
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	/**
	 * @return the isVD
	 */
	public String getIsVD() {
		return isVD;
	}
	/**
	 * @param isVD the isVD to set
	 */
	public void setIsVD(String isVD) {
		this.isVD = isVD;
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
	
	
	
}
