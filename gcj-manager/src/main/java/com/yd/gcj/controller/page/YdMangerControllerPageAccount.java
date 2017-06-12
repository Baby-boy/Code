package com.yd.gcj.controller.page;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yd.gcj.entity.vo.YdMangerUserVo;
import com.yd.gcj.service.YdMangerServiceAccount;
import com.yd.gcj.tool.MapInitFactory;

@RestController
@RequestMapping(value = "/page/account", produces = { "application/json;charset=UTF-8" })
public class YdMangerControllerPageAccount {

	@Autowired
	private YdMangerServiceAccount serviceAccount;

	@RequestMapping(value = "/zfbRecharge", method = RequestMethod.POST)
	public Object recharge(Integer userId, float price, Integer type, HttpServletRequest request) {
		YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
		if (userVo != null) {
			if (userVo.getUser_id() == userId) {
				if (type == 1) {
					/* return serviceAccount.sign(userId,price); */
					return new MapInitFactory("200", "成功").setData("123456789").getMap();
				} else {
					return new MapInitFactory("502", "还没有其他支付方式！").getMap();
				}
			} else {
				return new MapInitFactory("501", "参数异常！").getMap();
			}
		} else {
			return new MapInitFactory("600", "登录超时或者没有登录，请登录后再进行操作！").getMap();
		}
	}

	@RequestMapping(value = "/zfbPayNotive", method = RequestMethod.POST)
	public Object payNotive(Integer userId, Integer price) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		try {
			return serviceAccount.$zfbNotive(userId, price, 1);
		} catch (Exception e) {
			mapInitFactory.setSystemError();
		}
		return mapInitFactory.getMap();
	}
}