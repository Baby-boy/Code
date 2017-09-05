package com.glanway.iclock.dao.employee;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.glanway.iclock.dao.BaseDao;
import com.glanway.iclock.entity.employee.FingerFaceTemplate;
import com.glanway.iclock.entity.vo.device.EmployeeDeviceFingerFaceVo;

/**
 * 说明 : 员工指纹模板和脸纹模板
 * 
 * @author zhangshaung
 * @version 1.0.0
 * @dateTime 2017年4月19日 下午5:55:57
 */
public interface FingerFaceTemplateDao extends BaseDao<FingerFaceTemplate> {

	/** 根据员工代码,查询员工的指纹模板或脸纹模板 */
	public List<FingerFaceTemplate> selectByEmployeeCodeAndType(Map<String, Object> params);

	/** 根据员工代码(EMPLOYEE_CODE)和指纹标号脸纹标号(FID),查询员工的指纹模板或脸纹模板 */
	public FingerFaceTemplate findInfoByEmployeeCodeAndTypeAndFid(Map<String, Object> params);

	/** 根据员工Code查询员工指纹或者脸纹模板 */
	public List<EmployeeDeviceFingerFaceVo> findEmployeeFingerFaceBySn(@Param("codes") String[] codes,
			@Param("type") Integer type);

	/****************************************************************
	 * *********************** 以下是考勤机相关的新逻辑 **********************
	 ****************************************************************/
	public List<FingerFaceTemplate> findFingerFaceTemplateByStateType(@Param("type") Integer type,
			@Param("stateType") Integer stateType);
}
