package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.EnclosureMapper;
import com.pojo.Enclosure;
import com.pojo.EnclosureExample;
import com.service.EnclosureService;

@Service
public class EnclosureServiceImpl implements EnclosureService {

	@Autowired
	private EnclosureMapper logMapper;

	@Override
	public long countByExample(EnclosureExample example) {
		// TODO Auto-generated method stub
		return logMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(EnclosureExample example) {
		// TODO Auto-generated method stub
		return logMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return logMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Enclosure record) {
		// TODO Auto-generated method stub
		return logMapper.insert(record);
	}

	@Override
	public int insertSelective(Enclosure record) {
		// TODO Auto-generated method stub
		return logMapper.insertSelective(record);
	}

	@Override
	public List<Enclosure> selectByExample(EnclosureExample example) {
		// TODO Auto-generated method stub
		return logMapper.selectByExample(example);
	}

	@Override
	public Enclosure selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return logMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(Enclosure record,
			EnclosureExample example) {
		// TODO Auto-generated method stub
		return logMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(Enclosure record, EnclosureExample example) {
		// TODO Auto-generated method stub
		return logMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(Enclosure record) {
		// TODO Auto-generated method stub
		return logMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Enclosure record) {
		// TODO Auto-generated method stub
		return logMapper.updateByPrimaryKey(record);
	}

}
