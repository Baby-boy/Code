package com.glanway.iclock.taskJob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.glanway.iclock.service.device.EmployeeDeviceService;

@Service
public class SyncEmployeeDeviceJob {

	@Autowired
	private EmployeeDeviceService employeeDeviceService;

	@Scheduled(cron = "0 0 6,12,16,21 * * ?")
	public void syncEmployeeDeviceJob() {
		employeeDeviceService.syncEmployeeDeviceJob();
	}
}
