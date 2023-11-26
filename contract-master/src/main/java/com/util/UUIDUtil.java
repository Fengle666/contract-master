package com.util;

import java.util.UUID;
/**
 * @author wangwei
 */
public class UUIDUtil {

	/**
	 * 生成32位随机数
	 * @return
	 */
	public static  String getUUID(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	
	/**
	 * 生成20位的随机激活码
	 * @return
	 */
	public static  String getRandomLicenceNo(){
		String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		uuid = uuid.substring(0, 20);
		return uuid;
	}
	
	
}
