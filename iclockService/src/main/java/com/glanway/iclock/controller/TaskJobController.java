package com.glanway.iclock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glanway.iclock.common.HttpCode;
import com.glanway.iclock.common.JsonResult;
import com.glanway.iclock.service.device.EmployeeDeviceService;

@Controller
@RequestMapping("api/taskJob")
public class TaskJobController {

	@Autowired
	private EmployeeDeviceService employeeDeviceService;

	/**
	 * 同步机制.
	 *
	 * @return
	 * @author FUQIHAO
	 * @dateTime 2017年9月4日 下午10:46:57
	 */
	@ResponseBody
	@RequestMapping("sync")
	public JsonResult syncEmployeeDeviceJob() {
		JsonResult result = new JsonResult();
		try {
			employeeDeviceService.syncEmployeeDeviceJob();
			result.setMsg("操作成功!");
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("操作失败!");
			result.setCode(HttpCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

}
