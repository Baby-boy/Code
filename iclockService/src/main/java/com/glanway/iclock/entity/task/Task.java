package com.glanway.iclock.entity.task;

import java.util.Date;

import com.glanway.iclock.entity.BaseEntity;

/**
 * 说明 : 任务实体
 * 
 * @author zhangshaung
 * @version 1.0.0
 * @dateTime 2017年4月18日 上午10:15:01
 */
public class Task extends BaseEntity {

	private static final long serialVersionUID = -769738822427404731L;

	private String operator;// 操作者

	private String handleType;// 处理类型

	private String sn;// 设备序列号

	private Integer state;// 状态(1:未处理, 2:处理中, 3:已完成)

	private String command;// 命令

	private String args;// 命令参数

	private Date startHandleTime;// 开始处理时间

	private Date completeTime;// 完成时间

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getHandleType() {
		return handleType;
	}

	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public Date getStartHandleTime() {
		return startHandleTime;
	}

	public void setStartHandleTime(Date startHandleTime) {
		this.startHandleTime = startHandleTime;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

}