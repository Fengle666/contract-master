package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ApprovalMapper;
import com.dao.TemplateMapper;
import com.dao.UserMapper;
import com.pojo.Approval;
import com.pojo.ApprovalExample;
import com.pojo.Template;
import com.pojo.User;
import com.service.ApprovalService;

@Service
public class ApprovalServiceImpl implements ApprovalService {

	@Autowired
	private ApprovalMapper approvalMapper;

	@Autowired
	private TemplateMapper templateMapper;

	@Autowired
	private UserMapper userMapper;

	@Override
	public long countByExample(ApprovalExample example) {
		// TODO Auto-generated method stub
		return approvalMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(ApprovalExample example) {
		// TODO Auto-generated method stub
		return approvalMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return approvalMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Approval record) {
		// TODO Auto-generated method stub
		return approvalMapper.insert(record);
	}

	@Override
	public int insertSelective(Approval record) {
		// TODO Auto-generated method stub
		return approvalMapper.insertSelective(record);
	}

	@Override
	public List<Approval> selectByExample(ApprovalExample example) {
		// TODO Auto-generated method stub

		List<Approval> approvals = approvalMapper.selectByExample(example);
		User user = null;
		Template template = null;
		for (Approval approval : approvals) {
			user = userMapper.selectByPrimaryKey(approval.getUserId());
			approval.setUserName(user.getName());

			template = templateMapper.selectByPrimaryKey(approval
					.getTemplateId());
			approval.setTemplateName(template.getName());
		}

		return approvals;
	}

	@Override
	public Approval selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		Approval approval = approvalMapper.selectByPrimaryKey(id);
		User user = userMapper.selectByPrimaryKey(approval.getUserId());
		approval.setUserName(user.getName());

		Template template = templateMapper.selectByPrimaryKey(approval
				.getTemplateId());
		approval.setTemplateName(template.getName());
		return approval;
	}

	@Override
	public int updateByExampleSelective(Approval record, ApprovalExample example) {
		// TODO Auto-generated method stub
		return approvalMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(Approval record, ApprovalExample example) {
		// TODO Auto-generated method stub
		return approvalMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(Approval record) {
		// TODO Auto-generated method stub
		return approvalMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Approval record) {
		// TODO Auto-generated method stub
		return approvalMapper.updateByPrimaryKey(record);
	}

}
