/**
 * @author zhangshuang
 * 2017年4月19日 上午11:01:53
 */
package com.glanway.iclock.service.employee;

import java.util.List;

import com.glanway.iclock.entity.employee.EmployeeDeviceInfo;
import com.glanway.iclock.service.BaseService;

/**
 * 说明 : 员工的考勤机信息(密码,指纹模板,人脸模板等),service
 * 
 * @author zhangshaung
 * @version 1.0.0
 * @dateTime 2017年4月19日 上午11:01:53
 */
public interface EmployeeDeviceInfoService extends BaseService<EmployeeDeviceInfo> {

	/**
	 * 说明 : 根据Id查询员工的考勤机信息
	 * 
	 * @param id
	 * @return
	 * @author zhangshaung
	 * @dateTime 2017年4月19日 上午11:06:54
	 */
	public EmployeeDeviceInfo getInfoById(Long id);

	/**
	 * 说明 : 根据员工代码employeeCode查询员工的考勤机信息
	 * 
	 * @param employeeCode
	 * @return
	 * @author zhangshaung
	 * @dateTime 2017年4月19日 上午11:06:54
	 */
	public EmployeeDeviceInfo getInfoByEmployeeCode(String employeeCode);

	/**
	 * 说明: 更新员工设备信息
	 *
	 * @param employeeInfo
	 * @author FUQIHAO
	 * @dateTime 2017年8月10日 下午6:30:10
	 */
	public void updateById(EmployeeDeviceInfo employeeInfo);

	/**
	 * 根据员工code查询设备员工信息.
	 *
	 * @param employeeCodes
	 * @return
	 * @author FUQIHAO
	 * @dateTime 2017年8月23日 下午5:46:56
	 */
	public List<String> findEmployeeDeviceInfo(String employeeCodes);

	/**
	 * 根据员工code查询设备员工头像信息.
	 *
	 * @param employeeCodes
	 * @return
	 * @author FUQIHAO
	 * @dateTime 2017年8月23日 下午6:12:27
	 */
	public List<String> findEmployeeDeviceInfoPhone(String employeeCodes);

	/****************************************************************
	 * *********************** 以下是考勤机相关的新逻辑 **********************
	 ****************************************************************/
	/**
	 * 查询待处理的员工基本信息.
	 *
	 * @return
	 * @param stateType
	 * @author FUQIHAO
	 * @dateTime 2017年9月3日 下午5:34:50
	 */
	public List<EmployeeDeviceInfo> findEmployeeDeviceInfoByStateType(Integer stateType);

	/**
	 * 查询离职人员脸纹或者指纹(当天离职).
	 *
	 * @return
	 * @param stateType
	 * @author FUQIHAO
	 * @dateTime 2017年9月3日 下午5:34:50
	 */
	public List<EmployeeDeviceInfo> findEmployeeDeviceInfoByQuitStateType(Integer stateType);
}
