package com.glanway.iclock.entity.vo.device;

/**
 * 说明 : 应该在设备上打卡员工的指纹模板或人脸模板
 * 
 * @author zhangshaung
 * @version 1.0.0
 * @dateTime 2017年4月21日 下午6:10:46
 */
public class EmployeeDeviceFingerFaceVo {

	private String code;// 员工代码

	private String fid;// 手指编号/脸纹编号

	private Integer tmpSize;// 图片字节码长度

	private String valid;// 描述模板的有效性及胁迫标志 0.无效模板 1.正常模板 3.胁迫模板

	private String tmp;// 模板图片 原始二进制指纹或面部模板的base64编码

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

}
