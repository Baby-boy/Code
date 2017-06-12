package com.yd.gcj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yd.gcj.entity.YdMangerFiles;
import com.yd.gcj.entity.YdMangerUserTeamFile;

public interface YdMangerMapperUserTeamFile {

	/***
	 * 根据服务商用户信息id查询所有团队文件资料
	 * @param userId
	 * @return
	 */
	List<YdMangerFiles> $queryAllByUserId(@Param("userId") Integer userId);
	
	/***
	 * 查询团队信息中文件数量
	 * @param userId
	 * @return
	 */
	Integer $queryCount(@Param("userId") Integer userId);
	
	/***
	 * 添加单个服务商团队文件资料信息
	 * @param teamFile
	 * @return
	 */
	Integer $insert(YdMangerUserTeamFile teamFile);

	/***
	 * 根据团队信息文件id删除单个文件信息
	 * @param id
	 * @return
	 */
	Integer $deleteById(@Param("id") Integer id);

	/***
	 * 根据服务商id删除所有团队文件资料
	 * @param userId
	 * @return
	 */
	Integer $deleteByUserId(@Param("userId") Integer userId);
	
}