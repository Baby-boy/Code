package com.glanway.iclock.service.employee.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glanway.iclock.common.CommandWrapper;
import com.glanway.iclock.dao.employee.FingerFaceTemplateDao;
import com.glanway.iclock.entity.employee.FingerFaceTemplate;
import com.glanway.iclock.entity.vo.device.EmployeeDeviceFingerFaceVo;
import com.glanway.iclock.service.BaseServiceImpl;
import com.glanway.iclock.service.employee.FingerFaceTemplateService;

/**
 * 说明 : 员工指纹模板和脸纹模板 实现类
 * 
 * @author zhangshaung
 * @version 1.0.0
 * @dateTime 2017年4月19日 下午7:08:39
 */
@Transactional
@Service("fingerFaceTemplateService")
public class FingerFaceTemplateServiceImpl extends BaseServiceImpl<FingerFaceTemplate>
		implements FingerFaceTemplateService {

	@Autowired
	private FingerFaceTemplateDao fingerFaceTemplateDao;

	@Override
	public List<FingerFaceTemplate> selectByEmployeeCodeAndType(String employeeCode, Integer type) {
		if (null == employeeCode || null == type) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("employeeCode", employeeCode);
		params.put("type", type);

		return fingerFaceTemplateDao.selectByEmployeeCodeAndType(params);
	}

	@Override
	public FingerFaceTemplate findInfoByEmployeeCodeAndTypeAndFid(String employeeCode, String fid, Integer type) {
		if (null == employeeCode || null == fid || null == type) {
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("employeeCode", employeeCode);
		params.put("fid", fid);
		params.put("type", type);
		return fingerFaceTemplateDao.findInfoByEmployeeCodeAndTypeAndFid(params);
	}

	@Override
	public List<String> findEmployeeFingerAndFaceTmpData(String employeeCodes, Integer type) {
		// 先根据设备sn 查询 需要到当前设备上打卡的所有员工指纹
		List<EmployeeDeviceFingerFaceVo> employeeVos = fingerFaceTemplateDao
				.findEmployeeFingerFaceBySn(employeeCodes.split(","), type);

		List<String> list = new ArrayList<String>();
		// 开始拼接命令参数集
		for (EmployeeDeviceFingerFaceVo eInfoVO : employeeVos) {
			final StringBuilder param = new StringBuilder();

			param.append("PIN=").append(eInfoVO.getCode());
			param.append(CommandWrapper.HT).append("FID=").append(eInfoVO.getFid());
			param.append(CommandWrapper.HT).append("Size=").append(eInfoVO.getTmpSize());
			param.append(CommandWrapper.HT).append("Valid=").append(eInfoVO.getValid());
			param.append(CommandWrapper.HT).append("TMP=").append(eInfoVO.getTmp());

			list.add(param.toString());
		}
		return list;
	}
}
