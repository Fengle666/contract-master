package com.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.constant.Constant;
import com.pojo.Log;
import com.pojo.Template;
import com.pojo.TemplateExample;
import com.service.LogService;
import com.service.TemplateService;
import com.service.UserService;
import com.util.FileUtil;
import com.util.UUIDUtil;

@Controller
@RequestMapping("/template")
public class TemplateController {

	@Autowired
	private TemplateService templateService;

	@Autowired
	private LogService logService;

	@Autowired
	private UserService userService;

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 增加模板信息
	 * 
	 * @param template
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/templateadd", produces = "text/html;charset=UTF-8")
	public String templateadd(Template template, MultipartFile file,
			HttpServletRequest request, HttpSession session) throws Exception {
		File newFile = new File(request.getServletContext().getRealPath(
				"/template"));
		if (!newFile.exists()) {
			newFile.mkdirs();
		}
		// 上传文件路径及文件名
		String fileName = FileUtil.executeUpload(newFile.getAbsolutePath()
				+ "/", file);

		template.setId(UUIDUtil.getUUID());
		template.setPath("template/" + fileName);
		template.setState(Constant.TEMPLATE_STATE_0);// 未审核
		template.setIsDelete(Constant.IS_DELETE_0);// 设置未删除状态
		template.setUserId((String) session.getAttribute(Constant.SESSION_USER));// 设置合同模板起草人
		template.setTime(df.format(new Date()));// 设置增加时间
		if (templateService.insert(template) > 0) {

			// log日志信息存储
			Log log = new Log();
			log.setId(UUIDUtil.getUUID());
			log.setUserName(userService.selectByPrimaryKey(
					(String) session.getAttribute(Constant.SESSION_USER))
					.getName());
			log.setTime(df.format(new Date()));
			log.setDescription("新增合同模板," + template.getName());
			logService.insertSelective(log);

			return "1";
		}
		return "增加失败";
	}

	/**
	 * 模板列表
	 * 
	 * @return
	 */
	@RequestMapping("/templatelist")
	@ResponseBody
	public String templatelist(HttpSession session) {
		// log日志信息存储
		Log log = new Log();
		log.setId(UUIDUtil.getUUID());
		log.setUserName(userService.selectByPrimaryKey(
				(String) session.getAttribute(Constant.SESSION_USER)).getName());
		log.setTime(df.format(new Date()));
		log.setDescription("查看模板列表");
		logService.insertSelective(log);
		TemplateExample templateExample = new TemplateExample();
		// 设置查询条件，模板未被删除的/以及不查询审核通过的
		templateExample.createCriteria()
				.andIsDeleteEqualTo(Constant.IS_DELETE_0)
				.andStateNotEqualTo(Constant.TEMPLATE_STATE_1);
		return JSON.toJSONString(templateService
				.selectByExample(templateExample));
	}

	/**
	 * 管理员审核模板列表
	 * 
	 * @return
	 */
	@RequestMapping("/templatelistadmin")
	@ResponseBody
	public String templatelistadmin(HttpSession session) {
		// log日志信息存储
		Log log = new Log();
		log.setId(UUIDUtil.getUUID());
		log.setUserName(userService.selectByPrimaryKey(
				(String) session.getAttribute(Constant.SESSION_USER)).getName());
		log.setTime(df.format(new Date()));
		log.setDescription("管理员查看需审核合同模板列表");
		logService.insertSelective(log);
		TemplateExample templateExample = new TemplateExample();
		// 设置查询条件，模板未被删除的、未审核的
		templateExample.createCriteria()
				.andIsDeleteEqualTo(Constant.IS_DELETE_0)
				.andStateEqualTo(Constant.TEMPLATE_STATE_0);
		return JSON.toJSONString(templateService
				.selectByExample(templateExample));
	}

	/**
	 * 合同模板库
	 * 
	 * @return
	 */
	@RequestMapping("/templatelistlib")
	@ResponseBody
	public String templatelistlib(HttpSession session) {
		// log日志信息存储
		Log log = new Log();
		log.setId(UUIDUtil.getUUID());
		log.setUserName(userService.selectByPrimaryKey(
				(String) session.getAttribute(Constant.SESSION_USER)).getName());
		log.setTime(df.format(new Date()));
		log.setDescription("查看合同模板库信息");
		logService.insertSelective(log);
		TemplateExample templateExample = new TemplateExample();
		// 设置查询条件，模板未被删除的、审核通过审核的
		templateExample.createCriteria()
				.andIsDeleteEqualTo(Constant.IS_DELETE_0)
				.andStateEqualTo(Constant.TEMPLATE_STATE_1);
		return JSON.toJSONString(templateService
				.selectByExample(templateExample));
	}

	/**
	 * 进入修改模板信息页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findById")
	public ModelAndView findById(String id) {
		ModelAndView mv = new ModelAndView("template_update");
		mv.addObject("template", templateService.selectByPrimaryKey(id));
		return mv;
	}

	/**
	 * 修改模板信息(修改模板时把状态重置)
	 * 
	 * @param template
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/templateupdate", produces = "text/html;charset=UTF-8")
	public String templateupdate(Template template, MultipartFile file,
			HttpServletRequest request, HttpSession session) throws Exception {
		if (file.isEmpty()) {// 如果未修改模板文件
			template.setState(Constant.TEMPLATE_STATE_0);// 重置审核状态
			if (templateService.updateByPrimaryKeySelective(template) > 0) {

				// log日志信息存储
				Log log = new Log();
				log.setId(UUIDUtil.getUUID());
				log.setUserName(userService.selectByPrimaryKey(
						(String) session.getAttribute(Constant.SESSION_USER))
						.getName());
				log.setTime(df.format(new Date()));
				log.setDescription("修改合同模板信息，未修改模板文件");
				logService.insertSelective(log);

				return "1";
			}
			return "修改失败";
		}
		File newFile = new File(request.getServletContext().getRealPath(
				"/template"));
		if (!newFile.exists()) {
			newFile.mkdirs();
		}
		// 上传文件路径及文件名
		String fileName = FileUtil.executeUpload(newFile.getAbsolutePath()
				+ "/", file);
		template.setPath("template/" + fileName);
		template.setState(Constant.TEMPLATE_STATE_0);// 重置审核状态
		if (templateService.updateByPrimaryKeySelective(template) > 0) {

			// log日志信息存储
			Log log = new Log();
			log.setId(UUIDUtil.getUUID());
			log.setUserName(userService.selectByPrimaryKey(
					(String) session.getAttribute(Constant.SESSION_USER))
					.getName());
			log.setTime(df.format(new Date()));
			log.setDescription("修改合同模板信息，修改模板文件");
			logService.insertSelective(log);

			return "1";
		}
		return "修改失败";
	}

	/**
	 * 删除模板信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deteteById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteById(String id, HttpSession session) {
		Template template = templateService.selectByPrimaryKey(id);
		template.setIsDelete(Constant.IS_DELETE_1);
		if (templateService.updateByPrimaryKeySelective(template) > 0) {
			// log日志信息存储
			Log log = new Log();
			log.setId(UUIDUtil.getUUID());
			log.setUserName(userService.selectByPrimaryKey(
					(String) session.getAttribute(Constant.SESSION_USER))
					.getName());
			log.setTime(df.format(new Date()));
			log.setDescription("删除合同模板信息：" + template.getName());
			logService.insertSelective(log);

			return "删除成功";
		}
		return "删除失败";
	}

	/**
	 * 批量删除模板信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deteteByIds", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteByIds(String[] ids, HttpSession session) {
		if (ids.length > 0) {
			Template template = null;
			// log日志信息存储
			Log log = new Log();
			for (String id : ids) {
				template = templateService.selectByPrimaryKey(id);
				template.setIsDelete(Constant.IS_DELETE_1);
				templateService.updateByPrimaryKeySelective(template);

				log.setId(UUIDUtil.getUUID());
				log.setUserName(userService.selectByPrimaryKey(
						(String) session.getAttribute(Constant.SESSION_USER))
						.getName());
				log.setTime(df.format(new Date()));
				log.setDescription("删除合同模板信息：" + template.getName());
				logService.insertSelective(log);

			}
			return "删除成功";
		}
		return "删除失败";
	}

	/**
	 * 模板下载
	 * 
	 * @param request
	 * @param id
	 *            模板id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/fileDownLoad")
	public ResponseEntity<byte[]> fileDownLoad(HttpServletRequest request,
			String id, HttpSession session) throws Exception {

		Template template = templateService.selectByPrimaryKey(id);
		ServletContext servletContext = request.getServletContext();
		String realPath = servletContext.getRealPath("/" + template.getPath());// 得到文件所在位置
		InputStream in = new FileInputStream(new File(realPath));// 将该文件加入到输入流之中
		byte[] body = null;
		body = new byte[in.available()];// 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数
		in.read(body);// 读入到输入流里面

		String fileName = new String(template.getName().getBytes("gbk"),
				"iso8859-1");// 防止中文乱码
		HttpHeaders headers = new HttpHeaders();// 设置响应头
		headers.add(
				"Content-Disposition",
				"attachment;filename="
						+ fileName
						+ template.getPath().substring(
								template.getPath().lastIndexOf("."),
								template.getPath().length()));
		HttpStatus statusCode = HttpStatus.OK;// 设置响应吗
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body,
				headers, statusCode);

		// log日志信息存储
		Log log = new Log();
		log.setId(UUIDUtil.getUUID());
		log.setUserName(userService.selectByPrimaryKey(
				(String) session.getAttribute(Constant.SESSION_USER)).getName());
		log.setTime(df.format(new Date()));
		log.setDescription("下载合同模板：" + template.getName());
		logService.insertSelective(log);

		return response;

		// public ResponseEntity（T body，
		// MultiValueMap < String，String > headers，
		// HttpStatus statusCode）
		// HttpEntity使用给定的正文，标题和状态代码创建一个新的。
		// 参数：
		// body - 实体机构
		// headers - 实体头
		// statusCode - 状态码
	}
}
