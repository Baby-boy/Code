package com.yd.gcj.controller.page;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yd.gcj.entity.YdMangerSpcs;
import com.yd.gcj.entity.vo.YdMangerUserVo;
import com.yd.gcj.service.YdMangerServiceSpcs;
import com.yd.gcj.tool.MapInitFactory;
import com.yd.gcj.tool.ObjectMapperFactory;

@RestController
@RequestMapping(value = "/page/spcs", produces = { "application/json;charset=UTF-8" })
public class YdMangerControllerPageSpcs {
	@Autowired
	private YdMangerServiceSpcs ydMangerServiceSpcs;

	/**
	 * 查询指定服务商的评价信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/queryAll", method = RequestMethod.POST)
	public Object queryAll(HashMap<String, Object> map) {
		return ydMangerServiceSpcs.$queryAll(map);
	}

	/**
	 * 分页查询指定服务商的评价信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/queryAllByPageNum", method = RequestMethod.POST)
	public Object queryAllByPageNum(HashMap<String, Object> map) {
		return ydMangerServiceSpcs.$queryAllByPageNum(map);
	}

	/**
	 * 查询指定用户评价数量（主要用于分页）
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/queryCountNum", method = RequestMethod.POST)
	public Object queryCountNum(HashMap<String, Object> map) {
		return null;
	}

	/**
	 * 查询指定的服务商评价信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/queryById", method = RequestMethod.POST)
	public Object queryById(HashMap<String, Object> map) {
		return ydMangerServiceSpcs.$queryById(map);
	}

	/**
	 * 修改评价信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Object update(HashMap<String, Object> map) {
		try {
			return ydMangerServiceSpcs.$update(map);
		} catch (Exception e) {
			e.printStackTrace();
			return new MapInitFactory().setSystemError().getMap();
		}

	}

	/**
	 * 雇主对服务商评价
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/pcs", method = RequestMethod.POST)
	public Object pcs(Integer svaluation, String spcsReason, String sContents, Integer taskId,
			HttpServletRequest request) {
		try {
			YdMangerUserVo userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
			if (userVo != null && userVo.getUser_type() > 0) {
				YdMangerSpcs spcs = new YdMangerSpcs();
				spcs.setSpcs_grade(svaluation);
				spcs.setSpcs_reason(spcsReason);
				spcs.setSpcs_content(sContents);
				spcs.setSpcs_tid(taskId);
				spcs.setSpcs_sid(userVo.getUser_id());
				spcs.setSpcs_uname(userVo.getUser_name());
				ObjectMapperFactory.doIt(spcs);
				return ydMangerServiceSpcs.$insert(spcs);
			} else {
				return new MapInitFactory("600", "对不起，您没有操作权限！").getMap();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new MapInitFactory().setSystemError().getMap();
		}

	}

	/**
	 * 删除评价信息
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object delete(HashMap<String, Object> map) {
		try {
			return ydMangerServiceSpcs.$delete(map);
		} catch (Exception e) {
			e.printStackTrace();
			return new MapInitFactory().setSystemError().getMap();
		}
	}

}
