package com.yd.gcj.service.impl.page;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.gcj.entity.YdMangerLabel;
import com.yd.gcj.entity.YdMangerTaskCondition;
import com.yd.gcj.entity.vo.YdMangerLabelVo;
import com.yd.gcj.mapper.YdMangerMapperLabel;
import com.yd.gcj.util.YdMangerLabelFactory;

@Service("pageLabel")
public class YdMangerServiceImplPageLabel {

	@Autowired
	private YdMangerMapperLabel ydMangerMapperTaskLabel;

	@Autowired
	private ServletContext context;

	@Autowired
	private HttpSession session;

	public List<YdMangerLabelVo> $queryAll() {

		@SuppressWarnings("unchecked")
		List<YdMangerLabelVo> taskLabelVos = (List<YdMangerLabelVo>) context.getAttribute("taskLabelVos");
		if (taskLabelVos == null) {
			List<YdMangerLabel> taskLabels = ydMangerMapperTaskLabel.$queryAll();
			taskLabelVos = YdMangerLabelFactory.getLabelVos(taskLabels);
			context.setAttribute("taskLabelVos", taskLabelVos);
		}
		YdMangerTaskCondition condition = (YdMangerTaskCondition) session.getAttribute("taskCondition");
		if (condition != null && condition.taskLabelIdList() != null) {
			for (YdMangerLabelVo labelVo : taskLabelVos) {
				labelVo.setIs_select(0);
				for (Integer id : condition.taskLabelIdList()) {
					if (labelVo.getLabel_id() == id) {
						labelVo.setIs_select(1);
					}
				}
				List<YdMangerLabel> labels = labelVo.getLabels();
				for (YdMangerLabel label : labels) {
					label.setIs_select(0);
					for (Integer id : condition.taskLabelIdList()) {
						if (label.getLabel_id() == id) {
							label.setIs_select(1);
						}
					}
				}
			}
		} else {
			for (YdMangerLabelVo labelVo : taskLabelVos) {
				labelVo.setIs_select(0);
				List<YdMangerLabel> labels = labelVo.getLabels();
				for (YdMangerLabel label : labels) {
					label.setIs_select(0);
				}
			}
		}
		return taskLabelVos;

	}

}