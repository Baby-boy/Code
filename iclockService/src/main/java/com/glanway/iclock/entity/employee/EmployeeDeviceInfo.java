package com.glanway.iclock.entity.employee;

import com.glanway.iclock.entity.BaseEntity;

/**
 * 
 * 说明 : 考勤机上传的员工信息(密码,照片,卡号等)
 * 
 * @author zhangshaung
 * @version 1.0.0
 * @dateTime 2017年4月19日 上午10:36:02
 */
public class EmployeeDeviceInfo extends BaseEntity {

	private static final long serialVersionUID = -4798201159405616444L;

	private String employeeCode;// 用户考勤号码

	private String pri;// 考勤权限

	private String pic;// 用户照片(头像)

	private String pwd;// 密码

	private String card;// 卡号

	private Integer stateType;// 处理标志(0:处理完成, 1:待处理)

	private String deleted;// 是否删除(0.否，1.是)

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getPri() {
		return pri;
	}

	public void setPri(String pri) {
		this.pri = pri;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public Integer getStateType() {
		return stateType;
	}

	public void setStateType(Integer stateType) {
		this.stateType = stateType;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

}