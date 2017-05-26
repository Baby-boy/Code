package com.yd.gcj.service.impl.page;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.gcj.entity.vo.PageNum;
import com.yd.gcj.entity.vo.YdMangerUserVo;
import com.yd.gcj.mapper.YdMangerMapperUser;

@Service("serviceUsers")
public class YdMangerPageUser {
	@Autowired
	private YdMangerMapperUser mapperUser;
	
	@Autowired
	HttpServletRequest request;
	
	public Object $queryAlls() {
		HttpSession session = request.getSession();
		Integer countNum = mapperUser.$queryCountByS();
		PageNum pageNum = new PageNum(session,countNum,"pepolePageNum");
		YdMangerUserVo userVo = (YdMangerUserVo) session.getAttribute("user");
		List<YdMangerUserVo> userVos = new ArrayList<YdMangerUserVo>();
		if(userVo != null){
			userVos = mapperUser.$queryAllInLogin(userVo.getUser_id(),pageNum.getStartPageNum(),pageNum.getPageNum());
		}else{
			userVos = mapperUser.$queryAll(pageNum.getStartPageNum(), pageNum.getPageNum());
		}
		pageNum.setData(userVos);
		return pageNum;
	}
}
