package com.yd.gcj.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yd.gcj.entity.YdMangerUserLabel;
import com.yd.gcj.entity.vo.YdMangerUserVo;

public interface YdMangerServiceUserLabel {

	/***
	 * 查询用户所有技能标签信息
	 * @param userl_uid 
	 * @return
	 */
	List<YdMangerUserLabel> $queryAll(@Param("userl_uid") Integer userl_uid);
	
	/***
	 * 查询用户指定技能标签信息
	 * @param userl_id
	 * @return
	 */
	YdMangerUserLabel $queryById(@Param("userl_id") Integer userl_id);
	
	/***
	 * 删除用户指定的技能标签
	 * @param userl_id
	 * @return
	 */
	@Transactional
	Integer $delete(@Param("userl_id") Integer userl_id);
	
	/***
	 * 为用户绑定新的技能标签
	 * @param userLabel
	 * @return
	 */
	@Transactional
	Object $insert(Integer userId,String labelIds,final YdMangerUserVo userVo);
	
	/***
	 * 添加个人技能
	 * @param userId
	 * @param reLabel
	 * @return
	 */
	@Transactional
	Object $insertRe(Integer userId,String reLabel);
	
	/***
	 * 查询指定用户技能标签数量
	 * @param userl_id
	 * @return
	 */
	Integer $queryCountNumById(@Param("userl_id") Integer userl_id);
	
	
}
