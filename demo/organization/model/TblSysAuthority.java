/**
 * Copyright (c) 2012 by IKermi Inc. All Rights Reserved.
 * ClassName: shell.framework.model.TblSysAuthority.java
 * CreatedTime: 13-1-14 下午1:47
 *
 * This software is the proprietary information of IKermi, Inc.
 * Use is subject to license terms.
 */
package shell.framework.model;

/**
 * <p> 系统权限实体类 </p>
 *
 * @author changming.Y
 * @version 1.0   13-1-14 下午1:47
 */
public class TblSysAuthority {

    //权限id(唯一)
    private String id;
    //权限功能名称
    private String functionName;
    //权限对应的值，非必须
    private String functionURL;
    //权限类型
    private String functionType;
    //父节点ID
    private String parentID;
    private String orderNO;
    private String remark;
    private String createTime;
    private String updateTime;
    private String creator;
    //先序遍历左子树
    private int lft;
    //先序遍历右子树
    private int rgt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionURL() {
        return functionURL;
    }

    public void setFunctionURL(String functionURL) {
        this.functionURL = functionURL;
    }

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getLft() {
        return lft;
    }

    public void setLft(int lft) {
        this.lft = lft;
    }

    public int getRgt() {
        return rgt;
    }

    public void setRgt(int rgt) {
        this.rgt = rgt;
    }
}
