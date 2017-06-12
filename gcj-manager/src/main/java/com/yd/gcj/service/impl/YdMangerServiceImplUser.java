package com.yd.gcj.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import com.yd.gcj.entity.YdMangerMsgSize;
import com.yd.gcj.entity.YdMangerTask;
import com.yd.gcj.entity.YdMangerUser;
import com.yd.gcj.entity.YdMangerUserLabel;
import com.yd.gcj.entity.YdMangerUserTeam;
import com.yd.gcj.entity.YdMangerUserTeamEmpr;
import com.yd.gcj.entity.YdMangerVerified;
import com.yd.gcj.entity.vo.PageNum;
import com.yd.gcj.entity.vo.YdMangerUserDetail;
import com.yd.gcj.entity.vo.YdMangerUserVo;
import com.yd.gcj.mapper.YdMangerMapperAttention;
import com.yd.gcj.mapper.YdMangerMapperMessage;
import com.yd.gcj.mapper.YdMangerMapperTask;
import com.yd.gcj.mapper.YdMangerMapperUser;
import com.yd.gcj.mapper.YdMangerMapperUserLabel;
import com.yd.gcj.mapper.YdMangerMapperUserTeam;
import com.yd.gcj.mapper.YdMangerMapperUserTeamEmpr;
import com.yd.gcj.mapper.YdMangerMapperVerified;
import com.yd.gcj.service.YdMangerServiceUser;
import com.yd.gcj.tool.MD5Util;
import com.yd.gcj.tool.MapFactory;
import com.yd.gcj.tool.MapInitFactory;
import com.yd.gcj.tool.MySessionFactory;
import com.yd.gcj.tool.ObjectMapperFactory;
import com.yd.gcj.tool.Values;

@Service("YdMangerServiceUser")
public class YdMangerServiceImplUser implements YdMangerServiceUser {

	@Autowired
	private YdMangerMapperUser mapperUser;

	@Autowired
	private YdMangerMapperUserLabel mapperUserLabel;

	@Autowired
	private YdMangerMapperAttention mapperAttention;

	@Autowired
	private YdMangerMapperTask serviceTask;

	@Autowired
	private YdMangerMapperVerified mapperVerified;

	@Autowired
	private YdMangerMapperMessage mapperMessage;

	@Autowired
	private YdMangerMapperUserTeam mapperUserTeam;

	@Autowired
	private YdMangerMapperUserTeamEmpr mapperTeamEmpr;

	@Autowired
	private HttpSession session;// TODO 此session存在线程安全问题,后期进行修改

	@Override
	public Integer $updateUserAvatar(Integer userId, String imgPath) {
		Integer isOk = 0;
		try {
			String avatar = mapperUser.$queryUserImgPath(userId);
			Integer success = mapperUser.$updateUserAvatar(userId, imgPath);
			if (success > 0) {
				String path = session.getServletContext().getRealPath(avatar);
				File file = new File(path);
				if (file.exists()) {
					file.delete();
				}
				isOk = 1;
			} else {
				isOk = 2;
			}
		} catch (Exception e) {
			e.printStackTrace();
			isOk = 0;
		}
		return isOk;
	}

	@Override
	public Object $queryAll(HashMap<String, Object> map) {
		Integer countNum = mapperUser.$queryCountByS();
		PageNum pageNum = MySessionFactory.getPageMsg(session, countNum);
		MySessionFactory.setPageMsg(session, pageNum);
		List<YdMangerUserVo> userVos = mapperUser.$queryAll(pageNum.getStartPageNum(), pageNum.getPageNum());
		pageNum.setData(userVos);

		return pageNum;
	}

	public Object $queryAlls() {
		Integer countNum = mapperUser.$queryCountByS();
		PageNum pageNum = new PageNum(session, countNum, "pepolePageNum");
		YdMangerUserVo userVo = (YdMangerUserVo) session.getAttribute("user");
		List<YdMangerUserVo> userVos = new ArrayList<YdMangerUserVo>();
		if (userVo != null) {
			userVos = mapperUser.$queryAllInLogin(userVo.getUser_id(), pageNum.getStartPageNum(), pageNum.getPageNum());
		} else {
			userVos = mapperUser.$queryAll(pageNum.getStartPageNum(), pageNum.getPageNum());
		}
		pageNum.setData(userVos);
		return pageNum;
	}

	@Override
	public Object $queryCountByRSP(HashMap<String, Object> map) {
		Integer countNum = mapperUser.$queryCountByRSP();
		return countNum;
	}

	@Override
	public Object $queryRSP() {
		Integer countNum = mapperUser.$queryCountByRSP();
		List<YdMangerUser> users = new ArrayList<YdMangerUser>();
		if (countNum > 0) {
			users = mapperUser.$queryRSP(0, 10);
		}
		return users;
	}

	@Override
	public YdMangerUserVo $queryById(Integer userId) {
		YdMangerUserVo userVo = mapperUser.$queryById(userId);
		return userVo;
	}

	@Override
	public Integer $userIsExist(String phone) {
		return mapperUser.$userIsExist(phone);
	}

	@Override
	@Transactional
	public Object $regist(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		mapInitFactory.setSystemError();
		YdMangerUserVo userVo = (YdMangerUserVo) map.get("registEntity");
		if (userVo != null) {
			Integer isExist = mapperUser.$userIsExist(userVo.getUser_phone());
			if (isExist > 0) {
				mapInitFactory.setMsg("501", "此号码已被注册！");
			} else {
				if (userVo.getUser_phone() == null || userVo.getUser_phone().isEmpty()) {
					mapInitFactory.setMsg("504", "请输入账号！");
					return mapInitFactory.getMap();
				} else if (userVo.getUser_phone().length() != 11
						|| !userVo.getUser_phone().substring(0, 1).equals("1")) {
					mapInitFactory.setMsg("505", "手机号格式不正确！");
					return mapInitFactory.getMap();
				} else if (userVo.getUser_pwd() == null) {
					mapInitFactory.setMsg("506", "密码不能为空！");
					mapInitFactory.getMap();
				} else if (userVo.getUser_pwd().length() < 6) {
					mapInitFactory.setMsg("507", "密码不能小于6位！");
					return mapInitFactory.getMap();
				}
				userVo.setUser_pwd(MD5Util.textToMD5L32(userVo.getUser_pwd() + "#@"));

				Date date = new Date();
				userVo.setUser_create_time(date);
				userVo.setUser_update_time(date);

				Integer success = mapperUser.$regist(userVo);

				if (success > 0) {

					YdMangerVerified verified = new YdMangerVerified();
					verified.setV_uid(userVo.getUser_id());
					verified.setV_create_time(date);
					verified.setV_update_time(date);
					Integer vSuccess = mapperVerified.$insert(verified);
					if (vSuccess > 0) {
						mapInitFactory.setMsg(Values.INITSUCCESSCODE, "注册成功");
						HttpSession session = (HttpSession) map.get("session");
						userVo.setUser_pwd(null);
						userVo.setPhoneCode(null);
						userVo.setUser_cstate(0);
						userVo.setUser_verified(0);
						System.out.println(userVo.getUser_id());
						session.setAttribute("user", userVo);
						session.setAttribute("verified", verified);
					}
				} else {
					mapInitFactory.setMsg("502", "注册失败");
				}
			}
		} else {
			mapInitFactory.setMsg("503", "网络繁忙，请稍后再试！");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public MapInitFactory $login(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		YdMangerUser u = (YdMangerUser) map.get("user");
		YdMangerUserVo user = mapperUser.$queryByPhone(u.getUser_phone());
		if (user != null) {
			if ((u.getUser_type() == 0 && user.getUser_type() == 0)
					|| (u.getUser_type() == 1 && (user.getUser_type() == 1 || user.getUser_type() == 2))) {
				if (user.getUser_pwd().equals(MD5Util.textToMD5L32(u.getUser_pwd() + "#@"))) {
					// 清除实体类不需要返回前台的数据
					user.setUser_pwd(null);
					user.setUser_ppwd(null);
					List<YdMangerUserLabel> labels = mapperUserLabel.$queryAll(user.getUser_id());
					user.setUserLabels(labels);
					Integer attNum = mapperAttention.$queryACountNum(user.getUser_id());
					user.setAttentionNum(attNum);
					YdMangerMsgSize msgSize = (YdMangerMsgSize) mapperMessage.$queryAllTypeMsgSize(user.getUser_id());
					session.setAttribute("msgSize", msgSize);
					session.setAttribute("user", user);
					mapInitFactory.setMsg(Values.INITSUCCESSCODE, "登录成功").setData(user);
				} else {
					mapInitFactory.setMsg("502", "密码不正确");
				}
			} else {
				mapInitFactory.setMsg("503", "用户登录类型不正确！");
			}
		} else {
			mapInitFactory.setMsg("501", "账号不存在");
		}
		return mapInitFactory;
	}

	@Override
	public Object $queryCountNumBySql(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		try {
			/*
			 * mapInitFactory.init().setData(); ydMangerMapperUser
			 */
		} catch (Exception e) {
			mapInitFactory.setSystemError();
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $updateMsg(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		try {

			YdMangerUser user = (YdMangerUser) MapFactory.toObject(map, YdMangerUser.class);
			user.setUser_update_time(new Date());

			Integer success = mapperUser.$update(user);
			if (success > 0) {
				mapInitFactory.setMsg(Values.INITSUCCESSCODE, "修改成功");
			} else {
				mapInitFactory.setMsg("501", "修改失败");
			}
		} catch (Exception e) {
			mapInitFactory.setSystemError();
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $updatePwd(Integer userId, String oldPwd, String newPwd) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		if (oldPwd != null && newPwd != null) {
			oldPwd = MD5Util.textToMD5L32(oldPwd + "#@");
			newPwd = MD5Util.textToMD5L32(newPwd + "#@");
			if (!oldPwd.equals(newPwd)) {
				Integer isId = mapperUser.$queryIdByPhoneAndPwd(userId, oldPwd);
				if (isId == 1) {
					Integer success = mapperUser.$updatePwd(userId, newPwd);
					if (success > 0) {
						mapInitFactory.setMsg(Values.INITSUCCESSCODE, "修改成功");
					} else {
						mapInitFactory.setMsg("503", "修改失败");
					}
				} else {
					mapInitFactory.setMsg("502", "此用户不存在");
				}
			} else {
				mapInitFactory.setMsg("504", "新密码与当前密码相同，无需修改！");
			}
		} else {
			mapInitFactory.setMsg("501", "参数有误");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $updatePPwd(Integer userId, String phone, String payPwd) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		payPwd = MD5Util.textToMD5L32(payPwd + "@$#");
		if (payPwd != null && phone != null) {
			Integer isExist = mapperUser.$queryByIdAndPhone(userId, phone);
			if (isExist == 1) {
				Integer success = mapperUser.$updatePPwd(userId, payPwd);
				if (success > 0) {
					mapInitFactory.setMsg(Values.INITSUCCESSCODE, "修改成功");
				} else {
					mapInitFactory.setMsg("503", "修改失败");
				}
			} else {
				mapInitFactory.setMsg("502", "此用户不存在");
			}
		} else {
			mapInitFactory.setMsg("501", "参数有误");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $labelCFA(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		try {

		} catch (Exception e) {
			mapInitFactory.setSystemError();
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $enterCFA(Integer userId, String userEname, String emprs, Integer state, Integer isResid) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		mapperUserTeam.$deleteByUserId(userId);
		mapperTeamEmpr.$deleteByUserId(userId);

		List<YdMangerUserTeamEmpr> emprsList = new GsonBuilder().create().fromJson(emprs,
				new TypeToken<List<YdMangerUserTeamEmpr>>() {
				}.getType());
		Integer success = mapperUser.$enterCFA(userId, 3, userEname);
		YdMangerUserTeam userTeam = new YdMangerUserTeam();
		userTeam.setTeam_ename(userEname);
		userTeam.setTeam_isresid(isResid);
		userTeam.setTeam_uid(userId);
		userTeam.setTeam_state(state);
		Date date = new Date();
		userTeam.setTeam_create_time(date);
		userTeam.setTeam_update_time(date);
		Integer resultTeam = mapperUserTeam.$insert(userTeam);
		if (resultTeam > 0) {
			for (YdMangerUserTeamEmpr teamEmpr : emprsList) {
				teamEmpr.setEmpr_tmid(userTeam.getTeam_id());
			}
			ObjectMapperFactory.doIt(emprsList);
			Integer resultEmpr = mapperTeamEmpr.insert(emprsList);
			if (success > 0 && resultEmpr > 0) {
				YdMangerUserVo userVo = (YdMangerUserVo) session.getAttribute("user");
				userVo.setUser_ename(userEname);
				userVo.setUser_cstate(3);
				session.setAttribute("user", userVo);
				mapInitFactory.setMsg(Values.INITSUCCESSCODE, "提交成功，后台审核中");
			} else {
				mapInitFactory.setMsg("502", "提交失败");
			}
		} else {
			mapInitFactory.setMsg("503", "申请失败，请稍后再试！");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $updateResume(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		Integer user_id = (Integer) map.get("user_id");
		String user_resume = map.get("user_resume").toString();// 获取用户简历信息
		YdMangerUser user = new YdMangerUser();
		user.setUser_id(user_id);
		user.setUser_resume(user_resume);
		Integer success = mapperUser.$update(user);
		if (success > 0) {
			mapInitFactory.setMsg(Values.INITSUCCESSCODE, "更新成功");
		} else {
			mapInitFactory.setMsg("501", "更新失败");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $addBankMsg(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		try {

		} catch (Exception e) {
			mapInitFactory.setSystemError();
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $delBankMsg(HashMap<String, Object> map) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		try {

		} catch (Exception e) {
			mapInitFactory.setSystemError();
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $updateNickname(Integer userId, String nickname) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		if (nickname.length() < 20) {
			if (isExsit("user")) {
				Integer isExist = mapperUser.$userIsExistById(userId);
				if (isExist > 0) {
					Integer success = mapperUser.$updateNickname(userId, nickname);
					if (success > 0) {
						YdMangerUserVo user = (YdMangerUserVo) session.getAttribute("user");
						user.setUser_nickname(nickname);
						session.setAttribute("user", user);
						mapInitFactory.setMsg("200", "设置成功！");
					} else {
						mapInitFactory.setMsg("502", "设置失败！");
					}
				} else {
					mapInitFactory.setMsg("501", "用户不存在！");
				}
			} else {
				mapInitFactory.setMsg("503", "对不起，您没有权限操作此功能！");
			}
		} else {
			mapInitFactory.setMsg("504", "昵称长度超出限制！");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $updateUserName(Integer userId, String userName) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		if (userName.length() < 15) {
			if (isExsit("user")) {
				Integer isExist = mapperUser.$userIsExistById(userId);
				if (isExist > 0) {
					Integer success = mapperUser.$updateUserName(userId, userName);
					if (success > 0) {
						YdMangerUserVo user = (YdMangerUserVo) session.getAttribute("user");
						user.setUser_name(userName);
						session.setAttribute("user", user);
						mapInitFactory.setMsg("200", "设置成功！");
					} else {
						mapInitFactory.setMsg("502", "设置失败！");
					}
				} else {
					mapInitFactory.setMsg("501", "用户不存在！");
				}
			} else {
				mapInitFactory.setMsg("503", "对不起，您没有权限操作此功能！");
			}
		} else {
			mapInitFactory.setMsg("504", "姓名长度超出限制！");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $updateAddr(Integer userId, String userAddr) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		if (userAddr.length() < 20) {
			if (isExsit("user")) {
				Integer isExist = mapperUser.$userIsExistById(userId);
				if (isExist > 0) {
					Integer success = mapperUser.$updateAddr(userId, userAddr);
					if (success > 0) {
						YdMangerUserVo user = (YdMangerUserVo) session.getAttribute("user");
						user.setUser_pca(userAddr);
						session.setAttribute("user", user);
						mapInitFactory.setMsg("200", "设置成功！");
					} else {
						mapInitFactory.setMsg("502", "设置失败！");
					}
				} else {
					mapInitFactory.setMsg("501", "用户不存在！");
				}
			} else {
				mapInitFactory.setMsg("503", "对不起，您没有权限操作此功能！");
			}
		} else {
			mapInitFactory.setMsg("504", "地址长度超出限制！");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $updateUserResume(Integer userId, String userResume) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		if (userResume.length() < 300) {
			if (isExsit("user")) {
				Integer isExist = mapperUser.$userIsExistById(userId);
				if (isExist > 0) {
					Integer success = mapperUser.$updateResume(userId, userResume);
					if (success > 0) {
						YdMangerUserVo user = (YdMangerUserVo) session.getAttribute("user");
						user.setUser_resume(userResume);
						session.setAttribute("user", user);
						mapInitFactory.setMsg("200", "设置成功！");
					} else {
						mapInitFactory.setMsg("502", "设置失败！");
					}
				} else {
					mapInitFactory.setMsg("501", "用户不存在！");
				}
			} else {
				mapInitFactory.setMsg("503", "对不起，您没有权限操作此功能！");
			}
		} else {
			mapInitFactory.setMsg("504", "简历长度超出限制！");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $updateUserEmail(Integer userId, String userEmail) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		if (userEmail.length() < 50) {
			if (isExsit("user")) {
				Integer isExist = mapperUser.$userIsExistById(userId);
				if (isExist > 0) {
					Integer success = mapperUser.$updateEmail(userId, userEmail);
					if (success > 0) {
						YdMangerUserVo user = (YdMangerUserVo) session.getAttribute("user");
						user.setUser_email(userEmail);
						session.setAttribute("user", user);
						mapInitFactory.setMsg("200", "设置成功！");
					} else {
						mapInitFactory.setMsg("502", "设置失败！");
					}
				} else {
					mapInitFactory.setMsg("501", "用户不存在！");
				}
			} else {
				mapInitFactory.setMsg("503", "对不起，您没有权限操作此功能！");
			}
		} else {
			mapInitFactory.setMsg("504", "邮箱格式不正确！");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $updateUserQQ(Integer userId, String userQQ) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		if (userQQ.length() < 300) {
			if (isExsit("user")) {
				Integer isExist = mapperUser.$userIsExistById(userId);
				if (isExist > 0) {
					Integer success = mapperUser.$updateQQ(userId, userQQ);
					if (success > 0) {
						YdMangerUserVo user = (YdMangerUserVo) session.getAttribute("user");
						user.setUser_qqtoken(userQQ);
						session.setAttribute("user", user);
						mapInitFactory.setMsg("200", "设置成功！");
					} else {
						mapInitFactory.setMsg("502", "设置失败！");
					}
				} else {
					mapInitFactory.setMsg("501", "用户不存在！");
				}
			} else {
				mapInitFactory.setMsg("503", "对不起，您没有权限操作此功能！");
			}
		} else {
			mapInitFactory.setMsg("504", "QQ号码格式不正确！");
		}
		return mapInitFactory.getMap();
	}

	private boolean isExsit(String sessionKey) {
		boolean exsit = false;
		exsit = session.getAttribute(sessionKey) != null;
		return exsit;
	}

	@Override
	public Object $updateOtype(Integer userId, Integer otype) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		if (isExsit("user")) {
			Integer isExist = mapperUser.$userIsExistById(userId);
			if (isExist > 0) {
				Integer success = mapperUser.$updateOtype(userId, otype);
				if (success > 0) {
					YdMangerUserVo user = (YdMangerUserVo) session.getAttribute("user");
					user.setUser_otype(otype);
					session.setAttribute("user", user);
					mapInitFactory.setMsg("200", "保存成功！");
				} else {
					mapInitFactory.setMsg("502", "保存失败，请检查后再试！");
				}
			} else {
				mapInitFactory.setMsg("501", "用户不存在！");
			}
		} else {
			mapInitFactory.setMsg("503", "对不起，您没有权限操作此功能！");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Object $updateUserPhone(Integer userId, String oldPhoneNum, String newPhoneNum) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		if (oldPhoneNum != null && newPhoneNum != null) {
			if (!oldPhoneNum.equals(newPhoneNum)) {
				Integer isExistNewPhone = mapperUser.$userIsExist(newPhoneNum);
				if (isExistNewPhone == 0) {
					Integer isExist = mapperUser.$queryByIdAndPhone(userId, oldPhoneNum);
					YdMangerUserVo userVo = (YdMangerUserVo) session.getAttribute("user");
					if (isExist == 1) {
						Integer success = mapperUser.$updateUserPhone(userId, newPhoneNum);
						if (success > 0) {
							mapInitFactory.setMsg(Values.INITSUCCESSCODE, "修改成功");
							if (userVo != null) {
								userVo.setUser_phone(newPhoneNum);
								session.setAttribute("user", userVo);
							}
						} else {
							mapInitFactory.setMsg("503", "修改失败");
						}
					} else {
						mapInitFactory.setMsg("502", "此用户不存在");
					}
				} else {
					mapInitFactory.setMsg("505", "该手机号已存在！");
				}
			} else {
				mapInitFactory.setMsg("504", "手机号与当前手机号相同，无需修改！");
			}
		} else {
			mapInitFactory.setMsg("501", "参数有误");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public YdMangerUserDetail $queryDetailById(Integer userId, Integer userEId) {
		YdMangerUserDetail userDetail = mapperUser.$queryDetailById(userId);
		if (userDetail != null && userEId > 0) {
			List<YdMangerTask> tasks = serviceTask.$queryNameAndIdByEId(userEId);
			if (tasks != null && tasks.size() > 0) {
				userDetail.setTasks(tasks);
			}
		}
		return userDetail;
	}

	@Override
	public Object $forgetPwd(String userPhone, String userPwd) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		if (userPhone == null || userPhone.length() != 11 && !userPhone.substring(0, 1).equals("1")) {
			return mapInitFactory.setMsg("502", "请输入有效的手机号！").getMap();
		} else if (userPwd == null || userPwd.length() < 8) {
			return mapInitFactory.setMsg("503", "密码为8位以上英文、数字、符号组合！").getMap();
		}

		Integer isExist = mapperUser.$userIsExist(userPhone);
		if (isExist > 0) {
			Integer success = mapperUser.$forgetPwd(userPhone, MD5Util.textToMD5L32(userPwd + "#@"));
			if (success > 0) {
				mapInitFactory.setMsg(Values.INITSUCCESSCODE, "修改成功！");
			} else {
				mapInitFactory.setMsg("501", "修改失败！");
			}
		} else {
			mapInitFactory.setMsg("504", "该用户不存在！");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public Integer $queryVerifiedByUserId(Integer userId) {
		return mapperUser.$queryVerifiedByUserId(userId);
	}

	@Override
	public Object $jobTitleVerified(YdMangerUserVo userVo, Integer level) {
		MapInitFactory mapInitFactory = new MapInitFactory();
		Integer success = mapperUser.$updateJobTitleAndLevel(userVo.getUser_id(), 3, level);
		if (success > 0) {
			userVo.setUser_jobtitle(3);
			mapInitFactory.setMsg("200", "提交成功,等待后台审核!");
		}else{
			mapInitFactory.setMsg("501", "提交失败!");
		}
		return mapInitFactory.getMap();
	}

	@Override
	public float $queryUserPrice(Integer userId) {
		return mapperUser.$queryPriceByUserId(userId);
	}

}