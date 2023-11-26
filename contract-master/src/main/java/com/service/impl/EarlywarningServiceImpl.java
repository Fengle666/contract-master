package com.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ContractMapper;
import com.dao.EarlywarningMapper;
import com.dao.UserMapper;
import com.pojo.Contract;
import com.pojo.Earlywarning;
import com.pojo.EarlywarningExample;
import com.pojo.User;
import com.service.EarlywarningService;

@Service
public class EarlywarningServiceImpl implements EarlywarningService {

	@Autowired
	private EarlywarningMapper earlywarningMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private ContractMapper contractMapper;

	@Override
	public long countByExample(EarlywarningExample example) {
		// TODO Auto-generated method stub
		return earlywarningMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(EarlywarningExample example) {
		// TODO Auto-generated method stub
		return earlywarningMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return earlywarningMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Earlywarning record) {
		// TODO Auto-generated method stub
		return earlywarningMapper.insert(record);
	}

	@Override
	public int insertSelective(Earlywarning record) {
		// TODO Auto-generated method stub
		return earlywarningMapper.insertSelective(record);
	}

	@Override
	public List<Earlywarning> selectByExample(EarlywarningExample example) {
		// TODO Auto-generated method stub
		List<Earlywarning> earlywarnings = earlywarningMapper
				.selectByExample(example);
		User user = null;
		for (Earlywarning earlywarning : earlywarnings) {
			List<Contract> contracts = new ArrayList<>();
			for (String contractid : earlywarning.getContractId().split(",")) {
				contracts.add(contractMapper.selectByPrimaryKey(contractid));
			}
			user = userMapper.selectByPrimaryKey(earlywarning.getUserRoleId());
			earlywarning.setUser(user);
			earlywarning.setUserName(user.getName());

			earlywarning.setContracts(contracts);
		}
		return earlywarnings;
	}

	@Override
	public Earlywarning selectByPrimaryKey(String id) {
		Earlywarning earlywarning = earlywarningMapper.selectByPrimaryKey(id);
		User user = userMapper.selectByPrimaryKey(earlywarning.getUserRoleId());
		earlywarning.setUser(user);
		earlywarning.setUserName(user.getName());

		List<Contract> contracts = new ArrayList<>();
		for (String contractid : earlywarning.getContractId().split(",")) {
			contracts.add(contractMapper.selectByPrimaryKey(contractid));
		}

		earlywarning.setContracts(contracts);

		return earlywarning;
	}

	@Override
	public int updateByExampleSelective(Earlywarning record,
			EarlywarningExample example) {
		// TODO Auto-generated method stub
		return earlywarningMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(Earlywarning record, EarlywarningExample example) {
		// TODO Auto-generated method stub
		return earlywarningMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(Earlywarning record) {
		// TODO Auto-generated method stub
		return earlywarningMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Earlywarning record) {
		// TODO Auto-generated method stub
		return earlywarningMapper.updateByPrimaryKey(record);
	}

}
