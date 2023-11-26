package com.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pojo.Template;
import com.pojo.TemplateExample;

public interface TemplateService {
	long countByExample(TemplateExample example);

	int deleteByExample(TemplateExample example);

	int deleteByPrimaryKey(String id);

	int insert(Template record);

	int insertSelective(Template record);

	List<Template> selectByExample(TemplateExample example);

	Template selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Template record,
			@Param("example") TemplateExample example);

	int updateByExample(@Param("record") Template record,
			@Param("example") TemplateExample example);

	int updateByPrimaryKeySelective(Template record);

	int updateByPrimaryKey(Template record);
}
