package com.glanway.iclock.controller.command;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glanway.iclock.common.CommandWrapper;
import com.glanway.iclock.common.HttpCode;
import com.glanway.iclock.common.JsonResult;
import com.glanway.iclock.entity.sign.Device;
import com.glanway.iclock.entity.task.Task;
import com.glanway.iclock.service.employee.EmployeeDeviceInfoService;
import com.glanway.iclock.service.employee.FingerFaceTemplateService;
import com.glanway.iclock.service.sign.DeviceService;
import com.glanway.iclock.service.task.TaskService;

@Controller
@RequestMapping("api/command")
public class CommandController {

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private EmployeeDeviceInfoService employeeDeviceInfoService;

	@Autowired
	private FingerFaceTemplateService fingerFaceTemplateService;

	/**
	 * 将指定员工信息同步到指定考勤机中.
	 *
	 * @param employeeCodes
	 * @param sn
	 * @return
	 * @author FUQIHAO
	 * @dateTime 2017年8月23日 下午5:35:07
	 */
	@ResponseBody
	@RequestMapping("updateUserInfo")
	public JsonResult syncEmployeeToDevice(String employeeCodes, String sn) {
		JsonResult jsonResult = new JsonResult();

		if (StringUtils.isEmpty(employeeCodes) || StringUtils.isEmpty(sn)) {
			jsonResult.setMsg("参数异常!");
			jsonResult.setCode(HttpCode.BAD_REQUEST);
			return jsonResult;
		}

		Device device = deviceService.selectByDeviceSn(sn);
		if (null != device) {
			device.setSyncState(2);// 同步中
			deviceService.updateByPrimaryKeySelective(device);
			
			/***** 同步员工基本信息 ******/
			syncUserInfo(employeeCodes, sn);
			/***** 同步员工头像信息 ******/
			syncUserPhoto(employeeCodes, sn);
			/***** 同步员工指纹模板信息 ******/
			syncUserFinger(employeeCodes, sn);
			/***** 同步员工面部模板信息 ******/
			syncUserFace(employeeCodes, sn);
		} else {
			jsonResult.setMsg("设备不存在!");
		}

		jsonResult.setLoginState(3);
		return jsonResult;
	}

	/**
	 * 同步员工基本信息.
	 *
	 * @param employeeCodes
	 * @param sn
	 * @author FUQIHAO
	 * @dateTime 2017年8月23日 下午5:38:39
	 */
	public void syncUserInfo(String employeeCodes, String sn) {
		Task task = new Task();
		task.setId(null);
		task.setSn(sn);
		task.setState(1);
		task.setDeleted("0");
		// 创建命令执行参数
		List<String> paramList = employeeDeviceInfoService.findEmployeeDeviceInfo(employeeCodes);
		if (null != paramList) {
			for (String param : paramList) {
				task.setArgs(param);
				task.setCommand(CommandWrapper.CMD_DATA_UPDATE_USER);
				task.setCreatedDate(new Date());
				taskService.save(task);
			}
		}
	}

	/**
	 * 同步员工头像信息.
	 *
	 * @param employeeCodes
	 * @param sn
	 * @author FUQIHAO
	 * @dateTime 2017年8月23日 下午5:38:53
	 */
	public void syncUserPhoto(String employeeCodes, String sn) {
		clearUserPhoto(sn);

		Task task = new Task();
		task.setId(null);
		task.setSn(sn);
		task.setState(1);
		task.setDeleted("0");
		// 创建命令执行参数
		List<String> paramList = employeeDeviceInfoService.findEmployeeDeviceInfoPhone(employeeCodes);
		if (null != paramList) {
			for (String param : paramList) {
				task.setArgs(param);
				task.setCommand(CommandWrapper.CMD_DATA_UPDATE_PHOTO);
				task.setCreatedDate(new Date());

				taskService.save(task);
			}
		}
	}

	/**
	 * 清除员工头像信息.
	 *
	 * @param sn
	 * @author FUQIHAO
	 * @dateTime 2017年8月23日 下午5:39:07
	 */
	public void clearUserPhoto(String sn) {
		Task clearTask = new Task();
		clearTask.setId(null);
		clearTask.setSn(sn);
		clearTask.setState(1);
		clearTask.setArgs(null);
		clearTask.setCommand(CommandWrapper.CMD_CLEAR_PHOTO);
		clearTask.setCreatedDate(new Date());
		clearTask.setDeleted("0");

		taskService.save(clearTask);
	}

	/**
	 * 同步员工指纹模板信息.
	 *
	 * @param employeeCodes
	 * @param sn
	 * @author FUQIHAO
	 * @dateTime 2017年8月23日 下午5:39:15
	 */
	public void syncUserFinger(String employeeCodes, String sn) {
		Task task = new Task();
		task.setId(null);
		task.setSn(sn);
		task.setState(1);
		task.setDeleted("0");
		// 创建命令执行参数
		List<String> paramList = fingerFaceTemplateService.findEmployeeFingerAndFaceTmpData(employeeCodes, 1);
		if (null != paramList) {
			for (String param : paramList) {
				task.setArgs(param);
				task.setCommand(CommandWrapper.CMD_DATA_UPDATE_FINGER);
				task.setCreatedDate(new Date());

				taskService.save(task);
			}
		}
	}

	/**
	 * 同步员工面部模板信息.
	 *
	 * @param employeeCodes
	 * @param sn
	 * @author FUQIHAO
	 * @dateTime 2017年8月23日 下午5:39:27
	 */
	public void syncUserFace(String employeeCodes, String sn) {
		Task task = new Task();
		task.setId(null);
		task.setSn(sn);
		task.setState(1);
		// 创建命令执行参数
		List<String> paramList = fingerFaceTemplateService.findEmployeeFingerAndFaceTmpData(employeeCodes, 2);
		int count = 0;
		if (null != paramList) {
			for (String param : paramList) {
				task.setArgs(param);
				task.setCommand(CommandWrapper.CMD_DATA_UPDATE_FACE);
				task.setCreatedDate(new Date());
				task.setDeleted("0");

				taskService.save(task);
				count++;
				// 下载重启命令
			}
			if (count > 0) {
				Task reBootTask = new Task();
				reBootTask.setId(null);
				reBootTask.setSn(sn);
				reBootTask.setState(1);
				reBootTask.setCommand("C:R-001:REBOOT");
				reBootTask.setArgs("");
				reBootTask.setCreatedDate(new Date());
				reBootTask.setDeleted("0");
				taskService.save(reBootTask);
			}
		}
	}
}
