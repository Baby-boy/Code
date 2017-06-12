package com.yd.gcj.controller.page;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yd.gcj.entity.YdMangerPhoneCode;
import com.yd.gcj.entity.vo.YdMangerUserVo;
import com.yd.gcj.service.YdMangerServiceContract;
import com.yd.gcj.service.YdMangerServiceTask;
import com.yd.gcj.service.YdMangerServiceTaskPM;
import com.yd.gcj.tool.MapInitFactory;

@RestController
@RequestMapping(value = "/page/contract", produces = { "application/json;charset=UTF-8" })
public class YdMangerControllerPageContract {

	@Autowired
	private YdMangerServiceContract serviceContract;
	
	@Autowired
	private YdMangerServiceTaskPM serviceTaskPM;
	
	@Autowired
	private YdMangerServiceTask serviceTask;
	
	/**
	 * 项目托管佣金
	 * @Description: 
	 * @param desc
	 * @param price
	 * @param phone
	 * @param phoneCode
	 * @param payPwd
	 * @param priceNow
	 * @return    
	 * @date: 2017年3月8日 下午2:48:13
	 */
	@RequestMapping("tg")
	public Object tg(Integer taskId,Integer userId,String desc, float price, String phone, String phoneCode, String payPwd, HttpServletRequest request) {
		MapInitFactory mf = new MapInitFactory();
		try {
			YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
			if(userVo != null && phone.equals(userVo.getUser_phone())){
				YdMangerPhoneCode pc = (YdMangerPhoneCode) request.getSession().getAttribute("tgPhoneCode");
				if(pc != null && pc.getCode().equals(phoneCode) && pc.getPhone().equals(phone)){
					Object obj = serviceTaskPM.tg(userVo, taskId, desc, price, phone, payPwd);
					request.getSession().getAttribute("user");
					return obj;
				}else{
					mf.setMsg("503", "验证码错误！");
				}
			}else{
				mf.setMsg("502", "请登录！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mf.setSystemError();
		}
		return mf.getMap();
	}
	
	/***
	 * 保存任务中签订合同的文件
	 * @param taskId
	 * @param path
	 * @param fileName
	 * @param request
	 * @return
	 */
	@RequestMapping("saveContractFile")
	public Object saveContractFile(Integer taskId,String path,String fileName,HttpServletRequest request){
		MapInitFactory mf = new MapInitFactory();
		try {
			YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
			if(userVo != null){
				return 	serviceContract.saveContractFile(taskId, path, fileName);
			}else{
				mf.setMsg("600", "请登录后再操作！");
			}
		} catch (Exception e) {
			mf.setSystemError();
			e.printStackTrace();
		}
		return mf.getMap();
	}
	
	/***
	 * 合同补充
	 * 
	 * @param contract_id
	 * @param contract_supp
	 * @return
	 */
	@RequestMapping("/supplementaryContract")
	@ResponseBody
	public Object supplementaryContract(Integer contract_id, String contract_supp) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Integer updateNum = serviceContract.supplementaryContract(contract_id, contract_supp);
			if (updateNum > 0) {
				map.put("msg", true);
			} else {
				map.put("msg", false);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return new MapInitFactory().setSystemError().getMap();
		}

	}
}