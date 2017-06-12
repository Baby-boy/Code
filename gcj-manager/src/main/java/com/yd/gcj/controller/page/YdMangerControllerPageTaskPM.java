package com.yd.gcj.controller.page;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yd.gcj.entity.YdMangerPhoneCode;
import com.yd.gcj.entity.YdMangerTaskPM;
import com.yd.gcj.entity.vo.YdMangerUserVo;
import com.yd.gcj.service.YdMangerServiceTaskPM;
import com.yd.gcj.tool.MapInitFactory;

@RestController
@RequestMapping(value = "/page/taskPM", produces = { "application/json;charset=UTF-8" })
public class YdMangerControllerPageTaskPM {

	@Autowired
	private YdMangerServiceTaskPM serviceTaskPM;

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public Object insert(Integer taskId, String name, float price, String description, HttpServletRequest request) {
		try {
			YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
			if (userVo != null) {
				YdMangerTaskPM taskPM = new YdMangerTaskPM();
				taskPM.setTpm_taskid(taskId);
				taskPM.setTpm_name(name);
				taskPM.setTpm_money(price);
				taskPM.setTpm_state(0);
				taskPM.setTpm_desc(description);
				return serviceTaskPM.$insert(taskPM);
			} else {
				return new MapInitFactory("600", "登录超时或者没有登录，请登录后再进行操作！").getMap();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MapInitFactory().setSystemError().getMap();
		}
	}

	/***
	 * 服务商提交付款申请
	 * 
	 * @return
	 */
	@RequestMapping("/subPayAppli")
	public Object sPMpayApplic(Integer userId,Integer taskId,Integer tpmId, HttpServletRequest request) {
		MapInitFactory mf = new MapInitFactory();
		try {
			YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
			if (userVo != null && userVo.getUser_id() == userId) {
				Integer state = serviceTaskPM.$queryStateById(tpmId);
				if(state == 2){
					int success = serviceTaskPM.$updateState(taskId,tpmId, 4,userVo.getUser_id());
					if(success > 0){
						mf.setMsg("200", "申请提交成功！");
					}else{
						mf.setMsg("501", "申请失败！");
					}
				}else{
					mf.setMsg("502", "申请失败！");
				}
			} else {
				mf.setMsg("600", "请登录后再操作此功能！");
			}
		} catch (Exception e) {
			mf.setSystemError();
			e.printStackTrace();
		}
		return mf.getMap();
	}
	
	/***
	 * 服务商申请雇主托管资金（付款跟进）
	 * @param userId
	 * @param taskId
	 * @param tpmId
	 * @return
	 */
	@RequestMapping("/hostingApplication")
	public Object hostingApplication(Integer userId,Integer taskId,Integer tpmId,HttpServletRequest request){
		MapInitFactory mf = new MapInitFactory();
		try {
			YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
			if (userVo != null && userVo.getUser_id() == userId) {
				Integer state = serviceTaskPM.$queryStateById(tpmId);
				if(state == 0){
					int success = serviceTaskPM.$updateState(taskId,tpmId, 1,userVo.getUser_id());
					if(success > 0){
						mf.setMsg("200", "申请提交成功！");
					}else{
						mf.setMsg("501", "申请失败！");
					}
				}else{
					mf.setMsg("502", "申请失败！");
				}
			} else {
				mf.setMsg("600", "请登录后再操作此功能！");
			}
		} catch (Exception e) {
			mf.setSystemError();
			e.printStackTrace();
		}
		return mf.getMap();
	}
	
	/***
	 * 雇主拒绝托管（付款跟进）
	 * @param userId
	 * @param taskId
	 * @param tpmId
	 * @return
	 */
	@RequestMapping("/refuseHost")
	public Object refuseHost(Integer userId,Integer taskId,Integer tpmId,String reason,HttpServletRequest request){
		MapInitFactory mf = new MapInitFactory();
		try{
			YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
			if(userVo != null && userVo.getUser_id() == userId && userVo.getUser_type() == 0){
				int state = serviceTaskPM.$queryStateById(tpmId);
				if(state == 1){
					int success = serviceTaskPM.$updateStateAndReason(taskId, tpmId, 3, reason);
					if(success > 0){
						mf.setMsg("200", "已拒绝托管节点佣金到平台！");
					}else{
						mf.setMsg("502", "操作失败！");
					}
				}else{
					mf.setMsg("501", "操作失败！");
				}
			}else{
				mf.setMsg("600", "请登录！");
			}
		}catch (Exception e) {
			mf.setSystemError();
			e.printStackTrace();
		}
		return mf.getMap();
	}
	
	/***
	 * 雇主拒绝支付款项到服务商账户（付款跟进）
	 * @param userId
	 * @param taskId
	 * @param tpmId
	 * @return
	 */
	@RequestMapping("/refusePay")
	public Object refusePay(Integer userId,Integer taskId,Integer tpmId,String reason,HttpServletRequest request){
		MapInitFactory mf = new MapInitFactory();
		try{
			YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
			if(userVo != null && userVo.getUser_id() == userId && userVo.getUser_type() == 0){
				int state = serviceTaskPM.$queryStateById(tpmId);
				if(state == 4){
					int success = serviceTaskPM.$updateStateAndReason(taskId, tpmId, 6, reason);
					if(success > 0){
						mf.setMsg("200", "已拒绝托管节点佣金到平台！");
					}else{
						mf.setMsg("502", "操作失败！");
					}
				}else{
					mf.setMsg("501", "操作失败！");
				}
			}else{
				mf.setMsg("600", "请登录！");
			}
		}catch (Exception e) {
			mf.setSystemError();
			e.printStackTrace();
		}
		return mf.getMap();
	}
	
	/***
	 * 雇主确认托管资金（付款跟进）
	 * @param taskId
	 * @param userId
	 * @param tpmId
	 * @param desc
	 * @param price
	 * @param phone
	 * @param phoneCode
	 * @param payPwd
	 * @param request
	 * @return
	 */
	@RequestMapping("/sureHost")
	public Object sureHost(Integer taskId,Integer userId,Integer tpmId,String phone, String phoneCode, String payPwd,HttpServletRequest request){
		MapInitFactory mf = new MapInitFactory();
		try{
			YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
			if(userVo != null && userVo.getUser_id() == userId && userVo.getUser_type() == 0){
				YdMangerPhoneCode pc = (YdMangerPhoneCode) request.getSession().getAttribute("tgpc");
				if(pc != null && pc.getCode().equals(phoneCode) && pc.getPhone().equals(phone)){
					Object obj = serviceTaskPM.updateTg(userVo, taskId, tpmId, phone, payPwd);
					request.getSession().getAttribute("user");
					return obj;
				}else{
					mf.setMsg("503", "验证码错误！");
				}
				
			}else{
				mf.setMsg("600", "请登录！");
			}
		}catch (Exception e) {
			mf.setSystemError();
			e.printStackTrace();
		}
		return mf.getMap();
	}
	
	/***
	 * 雇主确认支付节点佣金到服务商用户账户
	 * @param taskId
	 * @param userId
	 * @param tpmId
	 * @param phone
	 * @param phoneCode
	 * @param payPwd
	 * @return
	 */
	@RequestMapping("/surePay")
	public Object surePay(Integer taskId,Integer userId,Integer tpmId, String phone,String phoneCode,String payPwd,HttpServletRequest request){
		MapInitFactory mf = new MapInitFactory();
		try{
			YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
			if(userVo != null && userVo.getUser_id() == userId && userVo.getUser_type() == 0){
				YdMangerPhoneCode pc = (YdMangerPhoneCode) request.getSession().getAttribute("paypc");
				if(pc != null && pc.getCode().equals(phoneCode) && pc.getPhone().equals(phone)){
					Object obj = serviceTaskPM.surePay(userVo, taskId, tpmId, phone, payPwd);
					request.getSession().getAttribute("user");
					return obj;
				}else{
					mf.setMsg("503", "验证码错误！");
				}
				
			}else{
				mf.setMsg("600", "请登录！");
			}
		}catch (Exception e) {
			mf.setSystemError();
			e.printStackTrace();
		}
		return mf.getMap();
	}
	
/*	@RequestMapping("/del")
	public Object del(Integer tpmId) {
		try {
			return serviceTaskPM.$delete(tpmId);
		} catch (Exception e) {
			e.printStackTrace();
			return new MapInitFactory().setSystemError().getMap();
		}
	}*/

}