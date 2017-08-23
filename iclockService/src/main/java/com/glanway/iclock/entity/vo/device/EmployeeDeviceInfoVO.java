package com.glanway.iclock.entity.vo.device;

/**
 * 说明 : 应该在设备上打卡员工的基本信息
 * 
 * @author zhangshaung
 * @version 1.0.0
 * @dateTime 2017年4月21日 上午10:27:52
 */
public class EmployeeDeviceInfoVO {

	private Long id;// 员工表ID

	private String code;// 员工代码

	private String name;// 用户名

	private Integer sex;// 性别(1:男, 2:女.3:保密)

	private String mobile;// 手机号

	private Integer jobState;// 在职状态:(1试用:, 2:正式, 3:离职)

	private String pic;// 用户头像

	private String pwd;// 密码

	private String card;// 卡号

	private String pri;// 考勤权限

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getJobState() {
		return jobState;
	}

	public void setJobState(Integer jobState) {
		this.jobState = jobState;
	}

}
