package com.glanway.iclock.service.task;

import java.util.List;
import java.util.Map;

import com.glanway.iclock.entity.device.EmployeeDevice;
import com.glanway.iclock.entity.employee.EmployeeDeviceInfo;
import com.glanway.iclock.entity.employee.FingerFaceTemplate;
import com.glanway.iclock.entity.task.Task;
import com.glanway.iclock.service.BaseService;

/**
 * 说明 : 任务(存储命令)service
 * 
 * @author zhangshaung
 * @version 1.0.0
 * @dateTime 2017年4月18日 上午11:05:57
 */
public interface TaskService extends BaseService<Task> {

	/**
	 * 说明 : 根据设备代码SN,删除(逻辑删除)所有关于当前设备的记录
	 * 
	 * @param sn
	 * @return
	 * @author zhangshaung
	 * @dateTime 2017年4月18日 下午4:37:29
	 */
	public int chearCommandsBySn(String sn);

	/**
	 * 说明 : 根据设备代码SN,修改当前命令的状态
	 * 
	 * @param sn(设备代码sn)
	 * @param state(将要修改的状态)
	 * @param oldSate(指定某一状态)
	 * @author zhangshaung
	 * @dateTime 2017年4月18日 下午6:08:52
	 */
	public void updateStateBySn(String sn, Integer state, Integer oldState);

	/**
	 * 说明 : 根据任务ID,修改当前命令的状态
	 * 
	 * @param id(任务(命令)Id)
	 * @param state(将要修改的状态)
	 * @param oldSate(指定某一状态)
	 * @author zhangshaung
	 * @dateTime 2017年4月18日 下午6:08:52
	 */
	public void updateStateById(Long id, Integer state, Integer oldState);

	/**
	 * 说明: 删除Task中的命令,并在命令记录表中记录一条相同的记录
	 *
	 * @param parseLong
	 * @author FUQIHAO
	 * @dateTime 2017年8月8日 下午3:50:42
	 */
	public void recordTaskLog(Long id);

	/**
	 * 说明 : 查询命令
	 * 
	 * @param params
	 * @return
	 * @author zhangshaung
	 * @dateTime 2017年4月20日 下午7:59:00
	 */
	public Task findOneTask(Map<String, Object> params);

	/**
	 * 说明 : 根据设备序列号查询该设备是否还存在任务
	 * 
	 * @param sn
	 * @return
	 * @author fuqihao
	 * @dateTime 2017年6月7日 下午1:47:37
	 */
	public List<Task> findTaskByDeviceSn(String sn);

	/**
	 * 说明: 根据命令查询命令任务
	 *
	 * @param sn
	 * @param state
	 * @param command
	 * @return
	 * @author FUQIHAO
	 * @dateTime 2017年8月8日 下午5:45:53
	 */
	public List<Task> findTaskByCommand(String sn, Long id, String command);

	/**
	 * 说明: 校验命令执行情况
	 *
	 * @param sn
	 * @param id
	 * @return
	 * @author FUQIHAO
	 * @dateTime 2017年8月10日 上午11:23:14
	 */
	public Task checkCommandHandle(String sn, Long id);

	/**
	 * 说明: 根据ID查询命令任务
	 *
	 * @param commandId
	 * @return
	 * @author FUQIHAO
	 * @dateTime 2017年8月18日 下午2:44:11
	 */
	public Task findTaskById(Long id);

	/****************************************************************
	 * *********************** 以下是考勤机相关的新逻辑 **********************
	 ****************************************************************/
	/**
	 * 同步员工基本信息.
	 *
	 * @param employeeDeviceInfo
	 * @param employeeDevice
	 * @author FUQIHAO
	 * @dateTime 2017年9月3日 下午7:37:15
	 */
	public void syncUserInfo(String operator, String handleType, EmployeeDeviceInfo employeeDeviceInfo,
			EmployeeDevice employeeDevice);

	/**
	 * 同步员工头像信息.
	 *
	 * @param employeeDeviceInfo
	 * @param employeeDevice
	 * @author FUQIHAO
	 * @dateTime 2017年9月3日 下午7:37:32
	 */
	public void syncUserPhoto(String operator, String handleType, EmployeeDeviceInfo employeeDeviceInfo,
			EmployeeDevice employeeDevice);

	/**
	 * 同步员工指纹信息.
	 *
	 * @param fingerFaceTemplate
	 * @param employeeDevice
	 * @author FUQIHAO
	 * @dateTime 2017年9月3日 下午8:05:56
	 */
	public void syncUserFinger(String operator, String handleType, FingerFaceTemplate fingerFaceTemplate,
			EmployeeDevice employeeDevice);

	/**
	 * 同步员工脸纹信息.
	 *
	 * @param fingerFaceTemplate
	 * @param employeeDevice
	 * @author FUQIHAO
	 * @dateTime 2017年9月3日 下午8:09:45
	 */
	public void syncUserFace(String operator, String handleType, FingerFaceTemplate fingerFaceTemplate,
			EmployeeDevice employeeDevice);

	/**
	 * 保存命令.
	 *
	 * @param handleType
	 * @param sn
	 * @param command
	 * @param args
	 * @author FUQIHAO
	 * @dateTime 2017年9月3日 下午9:32:11
	 */
	public void pushCommand(String operator, String handleType, String sn, String command, String... args);
}
