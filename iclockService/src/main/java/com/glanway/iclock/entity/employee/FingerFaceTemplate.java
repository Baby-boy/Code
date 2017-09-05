package com.glanway.iclock.entity.employee;

import com.glanway.iclock.entity.BaseEntity;

/**
 * 说明 : 员工的指纹模板和人脸模板
 * 
 * @author zhangshaung
 * @version 1.0.0
 * @dateTime 2017年4月19日 下午5:46:23
 */
public class FingerFaceTemplate extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String employeeCode;// 员工代码

	private String fid;// 指纹或脸纹标号

	private Integer tmpSize;// 指纹或脸纹内容长度

	private String valid;// 是否有效

	private String tmp;// 指纹或脸纹内容

	private Integer type;// 类型(1.指纹,2.脸纹)

	private Integer stateType;// 处理标志(0:处理完成, 1:待处理)

	private String deleted;// 是否删除 (0:正常 ,1:已删除)

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Integer getTmpSize() {
		return tmpSize;
	}

	public void setTmpSize(Integer tmpSize) {
		this.tmpSize = tmpSize;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getTmp() {
		return tmp;
	}

	public void setTmp(String tmp) {
		this.tmp = tmp;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
