/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * $Id: TblSysUserDetailVO.java $
 * $LastChangedDate: 2012-11-2 上午9:41:53 $
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.organization.user.vo;

/**
 * <p> 用户详细信息值对象 </p>
 *
 * @author ChangMing.Yang
 * @version 1.0 $LastChangedDate: 2012-11-2 上午9:41:53 $
 */
public class TblSysUserDetailVO {

	private String id;
	//用户登录ID
	private String userCode;
	//密码要进行加密存储
	private String password;
	private String passwordDuration;
	//系统用户全名
	private String fullName;
	private String address;
	private String sex;
	private String telephone;
	private String mobile;
	private String education;
	private String email;
	private String postCode;
	private String photo;
	private String agentCode_id;
	private String status;
	private String hireDate;
	private String birthday;
	
	private String departmentName;
	private String departmentType;
	//所在组织ID
	private String organizationID;
	
	//岗位名称
	private String positionName;
	//角色名称
	private String roleName;
	
	
	
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
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}
	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the passwordDuration
	 */
	public String getPasswordDuration() {
		return passwordDuration;
	}
	/**
	 * @param passwordDuration the passwordDuration to set
	 */
	public void setPasswordDuration(String passwordDuration) {
		this.passwordDuration = passwordDuration;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the education
	 */
	public String getEducation() {
		return education;
	}
	/**
	 * @param education the education to set
	 */
	public void setEducation(String education) {
		this.education = education;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}
	/**
	 * @param postCode the postCode to set
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	/**
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}
	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	/**
	 * @return the agentCode_id
	 */
	public String getAgentCode_id() {
		return agentCode_id;
	}
	/**
	 * @param agentCode_id the agentCode_id to set
	 */
	public void setAgentCode_id(String agentCode_id) {
		this.agentCode_id = agentCode_id;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the hireDate
	 */
	public String getHireDate() {
		return hireDate;
	}
	/**
	 * @param hireDate the hireDate to set
	 */
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}
	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
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
	 * @return the positionName
	 */
	public String getPositionName() {
		return positionName;
	}
	/**
	 * @param positionName the positionName to set
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
