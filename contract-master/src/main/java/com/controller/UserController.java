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
import com.pojo.Log;
import com.pojo.User;
import com.pojo.UserExample;
import com.service.LogService;
import com.service.UserService;
import com.util.UUIDUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private LogService logService;

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 登录
	 * 
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String login(User user, HttpSession session) {
		// 设置查找条件
		UserExample userExample = new UserExample();
		userExample.createCriteria().andPhoneEqualTo(user.getPhone())
				.andPasswordEqualTo(user.getPassword())
				.andIsDeleteEqualTo(Constant.IS_DELETE_0);
		if (userService.selectByExample(userExample).size() > 0) {
			// 把登录信息存入session
			session.setAttribute(Constant.SESSION_USER, userService
					.selectByExample(userExample).get(0).getId());
			session.setAttribute(
					Constant.SESSION_USER_ROLE,
					String.valueOf(userService.selectByExample(userExample)
							.get(0).getRoleId()));

			// log日志信息存储
			Log log = new Log();
			log.setId(UUIDUtil.getUUID());
			log.setUserName(userService.selectByExample(userExample).get(0)
					.getName());
			log.setTime(df.format(new Date()));
			log.setDescription("登录系统");
			logService.insertSelective(log);

			return "1";
		}
		return "账号或是密码错误!";
	}

	/**
	 * 退出系统
	 * 
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/loginout")
	public String loginout(HttpSession session) {

		Log log = new Log();
		log.setId(UUIDUtil.getUUID());
		log.setUserName(userService.selectByPrimaryKey(
				(String) session.getAttribute(Constant.SESSION_USER)).getName());
		log.setTime(df.format(new Date()));
		log.setDescription("退出系统");
		logService.insertSelective(log);

		session.removeAttribute(Constant.SESSION_USER);
		session.removeAttribute(Constant.SESSION_USER_ROLE);
		return "login";
	}

	/**
	 * 增加用户信息
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/useradd", produces = "text/html;charset=UTF-8")
	public String useradd(User user, HttpSession session) {
		user.setId(UUIDUtil.getUUID());
		// 手机号查重
		UserExample userExample = new UserExample();
		userExample.createCriteria().andPhoneEqualTo(user.getPhone())
				.andIsDeleteEqualTo(Constant.IS_DELETE_0);
		user.setPassword("123456");
		if (userService.selectByExample(userExample).size() > 0) {
			return "手机号已被占用";
		}
		user.setIsDelete(Constant.IS_DELETE_0);
		if (userService.insert(user) > 0) {

			Log log = new Log();
			log.setId(UUIDUtil.getUUID());
			log.setUserName(userService.selectByPrimaryKey(
					(String) session.getAttribute(Constant.SESSION_USER))
					.getName());
			log.setTime(df.format(new Date()));
			log.setDescription("新增用户:" + user.getName());
			logService.insertSelective(log);

			return "1";
		}
		return "增加失败";
	}

	/**
	 * 用户列表
	 * 
	 * @return
	 */
	@RequestMapping("/userlist")
	@ResponseBody
	public String userlist(HttpSession session) {

		Log log = new Log();
		log.setId(UUIDUtil.getUUID());
		log.setUserName(userService.selectByPrimaryKey(
				(String) session.getAttribute(Constant.SESSION_USER)).getName());
		log.setTime(df.format(new Date()));
		log.setDescription("查询用户列表");
		logService.insertSelective(log);

		UserExample phoneExample = new UserExample();
		// 不能被查到删除的角色信息以及系统管理员信息
		phoneExample.createCriteria().andIdNotEqualTo(Constant.SYS_ADMIN_ID)
				.andIsDeleteEqualTo(Constant.IS_DELETE_0);
		return JSON.toJSONString(userService.selectByExample(phoneExample));
	}

	/**
	 * 进入修改用户信息页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findById")
	public ModelAndView findById(String id) {
		ModelAndView mv = new ModelAndView("user_update");
		mv.addObject("user", userService.selectByPrimaryKey(id));
		return mv;
	}

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userupdate", produces = "text/html;charset=UTF-8")
	public String userupdate(User user, HttpSession session) {
		// 查询原用户信息
		UserExample updateExample = new UserExample();
		updateExample.createCriteria().andIdEqualTo(user.getId());
		User user2 = userService.selectByPrimaryKey(user.getId());
		if (user2 != null) {
			// 如果手机号更改了,先对手机号进行查重操作，然后更改。
			if (user2.getPhone() != user.getPhone()
					&& !user2.getPhone().equals(user.getPhone())) {
				// 手机号查重
				UserExample phoneExample = new UserExample();
				phoneExample.createCriteria().andPhoneEqualTo(user.getPhone());
				if (userService.selectByExample(phoneExample).size() > 0) {
					return "手机号已被占用";
				} else {
					if (userService.updateByExampleSelective(user,
							updateExample) > 0) {

						Log log = new Log();
						log.setId(UUIDUtil.getUUID());
						log.setUserName(userService.selectByPrimaryKey(
								(String) session
										.getAttribute(Constant.SESSION_USER))
								.getName());
						log.setTime(df.format(new Date()));
						log.setDescription("修改用户,用户名：" + user.getName());
						logService.insertSelective(log);

						return "1";
					}
				}
			}
			if (userService.updateByExampleSelective(user, updateExample) > 0) {

				Log log = new Log();
				log.setId(UUIDUtil.getUUID());
				log.setUserName(userService.selectByPrimaryKey(
						(String) session.getAttribute(Constant.SESSION_USER))
						.getName());
				log.setTime(df.format(new Date()));
				log.setDescription("修改用户,用户名：" + user.getName());
				logService.insertSelective(log);

				return "1";
			}
		}
		return "修改失败";
	}

	/**
	 * 删除用户信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deteteById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteById(String id, HttpSession session) {
		User user = userService.selectByPrimaryKey(id);
		user.setIsDelete(Constant.IS_DELETE_1);
		if (userService.updateByPrimaryKeySelective(user) > 0) {
			Log log = new Log();
			log.setId(UUIDUtil.getUUID());
			log.setUserName(userService.selectByPrimaryKey(
					(String) session.getAttribute(Constant.SESSION_USER))
					.getName());
			log.setTime(df.format(new Date()));
			log.setDescription("删除用户,用户名：" + user.getName());
			logService.insertSelective(log);

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
	public String deteteByIds(String[] ids, HttpSession session) {
		if (ids.length > 0) {
			User user = null;

			Log log = new Log();
			log.setId(UUIDUtil.getUUID());
			log.setUserName(userService.selectByPrimaryKey(
					(String) session.getAttribute(Constant.SESSION_USER))
					.getName());

			for (String id : ids) {
				user = userService.selectByPrimaryKey(id);
				user.setIsDelete(Constant.IS_DELETE_1);

				log.setTime(df.format(new Date()));
				log.setDescription("删除用户,用户名：" + user.getName());
				logService.insertSelective(log);
				userService.updateByPrimaryKeySelective(user);
			}
			return "删除成功";
		}
		return "删除失败";
	}

	/**
	 * 进入修改用户信息页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findByIdToPassword")
	public ModelAndView findByIdToPassword(String id) {
		ModelAndView mv = new ModelAndView("user_password");
		mv.addObject("user", userService.selectByPrimaryKey(id));
		return mv;
	}

	/**
	 * 重置密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "/resetPassword", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String resetPassword(User user) {
		user.setPassword("123456");
		if (userService.updateByPrimaryKeySelective(user) > 0) {
			Log log = new Log();
			log.setId(UUIDUtil.getUUID());
			log.setUserName(user.getName());
			log.setTime(df.format(new Date()));
			log.setDescription("重置密码");
			logService.insertSelective(log);

			return "1";
		}
		return "修改失败";
	}

	@ResponseBody
	@RequestMapping(value = "/userInfo", produces = "text/html;charset=UTF-8")
	public String userInfo(HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		User user = userService.selectByPrimaryKey(userid);
		return JSON.toJSON(user).toString();
	}

	@RequestMapping("/findByPhone")
	@ResponseBody
	public String findByPhone(User user) {
		// 手机号查重
		UserExample userExample = new UserExample();
		userExample.createCriteria().andPhoneEqualTo(user.getPhone());
		if (userService.selectByExample(userExample).size() > 0) {
			return "1";
		}
		return "0";
	}

	/**
	 * 按照角色查询
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/findByroleId", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findByroleId(int roleId) {
		// 手机号查重
		UserExample userExample = new UserExample();
		userExample.createCriteria().andRoleIdEqualTo(String.valueOf(roleId));
		return JSON.toJSONString(userService.selectByExample(userExample));
	}

}
