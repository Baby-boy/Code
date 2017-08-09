/**
 * @author zhangshuang
 * 2017年4月20日 下午5:28:40
 */
package com.glanway.iclock.controller.taskJob;

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
import com.glanway.iclock.entity.sign.Device;
import com.glanway.iclock.entity.task.Task;
import com.glanway.iclock.service.sign.DeviceService;
import com.glanway.iclock.service.task.TaskService;
import com.glanway.iclock.util.StringUtil;

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
	@Scheduled(cron = "0 0 3 * * ? ")
	public void checkDeviceData() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("state", 2);
		List<Device> list = deviceService.findMany(params);
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

		for (Device device : list) {
			pushCommand(device.getSn(), CommandWrapper.CMD_CHECK, CommandWrapper.DEV_DIRTY_CHECK_ID_PREFIX + timestamp);
			LOGGER.info(new Date() + " 将设备名称:{},设备代码:{} 下发脏检查命令", device.getDeleted(), device.getSn());
		}

	}

	private void pushCommand(final String sn, final String command, final String... args) {
		try {
			Task task = new Task();

			task.setSn(sn);
			task.setState(1);

			task.setArgs(StringUtil.stringArrToString(args, ""));
			task.setCommand(command);
			task.setCreatedDate(new Date());
			task.setDeleted("0");

			taskService.save(task);

		} catch (final Exception e) {
			LOGGER.error("设备{},向任务表中下达命令{} ,参数{} 报错!", sn, command, args);
			LOGGER.error("设备{},向任务表中下达命令{} ,参数{} 报错:{}", sn, command, args, e.getMessage());
			LOGGER.error("pushCommand error", e);
		}
	}
}
