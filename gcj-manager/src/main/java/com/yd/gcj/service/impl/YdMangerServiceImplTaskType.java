package com.yd.gcj.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.gcj.entity.YdMangerTaskType;
import com.yd.gcj.mapper.YdMangerMapperTaskType;
import com.yd.gcj.service.YdMangerServiceTaskType;

@Service("YdMangerServiceTaskType")
public class YdMangerServiceImplTaskType implements YdMangerServiceTaskType {

	@Autowired
	private YdMangerMapperTaskType ydMangerMapperTaskType;

	@Autowired
	private ServletContext context;

	@Override
	public List<YdMangerTaskType> $queryAll() {
		@SuppressWarnings("unchecked")
		List<YdMangerTaskType> taskTypes = (List<YdMangerTaskType>) context.getAttribute("taskTypes");
		if (taskTypes == null) {
			taskTypes = ydMangerMapperTaskType.$queryAll();
			context.setAttribute("taskTypes", taskTypes);
		}
		return taskTypes;
	}

	@Override
	public YdMangerTaskType $queryById(HashMap<String, Object> map) {
		// ydMangerMapperTaskType.$queryById(map);
		return null;
	}

	@Override
	public Integer $insert(HashMap<String, Object> map) {
		// ydMangerMapperTaskType.$insert(map);
		return null;
	}

	@Override
	public Integer $update(HashMap<String, Object> map) {
		// ydMangerMapperTaskType.$update(map);
		return null;
	}

	@Override
	public Integer $delete(HashMap<String, Object> map) {
		// ydMangerMapperTaskType.$delete(map);
		return null;
	}

}
