package com.constant;

public class Constant {

	/* session状态 */
	public static String SESSION_USER = "userid";
	public static String SESSION_USER_ROLE = "roleid";
	public static String SYS_ADMIN_ID = "1000000000000001"; // 超级管理员用户ID

	/* 删除标识 */
	public static int IS_DELETE_0 = 0;// 未删除
	public static int IS_DELETE_1 = 1;// 已删除

	/* 角色分类 */
	public static int ROLE_TYPE_1 = 1; // 管理员
	public static int ROLE_TYPE_2 = 2; // 采购人员
	public static int ROLE_TYPE_3 = 3; // 库存管理人员

	/* 合同模板 */
	public static int TEMPLATE_STATE_0 = 0;// 未审核
	public static int TEMPLATE_STATE_1 = 1;// 已审核通过
	public static int TEMPLATE_STATE_2 = 2;// 已审核未通过

	/* 提醒状态 */
	public static int EARYWARNING_STATE_0 = 0; // 执行中
	public static int EARYWARNING_STATE_1 = 1; // 已关闭

	/* 合同变更标识 */
	public static int CHANGE_STATE_0 = 0;// 未变更
	public static int CHANGE_STATE_1 = 1;// 已变更

	/* 合同状态 */
	public static int ENCLOSURE_STATE_0 = 0;// 执行
	public static int ENCLOSURE_STATE_1 = 1;// 结束
	public static int ENCLOSURE_STATE_2 = 2;// 废止

	public static int ENCLOSURE_CHANGESTATE_0 = 0;// 未修改
	public static int ENCLOSURE_CHANGESTATE_1 = 1;// 修改

	public static int ENCLOSURE_PAYSTATE_0 = 0;// 未付清
	public static int ENCLOSURE_PAYSTATE_1 = 1;// 付清
}
