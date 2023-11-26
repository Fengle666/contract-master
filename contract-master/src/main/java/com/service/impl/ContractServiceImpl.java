package com.service.impl;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.vo.ContractAdd;
import com.vo.ContractAddVO;
import javafx.scene.input.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.dao.ContractMapper;
import com.dao.TypeMapper;
import com.dao.UserMapper;
import com.pojo.Contract;
import com.pojo.ContractExample;
import com.pojo.Type;
import com.pojo.User;
import com.service.ContractService;

@Service
public class ContractServiceImpl implements ContractService {

	@Autowired
	private ContractMapper contractMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private TypeMapper typeMapper;

	@Override
	public long countByExample(ContractExample example) {
		// TODO Auto-generated method stub
		return contractMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(ContractExample example) {
		// TODO Auto-generated method stub
		return contractMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return contractMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Contract record) {
		// TODO Auto-generated method stub
		return contractMapper.insert(record);
	}

	@Override
	public int insertSelective(Contract record) {
		// TODO Auto-generated method stub
		return contractMapper.insertSelective(record);
	}

	@Override
	public List<Contract> selectByExample(ContractExample example) {

		List<Contract> contracts = contractMapper.selectByExample(example);

		User user = null;
		Type type = null;
		for (Contract contract : contracts) {
			user = userMapper.selectByPrimaryKey(contract.getUserRoleId());
			contract.setUser(user);
			contract.setUserName(user.getName());

			type = typeMapper.selectByPrimaryKey(contract.getTypeId());
			contract.setType(type);
			contract.setTypeName(type.getType());
		}

		// TODO Auto-generated method stub
		return contracts;
	}

	@Override
	public Contract selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		Contract contract = contractMapper.selectByPrimaryKey(id);

		User user = userMapper.selectByPrimaryKey(contract.getUserRoleId());
		contract.setUser(user);
		contract.setUserName(user.getName());

		Type type = typeMapper.selectByPrimaryKey(contract.getTypeId());
		contract.setType(type);
		contract.setTypeName(type.getType());

		return contract;
	}

	@Override
	public int updateByExampleSelective(Contract record, ContractExample example) {
		// TODO Auto-generated method stub
		return contractMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(Contract record, ContractExample example) {
		// TODO Auto-generated method stub
		return contractMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(Contract record) {
		// TODO Auto-generated method stub
		return contractMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Contract record) {
		// TODO Auto-generated method stub
		return contractMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ContractAddVO> getContractAdd() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// 获取当前日期
		Calendar cal = Calendar.getInstance();
		// 获取7天前的日期
		cal.add(Calendar.DAY_OF_MONTH, +1);
		String end = formatter.format(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, -7);
		String start = formatter.format(cal.getTime());
		List<ContractAdd> contractAdd = contractMapper.getContractAdd(start, end);
		ArrayList<ContractAddVO> objects = new ArrayList<>();
		for (ContractAdd add: contractAdd){
			String format = formatter.format(add.getDay());
			ContractAddVO contractAddVO = new ContractAddVO();
			contractAddVO.setCount(add.getCount());
			contractAddVO.setDay(format);
			objects.add(contractAddVO);
		}
		return objects;
	}

}
