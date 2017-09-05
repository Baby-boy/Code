package com.glanway.iclock.dao.employee;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.glanway.iclock.dao.BaseDao;
import com.glanway.iclock.entity.employee.EmployeeDeviceInfo;
import com.glanway.iclock.entity.vo.device.EmployeeDeviceInfoVO;

public interface EmployeeDeviceInfoDao extends BaseDao<EmployeeDeviceInfo> {
	public int deleteByPrimaryKey(Long id);

	public int insert(EmployeeDeviceInfo record);

	public EmployeeDeviceInfo selectByPrimaryKey(Long id);

	public EmployeeDeviceInfo selectByEmployeeCode(String employeeCode);

	public int updateByPrimaryKeySelective(EmployeeDeviceInfo record);

	public int updateByPrimaryKey(EmployeeDeviceInfo record);

	/** 根据员工Code查询设备员工信息 */
	public List<EmployeeDeviceInfoVO> findEmployeeDeviceInfo(@Param("codes") String[] codes);

	/****************************************************************
	 * *********************** 以下是考勤机相关的新逻辑 **********************
	 ****************************************************************/
	public List<EmployeeDeviceInfo> findEmployeeDeviceInfoByStateType(@Param("stateType") Integer stateType);
}