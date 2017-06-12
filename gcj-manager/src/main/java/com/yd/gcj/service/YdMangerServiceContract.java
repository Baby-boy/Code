package com.yd.gcj.service;

import org.springframework.transaction.annotation.Transactional;

import com.yd.gcj.entity.YdMangerContract;

public interface YdMangerServiceContract {

	/***
	 * 查询指定合同信息
	 * @param contract_id
	 * @return
	 */
	YdMangerContract $queryById(Integer contract_id);
	
	/***
	 * 查询指定的任务合同
	 * @param taskId
	 * @return
	 */
	YdMangerContract $queryByTaskId(Integer taskId);
	
	/***
	 * 多条件使用sql动态查询
	 * @param sql
	 * @return
	 */
	YdMangerContract $queryBySql(String sql);
	
	/***
	 * 添加新合同信息
	 * @param contract
	 * @return
	 */
	@Transactional
	Object $insert(YdMangerContract contract);
	
	/***
	 * 雇主签字
	 * @param contract
	 * @return
	 */
	@Transactional
	Object $eSign(YdMangerContract contract,Integer contractState);
	
	/***
	 * 服务商签字
	 * @param contract
	 * @return
	 */
	@Transactional
	Object $sSign(YdMangerContract contract,Integer contractState);
	
	/***
	 * 更新合同信息
	 * @param contract
	 * @return
	 */
	@Transactional
	Object $update(YdMangerContract contract,Integer userType,Integer taskState);
	
	/***
	 * 删除指定合同信息
	 * @param contract_id
	 * @return
	 */
	@Transactional
	Integer $delete(Integer contract_id);
	
	/***
	 * 合同內容补充
	 * @param contract_id
	 * @param contract_supp
	 * @return
	 */
	Integer supplementaryContract(Integer contract_id, String contract_supp);
	
	/***
	 * 保存合同文件
	 * @param taskId
	 * @param name
	 * @return
	 */
	@Transactional
	Object saveContractFile(Integer taskId,String path,String name);
}