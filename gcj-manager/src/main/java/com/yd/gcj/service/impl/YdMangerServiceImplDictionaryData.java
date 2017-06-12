package com.yd.gcj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.gcj.entity.YdMangerDictionaryData;
import com.yd.gcj.mapper.YdMangerMapperDictionaryData;
import com.yd.gcj.service.YdMangerServiceDictionaryData;

@Service("serviceDictionaryData")
public class YdMangerServiceImplDictionaryData implements YdMangerServiceDictionaryData {

	@Autowired
	private YdMangerMapperDictionaryData mapperDictionaryData;

	@Override
	public List<YdMangerDictionaryData> $queryAllByValue(Integer value) {
		return mapperDictionaryData.$queryAllByValue(value);
	}

	@Override
	public Integer $queryCountByValue(Integer value) {
		return mapperDictionaryData.$queryCountByValue(value);
	}

	@Override
	public List<YdMangerDictionaryData> $queryAllByValueAndIsfixed(Integer value, Integer isfixed) {
		return mapperDictionaryData.$queryAllByValueAndIsfixed(value, isfixed);
	}

	@Override
	public Integer $queryCountByValueAndIsfixed(Integer value, Integer isfixed) {
		return mapperDictionaryData.$queryCountByValueAndIsfixed(value, isfixed);
	}

	@Override
	public YdMangerDictionaryData $queryById(Integer id) {
		return mapperDictionaryData.$queryById(id);
	}

	@Override
	public Integer $isExsitById(Integer id) {
		return mapperDictionaryData.$isExsitById(id);
	}

	@Override
	public List<YdMangerDictionaryData> $queryPScale() {
		return mapperDictionaryData.$queryAllByValue(1055);
	}

	@Override
	public Map<String, Object> $queryDefContractValue() {
		List<YdMangerDictionaryData> datas = mapperDictionaryData.$queryAllByValue(1028);
		Map<String, Object> map = new HashMap<String, Object>();
		for (YdMangerDictionaryData data : datas) {
			map.put(String.valueOf(data.getIsfixed()), data);
		}
		return map;
	}

}