package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.TypeMapper;
import com.pojo.Type;
import com.pojo.TypeExample;
import com.service.TypeService;

@Service
public class TypeServiceImpl implements TypeService {

	@Autowired
	private TypeMapper typeMapper;

	@Override
	public long countByExample(TypeExample example) {
		// TODO Auto-generated method stub
		return typeMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(TypeExample example) {
		// TODO Auto-generated method stub
		return typeMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return typeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Type record) {
		// TODO Auto-generated method stub
		return typeMapper.insert(record);
	}

	@Override
	public int insertSelective(Type record) {
		// TODO Auto-generated method stub
		return typeMapper.insertSelective(record);
	}

	@Override
	public List<Type> selectByExample(TypeExample example) {
		// TODO Auto-generated method stub
		return typeMapper.selectByExample(example);
	}

	@Override
	public Type selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return typeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(Type record, TypeExample example) {
		// TODO Auto-generated method stub
		return typeMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(Type record, TypeExample example) {
		// TODO Auto-generated method stub
		return typeMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(Type record) {
		// TODO Auto-generated method stub
		return typeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Type record) {
		// TODO Auto-generated method stub
		return typeMapper.updateByPrimaryKey(record);
	}

}
