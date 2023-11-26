package com.service;

import java.util.List;

import com.vo.ContractAdd;
import com.vo.ContractAddVO;
import org.apache.ibatis.annotations.Param;

import com.pojo.Contract;
import com.pojo.ContractExample;

public interface ContractService {
	long countByExample(ContractExample example);

	int deleteByExample(ContractExample example);

	int deleteByPrimaryKey(String id);

	int insert(Contract record);

	int insertSelective(Contract record);

	List<Contract> selectByExample(ContractExample example);

	Contract selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Contract record,
			@Param("example") ContractExample example);

	int updateByExample(@Param("record") Contract record,
			@Param("example") ContractExample example);

	int updateByPrimaryKeySelective(Contract record);

	int updateByPrimaryKey(Contract record);

	List<ContractAddVO> getContractAdd();
}
