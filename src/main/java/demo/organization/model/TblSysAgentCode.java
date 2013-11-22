/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: TblSysAgentCode.java $
 * $LastChangedDate: 2012-8-30 上午11:59:17 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.model;

/**
 * <p> 系统工号 实体类 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-8-30 上午11:59:17 $
 */
public class TblSysAgentCode {

	private String id;
	private String agentCode;
	private String isValid;
	private String isVac;
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
	 * @return the agentCode
	 */
	public String getAgentCode() {
		return agentCode;
	}
	/**
	 * @param agentCode the agentCode to set
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
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
	 * @return the isVac
	 */
	public String getIsVac() {
		return isVac;
	}
	/**
	 * @param isVac the isVac to set
	 */
	public void setIsVac(String isVac) {
		this.isVac = isVac;
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
