package com.yd.gcj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.gcj.entity.YdMangerTPMsg;
import com.yd.gcj.mapper.YdMangerMapperTPMsg;
import com.yd.gcj.service.YdMangerServiceTPMsg;

@Service("YdMangerServiceTPMsg")
public class YdMangerServiceImplTPMsg implements YdMangerServiceTPMsg{
	
	@Autowired
	private YdMangerMapperTPMsg ydMangerMapperTPMsg;
	
	@Override
	public List<YdMangerTPMsg> $queryByTid(Integer taskmsg_tid) {
		return ydMangerMapperTPMsg.$queryByTid(taskmsg_tid);
	}

	@Override
	public YdMangerTPMsg $queryById(Integer taskmsg_id) {
		return ydMangerMapperTPMsg.$queryById(taskmsg_id);
	}

	@Override
	public Integer $insert(YdMangerTPMsg taskmsg) {
		return ydMangerMapperTPMsg.$insert(taskmsg);
	}

	@Override
	public Integer $update(YdMangerTPMsg taskmsg) {
		return ydMangerMapperTPMsg.$update(taskmsg);
	}

	@Override
	public Integer $delete(Integer taskmsg_id) {
		return ydMangerMapperTPMsg.$delete(taskmsg_id);
	}
	
}