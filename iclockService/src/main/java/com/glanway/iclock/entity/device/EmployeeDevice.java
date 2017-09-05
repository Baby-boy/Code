package com.glanway.iclock.entity.device;

import com.glanway.iclock.entity.BaseEntity;

public class EmployeeDevice extends BaseEntity {

	private static final long serialVersionUID = 8729396417046224976L;

	private String employeeName;// 员工姓名

	private String employeeCode;// 员工编号

	private Long deptId;// 部门ID

	private Long jobId;// 职位ID

	private String sn;// 设备序列号

	private Long tspId;// 考勤点ID

	private Long tsgId;// 考勤群组ID

	private Integer stateType;// 处理状态

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName == null ? null : employeeName.trim();
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode == null ? null : employeeCode.trim();
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn == null ? null : sn.trim();
	}

	public Long getTspId() {
		return tspId;
	}

	public void setTspId(Long tspId) {
		this.tspId = tspId;
	}

	public Long getTsgId() {
		return tsgId;
	}

	public void setTsgId(Long tsgId) {
		this.tsgId = tsgId;
	}

	public Integer getStateType() {
		return stateType;
	}

	public void setStateType(Integer stateType) {
		this.stateType = stateType;
	}
}