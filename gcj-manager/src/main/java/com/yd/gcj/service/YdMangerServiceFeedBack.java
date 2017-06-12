package com.yd.gcj.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yd.gcj.entity.YdMangerFeedBack;

public interface YdMangerServiceFeedBack {

	/***
	 * 按分页查询所有用户反馈信息
	 * @param startPageNum
	 * @param queryPageNum
	 * @param fb_isdel 是否被删除  0未删除 1删除 -1全部
	 * @return
	 */
	List<YdMangerFeedBack> $queryAllByPageNum(@Param("startPageNum") Integer startPageNum,@Param("queryPageNum") Integer queryPageNum,@Param("fb_isdel") Integer fb_isdel);
	
	/***
	 * 添加反馈信息
	 * @param fb
	 * @return
	 */
	@Transactional
	Integer $insert(YdMangerFeedBack fb);
	
	/***
	 * 标识反馈信息已读
	 * @param fb_id
	 * @return
	 */
	@Transactional
	Integer $updateIsRead(@Param("fb_id") Integer fb_id);
	
	/***
	 * 修改反馈后台备注信息
	 * @param fb_id
	 * @param fb_remarks
	 * @return
	 */
	@Transactional
	Integer $updateRemarks(@Param("fb_id") Integer fb_id,@Param("fb_remarks") String fb_remarks);
	
	/***
	 * 删除反馈信息
	 * @param fb_id
	 * @return
	 */
	@Transactional
	Integer $delete(@Param("fb_id") Integer fb_id);
}