package com.yd.gcj.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.gcj.entity.YdMangerCases;
import com.yd.gcj.entity.YdMangerContract;
import com.yd.gcj.entity.YdMangerDictionaryData;
import com.yd.gcj.entity.YdMangerMessage;
import com.yd.gcj.entity.YdMangerSpcs;
import com.yd.gcj.entity.YdMangerTender;
import com.yd.gcj.entity.YdMangerUserLabel;
import com.yd.gcj.entity.vo.YdMangerTaskVo;
import com.yd.gcj.entity.vo.YdMangerTenderVo;
import com.yd.gcj.entity.vo.YdMangerUserVo;
import com.yd.gcj.mapper.YdMangerMapperCases;
import com.yd.gcj.mapper.YdMangerMapperContract;
import com.yd.gcj.mapper.YdMangerMapperDictionaryData;
import com.yd.gcj.mapper.YdMangerMapperMessage;
import com.yd.gcj.mapper.YdMangerMapperSpcs;
import com.yd.gcj.mapper.YdMangerMapperTask;
import com.yd.gcj.mapper.YdMangerMapperTender;
import com.yd.gcj.mapper.YdMangerMapperUser;
import com.yd.gcj.mapper.YdMangerMapperUserLabel;
import com.yd.gcj.service.YdMangerServiceTender;
import com.yd.gcj.tool.MapInitFactory;
import com.yd.gcj.tool.Values;
import com.yd.gcj.util.MyStaticFactory;

@Service("YdMangerServiceTender")
public class YdMangerServiceImplTender implements YdMangerServiceTender {

	@Autowired
	private YdMangerMapperTender mapperTender;

	@Autowired
	private YdMangerMapperUserLabel mapperUserLabel;

	@Autowired
	private YdMangerMapperCases mapperCases;

	@Autowired
	private YdMangerMapperSpcs mapperSpcs;

	@Autowired
	private YdMangerMapperContract mapperContract;

	@Autowired
	private YdMangerMapperTask mapperTask;

	@Autowired
	private YdMangerMapperUser mapperUser;

	@Autowired
	private YdMangerMapperMessage mapperMessage;

	@Autowired
	private YdMangerMapperDictionaryData mapperDictionaryData;

	@Override
	public List<YdMangerTenderVo> $queryByTid(Integer taskId, Integer type) {
		List<YdMangerTenderVo> tenderVos = mapperTender.$queryByTid(taskId, 0, 20, type);
		return tenderVos;
	}

	@Override
	public YdMangerTenderVo $queryByIdAndUserMsg(Integer tenderId) {
		YdMangerTenderVo tenderVo = mapperTender.$queryById(tenderId);
		try {
			if (tenderVo != null) {
				List<YdMangerUserLabel> userLabels = mapperUserLabel.$queryAll(tenderVo.getTender_uid());
				List<YdMangerCases> cases = mapperCases.$queryAllByUserId(tenderVo.getTender_uid());
				List<YdMangerSpcs> spcs = mapperSpcs.$queryAll(tenderVo.getTender_uid());
				tenderVo.setLabelVos(userLabels);
				tenderVo.setCases(cases);
				tenderVo.setSpcs(spcs);
			} else {
				tenderVo = new YdMangerTenderVo();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tenderVo;
	}

	@Override
	public Object $queryByType(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		try {
			Integer tender_tid = (Integer) map.get("tender_tid");
			Integer tender_type = (Integer) map.get("tender_type");
			if (tender_tid != null && tender_type != null && tender_tid != 0 && tender_type != 0) {
				mapInitFactory.init().setData(mapperTender.$queryByType(tender_tid, tender_type));
			} else {
				mapInitFactory.setMsg("501", "参数有误");
			}
		} catch (Exception e) {
			mapInitFactory.setSystemError();
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $queryBySid(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		try {
			Integer tender_uid = (Integer) map.get("tender_uid");
			Integer startPageNum = (Integer) map.get("startPageNum");
			Integer queryPageNum = (Integer) map.get("queryPageNum");
			if (tender_uid != null && startPageNum != null && queryPageNum != null) {
				if (tender_uid > 0 && startPageNum >= 0 && queryPageNum < 15) {
					mapInitFactory.init().setData(mapperTender.$queryBySid(tender_uid, startPageNum, queryPageNum));
				} else {
					mapInitFactory.setMsg("502", "参数值异常");
				}
			} else {
				mapInitFactory.setMsg("501", "参数有误");
			}
		} catch (Exception e) {
			mapInitFactory.setSystemError();
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $queryCountNumBySid(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		Integer tender_uid = (Integer) map.get("tender_uid");
		if (tender_uid != null) {
			if (tender_uid > 0) {
				mapInitFactory.init().setData(mapperTender.$queryCountNumBySid(tender_uid));
			} else {
				mapInitFactory.setMsg("502", "参数值异常");
			}
		} else {
			mapInitFactory.setMsg("501", "参数有误");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $queryCountNumByTid(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		try {
			Integer tender_tid = (Integer) map.get("tender_tid");
			mapInitFactory.init().setData(mapperTender.$queryCountNumByTid(tender_tid));
		} catch (Exception e) {
			mapInitFactory.setSystemError();
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $isExist(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		try {
			Integer tender_tid = (Integer) map.get("tender_tid");
			Integer tender_uid = (Integer) map.get("tender_uid");
			Integer isExist = mapperTender.$isExist(tender_tid, tender_uid);
			if (isExist != null) {
				mapInitFactory.init().setData(isExist);
			} else {
				mapInitFactory.init().setData(0);
			}
		} catch (Exception e) {
			mapInitFactory.setSystemError();
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $insert(YdMangerTender tender) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		Integer isExist = mapperTender.$isExist(tender.getTender_tid(), tender.getTender_uid());
		if (isExist < 1) {
			tender.setTender_create_time(new Date());
			tender.setTender_update_time(new Date());
			Integer success = mapperTender.$insert(tender);
			if (success > 0) {
				mapInitFactory.setMsg(Values.INITSUCCESSCODE, "投标成功");
			} else {
				mapInitFactory.setMsg("504", "投标失败");
			}
		} else {
			mapInitFactory.setMsg("503", "您对该任务已经进行过投标了");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $update(YdMangerTender tender) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		if (tender.getTender_id() != null && tender.getTender_id() > 0) {
			Integer tender_state = mapperTender.$queryState(tender.getTender_id());
			if (tender_state == 0) {
				tender.setTender_update_time(new Date());
				Integer success = mapperTender.$update(tender);
				if (success > 0) {
					mapInitFactory.setMsg(Values.INITSUCCESSCODE, "修改成功");
					MyStaticFactory.queryGuzhuTask = false;
				} else {
					mapInitFactory.setMsg("501", "修改失败，请稍后再试");
				}
			} else {
				mapInitFactory.setMsg("502", "当前投标状态的信息不能修改！");
			}
		} else {
			mapInitFactory.setMsg("503", "参数有误！");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $updateState(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		Integer tender_id = (Integer) map.get("tender_id");
		Integer tender_state = (Integer) map.get("tender_state");
		Integer success = mapperTender.$updateState(tender_id, tender_state, new Date());
		if (success > 0) {
			mapInitFactory.setMsg(Values.INITSUCCESSCODE, "操作成功");
		} else {
			mapInitFactory.setMsg("501", "操作失败");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $selection(Integer taskId, Integer tenderId, Integer type, Integer userEId, Integer userSId) {

		MapInitFactory mapInitFactory = new MapInitFactory().setSystemError();
		Date date = new Date();
		YdMangerTaskVo taskVo = mapperTask.$queryById(taskId);
		if (taskVo != null) {
			if (taskVo.getTask_lear_state() == 0) {
				// 修改服务商用户投标信息
				Integer success = mapperTender.$selection(tenderId, 0, 1, new Date());
				if (success > 0) {

					YdMangerContract contract = new YdMangerContract();
					YdMangerUserVo userVoE = mapperUser.$queryById(taskVo.getTask_uid());
					YdMangerTenderVo tenderVo = mapperTender.$queryById(tenderId);
					List<YdMangerDictionaryData> dictionaryDatas = mapperDictionaryData.$queryAllByValue(1028);

					contract.setContract_tid(taskId);
					contract.setContract_eid(userVoE.getUser_id());
					contract.setContract_eaddr(taskVo.getTask_paddr());
					contract.setContract_ename(userVoE.getUser_name());
					contract.setContract_ephone(userVoE.getUser_phone());
					contract.setContract_sid(tenderVo.getTender_uid());
					contract.setContract_sname(tenderVo.getTender_uname());
					contract.setContract_saddr(taskVo.getTask_paddr());
					contract.setContract_sphone(tenderVo.getTender_uphone());
					contract.setContract_pname(taskVo.getTask_pname());
					contract.setContract_pdesc(taskVo.getTask_discrip());
					contract.setContract_cp(dictionaryDatas.get(0).getDictdata_value());
					contract.setContract_sp(dictionaryDatas.get(1).getDictdata_value());
					contract.setContract_scsd(dictionaryDatas.get(2).getDictdata_value());
					contract.setContract_durat(dictionaryDatas.get(3).getDictdata_value());
					contract.setContract_pad(dictionaryDatas.get(4).getDictdata_value());
					contract.setContract_pbr(dictionaryDatas.get(5).getDictdata_value());
					contract.setContract_paresp(dictionaryDatas.get(6).getDictdata_value());
					contract.setContract_pbresp(dictionaryDatas.get(7).getDictdata_value());
					contract.setContract_pay(dictionaryDatas.get(8).getDictdata_value());
					contract.setContract_dal(dictionaryDatas.get(9).getDictdata_value());
					contract.setContract_ce(dictionaryDatas.get(10).getDictdata_value());

					String nowDate = (new SimpleDateFormat("yyyy-MM-dd").format(new Date())).toString();

					contract.setContract_esig(userVoE.getUser_name());
					contract.setContract_esigd(nowDate);
					contract.setContract_ssig(tenderVo.getTender_uname());
					contract.setContract_ssigd(nowDate);

					contract.setContract_create_time(date);
					contract.setContract_update_time(date);

					// 生成合同模板
					Integer contractSuccess = mapperContract.$insert(contract);

					if (contractSuccess > 0) {
						// 并设置任务信息中合同状态为1（合同已生成状态）
						Integer taskSuccess = mapperTask.$updateContractState(taskId, 1);
						if (taskSuccess > 0) {
							// 设置任务中是否选标(0未选，1已选)
							Integer taskLearSuccess = mapperTask.$updateTaskLearState(taskId, 1);
							if (taskLearSuccess > 0) {
								// 设置任务进入合同签订状态
								Integer taskStateSuccess = mapperTask.$updateTaskState(taskId, 3);
								if (taskStateSuccess > 0) {
									YdMangerTender tender = mapperTender.$queryById(tenderId);
									YdMangerMessage message = new YdMangerMessage(null, 0, taskVo.getTask_uid(),
											tender.getTender_uid(), "您投标的“" + taskVo.getTask_pname() + "”任务已中标！",
											taskId, 0, date);
									Integer mesSuccess = mapperMessage.$insert(message);
									if (mesSuccess > 0) {
										mapInitFactory.setMsg(Values.INITSUCCESSCODE, "操作成功");
									} else {
										mapInitFactory.setMsg("508", "操作失败！");
									}
								} else {
									mapInitFactory.setMsg("507", "操作失败，请稍检查后再试！");
								}
							} else {
								mapInitFactory.setMsg("506", "操作失败，请稍后再试！");
							}
						} else {
							mapInitFactory.setMsg("505", "操作失败，请稍后再试");
						}
					} else {
						mapInitFactory.setMsg("504", "操作失败，请稍后再试");
					}
				} else {
					mapInitFactory.setMsg("503", "选标失败，请及时联系客服人员！");
				}
			} else {
				mapInitFactory.setMsg("502", "您已经对任务进行过选标，无需再次操作！");
			}
		} else {
			mapInitFactory.setMsg("501", "操作失败,请稍后再试！");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $delete(Integer taskId, Integer userId) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		Integer success = mapperTender.$delete(taskId, userId);
		if (success != null && success > 0) {
			mapInitFactory.setMsg(Values.INITSUCCESSCODE, "撤销申请成功！");
		} else {
			mapInitFactory.setMsg("502", "撤销失败，请刷新后再试");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public YdMangerTenderVo queryTenderByTid(Integer taskId) {
		return mapperTender.$queryLearByTaskId(taskId);
	}

}
