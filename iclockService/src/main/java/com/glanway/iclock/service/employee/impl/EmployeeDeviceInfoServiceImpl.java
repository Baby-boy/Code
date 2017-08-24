package com.glanway.iclock.service.employee.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glanway.gone.util.StringUtils;
import com.glanway.iclock.common.CommandWrapper;
import com.glanway.iclock.dao.employee.EmployeeDeviceInfoDao;
import com.glanway.iclock.entity.employee.EmployeeDeviceInfo;
import com.glanway.iclock.entity.vo.device.EmployeeDeviceInfoVO;
import com.glanway.iclock.service.BaseServiceImpl;
import com.glanway.iclock.service.employee.EmployeeDeviceInfoService;
import com.glanway.iclock.service.sign.DeviceService;

/**
 * 说明 : 员工的考勤机信息(密码,指纹模板,人脸模板等),service的实现类
 * 
 * @author zhangshaung
 * @version 1.0.0
 * @dateTime 2017年4月19日 上午11:04:21
 */
@Transactional
@Service("employeeDeviceInfoService")
public class EmployeeDeviceInfoServiceImpl extends BaseServiceImpl<EmployeeDeviceInfo>
		implements EmployeeDeviceInfoService {

	@Autowired
	private EmployeeDeviceInfoDao employeeDeviceInfoDao;

	@Autowired
	private DeviceService deviceService;

	/**
	 * 说明 : 根据Id查询员工的考勤机信息
	 * 
	 * @param id
	 * @return
	 * @author zhangshaung
	 * @dateTime 2017年4月19日 上午11:06:54
	 */
	@Override
	public EmployeeDeviceInfo getInfoById(Long id) {
		return employeeDeviceInfoDao.selectByPrimaryKey(id);
	}

	/**
	 * 说明 : 根据员工代码employeeCode查询员工的考勤机信息
	 * 
	 * @param employeeCode
	 * @return
	 * @author zhangshaung
	 * @dateTime 2017年4月19日 上午11:06:54
	 */
	@Override
	public EmployeeDeviceInfo getInfoByEmployeeCode(String employeeCode) {
		return employeeDeviceInfoDao.selectByEmployeeCode(employeeCode);
	}

	@Override
	public void updateById(EmployeeDeviceInfo employeeInfo) {
		employeeDeviceInfoDao.updateByPrimaryKeySelective(employeeInfo);
	}

	@Override
	public List<String> findEmployeeDeviceInfo(String employeeCodes) {
		// 先根据设备sn 查询 需要到当前设备上打卡的所有员工信息
		List<EmployeeDeviceInfoVO> employeeVos = employeeDeviceInfoDao.findEmployeeDeviceInfo(employeeCodes.split(","));

		List<String> list = new ArrayList<String>();
		// 开始拼接命令参数集
		for (EmployeeDeviceInfoVO eInfoVO : employeeVos) {
			final StringBuilder param = new StringBuilder();
			final String code = eInfoVO.getCode();
			final String name = eInfoVO.getName();
			final String pwd = eInfoVO.getPwd();
			final String card = eInfoVO.getCard();
			final String pri = eInfoVO.getPri();

			param.append("PIN=").append(code);
			if (StringUtils.hasText(name)) {
				param.append(CommandWrapper.HT).append("Name=").append(name);
			}
			if (StringUtils.hasText(pwd)) {
				param.append(CommandWrapper.HT).append("Passwd=").append(pwd);
			}
			if (StringUtils.hasText(card)) {
				// param.append(HT).append("Card=").append(pwd);
			}
			param.append(CommandWrapper.HT).append("Pri=").append(StringUtils.hasText(pri) ? pri : "0");

			for (int i = 0; i < name.length(); i++) {
				param.append(CommandWrapper.HT);
			}

			// 先根据员工编号查询是否有指纹和脸纹 , 如果没有指纹或面部数据,就不同步当前员工
			final int count = deviceService.countFingerAndFaceByEmployeeCode(code);
			if (count > 0) {
				list.add(param.toString());
			}
		}
		return list;
	}

	@Override
	public List<String> findEmployeeDeviceInfoPhone(String employeeCodes) {
		// 先根据设备sn 查询 需要到当前设备上打卡的所有员工信息
		List<EmployeeDeviceInfoVO> employeeVos = employeeDeviceInfoDao.findEmployeeDeviceInfo(employeeCodes.split(","));

		List<String> list = new ArrayList<String>();
		// 开始拼接命令参数集
		for (EmployeeDeviceInfoVO eInfoVO : employeeVos) {
			final StringBuilder param = new StringBuilder();
			final String code = eInfoVO.getCode();
			String pic = "";
			param.append("PIN=").append(code);
			EmployeeDeviceInfo employeeDeviceInfo = employeeDeviceInfoDao.selectByEmployeeCode(eInfoVO.getCode());
			if (null != employeeDeviceInfo && null != employeeDeviceInfo.getPic()) {
				pic = employeeDeviceInfo.getPic();
				param.append(CommandWrapper.HT).append("Size=").append(pic.length());
				param.append(CommandWrapper.HT).append("Content=").append(pic);
				// 先根据员工编号查询是否有指纹和脸纹 , 如果没有指纹或面部数据,就不同步当前员工
				final int count = deviceService.countFingerAndFaceByEmployeeCode(code);
				if (count > 0) {
					list.add(param.toString());
				}
			}
		}
		return list;
	}
}
