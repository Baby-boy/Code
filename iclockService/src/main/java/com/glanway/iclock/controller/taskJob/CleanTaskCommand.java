/**
 * @author zhangshuang
 * 2017年4月20日 下午4:56:47
 */
package com.glanway.iclock.controller.taskJob;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.glanway.iclock.entity.task.Task;
import com.glanway.iclock.service.task.TaskService;

/**
 * 说明 : 定期清除task表的命令
 * 
 * @author fuqihao
 * @version 1.0.0
 * @dateTime 2017年8月3日 下午2:56:35
 */
@Service
public class CleanTaskCommand {

	@Autowired
	private TaskService taskService;

	/**
	 * 说明 : 每天凌晨三点清除重启命令表
	 * 
	 * @author fuqihao
	 * @dateTime 2017年8月3日 下午3:00:05
	 */
	@Scheduled(cron = "0 0 4 * * ?")
	public void cleanTaskCommand() {
		// 将状态为2的重启命令移至日志表中
		List<Task> tasks = taskService.findTaskByCommand(null, null, "C:R-001:REBOOT");
		for (Task task : tasks) {
			taskService.recordTaskLog(task.getId());
		}
	}
}
