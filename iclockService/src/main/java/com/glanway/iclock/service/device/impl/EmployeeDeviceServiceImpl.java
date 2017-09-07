package com.glanway.iclock.service.device.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glanway.iclock.common.CommandWrapper;
import com.glanway.iclock.dao.device.EmployeeDeviceDao;
import com.glanway.iclock.entity.device.EmployeeDevice;
import com.glanway.iclock.entity.employee.EmployeeDeviceInfo;
import com.glanway.iclock.entity.employee.FingerFaceTemplate;
import com.glanway.iclock.service.BaseServiceImpl;
import com.glanway.iclock.service.device.EmployeeDeviceService;
import com.glanway.iclock.service.employee.EmployeeDeviceInfoService;
import com.glanway.iclock.service.employee.FingerFaceTemplateService;
import com.glanway.iclock.service.task.TaskService;

@Service("employeeDeviceService")
public class EmployeeDeviceServiceImpl extends BaseServiceImpl<EmployeeDevice> implements EmployeeDeviceService {

	@Autowired
	private EmployeeDeviceDao employeeDeviceDao;

	@Autowired
	private EmployeeDeviceInfoService employeeDeviceInfoService;

	@Autowired
	private FingerFaceTemplateService fingerFaceTemplateService;

	@Autowired
	private TaskService taskService;

	@Override
	public void syncEmployeeDeviceJob() {
		/** 处理考勤机相关表 */
		handleDevice();
		/** 处理员工设备相关表 */
		handleEmployeeDevice();
	}

	private void handleDevice() {// 处理员工设备信息表和指纹脸纹模板表 -因考勤机数据更新和离职原因更新了这两表情况
		/** 处理员工设备基本信息表 */
		handleEmployeeDeviceInfo();
		/** 处理员工头像 */
		handleEmployeeDeviceInfoPhoto();
		/** 处理员工指纹模板表 */
		handleFingerTemplate();
		/** 处理员工脸纹模板表 */
		handleFaceTemplate();

		/** 处理离职人员信息 */
		handleQuitEmployee();
	}

	private void handleEmployeeDevice() {// 处理员工和设备关联表 -因新人入职,人员异动,考勤点以及考勤群组相关操作
		/** 处理员工设备关联表被删除的待处理员工 */
		handleDeletedEmployeeDevice();

		/** 处理员工设备关联表新增的待处理员工 */
		handleNewEmployeeDevice();
	}

	/**
	 * 离职相关处理.
	 */
	private void handleQuitEmployee() {
		/** 处理离职员工设备基本信息(包含头像信息)表 */
		handleQuitEmployeeDeviceInfo();
		/** 处理员工指纹模板表 */
		handleQuitFingerTemplate();
		/** 处理员工脸纹模板表 */
		handleQuitFaceTemplate();
	}

	private void handleQuitEmployeeDeviceInfo() {
		// 通过员工表和设备员工基本信息表查询出离职人员信息
		List<EmployeeDeviceInfo> employeeDeviceInfos = employeeDeviceInfoService.findEmployeeDeviceInfoByStateType(3);// 离职人员基本信息待处理标志位
		for (EmployeeDeviceInfo employeeDeviceInfo : employeeDeviceInfos) {
			// 通过员工ID查询对应的设备(通过视图)
			List<EmployeeDevice> employeeDevices = employeeDeviceDao
					.findEmployeeDeviceByCode(employeeDeviceInfo.getEmployeeCode());
			for (EmployeeDevice employeeDevice : employeeDevices) {
				taskService.pushCommand(
						employeeDeviceInfo.getLastModifiedBy() == 1 ? "超级管理员"
								: employeeDeviceInfo.getLastModifiedBy().toString(),
						"删除离职员工基本信息", employeeDevice.getEmployeeCode(), employeeDevice.getDeptId(),
						employeeDevice.getSn(), CommandWrapper.CMD_DATA_DELETE_USER,
						"PIN=" + employeeDeviceInfo.getEmployeeCode());
				taskService.pushCommand(
						employeeDeviceInfo.getLastModifiedBy() == 1 ? "超级管理员"
								: employeeDeviceInfo.getLastModifiedBy().toString(),
						"删除离职员工头像信息", employeeDevice.getEmployeeCode(), employeeDevice.getDeptId(),
						employeeDevice.getSn(), CommandWrapper.CMD_DATA_DELETE_PHOTO,
						"PIN=" + employeeDeviceInfo.getEmployeeCode());
			}

			// 插入完命令后直接更新状态
			employeeDeviceInfo.setStateType(0);
			employeeDeviceInfo.setDeleted("1");
			employeeDeviceInfo.setLastModifiedDate(new Date());
			employeeDeviceInfoService.update(employeeDeviceInfo);
		}
	}

	private void handleQuitFingerTemplate() {
		// 查询待处理的指纹信息(通过视图)
		List<FingerFaceTemplate> fingerFaceTemplates = fingerFaceTemplateService.findFingerFaceTemplateByStateType(1,
				3);// 指纹类型,离职人员指纹信息待处理标志位
		for (FingerFaceTemplate fingerFaceTemplate : fingerFaceTemplates) {
			List<EmployeeDevice> employeeDevices = employeeDeviceDao
					.findEmployeeDeviceByCode(fingerFaceTemplate.getEmployeeCode());
			for (EmployeeDevice employeeDevice : employeeDevices) {
				taskService.pushCommand(
						fingerFaceTemplate.getLastModifiedBy() == 1 ? "超级管理员"
								: fingerFaceTemplate.getLastModifiedBy().toString(),
						"删除离职员工指纹信息", employeeDevice.getEmployeeCode(), employeeDevice.getDeptId(),
						employeeDevice.getSn(), CommandWrapper.CMD_DATA_DELETE_FINGER,
						"PIN=" + fingerFaceTemplate.getEmployeeCode() + CommandWrapper.HT + "FID="
								+ fingerFaceTemplate.getFid());
			}

			// 插入完命令后直接更新状态
			fingerFaceTemplate.setStateType(0);
			fingerFaceTemplate.setDeleted("1");
			fingerFaceTemplate.setLastModifiedDate(new Date());
			fingerFaceTemplateService.update(fingerFaceTemplate);
		}
	}

	private void handleQuitFaceTemplate() {
		// 查询待处理的脸纹信息(通过视图)
		List<FingerFaceTemplate> fingerFaceTemplates = fingerFaceTemplateService.findFingerFaceTemplateByStateType(2,
				3);// 脸纹类型,离职人员脸纹信息待处理标志位
		for (FingerFaceTemplate fingerFaceTemplate : fingerFaceTemplates) {
			List<EmployeeDevice> employeeDevices = employeeDeviceDao
					.findEmployeeDeviceByCode(fingerFaceTemplate.getEmployeeCode());
			for (EmployeeDevice employeeDevice : employeeDevices) {
				taskService.pushCommand(
						fingerFaceTemplate.getLastModifiedBy() == 1 ? "超级管理员"
								: fingerFaceTemplate.getLastModifiedBy().toString(),
						"删除离职员工脸纹信息", employeeDevice.getEmployeeCode(), employeeDevice.getDeptId(),
						employeeDevice.getSn(), CommandWrapper.CMD_DATA_DELETE_FACE,
						"PIN=" + fingerFaceTemplate.getEmployeeCode() + CommandWrapper.HT + "FID="
								+ fingerFaceTemplate.getFid());
			}

			// 插入完命令后直接更新状态
			fingerFaceTemplate.setStateType(0);
			fingerFaceTemplate.setDeleted("1");
			fingerFaceTemplate.setLastModifiedDate(new Date());
			fingerFaceTemplateService.update(fingerFaceTemplate);
		}
	}

	private void handleEmployeeDeviceInfo() {
		// 查询待处理的基本信息
		List<EmployeeDeviceInfo> employeeDeviceInfos = employeeDeviceInfoService.findEmployeeDeviceInfoByStateType(1);
		for (EmployeeDeviceInfo employeeDeviceInfo : employeeDeviceInfos) {
			// 通过员工ID查询对应的设备
			List<EmployeeDevice> employeeDevices = employeeDeviceDao
					.findEmployeeDeviceByCode(employeeDeviceInfo.getEmployeeCode());
			for (EmployeeDevice employeeDevice : employeeDevices) {
				taskService.syncUserInfo("设备管理员", "设备上数据变更同步推送-员工基本信息", employeeDeviceInfo, employeeDevice);
			}

			// 插入完命令后直接更新状态
			employeeDeviceInfo.setStateType(0);
			employeeDeviceInfo.setLastModifiedDate(new Date());
			employeeDeviceInfoService.update(employeeDeviceInfo);
		}
	}

	private void handleEmployeeDeviceInfoPhoto() {
		// 查询待处理的头像信息
		List<EmployeeDeviceInfo> employeeDeviceInfos = employeeDeviceInfoService.findEmployeeDeviceInfoByStateType(2);
		for (EmployeeDeviceInfo employeeDeviceInfo : employeeDeviceInfos) {
			// 通过员工ID查询对应的设备
			List<EmployeeDevice> employeeDevices = employeeDeviceDao
					.findEmployeeDeviceByCode(employeeDeviceInfo.getEmployeeCode());
			for (EmployeeDevice employeeDevice : employeeDevices) {
				taskService.syncUserPhoto("设备管理员", "设备上数据变更同步推送-员工头像信息", employeeDeviceInfo, employeeDevice);
			}

			// 插入完命令后直接更新状态
			employeeDeviceInfo.setStateType(0);
			employeeDeviceInfo.setLastModifiedDate(new Date());
			employeeDeviceInfoService.update(employeeDeviceInfo);
		}
	}

	private void handleFingerTemplate() {
		// 查询待处理的指纹信息
		List<FingerFaceTemplate> fingerFaceTemplates = fingerFaceTemplateService.findFingerFaceTemplateByStateType(1,
				1);// 指纹,待处理
		for (FingerFaceTemplate fingerFaceTemplate : fingerFaceTemplates) {
			List<EmployeeDevice> employeeDevices = employeeDeviceDao
					.findEmployeeDeviceByCode(fingerFaceTemplate.getEmployeeCode());
			for (EmployeeDevice employeeDevice : employeeDevices) {
				taskService.syncUserFinger("设备管理员", "设备上数据变更同步推送-员工指纹信息", fingerFaceTemplate, employeeDevice);
			}

			// 插入完命令后直接更新状态
			fingerFaceTemplate.setStateType(0);
			fingerFaceTemplate.setLastModifiedDate(new Date());
			fingerFaceTemplateService.update(fingerFaceTemplate);
		}
	}

	private void handleFaceTemplate() {
		// 查询待处理的脸纹信息
		List<FingerFaceTemplate> fingerFaceTemplates = fingerFaceTemplateService.findFingerFaceTemplateByStateType(2,
				1);// 脸纹,待处理
		// 创建一个List,用于存储被同步的考勤机
		List<String> sns = new ArrayList<>();
		for (FingerFaceTemplate fingerFaceTemplate : fingerFaceTemplates) {
			List<EmployeeDevice> employeeDevices = employeeDeviceDao
					.findEmployeeDeviceByCode(fingerFaceTemplate.getEmployeeCode());
			for (EmployeeDevice employeeDevice : employeeDevices) {
				taskService.syncUserFace("设备管理员", "设备上数据变更同步推送-员工脸纹信息", fingerFaceTemplate, employeeDevice);
				if (!sns.contains(employeeDevice.getSn())) {
					sns.add(employeeDevice.getSn());
				}
			}

			// 插入完命令后直接更新状态
			fingerFaceTemplate.setStateType(0);
			fingerFaceTemplate.setLastModifiedDate(new Date());
			fingerFaceTemplateService.update(fingerFaceTemplate);
		}
		for (String sn : sns) {
			taskService.pushCommand("设备管理员", "重启设备", null, null, sn, CommandWrapper.CMD_REBOOT);
		}
	}

	private void handleDeletedEmployeeDevice() {// 处理员工和设备因为逻辑删除的而需要删除的员工(后期需要优化的是不要全量逻辑删除)
		List<EmployeeDevice> employeeDevices = employeeDeviceDao.findEmployeeDeviceList("1", 1);// deleted=1(被删除),stateType=1(待处理)
		for (EmployeeDevice employeeDevice : employeeDevices) {// 此时有员工Code和设备
			// 查询员工设备信息
			EmployeeDeviceInfo employeeDeviceInfo = employeeDeviceInfoService
					.getInfoByEmployeeCode(employeeDevice.getEmployeeCode());
			if (null != employeeDeviceInfo) {
				// 删除员工基本信息
				taskService.pushCommand("Job执行", "删除员工基本信息", employeeDevice.getEmployeeCode(),
						employeeDevice.getDeptId(), employeeDevice.getSn(), CommandWrapper.CMD_DATA_DELETE_USER,
						"PIN=" + employeeDeviceInfo.getEmployeeCode());
				// 删除员工头像信息
				taskService.pushCommand("Job执行", "删除员工头像信息", employeeDevice.getEmployeeCode(),
						employeeDevice.getDeptId(), employeeDevice.getSn(), CommandWrapper.CMD_DATA_DELETE_PHOTO,
						"PIN=" + employeeDeviceInfo.getEmployeeCode());
			}

			// 查询员工指纹信息
			List<FingerFaceTemplate> fingerTemplates = fingerFaceTemplateService
					.selectByEmployeeCodeAndType(employeeDevice.getEmployeeCode(), 1);
			for (FingerFaceTemplate fingerFaceTemplate : fingerTemplates) {
				// 删除员工指纹信息
				taskService.pushCommand("Job执行", "删除员工指纹信息", employeeDevice.getEmployeeCode(),
						employeeDevice.getDeptId(), employeeDevice.getSn(), CommandWrapper.CMD_DATA_DELETE_FINGER,
						"PIN=" + fingerFaceTemplate.getEmployeeCode() + CommandWrapper.HT + "FID="
								+ fingerFaceTemplate.getFid());
			}

			// 查询员工脸纹信息
			List<FingerFaceTemplate> faceTemplates = fingerFaceTemplateService
					.selectByEmployeeCodeAndType(employeeDevice.getEmployeeCode(), 2);
			for (FingerFaceTemplate fingerFaceTemplate : faceTemplates) {
				// 删除员工脸纹信息
				taskService.pushCommand("Job执行", "删除员工脸纹信息", employeeDevice.getEmployeeCode(),
						employeeDevice.getDeptId(), employeeDevice.getSn(), CommandWrapper.CMD_DATA_DELETE_FACE,
						"PIN=" + fingerFaceTemplate.getEmployeeCode() + CommandWrapper.HT + "FID="
								+ fingerFaceTemplate.getFid());
			}

			// 处理完员工和设备关联表后,直接更新处理状态
			employeeDeviceDao.updateAllByEmployeeCode(new Date(), employeeDevice.getEmployeeCode(), "1", 1);// deleted=1(被删除),stateType=1(待处理)
		}
	}

	private void handleNewEmployeeDevice() {
		List<EmployeeDevice> employeeDevices = employeeDeviceDao.findEmployeeDeviceList("0", 1);// deleted=0(新建),stateType=1(待处理)
		// 创建一个List,用于存储被同步的考勤机
		List<String> sns = new ArrayList<>();
		for (EmployeeDevice employeeDevice : employeeDevices) {
			// 查询员工设备信息
			EmployeeDeviceInfo employeeDeviceInfo = employeeDeviceInfoService
					.getInfoByEmployeeCode(employeeDevice.getEmployeeCode());
			// 当员工的员工设备基本信息不存在,所有把指纹和脸纹信息同步到考勤机也是无效的
			if (null == employeeDeviceInfo) {
				continue;
			}

			// 同步员工基本信息
			taskService.syncUserInfo("Job执行", "同步员工基本信息", employeeDeviceInfo, employeeDevice);
			// 同步员工头像信息
			taskService.syncUserPhoto("Job执行", "同步员工头像信息", employeeDeviceInfo, employeeDevice);

			// 查询员工指纹信息
			List<FingerFaceTemplate> fingerTemplates = fingerFaceTemplateService
					.selectByEmployeeCodeAndType(employeeDevice.getEmployeeCode(), 1);
			for (FingerFaceTemplate fingerFaceTemplate : fingerTemplates) {
				// 同步员工指纹信息
				taskService.syncUserFinger("Job执行", "同步员工指纹信息", fingerFaceTemplate, employeeDevice);
			}

			// 查询员工脸纹信息
			List<FingerFaceTemplate> faceTemplates = fingerFaceTemplateService
					.selectByEmployeeCodeAndType(employeeDevice.getEmployeeCode(), 2);
			for (FingerFaceTemplate fingerFaceTemplate : faceTemplates) {
				// 同步员工脸纹信息
				taskService.syncUserFinger("Job执行", "同步员工脸纹信息", fingerFaceTemplate, employeeDevice);
				if (!sns.contains(employeeDevice.getSn())) {
					sns.add(employeeDevice.getSn());
				}
			}

			// 处理完员工和设备关联表后,直接更新处理状态
			employeeDeviceDao.updateAllByEmployeeCode(new Date(), employeeDevice.getEmployeeCode(), "0", 1);// deleted=0(新建),stateType=1(待处理)
		}
		for (String sn : sns) {
			taskService.pushCommand("设备管理员", "重启设备", null, null, sn, CommandWrapper.CMD_REBOOT);
		}
	}
}
