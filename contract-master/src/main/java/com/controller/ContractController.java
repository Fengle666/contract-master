package com.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.vo.ContractAdd;
import com.vo.ContractAddVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.constant.Constant;
import com.pojo.Contract;
import com.pojo.ContractExample;
import com.pojo.Earlywarning;
import com.pojo.Enclosure;
import com.pojo.EnclosureExample;
import com.pojo.Log;
import com.service.ContractService;
import com.service.EarlywarningService;
import com.service.EnclosureService;
import com.service.LogService;
import com.service.UserService;
import com.util.FileUtil;
import com.util.UUIDUtil;

@Controller
@RequestMapping("/contract")
public class ContractController {

	@Autowired
	private ContractService contractService;

	@Autowired
	private LogService logService;

	@Autowired
	private UserService userService;

	@Autowired
	private EnclosureService enclosureService;

	@Autowired
	private EarlywarningService earlywarningService;

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@ResponseBody
	@RequestMapping(value = "/getContractAdd", method = RequestMethod.GET)
	public List<ContractAddVO> getContractAdd(){
		return contractService.getContractAdd();
	}

	/**
	 * 增加合同信息
	 * 
	 * @param contract
	 *            合同对象
	 * @param files
	 *            文件对象
	 * @param request
	 * @param session
	 * @param earlywarning
	 *            提醒信息对象
	 * @param ename
	 *            提醒名称
	 * @param ename
	 *            提醒开始时间
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/contractadd", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	public String contractadd(Contract contract,
			@RequestParam("files") MultipartFile[] files,
			HttpServletRequest request, HttpSession session,
			Earlywarning earlywarning, String ename, String estartTime)
			throws Exception {

		// 创建上传文件目录
		File newFile = new File(request.getServletContext().getRealPath(
				"/contract"));
		if (!newFile.exists()) {
			newFile.mkdirs();
		}

		// 上传文件路径及文件名
		Enclosure enclosure = null;// 合同附件信息
		String id = UUIDUtil.getUUID();

		// 上传文件并执行把合同附近放入合同附件表
		for (int i = 0; i < files.length; i++) {
			if (files[i] != null) {
				String fileName = FileUtil.executeUpload(
						newFile.getAbsolutePath() + "/", files[i]);
				// 增加合同附件
				enclosure = new Enclosure();
				enclosure.setId(UUIDUtil.getUUID());
				enclosure.setContractId(id);
				enclosure.setIsDelete(Constant.IS_DELETE_0);
				enclosure.setPath("contract/" + fileName);// 附件路径
				enclosure.setTime(df.format(new Date()));
				enclosure.setName(files[i].getOriginalFilename());// 文件名
				enclosureService.insertSelective(enclosure);
			}
		}
		contract.setId(id);

		contract.setState(Constant.ENCLOSURE_STATE_0);// 未进行状态
		contract.setIsDelete(Constant.IS_DELETE_0);// 未删除
		contract.setChangeStatus(Constant.ENCLOSURE_CHANGESTATE_0);// 未改变
		contract.setPayState(Constant.ENCLOSURE_PAYSTATE_0);// 未付清
		contract.setUserRoleId((String) session
				.getAttribute(Constant.SESSION_USER));// 合同起草人

		if (contractService.insert(contract) > 0) {
			// log日志信息存储
			Log log = new Log();
			log.setUserName(userService.selectByPrimaryKey(
					(String) session.getAttribute(Constant.SESSION_USER))
					.getName());
			log.setTime(df.format(new Date()));
			// 提醒时间不为空的时候，增加合同提醒信息
			if (!"".equals(estartTime)) {
				earlywarning.setId(UUIDUtil.getUUID());
				earlywarning.setName(ename);// 提醒名称
				earlywarning.setUserRoleId((String) session
						.getAttribute(Constant.SESSION_USER));// 提请设置人
				earlywarning.setContractId(id);// 提醒 中合同id
				earlywarning.setIsDelete(Constant.IS_DELETE_0);// 未删除
				earlywarning.setState(Constant.EARYWARNING_STATE_0);// 未启动
				earlywarning.setStartTime(ename);
				earlywarningService.insertSelective(earlywarning);
				// 增加提醒信息日志存储
				log.setId(UUIDUtil.getUUID());
				log.setDescription("新增提醒信息," + ename);
				logService.insertSelective(log);
			}

			log.setId(UUIDUtil.getUUID());
			log.setDescription("新增合同合同," + contract.getName());
			logService.insertSelective(log);

			return "1";
		}
		return "增加失败";
	}

	/**
	 * 合同列表
	 * 
	 * @return
	 */
	@RequestMapping("/contractlist")
	@ResponseBody
	public String contractlist(HttpSession session) {
		// log日志信息存储
		Log log = new Log();
		log.setId(UUIDUtil.getUUID());
		log.setUserName(userService.selectByPrimaryKey(
				(String) session.getAttribute(Constant.SESSION_USER)).getName());
		log.setTime(df.format(new Date()));
		log.setDescription("查看合同列表");
		logService.insertSelective(log);
		ContractExample contractExample = new ContractExample();
		contractExample.setOrderByClause("state");
		// 设置查询条件，合同未被删除的
		contractExample.createCriteria().andIsDeleteEqualTo(
				Constant.IS_DELETE_0);
		return JSON.toJSONString(contractService
				.selectByExample(contractExample));
	}

	/**
	 * 查询执行中合同列表
	 * 
	 * @return
	 */
	@RequestMapping("/contractlistadd")
	@ResponseBody
	public String contractlistadd(HttpSession session) {
		// log日志信息存储
		ContractExample contractExample = new ContractExample();
		// 设置查询条件，合同未被删除的
		contractExample.createCriteria()
				.andIsDeleteEqualTo(Constant.IS_DELETE_0)
				.andStateEqualTo(Constant.ENCLOSURE_STATE_0);
		return JSON.toJSONString(contractService
				.selectByExample(contractExample));
	}

	/**
	 * 进入修改合同信息页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findById")
	public ModelAndView findById(String id, HttpSession session) {
		// log日志信息存储
		Log log = new Log();
		log.setId(UUIDUtil.getUUID());
		log.setUserName(userService.selectByPrimaryKey(
				(String) session.getAttribute(Constant.SESSION_USER)).getName());
		log.setTime(df.format(new Date()));
		log.setDescription("进入修改合同页面");
		logService.insertSelective(log);

		ModelAndView mv = new ModelAndView("contract_update");
		mv.addObject("contract", contractService.selectByPrimaryKey(id));
		return mv;
	}

	/**
	 * 进入查看合同详情信息页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findByIdInfo")
	public ModelAndView findByIdInfo(String id, HttpSession session) {
		ModelAndView mv = new ModelAndView("contract_info");
		// log日志信息存储
		Log log = new Log();
		log.setId(UUIDUtil.getUUID());
		log.setUserName(userService.selectByPrimaryKey(
				(String) session.getAttribute(Constant.SESSION_USER)).getName());
		log.setTime(df.format(new Date()));
		log.setDescription("查看合同详情");
		logService.insertSelective(log);
		mv.addObject("contract", contractService.selectByPrimaryKey(id));
		return mv;
	}

	/**
	 * 修改合同信息
	 * 
	 * @param contract
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/contractupdate", produces = "text/html;charset=UTF-8")
	public String contractupdate(Contract contract,
			@RequestParam("files") MultipartFile[] files,
			HttpServletRequest request, HttpSession session) throws Exception {
		/**
		 * 1. 如果修改合同信息时，未上传合同附件
		 */
		if (files[0].isEmpty()) {// 如果未修改合同文件
			if (contractService.updateByPrimaryKeySelective(contract) > 0) {

				// log日志信息存储
				Log log = new Log();
				log.setId(UUIDUtil.getUUID());
				log.setUserName(userService.selectByPrimaryKey(
						(String) session.getAttribute(Constant.SESSION_USER))
						.getName());
				log.setTime(df.format(new Date()));
				log.setDescription("修改合同合同信息，未修改合同文件");
				logService.insertSelective(log);

				return "1";
			}
			return "修改失败";
		}

		/**
		 * 2.修改合同信息时,上传了合同文件
		 */
		// 创建上传文件目录
		File newFile = new File(request.getServletContext().getRealPath(
				"/contract"));
		// 上传文件路径及文件名
		Enclosure enclosure = null;// 合同附件信息

		// 上传文件并执行把合同附近放入合同附件表
		for (int i = 0; i < files.length; i++) {
			if (files[i] != null) {
				String fileName = FileUtil.executeUpload(
						newFile.getAbsolutePath() + "/", files[i]);
				// 增加合同附件
				enclosure = new Enclosure();
				enclosure.setId(UUIDUtil.getUUID());
				enclosure.setContractId(contract.getId());
				enclosure.setIsDelete(Constant.IS_DELETE_0);
				enclosure.setPath("contract/" + fileName);// 附件路径
				enclosure.setTime(df.format(new Date()));
				enclosure.setName(files[i].getOriginalFilename());// 文件名
				enclosureService.insertSelective(enclosure);
			}
		}
		// 修改合同表信息
		if (contractService.updateByPrimaryKeySelective(contract) > 0) {
			// log日志信息存储
			Log log = new Log();
			log.setUserName(userService.selectByPrimaryKey(
					(String) session.getAttribute(Constant.SESSION_USER))
					.getName());
			log.setTime(df.format(new Date()));
			log.setId(UUIDUtil.getUUID());
			log.setDescription("修改合同信息," + contract.getName());
			logService.insertSelective(log);

			return "1";
		}
		return "增加失败";

	}

	/**
	 * 删除合同信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deteteById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteById(String id, HttpSession session) {
		Contract contract = contractService.selectByPrimaryKey(id);
		contract.setIsDelete(Constant.IS_DELETE_1);
		if (contractService.updateByPrimaryKeySelective(contract) > 0) {
			// log日志信息存储
			Log log = new Log();
			log.setId(UUIDUtil.getUUID());
			log.setUserName(userService.selectByPrimaryKey(
					(String) session.getAttribute(Constant.SESSION_USER))
					.getName());
			log.setTime(df.format(new Date()));
			log.setDescription("删除合同合同信息：" + contract.getName());
			logService.insertSelective(log);

			return "删除成功";
		}
		return "删除失败";
	}

	/**
	 * 批量删除合同信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deteteByIds", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deteteByIds(String[] ids, HttpSession session) {
		if (ids.length > 0) {
			Contract contract = null;
			// log日志信息存储
			Log log = new Log();
			for (String id : ids) {
				contract = contractService.selectByPrimaryKey(id);
				contract.setIsDelete(Constant.IS_DELETE_1);
				contractService.updateByPrimaryKeySelective(contract);

				log.setId(UUIDUtil.getUUID());
				log.setUserName(userService.selectByPrimaryKey(
						(String) session.getAttribute(Constant.SESSION_USER))
						.getName());
				log.setTime(df.format(new Date()));
				log.setDescription("删除合同合同信息：" + contract.getName());
				logService.insertSelective(log);

			}
			return "删除成功";
		}
		return "删除失败";
	}

	/**
	 * 合同下载
	 * 
	 * @param request
	 * @param id
	 *            合同id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/fileDownLoad")
	public ResponseEntity<byte[]> fileDownLoad(HttpServletRequest request,
			String id, HttpSession session) throws Exception {

		Enclosure enclosure = enclosureService.selectByPrimaryKey(id);
		ServletContext servletContext = request.getServletContext();
		String realPath = servletContext.getRealPath("/" + enclosure.getPath());// 得到文件所在位置
		InputStream in = new FileInputStream(new File(realPath));// 将该文件加入到输入流之中
		byte[] body = null;
		body = new byte[in.available()];// 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数
		in.read(body);// 读入到输入流里面

		String fileName = new String(enclosure.getName().getBytes("gbk"),
				"iso8859-1");// 防止中文乱码
		HttpHeaders headers = new HttpHeaders();// 设置响应头
		headers.add("Content-Disposition", "attachment;filename=" + fileName);
		HttpStatus statusCode = HttpStatus.OK;// 设置响应吗
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body,
				headers, statusCode);

		// log日志信息存储
		Log log = new Log();
		log.setId(UUIDUtil.getUUID());
		log.setUserName(userService.selectByPrimaryKey(
				(String) session.getAttribute(Constant.SESSION_USER)).getName());
		log.setTime(df.format(new Date()));
		log.setDescription("下载合同附件：" + enclosure.getName());
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

	/**
	 * 合同附件列表
	 * 
	 * @return
	 */
	@RequestMapping("/enclosurelist")
	@ResponseBody
	public String enclosurelist(HttpSession session, String contractid) {

		// log日志信息存储
		Log log = new Log();
		log.setId(UUIDUtil.getUUID());
		log.setUserName(userService.selectByPrimaryKey(
				(String) session.getAttribute(Constant.SESSION_USER)).getName());
		log.setTime(df.format(new Date()));
		log.setDescription("查看合同附件列表");
		logService.insertSelective(log);
		// 设置查询条件，合同未被删除的
		EnclosureExample enclosureExample = new EnclosureExample();
		enclosureExample.createCriteria().andContractIdEqualTo(contractid)
				.andIsDeleteEqualTo(Constant.IS_DELETE_0);
		return JSON.toJSONString(enclosureService
				.selectByExample(enclosureExample));
	}
}
