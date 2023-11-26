package com.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pojo.Enclosure;
import com.pojo.EnclosureExample;

public interface EnclosureService {
	long countByExample(EnclosureExample example);

	int deleteByExample(EnclosureExample example);

	int deleteByPrimaryKey(String id);

	int insert(Enclosure record);

	int insertSelective(Enclosure record);

	List<Enclosure> selectByExample(EnclosureExample example);

	Enclosure selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Enclosure record,
			@Param("example") EnclosureExample example);

	int updateByExample(@Param("record") Enclosure record,
			@Param("example") EnclosureExample example);

	int updateByPrimaryKeySelective(Enclosure record);

	int updateByPrimaryKey(Enclosure record);
}
