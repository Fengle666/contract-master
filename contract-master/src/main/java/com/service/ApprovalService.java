package com.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pojo.Approval;
import com.pojo.ApprovalExample;

public interface ApprovalService {
	long countByExample(ApprovalExample example);

	int deleteByExample(ApprovalExample example);

	int deleteByPrimaryKey(String id);

	int insert(Approval record);

	int insertSelective(Approval record);

	List<Approval> selectByExample(ApprovalExample example);

	Approval selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Approval record,
			@Param("example") ApprovalExample example);

	int updateByExample(@Param("record") Approval record,
			@Param("example") ApprovalExample example);

	int updateByPrimaryKeySelective(Approval record);

	int updateByPrimaryKey(Approval record);
}
