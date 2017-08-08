package com.glanway.iclock.dao.task;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.glanway.iclock.dao.BaseDao;
import com.glanway.iclock.entity.task.Task;

/**
 * 说明 : 任务 dao
 * 
 * @author zhangshaung
 * @version 1.0.0
 * @dateTime 2017年4月18日 上午10:12:36
 */
public interface TaskDao extends BaseDao<Task> {

	/** 根据设备代码SN,删除(逻辑删除)所有关于当前设备的记录 */
	public int chearCommandsBySn(String sn);

	/** 根据设备代码SN,修改当前命令的状态 */
	public void updateStateBySn(Map<String, Object> params);

	/** 根据任务ID,修改当前命令的状态 */
	public void updateStateById(Map<String, Object> params);

	/** 根据序列号查询命令 */
	public Task findOneTask(Map<String, Object> params);

	/** 根据设备序列号查询该设备是否还存在任务 */
	public List<Task> findTaskByDeviceSn(@Param("sn") String sn);

	/** 删除三天前的命令 */
	public void updateDeletedByDate(@Param("date") String date);

}
