package com.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.constant.Constant;
import com.pojo.Earlywarning;
import com.pojo.EarlywarningExample;
import com.pojo.Log;
import com.service.EarlywarningService;
import com.service.LogService;
import com.service.TemplateService;
import com.service.UserService;
import com.util.UUIDUtil;

@Controller
@RequestMapping("/earlywarning")
public class EarlywarningController {

	@Autowired
	private EarlywarningService earlywarningService;

	@Autowired
	private LogService logService;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private UserService userService;

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"MM/dd/yyyy hh:mm aa", Locale.ENGLISH);

	/**
	 * 查询未删除预警信息列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/earlywarninglist", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String earlywarninglist(HttpSession session) {
		EarlywarningExample earlywarningExample = new EarlywarningExample();
		earlywarningExample.setOrderByClause("start_time desc");
		earlywarningExample.createCriteria().andIsDeleteEqualTo(
				Constant.IS_DELETE_0);
		// log日志信息存储
		Log log = new Log();
		log.setId(UUIDUtil.getUUID());
		log.setUserName(userService.selectByPrimaryKey(
				(String) session.getAttribute(Constant.SESSION_USER)).getName());
		log.setTime(df.format(new Date()));
		log.setDescription("查询未删除预警信息列表");
		logService.insertSelective(log);

		return JSON.toJSONString(earlywarningService
				.selectByExample(earlywarningExample));
	}

	/**
	 * 查询实时预警信息列表
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/earlywarninglistrun", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String earlywarninglistrun(HttpSession session)
			throws ParseException {
		EarlywarningExample earlywarningExample = new EarlywarningExample();
		earlywarningExample.setOrderByClause("start_time desc");
		// 拼接查询条件： 1.未删除 2.正在执行
		earlywarningExample.createCriteria()
				.andIsDeleteEqualTo(Constant.IS_DELETE_0) // 未删除
				.andStateEqualTo(Constant.EARYWARNING_STATE_0); // 执行中
		// 查询所有符合list
		List<Earlywarning> earlywarnings = earlywarningService
				.selectByExample(earlywarningExample);

		// 3.日期比较，触发日期大于等于当前时间的，在列表中显示
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));// am时间单独处理
		List<Earlywarning> results = new ArrayList<>();
		for (Earlywarning earlywarning : earlywarnings) {

			if (sdf.parse(earlywarning.getStartTime()).before(new Date())) {
				results.add(earlywarning);
			}
		}

		// log日志信息存储
		Log log = new Log();
		log.setId(UUIDUtil.getUUID());
		log.setUserName(userService.selectByPrimaryKey(
				(String) session.getAttribute(Constant.SESSION_USER)).getName());
		log.setTime(df.format(new Date()));
		log.setDescription("查询实时预警信息列表");
		logService.insertSelective(log);

		// 当前时间大于触发开始时间
		return JSON.toJSONString(results);
	}

	public static void main(String[] args) throws ParseException {
		String source = "05/15/2019 5:00 PM";
		Date startDate;
		String sdfPattern = "MM/dd/yyyy hh:mm aa";
		SimpleDateFormat sdf = new SimpleDateFormat(sdfPattern, Locale.ENGLISH);
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		startDate = sdf.parse(source);
		System.out.println(startDate);
		System.out.println(new Date());
		if (startDate.before(new Date())) {
			System.out.println(1);
		}
		// System.out.println(df.parse("05/15/2019 12:00"));
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deteteById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteById(String id, HttpSession session) {
		Earlywarning earlywarning = earlywarningService.selectByPrimaryKey(id);
		earlywarning.setIsDelete(Constant.IS_DELETE_1);
		if (earlywarningService.updateByPrimaryKeySelective(earlywarning) > 0) {

			// log日志信息存储
			Log log = new Log();
			log.setId(UUIDUtil.getUUID());
			log.setUserName(userService.selectByPrimaryKey(
					(String) session.getAttribute(Constant.SESSION_USER))
					.getName());
			log.setTime(df.format(new Date()));
			log.setDescription("删除合同提醒信息," + earlywarning.getName());
			logService.insertSelective(log);

			return "删除成功";
		} else {
			return "删除失败";
		}
	}

	@RequestMapping("/findById")
	public ModelAndView findById(String id) {
		ModelAndView mv = new ModelAndView("earlywarning_update");
		mv.addObject("earlywarning", earlywarningService.selectByPrimaryKey(id));
		return mv;
	}

	/**
	 * 新增预警提醒信息
	 * 
	 * @param earlywarning
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/earlywarningadd", produces = "text/html;charset=UTF-8")
	public String earlywarningadd(Earlywarning earlywarning, HttpSession session) {

		earlywarning.setIsDelete(Constant.IS_DELETE_0);// 未删除状态

		// earlywarning.setStartTime(df.format(new Date()));
		earlywarning.setUserRoleId((String) session
				.getAttribute(Constant.SESSION_USER));
		earlywarning.setState(Constant.EARYWARNING_STATE_0);// 执行中

		earlywarning.setId(UUIDUtil.getUUID());
		if (earlywarningService.insert(earlywarning) > 0) {

			// log日志信息存储
			Log log = new Log();
			log.setId(UUIDUtil.getUUID());
			log.setUserName(userService.selectByPrimaryKey(
					(String) session.getAttribute(Constant.SESSION_USER))
					.getName());
			log.setTime(df.format(new Date()));
			log.setDescription("新增合同提醒信息," + earlywarning.getName());
			logService.insertSelective(log);

			return "1";
		}
		return "增加失败";
	}

	/**
	 * 批量删除预警信息
	 * 
	 * @param ids
	 * @return
	 */
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
				Earlywarning earlywarning = earlywarningService
						.selectByPrimaryKey(id);
				earlywarning.setIsDelete(Constant.IS_DELETE_1);

				log.setId(UUIDUtil.getUUID());
				log.setDescription("删除合同提醒信息," + earlywarning.getName());
				earlywarningService.updateByPrimaryKeySelective(earlywarning);

			}
			return "删除成功";
		}
		return "删除失败";
	}

	/**
	 * 更新预警信息
	 * 
	 * @param earlywarning
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/earlywarningupdate", produces = "text/html;charset=UTF-8")
	public String earlywarningupdate(Earlywarning earlywarning,
			HttpSession session) {
		if (earlywarningService.updateByPrimaryKeySelective(earlywarning) > 0) {
			// log日志信息存储
			Log log = new Log();
			log.setId(UUIDUtil.getUUID());
			log.setUserName(userService.selectByPrimaryKey(
					(String) session.getAttribute(Constant.SESSION_USER))
					.getName());
			log.setTime(df.format(new Date()));
			log.setDescription("更新合同提醒信息," + earlywarning.getName());

			return "1";
		}
		return "修改失败";
	}

	/**
	 * 关闭预警信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/earlywarningcolse", produces = "text/html;charset=UTF-8")
	public String earlywarningcolse(String id, int state, HttpSession session) {
		Earlywarning earlywarning = earlywarningService.selectByPrimaryKey(id);
		earlywarning.setState(state);
		if (earlywarningService.updateByPrimaryKeySelective(earlywarning) > 0) {

			// log日志信息存储
			Log log = new Log();
			log.setId(UUIDUtil.getUUID());
			log.setUserName(userService.selectByPrimaryKey(
					(String) session.getAttribute(Constant.SESSION_USER))
					.getName());
			log.setTime(df.format(new Date()));
			log.setDescription("关闭合同提醒信息," + earlywarning.getName());

			return "关闭成功";
		}
		return "关闭失败";
	}
}
