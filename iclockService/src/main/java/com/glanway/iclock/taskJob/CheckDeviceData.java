/**
 * @author zhangshuang
 * 2017年4月20日 下午5:28:40
 */
package com.glanway.iclock.taskJob;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.glanway.iclock.common.CommandWrapper;
import com.glanway.iclock.entity.device.Device;
import com.glanway.iclock.service.device.DeviceService;
import com.glanway.iclock.service.task.TaskService;

/**
 * 脏检查.
 * 
 * @author fuqihao
 * @version 1.0.0
 * @dateTime 2017年7月9日 下午1:35:14
 */
@Service
public class CheckDeviceData {

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckDeviceData.class);

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private TaskService taskService;

	/**
	 * 说明 : 每天晚上十二点发送脏检查命令
	 * 
	 * @author fuqihao
	 * @dateTime 2017年7月9日 下午1:36:14
	 */
	@Scheduled(cron = "0 0 3,15 * * ? ")
	public void checkDeviceData() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("state", 2);
		List<Device> list = deviceService.findMany(params);
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

		for (Device device : list) {
			taskService.pushCommand("Job执行", "Check检查(设备上传数据)", null, null, device.getSn(), CommandWrapper.CMD_CHECK,
					CommandWrapper.DEV_DIRTY_CHECK_ID_PREFIX + timestamp);
			LOGGER.info(new Date() + " 给设备{} 下发检查命令", device.getSn());
		}

	}
}
