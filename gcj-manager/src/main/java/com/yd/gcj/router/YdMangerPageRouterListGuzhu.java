package com.yd.gcj.router;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yd.gcj.entity.YdMangerAttention;
import com.yd.gcj.entity.YdMangerBankTR;
import com.yd.gcj.entity.YdMangerContract;
import com.yd.gcj.entity.YdMangerFiles;
import com.yd.gcj.entity.YdMangerSpcs;
import com.yd.gcj.entity.YdMangerTaskModel;
import com.yd.gcj.entity.YdMangerTaskMsg;
import com.yd.gcj.entity.YdMangerTaskPM;
import com.yd.gcj.entity.YdMangerVerified;
import com.yd.gcj.entity.vo.PageNum;
import com.yd.gcj.entity.vo.TaskVoNums;
import com.yd.gcj.entity.vo.YdMangerMessageVo;
import com.yd.gcj.entity.vo.YdMangerTaskFolderVo;
import com.yd.gcj.entity.vo.YdMangerTaskVo;
import com.yd.gcj.entity.vo.YdMangerTenderVo;
import com.yd.gcj.entity.vo.YdMangerUserVo;
import com.yd.gcj.service.YdMangerServiceAttention;
import com.yd.gcj.service.YdMangerServiceBankTR;
import com.yd.gcj.service.YdMangerServiceContract;
import com.yd.gcj.service.YdMangerServiceFiles;
import com.yd.gcj.service.YdMangerServiceMessage;
import com.yd.gcj.service.YdMangerServiceSpcs;
import com.yd.gcj.service.YdMangerServiceTask;
import com.yd.gcj.service.YdMangerServiceTaskFolder;
import com.yd.gcj.service.YdMangerServiceTaskModel;
import com.yd.gcj.service.YdMangerServiceTaskMsg;
import com.yd.gcj.service.YdMangerServiceTaskPM;
import com.yd.gcj.service.YdMangerServiceTender;
import com.yd.gcj.service.YdMangerServiceUser;
import com.yd.gcj.service.YdMangerServiceVerified;

@Controller
@RequestMapping("/list-guzhu")
public class YdMangerPageRouterListGuzhu {

	private static final String pageFiles = "list-guzhu/";

	@Autowired
	private YdMangerServiceTask serviceTask;

	@Autowired
	private YdMangerServiceAttention serviceAttention;

	@Autowired
	private YdMangerServiceTender serviceTender;

	@Autowired
	private YdMangerServiceTaskModel serviceTaskModel;

	@Autowired
	private YdMangerServiceTaskPM serviceTaskPM;

	@Autowired
	private YdMangerServiceMessage serviceMessage;

	@Autowired
	private YdMangerServiceSpcs serviceSpcs;

	@Autowired
	private YdMangerServiceFiles serviceFiles;

	@Autowired
	private YdMangerServiceContract serviceContract;

	@Autowired
	private YdMangerServiceBankTR ydMangerServiceBankTR;

	@Autowired
	private YdMangerServiceVerified serviceVerified;

	@Autowired
	private YdMangerServiceUser serviceUser;
	
	@Autowired
	private YdMangerServiceTaskFolder serviceTaskFolder;
	
	@Autowired
	private YdMangerServiceTaskMsg serviceTaskMsg;
	
	private YdMangerUserVo userVo = null;

	@RequestMapping("/guzhu-daipingjia")
	public String guzhuDaipingjia(HttpSession session, Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerTaskVo> taskVos = serviceTask.$queryByEId(userVo.getUser_id(), 3);
			float price = serviceUser.$queryUserPrice(userVo.getUser_id());
			userVo.setUser_cprice(price);
			request.getSession().setAttribute("user", userVo);
			model.addAttribute("taskVos", taskVos);
		}
		return isLogin("guzhu-daipingjia", request);
	}

	@RequestMapping("/guzhu-daiqianding")
	public String guzhuDaiqianding(HttpSession session, Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerTaskVo> taskVos = serviceTask.$queryByEId(userVo.getUser_id(), 1);
			model.addAttribute("taskVos", taskVos);
		}
		return isLogin("guzhu-daiqianding", request);
	}

	@RequestMapping("/guzhu-dianping")
	public String guzhuDianping(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerSpcs> spcs = serviceSpcs.$queryByUserId(userVo.getUser_id());
			model.addAttribute("spcs", spcs);
		}
		return isLogin("guzhu-dianping", request);
	}

	@RequestMapping("/guzhu-fukuan")
	public String guzhuFukuan(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			PageNum pageNum = new PageNum();
			Integer dataCountNum = ydMangerServiceBankTR.$queryCountNum(userVo.getUser_id());
			pageNum = new PageNum(request.getSession(),dataCountNum,"banktrPageNum");
			List<YdMangerBankTR> bankTRs = ydMangerServiceBankTR.$queryAll(userVo.getUser_id(),pageNum.getStartPageNum(),pageNum.getPageNum());
			pageNum.setData(bankTRs);
			model.addAttribute("pageNum", pageNum);
		}
		return isLogin("guzhu-fukuan", request);
	}

	@RequestMapping("/guzhu-gongzuoshi/{taskId}")
	public String guzhuGongzuoshi(@PathVariable Integer taskId, Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerTenderVo> tenderVos = serviceTender.$queryByTid(taskId, 1);
			model.addAttribute("tenderVos", tenderVos);
			model.addAttribute("taskId", taskId);
		}
		return isLogin("guzhu-gongzuoshi", request);
	}

	@RequestMapping("/guzhu-gongzuotai/{taskId}")
	public String guzhuGongzuotai(@PathVariable Integer taskId, Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			YdMangerTaskVo taskVo = serviceTask.$queryById(taskId, userVo.getUser_id());
			
			List<YdMangerTaskModel> taskModels = serviceTaskModel.$queryByTid(taskId);
			List<YdMangerTaskPM> taskPMs = serviceTaskPM.$queryByTid(taskId);
			List<YdMangerTaskMsg> taskMsgs = serviceTaskMsg.$queryByTid(taskId);
			List<YdMangerFiles> files = serviceFiles.$queryAllByTaskId(taskId);
			List<YdMangerTaskFolderVo> folderVos = serviceTaskFolder.queryFolderByTaskId(taskId);
			
			model.addAttribute("taskDetail", taskVo);
			model.addAttribute("tasdId", taskId);
			model.addAttribute("taskModels", taskModels);
			model.addAttribute("taskPM", taskPMs);
			model.addAttribute("taskMsgs", taskMsgs);
			model.addAttribute("folders", folderVos);
			model.addAttribute("files", files);
			
		}
		return isLogin("guzhu-gongzuotai", request);
	}

	@RequestMapping("/guzhu-guanzhu-xq/{tenderId}")
	public String guzhuGuanzhuXq(@PathVariable Integer tenderId, Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			if (userVo.getUser_type() == 0) {
				YdMangerTenderVo tenderVo = serviceTender.$queryByIdAndUserMsg(tenderId);
				model.addAttribute("tenderVo", tenderVo);
			} else {
				request.getSession().setAttribute("user", null);
			}
		}
		return isLogin("guzhu-guanzhu-xq", request);
	}

	@RequestMapping("/guzhu-guanzhu")
	public String guzhuGuanzhu(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerAttention> attentions = serviceAttention.$queryAll(userVo.getUser_id());
			model.addAttribute("attentions", attentions);
		}
		return isLogin("guzhu-guanzhu", "index", request);
	}

	@RequestMapping("/hetong/{taskId}")
	public String guzhuHetong(@PathVariable Integer taskId, Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			String result = "guzhu-hetongweiqian";
			YdMangerTaskVo taskVo = serviceTask.$queryById(taskId, userVo.getUser_id());
			if (taskVo != null) {
				Integer state = taskVo.getTask_state();
				Integer cons = taskVo.getTask_contract_state();
				if (state == 3) {
					switch (cons) {
					case 0:
					case 2:
					case 4:
						result = "guzhu-hetongweiqian";
						break;
					case 1:
						YdMangerTenderVo tenderVo = serviceTender.queryTenderByTid(taskId);
						
						Date date = new Date();
						model.addAttribute("date", date);
						model.addAttribute("taskId", taskId);
						model.addAttribute("taskVo", taskVo);
						model.addAttribute("tenderVo", tenderVo);
						result = "guzhu-hetongbianji";
						break;
					case 3:
						YdMangerContract contract1 = serviceContract.$queryByTaskId(taskId);
						List<YdMangerTaskPM> taskPMs1 = serviceTaskPM.$queryByTid(taskId);
						if (contract1 == null) {
							contract1 = new YdMangerContract();
						}
						model.addAttribute("contract", contract1);
						model.addAttribute("taskPM", taskPMs1);
						result = "guzhu-hetongyiqian";
						break;
					}
				} else if (state > 3) {
					YdMangerContract contract1 = serviceContract.$queryByTaskId(taskId);
					List<YdMangerTaskPM> taskPMs1 = serviceTaskPM.$queryByTid(taskId);
					if (contract1 == null) {
						contract1 = new YdMangerContract();
					}
					model.addAttribute("contract", contract1);
					model.addAttribute("taskPM", taskPMs1);
					result = "guzhu-hetongyiqian";
				}
			}
			model.addAttribute("taskId", taskId);
			model.addAttribute("taskVo", taskVo);
			return pageFiles + result;
		} else {
			return "list-login/login-guzhu";
		}

	}

	@RequestMapping("/guzhu-hetongbianji/{taskId}")
	public String guzhuHetongbianji(@PathVariable Integer taskId, Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			int isTask = serviceTask.$isTask(taskId, userVo.getUser_id(), userVo.getUser_type());
			if(isTask == 1){
				YdMangerTaskVo taskVo = serviceTask.$queryById(taskId, userVo.getUser_id());
				YdMangerTenderVo tenderVo = serviceTender.queryTenderByTid(taskId);
				
				Date date = new Date();
				model.addAttribute("date", date);
				model.addAttribute("taskId", taskId);
				model.addAttribute("taskVo", taskVo);
				model.addAttribute("tenderVo", tenderVo);
			}else{
				return "list-login/login-guzhu";
			}
		}
		return isLogin("guzhu-hetongbianji", request);
	}

	@RequestMapping("/guzhu-renwuxiugai/{taskId}")
	public String guzhuRenwuxiugai(@PathVariable Integer taskId, Model model, HttpServletRequest request) {
		if (userVo != null) {
			YdMangerTaskVo taskVo = serviceTask.$queryByIdToUpdate(taskId);
			model.addAttribute("task", taskVo);
			List<YdMangerFiles> files = serviceFiles.$queryAllByTaskId(taskId);
			model.addAttribute("files", files);
		}
		return isLogin("guzhu-renwuxiugai", request);
	}

	@RequestMapping("/guzhu-tuikuan")
	public String guzhuTuikuan(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerTaskVo> taskVos = serviceTask.$queryByEId(userVo.getUser_id(), 5);
			model.addAttribute("taskVos", taskVos);
		}
		return isLogin("guzhu-tuikuan", request);
	}

	@RequestMapping("/guzhu-working")
	public String guzhuWorking(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerTaskVo> taskVos = serviceTask.$queryByEId(userVo.getUser_id(), 2);
			model.addAttribute("taskVos", taskVos);
			TaskVoNums nums = serviceTask.$queryByTask(0, userVo.getUser_id());
			request.getSession().setAttribute("nums", nums);
		}
		return isLogin("guzhu-working", request);
	}

	@RequestMapping("/guzhu-xiaoxihuifu")
	public String guzhuXiaoxihuifu(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerMessageVo> msg = serviceMessage.$queryByUserIdAndByPageNum(userVo.getUser_id(), 0, 0);
			model.addAttribute("msg", msg);
		}
		return isLogin("guzhu-xiaoxihuifu", request);
	}

	@RequestMapping("/guzhu-xiaoxihuifu-task")
	public String guzhuXiaoxihuifuTask(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerMessageVo> msg = serviceMessage.$queryByUserIdAndByPageNum(userVo.getUser_id(), 1, 0);
			model.addAttribute("msg", msg);
		}
		return isLogin("guzhu-xiaoxihuifu-task", request);
	}

	@RequestMapping("/guzhu-xiaoxihuifu-msg")
	public String guzhuXiaoxihuifuMsg(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerMessageVo> msg = serviceMessage.$queryByUserIdAndByPageNum(userVo.getUser_id(), 2, 0);
			model.addAttribute("msg", msg);
		}
		return isLogin("guzhu-xiaoxihuifu-msg", request);
	}

	@RequestMapping("/guzhu-xiugaiziliao")
	public String guzhuXiugaiziliao(HttpServletRequest request) {
		return isLogin("guzhu-xiugaiziliao", request);
	}

	@RequestMapping("/guzhu-xuanzerencai/{taskId}")
	public String guzhuXuanzerencai(@PathVariable Integer taskId, Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerTenderVo> tenderVos = serviceTender.$queryByTid(taskId, 2);
			model.addAttribute("tenderVos", tenderVos);
			model.addAttribute("taskId", taskId);
			request.getSession().setAttribute("deftaskId", taskId);
		}
		return isLogin("guzhu-xuanzerencai", request);
	}

	@RequestMapping("/guzhu-xuqiumiaoshu/{taskId}")
	public String guzhuXuqiumiaoshu(@PathVariable Integer taskId, Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			YdMangerTaskVo taskVo = serviceTask.$queryById(taskId, userVo.getUser_id());
			model.addAttribute("task", taskVo);
			List<YdMangerFiles> files = serviceFiles.$queryAllByTaskId(taskId);
			model.addAttribute("files", files);
		}
		return isLogin("guzhu-xuqiumiaoshu", request);
	}

	@RequestMapping("/guzhu-yiwancheng")
	public String guzhuYiwancheng(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerTaskVo> taskVos = serviceTask.$queryByEId(userVo.getUser_id(), 4);
			model.addAttribute("taskVos", taskVos);
		}
		return isLogin("guzhu-yiwancheng", request);
	}

	@RequestMapping("/guzhufang")
	public String guzhufang(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerTaskVo> taskVos = serviceTask.$queryByEId(userVo.getUser_id(), 0);
			model.addAttribute("taskVos", taskVos);
			TaskVoNums nums = serviceTask.$queryByTask(0, userVo.getUser_id());
			request.getSession().setAttribute("nums", nums);
		}
		return isLogin("guzhufang", request);
	}

	@RequestMapping("/woderenzheng")
	public String woderenzheng(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			Integer vId = 0;
			if (userVo.getUser_verified() == 1 || userVo.getUser_verified() == 3) {
				YdMangerVerified verified = serviceVerified.queryByUserId(userVo.getUser_id());
				vId = verified.getV_id();
				model.addAttribute("verified", verified);

			} else {
				vId = serviceVerified.queryIdByUserId(userVo.getUser_id());
			}
			Integer isV = serviceUser.$queryVerifiedByUserId(userVo.getUser_id());
			userVo.setUser_verified(isV);
			request.getSession().setAttribute("user", userVo);
			model.addAttribute("vId", vId);
		}
		return isLogin("woderenzheng-shiming", request);
	}

	private String isLogin(String path, String defualPath, HttpServletRequest request) {
		if (isLogin(request)) {
			return pageFiles + path;
		} else {
			return defualPath;
		}
	}

	private String isLogin(String path, HttpServletRequest request) {
		if (isLogin(request)) {
			return pageFiles + path;
		} else {
			return "list-login/login-guzhu";
		}
	}

	private boolean isLogin(HttpServletRequest request) {
		userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
		return userVo != null && userVo.getUser_type() == 0 ? true : false;
	}
}
