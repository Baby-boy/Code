package com.yd.gcj.service.impl.page;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.gcj.entity.YdMangerTaskCondition;
import com.yd.gcj.entity.YdMangerTaskType;
import com.yd.gcj.mapper.YdMangerMapperTaskType;

@Service("pageTaskType")
public class YdMangerServiceImplPageTaskType {

	@Autowired
	private YdMangerMapperTaskType ydMangerMapperTaskType;

	@Autowired
	private ServletContext context;
	
	@Autowired
	HttpServletRequest request;
	
	public List<YdMangerTaskType> $queryAll() {
		@SuppressWarnings("unchecked")
		List<YdMangerTaskType> taskTypes = (List<YdMangerTaskType>) context.getAttribute("taskTypes");
		if (taskTypes == null) {
			taskTypes = ydMangerMapperTaskType.$queryAll();
			context.setAttribute("taskTypes", taskTypes);
		}

		YdMangerTaskCondition condition = (YdMangerTaskCondition) request.getSession().getAttribute("taskCondition");
		if (condition != null) {
			for (YdMangerTaskType taskType : taskTypes) {
				taskType.setIs_select(0);
				if (condition.taskTypeIdList() != null) {
					for (Integer id : condition.taskTypeIdList()) {
						if (taskType.getTaskt_id() == id) {
							taskType.setIs_select(1);
						}
					}
				}
			}
		}
		return taskTypes;
	}

}
