package com.service;

import java.util.List;

import com.vo.ContractAddVO;
import org.apache.ibatis.annotations.Param;

import com.pojo.Log;
import com.pojo.LogExample;

public interface LogService {
	long countByExample(LogExample example);

	int deleteByExample(LogExample example);

	int deleteByPrimaryKey(String id);

	int insert(Log record);

	int insertSelective(Log record);

	List<Log> selectByExample(LogExample example);

	Log selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Log record,
			@Param("example") LogExample example);

	int updateByExample(@Param("record") Log record,
			@Param("example") LogExample example);

	int updateByPrimaryKeySelective(Log record);

	int updateByPrimaryKey(Log record);

    List<ContractAddVO> getDownload();
}
