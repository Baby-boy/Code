package com.yd.gcj.controller.page;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yd.gcj.entity.YdMangerTender;
import com.yd.gcj.entity.vo.YdMangerUserVo;
import com.yd.gcj.service.YdMangerServiceTender;
import com.yd.gcj.service.YdMangerServiceUser;
import com.yd.gcj.tool.MapInitFactory;

@RestController
@RequestMapping(value = "/page/tender", produces = { "application/json;charset=UTF-8" })
public class YdMangerControllerPageTender {

	@Autowired
	private YdMangerServiceTender ydMangerServiceTender;

	@Autowired
	private YdMangerServiceUser serviceUser;

	/***
	 * 检查指定任务是否有该用户的投标信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/isExist", method = RequestMethod.POST)
	public Object isExist(@RequestBody HashMap<String, Object> map) {
		return ydMangerServiceTender.$isExist(map);
	}

	/**
	 * 添加投标信息（投标）
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/tender", method = RequestMethod.POST)
	public Object tender(String contents, Integer pNum, float price, Integer taskId, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			MapInitFactory mapInitFactory = new MapInitFactory();
			YdMangerUserVo userVo = (YdMangerUserVo) session.getAttribute("user");
			if (userVo != null) {
				if (userVo.getUser_type() > 0) {
					Integer verified = serviceUser.$queryVerifiedByUserId(userVo.getUser_id());
					userVo.setUser_verified(verified);
					session.setAttribute("user", userVo);
					if (verified == 1) {
						YdMangerTender tender = new YdMangerTender();
						tender.setTender_conts(contents);
						tender.setTender_pnum(pNum);
						tender.setTender_price(price);
						tender.setTender_tid(taskId);
						tender.setTender_type(0);

						tender.setTender_uid(userVo.getUser_id());
						tender.setTender_uemail(userVo.getUser_email());
						tender.setTender_uphone(userVo.getUser_phone());
						tender.setTender_ugnum(userVo.getUser_pcn());
						tender.setTender_ulevel(userVo.getUser_level());
						tender.setTender_uname(userVo.getUser_name());
						tender.setTender_utype(userVo.getUser_type());

						tender.setTender_state(0);
						// 如果此用户为企业认证设计师，补充企业名称信息
						if (userVo.getUser_type() == 2) {
							tender.setTender_uine(userVo.getUser_ename());
						}
						return ydMangerServiceTender.$insert(tender);
					} else if (userVo.getUser_verified() == 0) {
						mapInitFactory.setMsg("503", "您还未实名认证，实名认证后才可以申请任务！");
					} else if (userVo.getUser_verified() == 3) {
						mapInitFactory.setMsg("504", "您的实名认证还在审核中，实名认证通过后才能进行投标！");
					} else if (userVo.getUser_verified() == 2) {
						mapInitFactory.setMsg("503", "您的实名信息不符，重新提交实名认证信息审核通过后才能申请任务！");
					}
				} else {
					mapInitFactory.setMsg("502", "您没有权限操作此功能！").getMap();
				}
			} else {
				mapInitFactory.setMsg("501", "登录超时，请重新登录后再试！").getMap();
			}
			return mapInitFactory.getMap();
		} catch (Exception e) {
			e.printStackTrace();
			return new MapInitFactory().setSystemError().getMap();
		}
	}

	/**
	 * 选标
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/selection", method = RequestMethod.POST)
	public Object selection(Integer taskId, Integer tenderId, Integer type, Integer userSId,
			HttpServletRequest request) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
			return new MapInitFactory().setSystemError().getMap();
		}
		YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
		if (userVo != null && userVo.getUser_type() == 0) {
			return ydMangerServiceTender.$selection(taskId, tenderId, type, userVo.getUser_id(), userSId);
		} else {
			return new MapInitFactory().setMsg("503", "登录超时或者没有登录，请登录后再进行操作！").getMap();
		}
	}

	@RequestMapping(value = "/queryByIdAndUserMsg", method = RequestMethod.POST)
	public Object queryByIdAndUserMsg(Integer tenderId, HttpServletRequest request) {
		YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
		if (userVo != null) {
			if (userVo.getUser_type() != 0) {
				return ydMangerServiceTender.$queryByIdAndUserMsg(tenderId);
			} else {
				return new MapInitFactory("501", "登录过期或者没有登录，请登录后再进行操作！").getMap();
			}
		} else {
			return new MapInitFactory("600", "登录过期或者没有登录，请登录后再进行操作！").getMap();
		}
	}

	/**
	 * 根据指定任务，查询该任务的投标信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/queryByTid", method = RequestMethod.POST)
	public Object queryAllByTid(@RequestBody HashMap<String, Object> map) {
		return ydMangerServiceTender.$queryByTid(0, 0);
	}

	/**
	 * 查询指定任务中投标信息的数量
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/queryCountNumByTid", method = RequestMethod.POST)
	public Object queryCountNumByTid(@RequestBody HashMap<String, Object> map) {
		return ydMangerServiceTender.$queryCountNumByTid(map);
	}

	/***
	 * 查询任务中各个角色类型投标信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/queryByType", method = RequestMethod.POST)
	public Object queryByType(@RequestBody HashMap<String, Object> map) {
		return ydMangerServiceTender.$queryByType(map);
	}

	/**
	 * 查询该用户的投标信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/queryBySid", method = RequestMethod.POST)
	public Object queryBySid(@RequestBody HashMap<String, Object> map) {
		return ydMangerServiceTender.$queryBySid(map);
	}

	/***
	 * 查询指定用户的投标信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/queryCountNumBySid", method = RequestMethod.POST)
	public Object queryCountNumBySid(HashMap<String, Object> map) {
		return ydMangerServiceTender.$queryCountNumBySid(map);
	}

	/**
	 * 修改投标信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Object update(Integer tenderId, String contents, Integer pNum, float price, Integer taskId,
			HttpServletRequest request) {
		try {
			YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
			if (userVo != null) {
				if (userVo.getUser_type() > 0) {
					YdMangerTender tender = new YdMangerTender();
					tender.setTender_id(tenderId);
					tender.setTender_tid(taskId);
					tender.setTender_uid(userVo.getUser_id());

					tender.setTender_conts(contents);
					tender.setTender_pnum(pNum);
					tender.setTender_price(price);

					// 如果此用户为企业认证设计师，补充企业名称信息
					if (userVo.getUser_type() == 2) {
						tender.setTender_uine(userVo.getUser_ename());
					}
					return ydMangerServiceTender.$update(tender);
				} else {
					return new MapInitFactory().setMsg("505", "您没有权限操作此功能！").getMap();
				}
			} else {
				return new MapInitFactory().setMsg("504", "登录超时，请重新登录后再试！").getMap();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MapInitFactory().setSystemError().getMap();
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object delete(Integer taskId, HttpServletRequest request) {
		try {
			YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
			if (userVo != null) {
				return ydMangerServiceTender.$delete(taskId, userVo.getUser_id());
			} else {
				return new MapInitFactory().setMsg("501", "登录超时或者没有登陆，请登陆后再进行操作！").getMap();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MapInitFactory().setSystemError().getMap();
		}
	}

}
