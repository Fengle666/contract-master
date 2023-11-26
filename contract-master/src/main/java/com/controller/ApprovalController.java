package com.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.constant.Constant;
import com.pojo.Approval;
import com.pojo.ApprovalExample;
import com.pojo.Log;
import com.pojo.Template;
import com.service.ApprovalService;
import com.service.LogService;
import com.service.TemplateService;
import com.service.UserService;
import com.util.UUIDUtil;

@Controller
@RequestMapping("/approval")
public class ApprovalController {

	@Autowired
	private ApprovalService approvalService;

	@Autowired
	private LogService logService;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private UserService userService;

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@RequestMapping(value = "/approvallist", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String approvallist(HttpSession session) {
		ApprovalExample approvalExample = new ApprovalExample();
		approvalExample.setOrderByClause("time desc");
		// approvalExample.createCriteria().andStateEqualTo(Constant.IS_DELETE_0);

		// log日志信息存储
		Log log = new Log();
		log.setId(UUIDUtil.getUUID());
		log.setUserName(userService.selectByPrimaryKey(
				(String) session.getAttribute(Constant.SESSION_USER)).getName());
		log.setTime(df.format(new Date()));
		log.setDescription("查看审批记录列表");
		logService.insertSelective(log);

		return JSON.toJSONString(approvalService
				.selectByExample(approvalExample));
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deteteById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteById(String id) {
		Approval approval = approvalService.selectByPrimaryKey(id);
		// approval.setState(Constant.IS_DELETE_1);
		if (approvalService.updateByPrimaryKeySelective(approval) > 0) {
			return "删除成功";
		} else {
			return "删除失败";
		}
	}

	@RequestMapping("/findById")
	public ModelAndView findById(String id) {
		ModelAndView mv = new ModelAndView("approval_update");
		mv.addObject("approval", approvalService.selectByPrimaryKey(id));
		return mv;
	}

	/**
	 * 新增审批(支持批量审批)
	 * 
	 * @param approval
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/approvaladd", produces = "text/html;charset=UTF-8")
	public String useradd(String templateIds, Approval approval,
			HttpSession session) {

		approval.setIsDelete(Constant.IS_DELETE_0);// 未删除状态
		approval.setTime(df.format(new Date()));
		approval.setUserId((String) session.getAttribute(Constant.SESSION_USER));
		if (!"".equals(templateIds)) {// 不为空判断
			for (String templateId : templateIds.split(",")) {
				approval.setId(UUIDUtil.getUUID());
				approval.setTemplateId(templateId);
				if (approvalService.insert(approval) > 0) {
					// 如果审核通过，更改模板状态
					Template template = templateService
							.selectByPrimaryKey(approval.getTemplateId());
					template.setState(approval.getState());
					templateService.updateByPrimaryKeySelective(template);
				}
			}
			return "1";
		}
		return "增加失败";
	}

	@RequestMapping(value = "/deteteByIds", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteByIds(String[] ids) {
		if (ids.length > 0) {
			for (String id : ids) {
				Approval approval = approvalService.selectByPrimaryKey(id);
				// approval.setState(Constant.IS_DELETE_1);
				approvalService.updateByPrimaryKeySelective(approval);
			}
			return "删除成功";
		}
		return "删除失败";
	}

	@ResponseBody
	@RequestMapping(value = "/approvalupdate", produces = "text/html;charset=UTF-8")
	public String approvalupdate(Approval approval) {
		if (approvalService.updateByPrimaryKeySelective(approval) > 0) {
			return "1";
		}
		return "修改失败";
	}

	public static void main(String[] args) {
		String string = "1";
		for (String string2 : string.split(",")) {
			System.out.println(string2);
		}
		System.out.println(string.split(",").toString());
	}
}
