package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.constant.Constant;
import com.pojo.Role;
import com.pojo.RoleExample;
import com.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	RoleService roleService;

	/**
	 * 增加分类信息
	 * 
	 * @param role
	 * @return
	 */
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/roleadd", produces = "text/html;charset=UTF-8")
	 * public String roleadd(Role role) { role.setId(UUIDUtil.getUUID());
	 * role.setState(Constant.TYPE_STATE_USE); if (roleService.insert(role) > 0)
	 * { return "1"; } return "增加失败"; }
	 */

	/**
	 * 分类列表
	 * 
	 * @return
	 */
	@RequestMapping("/rolelist")
	@ResponseBody
	public String rolelist() {
		RoleExample roleExample = new RoleExample();
		// 设置查询条件，分类未被删除的
		roleExample.createCriteria().andIsDeleteEqualTo(Constant.IS_DELETE_0);
		return JSON.toJSONString(roleService.selectByExample(roleExample));
	}

	/**
	 * 进入修改分类信息页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findById")
	public ModelAndView findById(String id) {
		ModelAndView mv = new ModelAndView("role_update");
		mv.addObject("role", roleService.selectByPrimaryKey(id));
		return mv;
	}

	/**
	 * 修改分类信息
	 * 
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/roleupdate", produces = "text/html;charset=UTF-8")
	public String roleupdate(Role role) {
		RoleExample updateExample = new RoleExample();
		if (roleService.updateByExampleSelective(role, updateExample) > 0) {
			return "1";
		}
		return "修改失败";
	}

	/**
	 * 删除分类信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deteteById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteById(String id) {
		Role role = roleService.selectByPrimaryKey(id);
		role.setIsDelete(Constant.IS_DELETE_1);
		if (roleService.updateByPrimaryKeySelective(role) > 0) {
			return "删除成功";
		}
		return "删除失败";
	}

	/**
	 * 批量删除用户信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deteteByIds", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteByIds(String[] ids) {
		if (ids.length > 0) {
			Role role = null;
			for (String id : ids) {
				role = roleService.selectByPrimaryKey(id);
				role.setIsDelete(Constant.IS_DELETE_1);
				roleService.updateByPrimaryKeySelective(role);
			}
			return "删除成功";
		}
		return "删除失败";
	}

}
