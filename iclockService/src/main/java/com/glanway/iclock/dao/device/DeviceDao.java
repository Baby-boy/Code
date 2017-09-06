package com.glanway.iclock.dao.device;

import java.util.List;
import java.util.Map;

import com.glanway.iclock.dao.BaseDao;
import com.glanway.iclock.entity.device.Device;
import com.glanway.iclock.entity.vo.device.EmployeeDeviceFingerFaceVo;
import com.glanway.iclock.entity.vo.device.EmployeeDeviceInfoVO;

public interface DeviceDao extends BaseDao<Device> {

	public int deleteByPrimaryKey(Long id);

	public int insert(Device record);

	/** 有选择的新建设备 */
	public int insertSelective(Device record);

	public Device selectByPrimaryKey(Long id);

	public int updateByPrimaryKeySelective(Device record);

	public int updateByPrimaryKey(Device record);

	/**
	 * 
	 * 说明 : 根据设备代码sn 查询设备信息
	 * 
	 * @param sn
	 * @return
	 * @author zhangshaung
	 * @dateTime 2017年4月20日 下午2:03:25
	 */
	Device selectByDeviceSn(String sn);

	/**
	 * 
	 * 说明 : 根据设备代码sn 查询考勤点不为空的设备
	 * 
	 * @param sn
	 * @return
	 * @author zhangshaung
	 * @dateTime 2017年4月25日 下午10:05:19
	 */
	Device selectSignPointNotNullByDeviceSn(String sn);

	/** 新增考勤设备 */
	void saveDevice(Device device);

	/**
	 * 
	 * 说明 : 取数据库中根据已连通设备最后一次连接时间超过5分钟的设备
	 * 
	 * @return
	 * @author zhangshaung
	 * @dateTime 2017年4月20日 下午6:05:16
	 */
	List<Device> findLastConnectionTimeExcendFiveMinute();

	/**
	 * 
	 * 说明 : 根据ID修改设备的状态
	 * 
	 * @param params
	 * @author zhangshaung
	 * @dateTime 2017年4月20日 下午6:16:34
	 */
	void updateStateById(Map<String, Object> params);

	/**
	 * 
	 * 说明 : 根据设备代码SN修改设备的状态
	 * 
	 * @param params
	 * @author zhangshaung
	 * @dateTime 2017年4月20日 下午6:16:46
	 */
	void updateStateBySn(Map<String, Object> params);

	/**
	 * 
	 * 说明 : 根据设备sn 查询 需要到当前设备上打卡的所有员工信息
	 * 
	 * @param params
	 * @return
	 * @author zhangshaung
	 * @dateTime 2017年4月21日 下午4:11:17
	 */
	List<EmployeeDeviceInfoVO> findEmployeeBySn(Map<String, Object> params);

	/**
	 * 
	 * 说明 : 根据设备sn 查询 需要到当前设备上打卡的所有员工指纹模板或脸部模板
	 * 
	 * @param params
	 * @return
	 * @author zhangshaung
	 * @dateTime 2017年4月21日 下午7:20:14
	 */
	List<EmployeeDeviceFingerFaceVo> findEmployeeFingerFaceBySn(Map<String, Object> params);

	/**
	 * 根据设备sn 查询 需要到当前设备上打卡的所有员工的数量
	 * 
	 * @author zhangshuagn
	 * @param param
	 * @return
	 * @since 1.0-20170426
	 */
	int countEmployeeBySn(Map<String, Object> param);

	/**
	 * 
	 * 说明 : 根据员工code,查询员工的指纹模板和面部模板数
	 * 
	 * @param code
	 * @return
	 * @author zhangshaung
	 * @dateTime 2017年4月27日 下午6:00:10
	 */
	int countFingerAndFaceByEmployeeCode(String code);

	/**
	 * 说明 : 根据考勤机序列号查询此考勤机上已经同步的指纹数
	 * 
	 * @param param
	 * @return
	 * @author fuqihao
	 * @dateTime 2017年6月5日 下午1:56:17
	 */
	Long syncPeopleCountEmployeeBySn(Map<String, Object> param);
}