package com.yd.gcj.entity;

/***
 * 服务商用户团队人员信息
 * @author Administrator
 *
 */
public class YdMangerUserTeamEmpr {
	/**唯一标识*/
	private Integer empr_id;
	/**团队信息id*/
	private Integer empr_tmid;
	/**人员 姓名*/
	private String empr_name;
	/**人员手机号*/
	private String empr_phone;
	
	/**
	 * 获取 唯一标识
	 * @return empr_id
	 */
	public Integer getEmpr_id() {
		return empr_id;
	}
	
	/**
	 * 设置 唯一标识
	 * @param empr_id
	 */
	public void setEmpr_id(Integer empr_id) {
		this.empr_id = empr_id;
	}
	
	/**
	 * 获取 团队信息id
	 * @return empr_tmid
	 */
	public Integer getEmpr_tmid() {
		return empr_tmid;
	}
	

	/**
	 * 设置 团队信息id
	 * @param empr_tmid
	 */
	public void setEmpr_tmid(Integer empr_tmid) {
		this.empr_tmid = empr_tmid;
	}
	

	/**
	 * 获取 人员姓名
	 * @return empr_name
	 */
	public String getEmpr_name() {
		return empr_name;
	}
	
	/**
	 * 设置 人员姓名
	 * @param empr_name
	 */
	public void setEmpr_name(String empr_name) {
		this.empr_name = empr_name;
	}
	
	/**
	 * 获取 人员手机号
	 * @return empr_phone
	 */
	public String getEmpr_phone() {
		return empr_phone;
	}
	
	/**
	 * 设置 人员手机号
	 * @param empr_phone
	 */
	public void setEmpr_phone(String empr_phone) {
		this.empr_phone = empr_phone;
	}

}
