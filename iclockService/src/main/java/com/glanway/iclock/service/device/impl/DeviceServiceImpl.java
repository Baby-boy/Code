package com.glanway.iclock.service.device.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glanway.gone.util.StringUtils;
import com.glanway.iclock.common.CommandWrapper;
import com.glanway.iclock.dao.device.DeviceDao;
import com.glanway.iclock.dao.employee.EmployeeDeviceInfoDao;
import com.glanway.iclock.dao.employee.FingerFaceTemplateDao;
import com.glanway.iclock.entity.device.Device;
import com.glanway.iclock.entity.employee.EmployeeDeviceInfo;
import com.glanway.iclock.entity.employee.FingerFaceTemplate;
import com.glanway.iclock.entity.vo.device.EmployeeDeviceFingerFaceVo;
import com.glanway.iclock.entity.vo.device.EmployeeDeviceInfoVO;
import com.glanway.iclock.service.BaseServiceImpl;
import com.glanway.iclock.service.device.DeviceService;

@Service("deviceService")
@Transactional
public class DeviceServiceImpl extends BaseServiceImpl<Device> implements DeviceService {

	@Autowired
	private DeviceDao deviceDao;

	@Autowired
	private EmployeeDeviceInfoDao employeeDeviceInfoDao;

	@Autowired
	private FingerFaceTemplateDao faceTemplateDao;

	@Override
	public void saveDevice(Device device) {
		device.setId(null);
		device.setBatchDate(new Date());
		device.setCreatedBy(1l);
		device.setCreatedDate(new Date());
		device.setCreProId(1l);
		device.setModProId(1l);
		device.setLastModifiedBy(1l);
		device.setLastModifiedDate(new Date());

		deviceDao.insertSelective(device);
	}

	@Override
	public Device selectByDeviceSn(String sn) {
		return deviceDao.selectByDeviceSn(sn);
	}

	@Override
	public List<Device> findLastConnectionTimeExcendFiveMinute() {
		return deviceDao.findLastConnectionTimeExcendFiveMinute();
	}

	@Override
	public void updateStateById(Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("state", 3);// 设备状态设置为异常
		param.put("syncState", 1);// 同步状态设置为未同步

		deviceDao.updateStateById(param);

	}

	@Override
	public void updateStateBySn(String sn, Integer state) {
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("sn", sn);

		param.put("state", state);

		deviceDao.updateStateBySn(param);
	}

	@Override
	public List<String> updateUserInfoDataBySn(String sn) {
		// 先根据设备sn 查询 需要到当前设备上打卡的所有员工信息
		List<EmployeeDeviceInfoVO> employeeVos = this.findEmployeeBySn(sn);
		List<String> list = new ArrayList<String>();
		// 开始拼接命令参数集
		for (EmployeeDeviceInfoVO eInfoVO : employeeVos) {
			final StringBuilder param = new StringBuilder();
			final String code = eInfoVO.getCode();
			final String name = eInfoVO.getName();
			final String pwd = eInfoVO.getPwd(); // FIXME
			final String card = eInfoVO.getCard();
			final String pri = eInfoVO.getPri();

			param.append("PIN=").append(code);

			if (StringUtils.hasText(name)) {
				param.append(HT).append("Name=").append(name);
			}

			if (StringUtils.hasText(pwd)) {
				param.append(HT).append("Passwd=").append(pwd);
			}

			if (StringUtils.hasText(card)) {
				// param.append(HT).append("Card=").append(pwd); // FIXME
			}

			param.append(HT).append("Pri=").append(StringUtils.hasText(pri) ? pri : "0");

			if (StringUtils.hasText(pri) && "14".equals(pri)) {
				for (int i = 0; i < name.length(); i++) {
					param.append(CommandWrapper.HT);
				}
			} else {
				for (int i = 0; i < name.length() - 1; i++) {
					param.append(CommandWrapper.HT);
				}
			}

			// 先根据员工编号查询是否有指纹和脸纹 , 如果没有指纹或面部数据,就不同步当前员工
			final int count = this.countFingerAndFaceByEmployeeCode(code);
			if (count > 0) {
				list.add(param.toString());
			}
		}
		return list;
	}

	@Override
	public List<EmployeeDeviceInfoVO> findEmployeeBySn(String sn) {
		if (null == sn && "".equals(sn)) {
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("sn", sn);
		List<EmployeeDeviceInfoVO> deviceEmployeeVos = deviceDao.findEmployeeBySn(param);
		return deviceEmployeeVos;
	}

	@Override
	public List<String> updateUserPhoneDataBySn(String sn) {
		// 先根据设备sn 查询 需要到当前设备上打卡的所有员工信息
		List<EmployeeDeviceInfoVO> employeeVos = this.findEmployeeBySn(sn);
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
				param.append(HT).append("Size=").append(pic.length());
				param.append(HT).append("Content=").append(pic);
				// 先根据员工编号查询是否有指纹和脸纹 , 如果没有指纹或面部数据,就不同步当前员工
				final int count = this.countFingerAndFaceByEmployeeCode(code);
				if (count > 0) {
					list.add(param.toString());
				}
			}
		}
		return list;
	}

	@Override
	public List<EmployeeDeviceFingerFaceVo> findEmployeeFingerFaceBySn(String sn, Integer type) {
		if (null == sn || null == type) {
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sn", sn);
		param.put("type", type);
		List<EmployeeDeviceFingerFaceVo> list = deviceDao.findEmployeeFingerFaceBySn(param);
		return list;
	}

	@Override
	public List<String> updateUserFingerTmpDataBySn(String sn) {
		// 先根据设备sn 查询 需要到当前设备上打卡的所有员工指纹
		List<EmployeeDeviceFingerFaceVo> employeeVos = this.findEmployeeFingerFaceBySn(sn, 1);
		List<String> list = new ArrayList<String>();
		// 开始拼接命令参数集
		for (EmployeeDeviceFingerFaceVo eInfoVO : employeeVos) {
			final StringBuilder param = new StringBuilder();
			final String code = eInfoVO.getCode();
			final String fid = eInfoVO.getFid();
			final Integer size = eInfoVO.getTmpSize();
			final String valid = eInfoVO.getValid();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", 1);
			map.put("fid", eInfoVO.getFid());
			map.put("employeeCode", eInfoVO.getCode());
			FingerFaceTemplate fingerTemplate = faceTemplateDao.findInfoByEmployeeCodeAndTypeAndFid(map);
			if (null != fingerTemplate && null != fingerTemplate.getTmp()) {
				String tmp = fingerTemplate.getTmp();
				param.append("PIN=").append(code);
				param.append(HT).append("FID=").append(fid);
				param.append(HT).append("Size=").append(size);
				param.append(HT).append("Valid=").append(valid);
				param.append(HT).append("TMP=").append(tmp);

				list.add(param.toString());
			}

		}
		return list;
	}

	@Override
	public List<String> updateUserFaceTmpDataBySn(String sn) {
		// 先根据设备sn 查询 需要到当前设备上打卡的所有员工面部模板
		List<EmployeeDeviceFingerFaceVo> employeeVos = this.findEmployeeFingerFaceBySn(sn, 2);
		List<String> list = new ArrayList<String>();
		// 开始拼接命令参数集
		for (EmployeeDeviceFingerFaceVo eInfoVO : employeeVos) {

			final StringBuilder param = new StringBuilder();
			final String code = eInfoVO.getCode();
			final String fid = eInfoVO.getFid();
			final Integer size = eInfoVO.getTmpSize();
			final String valid = eInfoVO.getValid();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", 2);
			map.put("fid", eInfoVO.getFid());
			map.put("employeeCode", eInfoVO.getCode());
			FingerFaceTemplate faceTemplate = faceTemplateDao.findInfoByEmployeeCodeAndTypeAndFid(map);
			if (null != faceTemplate && null != faceTemplate.getTmp()) {
				String tmp = faceTemplate.getTmp();

				param.append("PIN=").append(code);

				param.append(HT).append("FID=").append(fid);
				param.append(HT).append("Size=").append(size);
				param.append(HT).append("Valid=").append(valid);
				param.append(HT).append("TMP=").append(tmp);

				list.add(param.toString());
			}
		}
		return list;
	}

	@Override
	public int updateByPrimaryKeySelective(Device record) {
		return deviceDao.updateByPrimaryKey(record);
	}

	@Override
	public Device selectSignPointNotNullByDeviceSn(String sn) {
		return deviceDao.selectSignPointNotNullByDeviceSn(sn);
	}

	@Override
	public int countEmployeeBySn(String sn) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (null == sn) {
			return 0;
		}

		param.put("sn", sn);
		return deviceDao.countEmployeeBySn(param);
	}

	@Override
	public int countFingerAndFaceByEmployeeCode(String code) {
		if (null == code) {
			return 0;
		}
		return deviceDao.countFingerAndFaceByEmployeeCode(code);
	}

	@Override
	public Long syncPeopleCountEmployeeBySn(String sn) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (org.apache.commons.lang3.StringUtils.isEmpty(sn)) {
			return 0L;
		}

		param.put("sn", sn);
		return deviceDao.syncPeopleCountEmployeeBySn(param);
	}
}
