package com.yd.gcj.service;

import java.util.HashMap;

import org.springframework.transaction.annotation.Transactional;

public interface YdMangerServiceArticleClass {

	/***
	 * 查询文章所有类型信息
	 * @return
	 */
	Object $queryAll();
	
	/***
	 * 根据id查询类型信息
	 * @param ac_id
	 * @return
	 */
	Object $queryById(HashMap<String, Object> map);
	
	/***
	 * 查询类型信息数量
	 * @return
	 */
	Object $queryCountNum();
	
	/***
	 * 修改类型信息
	 * @param ac
	 * @return
	 */
	@Transactional
	Object $update(HashMap<String, Object> map);
	
	/***
	 * 删除指定文章类型数据
	 * @param ac_id
	 * @return
	 */
	@Transactional
	Object $delete(HashMap<String, Object> map);
	
	/***
	 * 添加新的文章类型信息
	 * @param ac
	 * @return
	 */
	@Transactional
	Object $insert(HashMap<String, Object> map);
}
