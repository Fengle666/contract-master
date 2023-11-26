package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.TemplateMapper;
import com.dao.TypeMapper;
import com.dao.UserMapper;
import com.pojo.Template;
import com.pojo.TemplateExample;
import com.pojo.Type;
import com.pojo.User;
import com.service.TemplateService;

@Service
public class TemplateServiceImpl implements TemplateService {

	@Autowired
	private TemplateMapper templateMapper;

	@Autowired
	private TypeMapper typeMapper;

	@Autowired
	private UserMapper userMapper;

	@Override
	public long countByExample(TemplateExample example) {
		// TODO Auto-generated method stub
		return templateMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(TemplateExample example) {
		// TODO Auto-generated method stub
		return templateMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return templateMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Template record) {
		// TODO Auto-generated method stub
		return templateMapper.insert(record);
	}

	@Override
	public int insertSelective(Template record) {
		// TODO Auto-generated method stub
		return templateMapper.insertSelective(record);
	}

	@Override
	public List<Template> selectByExample(TemplateExample example) {
		// TODO Auto-generated method stub
		List<Template> templates = templateMapper.selectByExample(example);
		Type type = null;
		User user = null;
		for (Template template : templates) {
			// 设置合同模板类别信息
			type = typeMapper.selectByPrimaryKey(template.getTypeId());
			template.setTypeName(type.getType());
			template.setType(type);

			// 设置合同模板人员信息
			user = userMapper.selectByPrimaryKey(template.getUserId());
			template.setUser(user);
			template.setUserName(user.getName());
		}
		return templates;
	}

	@Override
	public Template selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		Template template = templateMapper.selectByPrimaryKey(id);

		// 设置合同模板类别信息
		Type type = typeMapper.selectByPrimaryKey(template.getTypeId());
		template.setTypeName(type.getType());
		template.setType(type);

		// 设置合同模板人员信息
		User user = userMapper.selectByPrimaryKey(template.getUserId());
		template.setUser(user);
		template.setUserName(user.getName());

		return template;
	}

	@Override
	public int updateByExampleSelective(Template record, TemplateExample example) {
		// TODO Auto-generated method stub
		return templateMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(Template record, TemplateExample example) {
		// TODO Auto-generated method stub
		return templateMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(Template record) {
		// TODO Auto-generated method stub
		return templateMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Template record) {
		// TODO Auto-generated method stub
		return templateMapper.updateByPrimaryKey(record);
	}

}
