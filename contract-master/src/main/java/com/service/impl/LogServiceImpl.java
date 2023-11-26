package com.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.vo.ContractAdd;
import com.vo.ContractAddVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.LogMapper;
import com.pojo.Log;
import com.pojo.LogExample;
import com.service.LogService;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogMapper logMapper;

	@Override
	public long countByExample(LogExample example) {
		// TODO Auto-generated method stub
		return logMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(LogExample example) {
		// TODO Auto-generated method stub
		return logMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return logMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Log record) {
		// TODO Auto-generated method stub
		return logMapper.insert(record);
	}

	@Override
	public int insertSelective(Log record) {
		// TODO Auto-generated method stub
		return logMapper.insertSelective(record);
	}

	@Override
	public List<Log> selectByExample(LogExample example) {
		// TODO Auto-generated method stub
		return logMapper.selectByExample(example);
	}

	@Override
	public Log selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return logMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(Log record, LogExample example) {
		// TODO Auto-generated method stub
		return logMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(Log record, LogExample example) {
		// TODO Auto-generated method stub
		return logMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(Log record) {
		// TODO Auto-generated method stub
		return logMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Log record) {
		// TODO Auto-generated method stub
		return logMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ContractAddVO> getDownload() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// 获取当前日期
		Calendar cal = Calendar.getInstance();
		// 获取7天前的日期
		cal.add(Calendar.DAY_OF_MONTH, +1);
		String end = formatter.format(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, -7);
		String start = formatter.format(cal.getTime());

		List<ContractAdd> download = logMapper.getDownload(start, end);

		ArrayList<ContractAddVO> data = new ArrayList<>();
		for (ContractAdd add: download){
			String format = formatter.format(add.getDay());
			ContractAddVO contractAddVO = new ContractAddVO();
			contractAddVO.setCount(add.getCount());
			contractAddVO.setDay(format);
			data.add(contractAddVO);
		}
		return data;
	}

}
