package com.yd.gcj.router;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yd.gcj.entity.YdMangerAttention;
import com.yd.gcj.entity.YdMangerBankTR;
import com.yd.gcj.entity.YdMangerCollection;
import com.yd.gcj.entity.YdMangerContract;
import com.yd.gcj.entity.YdMangerEpcs;
import com.yd.gcj.entity.YdMangerFiles;
import com.yd.gcj.entity.YdMangerTaskModel;
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
import com.yd.gcj.service.YdMangerServiceCollection;
import com.yd.gcj.service.YdMangerServiceContract;
import com.yd.gcj.service.YdMangerServiceEpcs;
import com.yd.gcj.service.YdMangerServiceFiles;
import com.yd.gcj.service.YdMangerServiceMessage;
import com.yd.gcj.service.YdMangerServiceTask;
import com.yd.gcj.service.YdMangerServiceTaskFolder;
import com.yd.gcj.service.YdMangerServiceTaskModel;
import com.yd.gcj.service.YdMangerServiceTaskPM;
import com.yd.gcj.service.YdMangerServiceTender;
import com.yd.gcj.service.YdMangerServiceUser;
import com.yd.gcj.service.YdMangerServiceUserIsAutoLogin;
import com.yd.gcj.service.YdMangerServiceUserTeamFile;
import com.yd.gcj.service.YdMangerServiceVerified;

@Controller
@RequestMapping("/list-fuwushang")
public class YdMangerPageRouterListFuwushang {

	private static final String pageFiles = "list-fuwushang/";

	@Autowired
	private YdMangerServiceTask serviceTask;

	@Autowired
	private YdMangerServiceCollection servideCollection;

	@Autowired
	private YdMangerServiceAttention serviceAttention;

	@Autowired
	private YdMangerServiceMessage serviceMessage;

	@Autowired
	private YdMangerServiceTaskModel serviceTaskModel;

	@Autowired
	private YdMangerServiceTaskPM serviceTaskPM;

	@Autowired
	private YdMangerServiceEpcs serviceEpcs;

	@Autowired
	private YdMangerServiceContract serviceContract;

	@Autowired
	private YdMangerServiceFiles serviceFiles;

	@Autowired
	private YdMangerServiceBankTR serviceBankTR;

	@Autowired
	private YdMangerServiceTender serviceTender;

	@Autowired
	private YdMangerServiceVerified serviceVerified;

	@Autowired
	private YdMangerServiceUser serviceUser;
	
	@Autowired
	private YdMangerServiceTaskFolder serviceTaskFolder;
	
	@Autowired
	private YdMangerServiceUserIsAutoLogin userIsAutoLogin;
	
	@Autowired
	private YdMangerServiceUserTeamFile serviceUserTeamFile;
	
	private YdMangerUserVo userVo = null;

	@RequestMapping("/daiqianhetong")
	public String daiqianhetong(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerTaskVo> taskVos = serviceTask.$queryBySId(userVo.getUser_id(), 1);
			model.addAttribute("taskVos", taskVos);
		}
		return isLogin("daiqianhetong", request);
	}

	@RequestMapping("/fabugonggao/{taskId}")
	public String fabugonggao(@PathVariable Integer taskId, Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			YdMangerTaskVo taskVo = serviceTask.$queryById(taskId, userVo.getUser_id());
			List<YdMangerTaskModel> taskModels = serviceTaskModel.$queryByTid(taskId);
			List<YdMangerTaskPM> taskPMs = serviceTaskPM.$queryByTid(taskId);
			
			List<YdMangerFiles> files = serviceFiles.$queryAllByTaskId(taskId);
			model.addAttribute("files", files);
			
			List<YdMangerTaskFolderVo> folderVos = serviceTaskFolder.queryFolderByTaskId(taskId);
			model.addAttribute("folders", folderVos);
			
			model.addAttribute("taskDetail", taskVo);
			model.addAttribute("tasdId", taskId);
			model.addAttribute("taskModels", taskModels);
			model.addAttribute("taskPM", taskPMs);
			updateUserPrice(request);
		}
		return isLogin("fabugonggao", request);
	}

	@RequestMapping("/fuwushang")
	public String fuwushang(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerTaskVo> taskVos = serviceTask.$queryBySId(userVo.getUser_id(), 0);
			TaskVoNums nums = serviceTask.$queryByTask(1, userVo.getUser_id());
			request.getSession().setAttribute("nums", nums);
			model.addAttribute("taskVos", taskVos);
			updateUserPrice(request);
		}
		return isLogin("fuwushang", request);
	}

	@RequestMapping("/gongzuotai-zuyuan")
	public String gongzuotaiZuyuan(HttpServletRequest request) {
		return isLogin("gongzuotai-zuyuan", request);
	}

	@RequestMapping("/gongzuozhong")
	public String gongzuozhong(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerTaskVo> taskVos = serviceTask.$queryBySId(userVo.getUser_id(), 2);
			model.addAttribute("taskVos", taskVos);
		}
		return isLogin("gongzuozhong", request);
	}

	@RequestMapping("/hetong/{taskId}")
	public String hetong(@PathVariable Integer taskId, Model model, HttpServletRequest request) {

		if (isLogin(request)) {
			String result = "hetongweiqian";
			YdMangerTaskVo taskVo = serviceTask.$queryByTaskId(taskId);
			if (taskVo != null && taskVo.getTask_state() == 3) {
				switch (taskVo.getTask_contract_state()) {
				case 0:
				case 1:
				case 4:
					result = "hetongweiqian";
					break;
				case 2:
					 List<YdMangerTaskPM> taskPMs = serviceTaskPM.$queryByTid(taskId);
					 List<YdMangerFiles> files = serviceFiles.$queryAllByTaskId(taskId);
					 YdMangerTenderVo tenderVo = serviceTender.queryTenderByTid(taskId);
					 Date date = new Date();
					 model.addAttribute("taskPMs", taskPMs);
					 model.addAttribute("files", files);
					 model.addAttribute("taskVo", taskVo);
					 model.addAttribute("date", date);
					 model.addAttribute("tenderVo", tenderVo);
					result = "hetongbianji";
					break;
				case 3:
					YdMangerContract contract = serviceContract.$queryByTaskId(taskId);
					taskPMs = serviceTaskPM.$queryByTid(taskId);
					if (contract == null) {
						contract = new YdMangerContract();
					}
					model.addAttribute("contract", contract);
					model.addAttribute("taskPM", taskPMs);
					result = "hetongyiqian";
					break;
				}

				model.addAttribute("taskId", taskId);
				model.addAttribute("taskVo", taskVo);

			} else if (taskVo.getTask_state() > 3) {
				YdMangerContract contract = serviceContract.$queryByTaskId(taskId);
				List<YdMangerTaskPM> taskPMs = serviceTaskPM.$queryByTid(taskId);
				if (contract == null) {
					contract = new YdMangerContract();
				}
				model.addAttribute("contract", contract);
				model.addAttribute("taskPM", taskPMs);
				result = "hetongyiqian";
			}
			model.addAttribute("task", taskVo);
			return isLogin(result, request);
		} else {
			return "list-login/login";
		}
	}

	@RequestMapping("/hetongbianji/{taskId}")
	public String hetongbianji(@PathVariable Integer taskId, Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			model.addAttribute("taskId", taskId);
			YdMangerTaskVo taskVo = serviceTask.$queryById(taskId, userVo.getUser_id());
			model.addAttribute("taskVo", taskVo);
			YdMangerContract contract = serviceContract.$queryByTaskId(taskId);
			List<YdMangerTaskPM> taskPMs = serviceTaskPM.$queryByTid(taskId);
			if (contract == null) {
				contract = new YdMangerContract();
			}
			model.addAttribute("contract", contract);
			model.addAttribute("taskPM", taskPMs);
		}
		return isLogin("hetongbianji", request);
	}

	@RequestMapping("/hetongyiqian/{taskId}")
	public String hetongyiqian(Model model, @PathVariable Integer taskId, HttpServletRequest request) {
		String resultPage = "hetongweiqian";
		if (isLogin(request)) {
			model.addAttribute("taskId", taskId);
			YdMangerTaskVo taskVo = serviceTask.$queryById(taskId, userVo.getUser_id());
			model.addAttribute("taskDetail", taskVo);
			if (taskVo.getTask_contract_state() == 1 || taskVo.getTask_contract_state() == 4) {
				resultPage = "hetongyiqian";
			} else {
				resultPage = "hetongweiqian";
			}
		}
		return isLogin(resultPage, request);

	}

	@RequestMapping("/jiaoyisuccess")
	public String jiaoyisuccess(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerTaskVo> taskVos = serviceTask.$queryBySId(userVo.getUser_id(), 4);
			model.addAttribute("taskVos", taskVos);
		}
		return isLogin("jiaoyisuccess", request);
	}

	@RequestMapping("/pingjia")
	public String pingjia(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerTaskVo> taskVos = serviceTask.$queryBySId(userVo.getUser_id(), 3);
			model.addAttribute("taskVos", taskVos);
		}
		return isLogin("pingjia", request);
	}

	@RequestMapping("/shenqingrenwu-bj/{taskId}")
	public String shenqingrenwuBj(@PathVariable Integer taskId, Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			model.addAttribute("taskId", taskId);
			Integer isVerified = serviceUser.$queryVerifiedByUserId(userVo.getUser_id());
			if (isVerified != 1) {
				return "public/noverified";
			} else {
				return isLogin("shenqingrenwu-bj", request);
			}
		} else {
			return isLogin("shenqingrenwu-bj", request);
		}

	}

	@RequestMapping("/shenqingrenwu-ck/{tenderId}")
	public String shenqingrenwuCk(@PathVariable Integer tenderId, Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			YdMangerTenderVo tenderVo = serviceTender.$queryByIdAndUserMsg(tenderId);
			model.addAttribute("tender", tenderVo);
		}
		return isLogin("shenqingrenwu-ck", request);
	}

	@RequestMapping("/shenqingziliao")
	public String shenqingziliao(HttpServletRequest request) {
		return isLogin("shenqingziliao", request);
	}

	@RequestMapping("/tuikuanzhong")
	public String tuikuanzhong(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerTaskVo> taskVos = serviceTask.$queryBySId(userVo.getUser_id(), 5);
			model.addAttribute("taskVos", taskVos);
		}
		return isLogin("tuikuanzhong", request);
	}

	@RequestMapping("/wodeguanzhu-xq")
	public String wodeguanzhuXq(HttpServletRequest request) {
		if (isLogin(request)) {
			serviceAttention.$queryAll(userVo.getUser_id());
		}
		return isLogin("wodeguanzhu-xq", "index", request);
	}

	@RequestMapping("/wodeguanzhu")
	public String wodeguanzhu(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerAttention> attentions = serviceAttention.$queryAll(userVo.getUser_id());
			model.addAttribute("attentions", attentions);
		}
		return isLogin("wodeguanzhu", "index", request);
	}

	@RequestMapping("/wodejianli")
	public String wodejianli(HttpServletRequest request) {
		return isLogin("wodejianli", request);
	}

	@RequestMapping("/wodepingjia")
	public String wodepingjia(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerEpcs> epcs = serviceEpcs.$queryAllByUserId(userVo.getUser_id());
			model.addAttribute("epcs", epcs);
		}
		return isLogin("wodepingjia", request);
	}

	@RequestMapping("/woderenzheng-shiming")
	public String woderenzhengShiming(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			Integer vId = 0;
			if (userVo.getUser_verified() == 1 || userVo.getUser_verified() == 3) {
				YdMangerVerified verified = serviceVerified.queryByUserId(userVo.getUser_id());
				vId = verified.getV_id();
				model.addAttribute("verified", verified);
				Integer isV = serviceUser.$queryVerifiedByUserId(userVo.getUser_id());
				userVo.setUser_verified(isV);
				request.getSession().setAttribute("user", userVo);
			} else {
				vId = serviceVerified.queryIdByUserId(userVo.getUser_id());
			}
			model.addAttribute("vId", vId);
		}
		return isLogin("woderenzheng-shiming", request);
	}

	@RequestMapping("/woderenzheng-jineng")
	public String woderenzhengJineng(HttpServletRequest request) {
		return isLogin("woderenzheng-jineng", request);
	}

	@RequestMapping("/woderenzheng")
	public String woderenzheng(HttpServletRequest request,Model model) {
		if(isLogin(request)){
			List<YdMangerFiles> teamFiles = serviceUserTeamFile.$queryAllByUserId(userVo.getUser_id());
			model.addAttribute("teamFiles", teamFiles);
		}
		return isLogin("woderenzheng", request);
	}

	@RequestMapping("/wodeshoucang")
	public String wodeshoucang(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerCollection> collections = servideCollection.$queryAllByPageNum(userVo.getUser_id(), 0, 10);
			model.addAttribute("collections", collections);
		}
		return isLogin("wodeshoucang", request);
	}

	@RequestMapping("/wodexiaoxi-huifu")
	public String wodexiaoxiHuifu(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerMessageVo> msgs = serviceMessage.$queryByUserIdAndByPageNum(userVo.getUser_id(), 0, 1);
			model.addAttribute("msgs", msgs);
		}
		return isLogin("wodexiaoxi-huifu", request);
	}

	@RequestMapping("/wodexiaoxi-huifu-task")
	public String wodexiaoxiHuifuTask(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerMessageVo> msgs = serviceMessage.$queryByUserIdAndByPageNum(userVo.getUser_id(), 1, 1);
			model.addAttribute("msgs", msgs);
		}
		return isLogin("wodexiaoxi-huifu-task", request);
	}

	@RequestMapping("/wodexiaoxi-huifu-msg")
	public String wodexiaoxiHuifuMsg(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			List<YdMangerMessageVo> msgs = serviceMessage.$queryByUserIdAndByPageNum(userVo.getUser_id(), 2, 1);
			model.addAttribute("msgs", msgs);
		}
		return isLogin("wodexiaoxi-huifu-msg", request);
	}

	@RequestMapping("/wodexiaoxi")
	public String wodexiaoxi(Model model, HttpServletRequest request) {
		return isLogin("wodexiaoxi", request);
	}

	@RequestMapping("/wodezhanghu")
	public String wodezhanghu(Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			PageNum pageNum = new PageNum();
			Integer dataCountNum = serviceBankTR.$queryCountNum(userVo.getUser_id());
			pageNum = new PageNum(request.getSession(),dataCountNum,"banktrPageNum");
			List<YdMangerBankTR> bankTRs = serviceBankTR.$queryAll(userVo.getUser_id(),pageNum.getStartPageNum(),pageNum.getPageNum());
			pageNum.setData(bankTRs);
			model.addAttribute("pageNum", pageNum);
		}
		return isLogin("wodezhanghu", request);
	}

	@RequestMapping("/xiugaiziliao")
	public String xiugaiziliao(HttpServletRequest request) {
		return isLogin("xiugaiziliao", "index", request);
	}

	@RequestMapping("/xuanzerencai")
	public String xuanzerencai() {
		return pageFiles + "xuanzerencai";
	}

	@RequestMapping("/xuqiumiaoshu/{taskId}")
	public String xuqiumiaoshu(@PathVariable Integer taskId, Model model, HttpServletRequest request) {
		if (isLogin(request)) {
			model.addAttribute("taskId", taskId);
			YdMangerTaskVo taskVo = serviceTask.$queryById(taskId, userVo.getUser_id());
			model.addAttribute("taskDetail", taskVo);
			List<YdMangerFiles> files = serviceFiles.$queryAllByTaskId(taskId);
			model.addAttribute("files", files);
		}
		/*
		 * else{ YdMangerTaskVo taskVo = serviceTask.$queryById(taskId,0);
		 * model.addAttribute("taskDetail", taskVo); }
		 */
		return isLogin("xuqiumiaoshu", request);
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
			return "list-login/login";
		}
	}

	private boolean isLogin(HttpServletRequest request) {
		userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
		/*if(MyStaticFactory.num == 0 || userVo == null){
			if(userIsAutoLogin.IsAutoLogin(request)){
				userVo = (YdMangerUserVo) request.getSession().getAttribute("user");
				return true;
			}
		}*/
		return userVo != null && userVo.getUser_type() > 0 ? true : false;
	}
	
	//更新显示用户金额
	private void updateUserPrice(HttpServletRequest request){
		if(isLogin(request)){
			float price = serviceUser.$queryUserPrice(userVo.getUser_id());
			userVo.setUser_cprice(price);
			request.getSession().setAttribute("user", userVo);
		}
	}
	
}
