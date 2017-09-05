package com.glanway.iclock.service.device;

import com.glanway.iclock.entity.device.EmployeeDevice;
import com.glanway.iclock.service.BaseService;

public interface EmployeeDeviceService extends BaseService<EmployeeDevice> {

	/**
	 * 自动同步系统数据到设备.
	 *
	 * @author FUQIHAO
	 * @dateTime 2017年9月3日 下午4:57:21
	 */
	public void syncEmployeeDeviceJob();

}
