/**
 * @author zhangshuang
 * 2017年4月20日 下午4:56:47
 */
package com.glanway.iclock.controller.taskJob;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.glanway.iclock.service.task.TaskService;
import com.glanway.iclock.util.TimeUtil;

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
	 * 说明 : 每天凌晨三点清除命令表
	 * 
	 * @author fuqihao
	 * @dateTime 2017年8月3日 下午3:00:05
	 */
	@Scheduled(cron = "0 0 3 * * ?")
	public void cleanTaskCommand() {
		// 删除三天前的命令
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -2);
		String date = TimeUtil.format(calendar.getTime());
		taskService.updateDeletedByDate(date);
	}
}
