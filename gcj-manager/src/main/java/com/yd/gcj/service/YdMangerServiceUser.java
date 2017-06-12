package com.yd.gcj.service;

import java.util.HashMap;

import org.springframework.transaction.annotation.Transactional;

import com.yd.gcj.entity.vo.YdMangerUserDetail;
import com.yd.gcj.entity.vo.YdMangerUserVo;
import com.yd.gcj.tool.MapInitFactory;

public interface YdMangerServiceUser {

	/***
	 * 查看用户基本信息，主要用于雇主查看人才大厅时使用
	 * @param map
	 * @return
	 */
	Object $queryAll(HashMap<String, Object> map);
	
	/**
	 * 查询完成任务数量排前的服务商用户信息
	 * @param map
	 * @return
	 */
	Object $queryCountByRSP(HashMap<String, Object> map);
	
	/***
	 * 提交任务获取服务商信息
	 * @param map
	 * @return
	 */
	Object $queryRSP();
	
	/***
	 * 根据用户id查询用户信息
	 * @param map
	 * @return
	 */
	YdMangerUserVo $queryById(Integer userId);
	
	/***
	 * 查询用户金额
	 * @Description: 
	 * @param userId
	 * @return    
	 * @date: 2017年3月15日 下午1:52:57
	 */
	float $queryUserPrice(Integer userId);
	
	/***
	 * 获取用户详情（包括技能、案例、好评、查看服务商的雇主任务）
	 * @param userId
	 * @return
	 */
	YdMangerUserDetail $queryDetailById(Integer userId,Integer userEId);
	
	/***
	 * 根据手机号（即：普通账号）检查用户是否存在 
	 * @param map
	 * @return
	 */
	Integer $userIsExist(String phone);
	
	/***
	 * 根据手机号查询用户信息
	 * @param map
	 * @return
	 */
	MapInitFactory $login(HashMap<String, Object> map);
	
	/***
	 * 查询用户数据总记录
	 * @param map
	 * @return
	 */
	Object $queryCountNumBySql(HashMap<String, Object> map);
	
	/***
	 * 查询用户是否实名认证
	 * @param userId
	 * @return
	 */
	Integer $queryVerifiedByUserId(Integer userId);
	
	/***
	 * 注册用户信息
	 * @param map
	 * @return
	 */
	@Transactional
	Object $regist(HashMap<String, Object> map);
	
	/***
	 * 更新用户信息，主要用于添加用户基本信息和修改用户基本信息
	 * @param map
	 * @return
	 */
	@Transactional
	Object $updateMsg(HashMap<String, Object> map);
	
	/**
	 * 修改用户昵称
	 * @param userId
	 * @param nickname
	 * @return
	 */
	@Transactional
	Object $updateNickname(Integer userId,String nickname);
	
	/**
	 * 修改用户姓名
	 * @param userId
	 * @param userName
	 * @return
	 */
	@Transactional
	Object $updateUserName(Integer userId,String userName);
	
	/**
	 * 修改用户所在地
	 * @param userId
	 * @param userAddr
	 * @return
	 */
	@Transactional
	Object $updateAddr(Integer userId,String userAddr);
	
	/**
	 * 修改用户简历信息
	 * @param userId
	 * @param userResume
	 * @return
	 */
	@Transactional
	Object $updateUserResume(Integer userId,String userResume);
	
	/**
	 * 修改用户邮箱
	 * @param userId
	 * @param userEmail
	 * @return
	 */
	@Transactional
	Object $updateUserEmail(Integer userId,String userEmail);
	
	/**
	 * 修改用户QQ号码
	 * @param userId
	 * @param userQQ
	 * @return
	 */
	@Transactional
	Object $updateUserQQ(Integer userId,String userQQ);
	
	/***
	 * 重置密码
	 * @param map
	 * @return
	 */
	@Transactional
	Object $updatePwd(Integer userId,String oldPwd,String newPwd);
	
	/**
	 * 重置支付密码
	 * @param map
	 * @return
	 */
	@Transactional
	Object $updatePPwd(Integer userId,String phone,String payPwd);
	
	/***
	 * 用户技能认证
	 * @param map
	 * @return
	 */
	@Transactional
	Object $labelCFA(HashMap<String, Object> map);
	
	/***
	 * 用户认证企业信息
	 * @param map
	 * @return
	 */
	@Transactional
	Object $enterCFA(Integer userId,String userEname,String emprs,Integer state,Integer isResid);
	
	/***
	 * 添加简历信息
	 * @param map
	 * @return
	 */
	@Transactional
	Object $updateResume(HashMap<String, Object> map);
	
	/**
	 * 更新用户接单类型
	 * @param userId
	 * @param otype
	 * @return
	 */
	@Transactional
	Object $updateOtype(Integer userId,Integer otype);
	
	/**
	 * 保持用户头像
	 * @Description: 
	 * @param userId
	 * @param imgPath
	 * @return    
	 * @date: 2017年2月15日 下午4:23:01
	 */
	@Transactional
	Integer $updateUserAvatar(Integer userId,String imgPath);
	
	/***
	 * 添加银行卡信息
	 * @param map
	 * @return
	 */
	@Transactional
	Object $addBankMsg(HashMap<String, Object> map);
	
	/***
	 * 删除银行卡信息
	 * @param map
	 * @return
	 */
	@Transactional
	Object $delBankMsg(HashMap<String, Object> map);
	
	/***
	 * 修改用户手机号
	 * @param userId
	 * @param oldPhoneNum
	 * @param newPhoneNum
	 * @return
	 */
	@Transactional
	Object $updateUserPhone(Integer userId,String oldPhoneNum,String newPhoneNum);
	
	/***
	 * 忘记密码
	 * @param userPhone
	 * @param userPwd
	 * @return
	 */
	@Transactional
	Object $forgetPwd(String userPhone,String userPwd);
	
	/***
	 * 服务商用户职称认证
	 * @param jobTitle
	 * @return
	 */
	Object $jobTitleVerified(final YdMangerUserVo userVo,Integer level);
}