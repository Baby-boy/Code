package com.yd.gcj.entity.vo;

import com.yd.gcj.entity.YdMangerMsgSize;
import com.yd.gcj.entity.YdMangerUser;

public class YdMangerUserVo extends YdMangerUser{
	/**用于显示的手机号*/
	private String phone;
	/**用于显示的真实姓名*/
	private String name;
	/**性别描述*/
	private String sex;
	/**等级描述*/
	private String level;
	/**等级图标地址*/
	private String levelIcoPath;
	/**类型描述*/
	private String type;
	/**企业认证状态描述*/
	private String cstate;
	/**技能认证状态描述*/
	private String lstate;
	/**我的关注数量*/
	private Integer attentionNum = 0;
	
	/**被关注的数量*/
	private Integer BeConcernedNum=0;
	
	/**是否被关注标识*/
	private Integer is_att = 0;
	
	/**注册时使用的验证码*/
	private String phoneCode;
	
	private String otype;
	
	private YdMangerMsgSize msgSize;
	
	
	
	
	/**
	 * 获取 用于显示的手机号
	 * @return phone
	 */
	public String getPhone() {
		String phone1 = getUser_phone();
		if(phone1 != null && !phone1.isEmpty() && phone1.length()==11){
			phone = phone1.replaceAll(phone1.substring(3, 6), "****");
		}
		return phone;
	}
	
	/**
	 * 设置 用于显示的手机号
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * 获取 用于显示的真实姓名
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置 用于显示的真实姓名
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	public Integer getBeConcernedNum() {
		return BeConcernedNum;
	}


	public void setBeConcernedNum(Integer beConcernedNum) {
		BeConcernedNum = beConcernedNum;
	}


	public String getOtype() {
		if(otype==null||otype.isEmpty()){
			setOtype("未设置");
		}else{
			switch (super.getUser_otype()) {
			case 0:
				setOtype("全职");
				break;
			case 1:
				setOtype("较少 ");
				break;
			case 2:
				setOtype("较多");
				break;
			default:
				setSex("未设置");
				break;
			}
		}
		return otype;
	}


	public void setOtype(String otype) {
		this.otype = otype;
	}


	/**
	 * 获取 性别描述
	 * @return sex
	 */
	public String getSex() {
		if(sex==null||sex.isEmpty()){
			setSex("未设置");
		}else{
			switch (super.getUser_sex()) {
			case 0:
				setSex("男");
				break;
			case 1:
				setSex("女");
				break;
			default:
				setSex("未设置");
				break;
			}
		}
		return sex;
	}
	

	/**
	 * 设置 性别描述
	 * @param sex
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	

	/**
	 * 获取 等级描述
	 * @return level
	 */
	public String getLevel() {
		if(level==null||level.isEmpty()){
			switch (super.getUser_level()) {
				case 1:	
					setLevel("中级设计师");
					break;
				case 2:	
					setLevel("高级设计师");
					break;
				case 3:	
					setLevel("资深设计师");
					break;
				case 4:	
					setLevel("专家设计师");
					break;
				case 5:	
					setLevel("设计大师");
					break;
				case 0:
				default:
					setLevel("初级设计师");
					break;
			}
		}
		return level;
	}
	
	/**
	 * 设置 等级描述
	 * @param level
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * 获取 等级图标地址
	 * @return levelIcoPath
	 */
	public String getLevelIcoPath() {
		if(levelIcoPath==null || levelIcoPath.isEmpty()){
			switch (super.getUser_level()) {
				case 1:	
					setLevelIcoPath("/images/work/v1.png");
					break;
				case 2:	
					setLevelIcoPath("/images/work/v2.png");
					break;
				case 3:	
					setLevelIcoPath("/images/work/v3.png");
					break;
				case 4:	
					setLevelIcoPath("/images/work/v4.png");
					break;
				case 5:	
					setLevelIcoPath("/images/work/v5.png");
					break;
				case 0:
				default:
					setLevelIcoPath("/images/work/v.png");
					break;
				}
		}
		return levelIcoPath;
	}
	
	/**
	 * 设置 等级图标地址
	 * @param levelIcoPath
	 */
	public void setLevelIcoPath(String levelIcoPath) {
		this.levelIcoPath = levelIcoPath;
	}
	
	/**
	 * 获取 类型描述
	 * @return type
	 */
	public String getType() {
		if(type==null||type.isEmpty()){
			switch (super.getUser_type()) {
			case 0:
				setType("雇主");
				break;
			case 1:
				setType("设计师【个人】");
				break;
			case 2:
				setType("设计师【公司】");
				break;
			default:
				setType("普通用户");
				break;
			}
		}
		return type;
	}
	
	/**
	 * 设置 类型描述
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 获取 企业认证状态描述
	 * @return cstate
	 */
	public String getCstate() {
		if(cstate==null||cstate.isEmpty()){
			switch (super.getUser_cstate()) {
				case 1:
					setCstate("认证成功");
					break;
				case 2:
					setCstate("认证失败");
					break;
				case 3:
					setCstate("审核中");
					break;
				case 0:
				default:
					setCstate("未认证");
					break;
			}
		}
		return cstate;
	}
	
	/**
	 * 设置 企业认证状态描述
	 * @param cstate
	 */
	public void setCstate(String cstate) {
		this.cstate = cstate;
	}
	
	/**
	 * 获取 技能任庄状态描述
	 * @return lstate
	 */
	public String getLstate() {
		if(lstate==null||lstate.isEmpty()){
			switch (super.getUser_skillstate()) {
				case 1:
					setLstate("认证成功");
					break;
				case 2:
					setLstate("认证失败");
					break;
				case 3:
					setLstate("审核中");
				case 0:
				default:
					setLstate("未认证");
					break;
			}
		}
		return lstate;
	}
	
	/**
	 * 设置 技能任庄状态描述
	 * @param lstate
	 */
	public void setLstate(String lstate) {
		this.lstate = lstate;
	}

	/**
	 * 获取 我的关注数量
	 * @return attentionNum
	 */
	public Integer getAttentionNum() {
		return attentionNum;
	}
	
	/**
	 * 设置 我的关注数量
	 * @param attentionNum
	 */
	public void setAttentionNum(Integer attentionNum) {
		this.attentionNum = attentionNum;
	}

	/**
	 * 获取 是否被关注标识
	 * @return is_att
	 */
	public Integer getIs_att() {
		return is_att;
	}
	
	/**
	 * 设置 是否被关注标识
	 * @param is_att
	 */
	public void setIs_att(Integer is_att) {
		this.is_att = is_att;
	}


	/**
	 * 获取 注册时使用的验证码
	 * @return phoneCode
	 */
	public String getPhoneCode() {
		return phoneCode;
	}
	
	/**
	 * 设置 注册时使用的验证码
	 * @param phoneCode
	 */
	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public YdMangerMsgSize getMsgSize() {
		return msgSize;
	}

	public void setMsgSize(YdMangerMsgSize msgSize) {
		this.msgSize = msgSize;
	}
}
