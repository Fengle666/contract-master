package com.controller;

import com.pojo.Contract;
import com.vo.ContractAdd;
import com.vo.ContractAddVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.pojo.Log;
import com.pojo.LogExample;
import com.service.LogService;

import java.util.List;

@Controller
@RequestMapping("/log")
public class LogController {

	@Autowired
	LogService logService;

	@RequestMapping(value = "/getDownload", method = RequestMethod.GET)
	@ResponseBody
	public List<ContractAddVO> getDownload() {
		return logService.getDownload();
	}


	/**
	 * 日志列表
	 * 
	 * @return
	 */
	@RequestMapping("/loglist")
	@ResponseBody
	public String loglist() {
		LogExample logExample = new LogExample();
		// 设置查询条件，日志未被删除的
		logExample.setOrderByClause("time desc");
		return JSON.toJSONString(logService.selectByExample(logExample));
	}

	/**
	 * 进入修改日志信息页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findById")
	public ModelAndView findById(String id) {
		ModelAndView mv = new ModelAndView("log_update");
		mv.addObject("log", logService.selectByPrimaryKey(id));
		return mv;
	}

	/**
	 * 修改日志信息
	 * 
	 * @param log
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/logupdate", produces = "text/html;charset=UTF-8")
	public String logupdate(Log log) {
		LogExample updateExample = new LogExample();
		if (logService.updateByExampleSelective(log, updateExample) > 0) {
			return "1";
		}
		return "修改失败";
	}

	/**
	 * 删除日志信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deteteById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteById(String id) {
		Log log = logService.selectByPrimaryKey(id);
		if (logService.updateByPrimaryKeySelective(log) > 0) {
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
			Log log = null;
			for (String id : ids) {
				log = logService.selectByPrimaryKey(id);
				logService.updateByPrimaryKeySelective(log);
			}
			return "删除成功";
		}
		return "删除失败";
	}

}
