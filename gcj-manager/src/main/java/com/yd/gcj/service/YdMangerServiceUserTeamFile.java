package com.yd.gcj.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.yd.gcj.entity.YdMangerFiles;
import com.yd.gcj.entity.YdMangerUserTeamFile;

public interface YdMangerServiceUserTeamFile {
	/***
	 * 根据服务商用户信息id查询所有团队文件资料
	 * @param userId
	 * @return
	 */
	List<YdMangerFiles> $queryAllByUserId(Integer userId);
	
	/***
	 * 查询团队信息中上传了多少个文件
	 * @param userId
	 * @return
	 */
	Integer $queryCount(Integer userId);
	
	/***
	 * 添加单个服务商团队文件资料信息
	 * @param teamFile
	 * @return
	 */
	@Transactional
	Integer $insert(YdMangerUserTeamFile teamFile);

	/***
	 * 根据团队信息文件id删除单个文件信息
	 * @param id
	 * @return
	 */
	@Transactional
	Integer $deleteById(Integer id);

	/***
	 * 根据服务商id删除所有团队文件资料
	 * @param userId
	 * @return
	 */
	@Transactional
	Integer $deleteByUserId(Integer userId);
}
