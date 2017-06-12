package com.yd.gcj.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yd.gcj.entity.YdMangerSystemAdmin;
import com.yd.gcj.system.service.YdMangerServiceSystemAdmin;

/**
 * description(管理员登录管理)
 * 
 * @author Administrator
 * @param <HttpServletRequest>
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/system")
public class YdMangerControllerSystemLogin {

	@Autowired
	private YdMangerServiceSystemAdmin ydMangerServiceSystemAdmin;

	/**
	 * description(管理员登陆)
	 * 
	 * @param
	 * @param admin_account
	 * @param admin_password
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Object login(String admin_account, String admin_password, String code, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String codeLogin = (String) request.getSession().getAttribute("code");
		if (code != null && code.length() != 0) {
			if (code.equalsIgnoreCase(codeLogin)) {
				if (admin_account != null && admin_password != null) {
					YdMangerSystemAdmin systemAdmin = ydMangerServiceSystemAdmin
							.queryAdminByUserAccountAndUserPassword(admin_account, admin_password);
					if (systemAdmin == null) {
						// 账号和密码有误
						map.put("msg", 300);
					} else {
						// 登录成功
						// 修改登录次数
						systemAdmin.setAdmin_login_num(systemAdmin.getAdmin_login_num() + 1);
						Integer updateNum = ydMangerServiceSystemAdmin.updateAdminByAdminId(systemAdmin);
						request.getSession().setAttribute("admin", systemAdmin);
						map.put("msg", 200);
					}
				} else {
					// 账号和密码不能为空
					map.put("msg", 100);
				}
			} else {
				// 两次输入的验证码不一致
				map.put("msg", 500);
			}
		} else {
			// 验证码不能为空
			map.put("msg", 400);
		}
		return map;
	}

	/**
	 * description()
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/outLogin")
	public String outLogin(HttpServletRequest request) {
		request.getSession().removeAttribute("admin");
		return "system/login";
	}
}