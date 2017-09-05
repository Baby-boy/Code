package com.glanway.iclock.service.task.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glanway.gone.util.StringUtils;
import com.glanway.iclock.common.CommandWrapper;
import com.glanway.iclock.dao.task.TaskDao;
import com.glanway.iclock.entity.device.EmployeeDevice;
import com.glanway.iclock.entity.employee.EmployeeDeviceInfo;
import com.glanway.iclock.entity.employee.FingerFaceTemplate;
import com.glanway.iclock.entity.task.Task;
import com.glanway.iclock.service.BaseServiceImpl;
import com.glanway.iclock.service.task.TaskService;
import com.glanway.iclock.util.StringUtil;

/**
 * 说明 : 任务实现类
 * 
 * @author zhangshaung
 * @version 1.0.0
 * @dateTime 2017年4月18日 下午1:41:28
 */
@Service("taskService")
@Transactional
public class TaskServiceImpl extends BaseServiceImpl<Task> implements TaskService {

	@Autowired
	private TaskDao taskDao;

	@Override
	public int chearCommandsBySn(String sn) {
		if (null == sn && "".equals(sn)) {
			return 0;
		}
		return taskDao.chearCommandsBySn(sn);
	}

	@Override
	public void updateStateBySn(String sn, Integer state, Integer oldState) {
		if (null != sn && !("".equals(sn)) && null != state && state > 0) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("sn", sn);
			params.put("state", state);

			if (null != oldState && oldState > 0) {
				params.put("oldState", oldState);
			}
			taskDao.updateStateBySn(params);
		}

	}

	@Override
	public void updateStateById(Long id, Integer state, Integer oldState) {
		if (null != id && !("".equals(id)) && null != state && state > 0) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			params.put("state", state);

			if (null != oldState && oldState > 0) {
				params.put("oldState", oldState);
			}
			taskDao.updateStateById(params);
		}
	}

	@Override
	public void recordTaskLog(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("state", 3);
		taskDao.updateStateById(params);

		// taskDao.insertIntoSelect(id);
		// taskDao.deleteTaskById(id);
	}

	@Override
	public Task findOneTask(Map<String, Object> params) {
		return taskDao.findOneTask(params);
	}

	@Override
	public List<Task> findTaskByDeviceSn(String sn) {
		return taskDao.findTaskByDeviceSn(sn);
	}

	@Override
	public List<Task> findTaskByCommand(String sn, Long id, String command) {
		return taskDao.findTaskByCommand(sn, id, command);
	}

	@Override
	public Task checkCommandHandle(String sn, Long id) {
		return taskDao.checkCommandHandle(sn, id);
	}

	@Override
	public Task findTaskById(Long id) {
		return taskDao.findTaskById(id);
	}

	@Override
	public void syncUserInfo(String operator, String handleType, EmployeeDeviceInfo employeeDeviceInfo,
			EmployeeDevice employeeDevice) {
		// 创建任务
		Task task = new Task();
		task.setOperator(operator);
		task.setHandleType(handleType);
		task.setSn(employeeDevice.getSn());

		// 拼接命令参数集
		final StringBuilder param = new StringBuilder();
		param.append("PIN=").append(employeeDeviceInfo.getEmployeeCode());
		param.append(CommandWrapper.HT).append("Name=").append(employeeDevice.getEmployeeName());
		// 没密码不拼接参数
		if (StringUtils.hasText(employeeDeviceInfo.getPwd())) {
			param.append(CommandWrapper.HT).append("Passwd=").append(employeeDeviceInfo.getPwd());
		}
		param.append(CommandWrapper.HT).append("Pri=")
				.append(StringUtils.hasText(employeeDeviceInfo.getPri()) ? employeeDeviceInfo.getPri() : "0");

		// 经过测试发现,设置超级管理员权限时需要在参数末尾添加对应的汉字的制表符个数
		if (StringUtils.hasText(employeeDeviceInfo.getPri())) {
			for (int i = 0; i < employeeDevice.getEmployeeName().length(); i++) {
				param.append(CommandWrapper.HT);
			}
		}

		// 创建命令执行参数
		task.setArgs(param.toString());
		task.setCommand(CommandWrapper.CMD_DATA_UPDATE_USER);

		task = SaveTask(task);
		taskDao.insertSelective(task);
	}

	@Override
	public void syncUserPhoto(String operator, String handleType, EmployeeDeviceInfo employeeDeviceInfo,
			EmployeeDevice employeeDevice) {
		// 创建任务
		Task task = new Task();
		task.setOperator(operator);
		task.setHandleType(handleType);
		task.setSn(employeeDevice.getSn());

		// 拼接命令参数集
		final StringBuilder param = new StringBuilder();
		param.append("PIN=").append(employeeDeviceInfo.getEmployeeCode());
		param.append(CommandWrapper.HT).append("Size=").append(employeeDeviceInfo.getPic().length());
		param.append(CommandWrapper.HT).append("Content=").append(employeeDeviceInfo.getPic());

		// 创建命令执行参数
		task.setArgs(param.toString());
		task.setCommand(CommandWrapper.CMD_DATA_UPDATE_PHOTO);

		task = SaveTask(task);
		taskDao.insertSelective(task);
	}

	@Override
	public void syncUserFinger(String operator, String handleType, FingerFaceTemplate fingerFaceTemplate,
			EmployeeDevice employeeDevice) {
		// 创建任务
		Task task = new Task();
		task.setOperator(operator);
		task.setHandleType(handleType);
		task.setSn(employeeDevice.getSn());

		// 拼接命令参数集
		final StringBuilder param = new StringBuilder();
		param.append("PIN=").append(fingerFaceTemplate.getEmployeeCode());
		param.append(CommandWrapper.HT).append("FID=").append(fingerFaceTemplate.getFid());
		param.append(CommandWrapper.HT).append("Size=").append(fingerFaceTemplate.getTmpSize());
		param.append(CommandWrapper.HT).append("Valid=").append(fingerFaceTemplate.getValid());
		param.append(CommandWrapper.HT).append("TMP=").append(fingerFaceTemplate.getTmp());

		// 创建命令执行参数
		task.setArgs(param.toString());
		task.setCommand(CommandWrapper.CMD_DATA_UPDATE_FINGER);

		task = SaveTask(task);
		taskDao.insertSelective(task);
	}

	@Override
	public void syncUserFace(String operator, String handleType, FingerFaceTemplate fingerFaceTemplate,
			EmployeeDevice employeeDevice) {
		// 创建任务
		Task task = new Task();
		task.setOperator(operator);
		task.setHandleType(handleType);
		task.setSn(employeeDevice.getSn());

		// 拼接命令参数集
		final StringBuilder param = new StringBuilder();
		param.append("PIN=").append(fingerFaceTemplate.getEmployeeCode());
		param.append(CommandWrapper.HT).append("FID=").append(fingerFaceTemplate.getFid());
		param.append(CommandWrapper.HT).append("Size=").append(fingerFaceTemplate.getTmpSize());
		param.append(CommandWrapper.HT).append("Valid=").append(fingerFaceTemplate.getValid());
		param.append(CommandWrapper.HT).append("TMP=").append(fingerFaceTemplate.getTmp());

		// 创建命令执行参数
		task.setArgs(param.toString());
		task.setCommand(CommandWrapper.CMD_DATA_UPDATE_FACE);

		task = SaveTask(task);
		taskDao.insertSelective(task);
	}

	private Task SaveTask(Task task) {
		task.setId(null);
		task.setState(1);
		task.setBatchDate(new Date());
		task.setCreatedBy(9999L);// JOB自动设置为超级管理员,为9999
		task.setCreatedDate(new Date());
		task.setCreProId(1L);// 写死
		task.setLastModifiedBy(9999L);// JOB自动设置为超级管理员,为9999
		task.setLastModifiedDate(task.getCreatedDate());
		task.setModProId(1L);// 写死
		task.setDeleted("0");
		return task;
	}

	@Override
	public void pushCommand(String operator, String handleType, String sn, String command, String... args) {
		// 创建任务
		Task task = new Task();
		task.setOperator(operator);
		task.setHandleType(handleType);
		task.setSn(sn);

		// 创建命令执行参数
		task.setArgs(StringUtil.stringArrToString(args, CommandWrapper.HT));
		task.setCommand(command);

		task = SaveTask(task);
		taskDao.insertSelective(task);
	}
}
