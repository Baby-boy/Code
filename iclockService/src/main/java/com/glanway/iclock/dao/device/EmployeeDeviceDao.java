package com.glanway.iclock.dao.device;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.glanway.iclock.dao.BaseDao;
import com.glanway.iclock.entity.device.EmployeeDevice;

public interface EmployeeDeviceDao extends BaseDao<EmployeeDevice> {

	public int insertSelective(EmployeeDevice record);

	public int updateSelective(EmployeeDevice record);

	/** 根据员工Code查询对应的设备(视图) */
	public List<EmployeeDevice> findEmployeeDeviceByCode(@Param("employeeCode") String employeeCode);

	/** 根据状态查询待处理信息 */
	public List<EmployeeDevice> findEmployeeDeviceList(@Param("deleted") String deleted,
			@Param("stateType") Integer stateType);

	/** 批量更新所有和该条相关的关联相关的数据 */
	public void updateAllByEmployeeCode(@Param("lastModifiedDate") Date lastModifiedDate,
			@Param("employeeCode") String employeeCode, @Param("deleted") String deleted,
			@Param("stateType") Integer stateType);

}