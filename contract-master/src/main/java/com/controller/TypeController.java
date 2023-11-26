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
import com.pojo.Type;
import com.pojo.TypeExample;
import com.service.LogService;
import com.service.TypeService;
import com.service.UserService;
import com.util.UUIDUtil;

@Controller
@RequestMapping("/type")
public class TypeController {

	@Autowired
	private TypeService typeService;

	@Autowired
	private LogService logService;

	@Autowired
	private UserService userService;

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@RequestMapping(value = "/typelist", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String typelist(HttpSession session) {

		// log日志信息存储
		Log log = new Log();
		log.setId(UUIDUtil.getUUID());
		log.setUserName(userService.selectByPrimaryKey(
				(String) session.getAttribute(Constant.SESSION_USER)).getName());
		log.setTime(df.format(new Date()));
		log.setDescription("查询合同类型列表");
		logService.insertSelective(log);

		TypeExample typeExample = new TypeExample();
		typeExample.createCriteria().andStateEqualTo(Constant.IS_DELETE_0);
		return JSON.toJSONString(typeService.selectByExample(typeExample));
	}

	/**
	 * 伪删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deteteById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteById(String id, HttpSession session) {
		Type type = typeService.selectByPrimaryKey(id);
		type.setState(Constant.IS_DELETE_1);
		if (typeService.updateByPrimaryKeySelective(type) > 0) {

			// log日志信息存储
			Log log = new Log();
			log.setId(UUIDUtil.getUUID());
			log.setUserName(userService.selectByPrimaryKey(
					(String) session.getAttribute(Constant.SESSION_USER))
					.getName());
			log.setTime(df.format(new Date()));
			log.setDescription("删除合同类型," + type.getType());
			logService.insertSelective(log);

			return "删除成功";
		} else {
			return "删除失败";
		}
	}

	@RequestMapping("/findById")
	public ModelAndView findById(String id) {
		ModelAndView mv = new ModelAndView("type_update");
		mv.addObject("type", typeService.selectByPrimaryKey(id));
		return mv;
	}

	@ResponseBody
	@RequestMapping(value = "/typeadd", produces = "text/html;charset=UTF-8")
	public String useradd(Type type, HttpSession session) {
		type.setId(UUIDUtil.getUUID());
		type.setState(Constant.IS_DELETE_0);
		if (typeService.insert(type) > 0) {
			// log日志信息存储
			Log log = new Log();
			log.setId(UUIDUtil.getUUID());
			log.setUserName(userService.selectByPrimaryKey(
					(String) session.getAttribute(Constant.SESSION_USER))
					.getName());
			log.setTime(df.format(new Date()));
			log.setDescription("新增合同类型," + type.getType());
			logService.insertSelective(log);
			return "1";
		}
		return "增加失败";
	}

	@RequestMapping(value = "/deteteByIds", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteByIds(String[] ids, HttpSession session) {
		if (ids.length > 0) {

			// log日志信息存储
			Log log = new Log();

			log.setUserName(userService.selectByPrimaryKey(
					(String) session.getAttribute(Constant.SESSION_USER))
					.getName());
			log.setTime(df.format(new Date()));

			for (String id : ids) {
				Type type = typeService.selectByPrimaryKey(id);
				type.setState(Constant.IS_DELETE_1);

				log.setId(UUIDUtil.getUUID());
				log.setDescription("删除合同类型," + type.getType());
				logService.insertSelective(log);

				typeService.updateByPrimaryKeySelective(type);
			}
			return "删除成功";
		}
		return "删除失败";
	}

	@ResponseBody
	@RequestMapping(value = "/typeupdate", produces = "text/html;charset=UTF-8")
	public String typeupdate(Type type, HttpSession session) {
		if (typeService.updateByPrimaryKeySelective(type) > 0) {
			// log日志信息存储
			Log log = new Log();
			log.setId(UUIDUtil.getUUID());
			log.setUserName(userService.selectByPrimaryKey(
					(String) session.getAttribute(Constant.SESSION_USER))
					.getName());
			log.setTime(df.format(new Date()));
			log.setDescription("修改合同类型," + type.getType());
			logService.insertSelective(log);

			return "1";
		}
		return "修改失败";
	}
}
