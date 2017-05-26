package com.yd.gcj.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yd.gcj.entity.YdMangerFiles;
import com.yd.gcj.entity.YdMangerFilesTask;
import com.yd.gcj.entity.YdMangerLabel;
import com.yd.gcj.entity.YdMangerTask;
import com.yd.gcj.entity.YdMangerTaskActive;
import com.yd.gcj.entity.YdMangerTaskCondition;
import com.yd.gcj.entity.YdMangerTaskLabel;
import com.yd.gcj.entity.YdMangerTaskPM;
import com.yd.gcj.entity.YdMangerTaskType;
import com.yd.gcj.entity.YdMangerTaskTypeTR;
import com.yd.gcj.entity.vo.PageNum;
import com.yd.gcj.entity.vo.TaskVoNums;
import com.yd.gcj.entity.vo.YdMangerTaskVo;
import com.yd.gcj.entity.vo.YdMangerTenderVo;
import com.yd.gcj.entity.vo.YdMangerUserVo;
import com.yd.gcj.mapper.YdMangerMapperFiles;
import com.yd.gcj.mapper.YdMangerMapperFilesTask;
import com.yd.gcj.mapper.YdMangerMapperLabel;
import com.yd.gcj.mapper.YdMangerMapperTask;
import com.yd.gcj.mapper.YdMangerMapperTaskActive;
import com.yd.gcj.mapper.YdMangerMapperTaskLabel;
import com.yd.gcj.mapper.YdMangerMapperTaskPM;
import com.yd.gcj.mapper.YdMangerMapperTaskTypeTR;
import com.yd.gcj.mapper.YdMangerMapperTender;
import com.yd.gcj.mapper.YdMangerMapperUser;
import com.yd.gcj.service.YdMangerServiceTask;
import com.yd.gcj.tool.MapFactory;
import com.yd.gcj.tool.MapInitFactory;
import com.yd.gcj.tool.Values;
import com.yd.gcj.util.MyStaticFactory;
import com.yd.gcj.util.YdMangerLabelFactory;

@Service("serviceTask")
public class YdMangerServiceImplTask implements YdMangerServiceTask {

	@Autowired
	private YdMangerMapperTask ydMangerMapperTask;

	@Autowired
	private YdMangerMapperTaskTypeTR ydMangerMapperTaskTypeTR;

	@Autowired
	private YdMangerMapperTaskLabel ydMangerMapperTaskLabel;

	@Autowired
	private YdMangerMapperTender ydMangerMapperTender;

	@Autowired
	private YdMangerMapperLabel ydMangerMapperLabel;

	@Autowired
	private YdMangerMapperTaskActive ydMangerMapperTaskActive;

	@Autowired
	private YdMangerMapperFiles ydMangerMapperFiles;

	@Autowired
	private YdMangerMapperFilesTask ydMangerMapperFilesTask;
	
	@Autowired
	private YdMangerMapperTaskPM mapperTaskPm;
	
	@Autowired
	private YdMangerMapperUser mapperUser;
	
	@Autowired
	private HttpSession session;// TODO 此session存在线程安全问题,后期进行修改

	@Override
	public Object $queryByPageNum() {
		List<YdMangerTaskVo> taskVos = new ArrayList<YdMangerTaskVo>();
		PageNum pageNum = null;
		try {
			YdMangerTaskCondition condition = (YdMangerTaskCondition) session.getAttribute("taskCondition");
			if (condition == null) {
				condition = new YdMangerTaskCondition();
			}

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("taskTypeIds", condition.taskTypeIdList());
			map.put("taskLabelIds", condition.taskLabelIdList());
			map.put("taskAddr", condition.getTaskAddr());
			map.put("taskState", condition.getTaskState());
			map.put("taskTerm", condition.getTaskTerm());

			// 获取数据总条数
			Integer num = ydMangerMapperTask.$queryCountNum(map);
			// 分页函数
			pageNum = new PageNum(session, num, "renwudatingPageNum");

			map.put("startPageNum", pageNum.getStartPageNum());
			map.put("queryPageNum", pageNum.getPageNum());

			YdMangerUserVo userVo = (YdMangerUserVo) session.getAttribute("user");

			if (userVo != null) {
				map.put("userId", userVo.getUser_id());
				taskVos = ydMangerMapperTask.$queryByPageNumAndUserId(map);
			} else {
				taskVos = ydMangerMapperTask.$queryByPageNum(map);
			}
			if (taskVos.size() > 0) {
				List<Integer> tids = new ArrayList<Integer>();
				for (YdMangerTaskVo taskVo : taskVos) {
					tids.add(taskVo.getTask_id());
				}
				List<YdMangerTaskLabel> taskLabels = ydMangerMapperTaskLabel.$queryByTids(tids);
				for (YdMangerTaskVo taskVo : taskVos) {
					// 任务描述字符串截取
					if (taskVo.getTask_discrip().length() > 100) {
						taskVo.setTask_discrip(taskVo.getTask_discrip().substring(0, 70) + "......");
					}

					List<YdMangerTaskLabel> labels = new ArrayList<YdMangerTaskLabel>();
					for (YdMangerTaskLabel taskLabel : taskLabels) {
						if (taskVo.getTask_id() == taskLabel.getTaskl_tid()) {
							labels.add(taskLabel);
						}
					}
					taskVo.setLabels(labels);
				}
			}
			pageNum.setData(taskVos);
		} catch (Exception e) {
			e.printStackTrace();
			pageNum = new PageNum();
		}
		return pageNum;
	}

	@Override
	public List<YdMangerTaskVo> $queryByEId(Integer userId, Integer taskState) {
		return ydMangerMapperTask.$queryByEId(userId, taskState);
	}

	@Override
	public List<YdMangerTaskVo> $queryBySId(Integer userId, Integer taskState) {
		return ydMangerMapperTask.$queryBySId(userId, taskState);
	}

	@Override
	public Object $queryBySql(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		try {
			String sql = (String) map.get("sql");
			mapInitFactory.init().setData(ydMangerMapperTask.$queryBySql(sql));
		} catch (Exception e) {
			mapInitFactory.setSystemError();
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $queryAll() {
		MapInitFactory mapInitFactory = new MapInitFactory();
		try {
			mapInitFactory.init().setData(ydMangerMapperTask.$queryAll());
		} catch (Exception e) {
			mapInitFactory.setSystemError();
		}
		return mapInitFactory.getMap();
	}

	@Override
	public YdMangerTaskVo $queryById(Integer taskId, Integer userId) {

		YdMangerTaskVo taskVo = new YdMangerTaskVo();
		if (userId != 0) {
			taskVo = ydMangerMapperTask.$queryByTaskIdAndUserId(taskId, userId);
		} else {
			taskVo = ydMangerMapperTask.$queryById(taskId);
		}
		List<YdMangerTaskActive> actives = ydMangerMapperTaskActive.$queryByTid(taskId);
		if (actives != null && actives.size() > 0) {
			taskVo.setActives(actives);
		}
		List<YdMangerTenderVo> tenderVos = ydMangerMapperTender.$queryByTid(taskId, 1, 10, 2);
		if (tenderVos != null && tenderVos.size() > 0) {
			taskVo.setTenderVos(tenderVos);
		}
		List<YdMangerTaskTypeTR> types = ydMangerMapperTaskTypeTR.$queryAllByTaskId(taskId);
		if (types != null) {
			taskVo.setTypes(types);
		}
		List<YdMangerTaskLabel> taskLabels = ydMangerMapperTaskLabel.$queryByTid(taskId);
		if (taskLabels != null) {
			taskVo.setLabels(taskLabels);
		}
		return taskVo;
	}

	@Override
	public YdMangerTaskVo $queryByIdToUpdate(Integer taskId) {

		YdMangerTaskVo taskVo = ydMangerMapperTask.$queryById(taskId);

		List<YdMangerTaskType> types = ydMangerMapperTaskTypeTR.$queryTypeByTaskId(taskId);
		if (types != null) {
			taskVo.setTypess(types);
		}
		List<YdMangerLabel> labels = ydMangerMapperLabel.$queryAllToTaskUpdate(taskId);

		if (labels != null && labels.size() > 0) {
			taskVo.setLabelVos(YdMangerLabelFactory.getLabelVos(labels));
		}
		return taskVo;
	}

	@Override
	public Object $queryCountNumByEId(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		try {
			Integer task_uid = (Integer) map.get("task_uid");
			mapInitFactory.init().setData(ydMangerMapperTask.$queryCountNumByEId(task_uid));
		} catch (Exception e) {
			mapInitFactory.setSystemError();
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $queryCountNum() {
		MapInitFactory mapInitFactory = new MapInitFactory();
		try {
			/*
			 * mapInitFactory.init().setData(ydMangerMapperTask.$queryCountNum()
			 * );
			 */
		} catch (Exception e) {
			mapInitFactory.setSystemError();
		}
		return mapInitFactory.getMap();
	}

	@Override
	@Transactional
	public Object $insert(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		YdMangerTask task = (YdMangerTask) MapFactory.toObject(map, YdMangerTask.class);
		if (task.getTask_start_time().getTime() <= task.getTask_end_time().getTime()) {
			Integer success = ydMangerMapperTask.$insert(task);
			if (success > 0) {
				String typeIdsStr = (String) map.get("typeIds");
				String labelIdsStr = (String) map.get("labelIds");
				String fileIds = (String) map.get("fileIds");
				String[] tIds = typeIdsStr.split(",");
				String[] lids = labelIdsStr.split(",");

				Integer taskId = task.getTask_id();

				if (tIds.length > 0 && !typeIdsStr.equals("0")) {
					for (String tId : tIds) {
						Integer typeId = Integer.parseInt(tId);
						ydMangerMapperTaskTypeTR.$insert(taskId, typeId);
					}
				}

				if (lids.length > 0) {
					for (String lid : lids) {
						Integer labelId = Integer.parseInt(lid);
						ydMangerMapperTaskLabel.$insert(taskId, labelId);
					}
				}

				if (fileIds != null && !fileIds.isEmpty()) {
					String[] fileId = fileIds.split(",");
					for (String id : fileId) {
						YdMangerFilesTask filesTask = new YdMangerFilesTask();
						filesTask.setFiletr_id(Integer.parseInt(id));
						filesTask.setFiletr_tid(taskId);
						ydMangerMapperFilesTask.$insert(filesTask);
					}
				}
				mapInitFactory.setMsg("200", "任务发布成功！");
			} else {
				mapInitFactory.setMsg("502", "参数有误，请检查后再试！");
			}
		} else {
			mapInitFactory.setMsg("503", "截止时间不能早于发布时间！");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $update(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		YdMangerTask task = (YdMangerTask) MapFactory.toObject(map, YdMangerTask.class);
		Integer success = ydMangerMapperTask.$update(task);
		if (success > 0) {
			Integer taskId = task.getTask_id();
			Integer delTaskTypeSuccess = ydMangerMapperTaskTypeTR.$delete(taskId);
			if (delTaskTypeSuccess > 0) {
				String typeIdsStr = (String) map.get("typeIds");
				String[] tIds = typeIdsStr.split(",");
				if (tIds.length > 0 && !typeIdsStr.equals("0")) {
					for (String tId : tIds) {
						Integer typeId = Integer.parseInt(tId);
						ydMangerMapperTaskTypeTR.$insert(taskId, typeId);
					}
				}
			}
			Integer delTaskLabelSuccess = ydMangerMapperTaskLabel.$deleteByTaskId(taskId);
			if (delTaskLabelSuccess > 0) {
				String labelIdsStr = (String) map.get("labelIds");
				String[] lids = labelIdsStr.split(",");
				if (lids.length > 0) {
					for (String lid : lids) {
						Integer labelId = Integer.parseInt(lid);
						ydMangerMapperTaskLabel.$insert(taskId, labelId);
					}
				}
			}
			mapInitFactory.setMsg("200", "任务修改成功！");
			MyStaticFactory.queryGuzhuTask = false;
		} else {
			mapInitFactory.setMsg("503", "参数有误，请检查后再试！");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $delete(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		Integer task_id = (Integer) map.get("task_id");
		mapInitFactory.init().setData(ydMangerMapperTask.$delete(task_id));
		return mapInitFactory.getMap();
	}

	@Override
	public Integer $queryStateByEidAndTaskId(Integer userId, Integer taskId) {
		return ydMangerMapperTask.$queryStateByEidAndTaskId(userId, taskId);
	}

	@Override
	public TaskVoNums $queryByTask(Integer userType, Integer userId) {
		TaskVoNums nums = new TaskVoNums();
		if (userType == 0) {
			nums = ydMangerMapperTask.$queryByTaskE(userId);
		} else {
			nums = ydMangerMapperTask.$queryByTaskS(userId);
		}
		return nums;

	}

	@Override
	public MapInitFactory $updateTaskContractState(Integer taskId, Integer state) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		Integer success = ydMangerMapperTask.$updateContractState(taskId, state);
		if (success > 0) {
			if (state == 3) {
				ydMangerMapperTask.$updateTaskState(taskId, 4);
			}
			mapInitFactory.setMsg("200", "操作成功！");
		} else {
			mapInitFactory.setMsg("501", "操作失败！");
		}
		return mapInitFactory;
	}

	@Override
	public Object $delTaskFile(Integer fileId, Integer taskId) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		YdMangerFiles files = ydMangerMapperFiles.$queryById(fileId);
		if (files != null) {
			
			Integer delFile = ydMangerMapperFiles.$delete(fileId);
			if (taskId != -1) {
				Integer delTaskFileTR = ydMangerMapperFilesTask.$del(taskId, fileId);
				if (delFile > 0 && delTaskFileTR > 0) {
					mapInitFactory.setMsg(Values.INITSUCCESSCODE, "文件删除成功！");
				} else {
					mapInitFactory.setMsg("501", "文件删除失败！");
				}
			} else {
				if (delFile > 0) {
					mapInitFactory.setMsg(Values.INITSUCCESSCODE, "文件删除成功！");
				} else {
					mapInitFactory.setMsg("501", "文件删除失败！");
				}
			}
		} else {
			mapInitFactory.setMsg("502", "此文件不存在！");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $updateTaskState(Integer taskId, Integer state) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		int success = ydMangerMapperTask.$updateTaskState(taskId, state);
		YdMangerTaskVo taskVo = ydMangerMapperTask.$queryById(taskId);
		List<YdMangerTaskPM> taskPMs = mapperTaskPm.$queryByTid(taskId);
		float cprice = 0;
		for(YdMangerTaskPM taskPM : taskPMs){
			if(taskPM.getTpm_state() == 2 || taskPM.getTpm_state() == 6){
				cprice += taskPM.getTpm_money();
			}
		}
		if(cprice > 0){
			float uprice = mapperUser.$queryPriceByUserId(taskVo.getTask_uid());
			float price = uprice+cprice;
			mapperUser.$updateUserPrice(taskVo.getTask_uid(), price);
		}
		
		if (success > 0) {
			String alt = "";
			if(cprice > 0){
				alt = "操作成功，已托管且未确认付款金额为："+cprice+" ￥ 已退回您的账户！";
			}else{
				alt = "操作成功！";
			}
			mapInitFactory.setMsg("200", alt);
		} else {
			mapInitFactory.setMsg("501", "操作失败！");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Integer $queryContractStateBytaskId(Integer taskId) {
		return ydMangerMapperTask.$queryContractStateBytaskId(taskId);
	}

	@Override
	public Integer $isTask(Integer taskId, Integer userId, Integer userType) {
		return ydMangerMapperTask.$isTask(taskId, userId, userType);
	}

	@Override
	public YdMangerTaskVo $queryByTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		return ydMangerMapperTask.$queryById(taskId);
	}

}
