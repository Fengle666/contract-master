package com.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class StringUtil
{

	public static String get32UUID(){
		String str = UUID.randomUUID().toString().replace("-", "");
		return str;
	}
	/**
	 * 判断String s 是否为null 或者 “” 如果是null 或者“” 则返回true
	 * @param s
	 * @return
	 */
	public static boolean isBlank(String s)
	{
		return s == null || s.trim().equalsIgnoreCase("") || "null".equalsIgnoreCase(s);
	}
	
	public static boolean isEmpty(String s){
		if(s==null || s.equals(""))return true;
		return false;
	}
	
	public static boolean isEmpty(String... s){
		for(String str : s){
			if(StringUtil.isEmpty(str))return true;
		}
		return false;
	}

	public static List<String> strings2List(String[] inputstring)
	{
		List<String> returnList = new ArrayList<String>(); // 循环遍历放入list，加入and查询条件
		for (String t : inputstring)
		{
			try
			{
				t = URLDecoder.decode(t, "utf-8");
				t = URLDecoder.decode(t, "utf-8");
			}
			catch (UnsupportedEncodingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			returnList.add(t);
		}
		return returnList;
	}

	public static String decodebyUTF8(String inputstring)
	{
		try
		{
			inputstring = URLDecoder.decode(inputstring, "utf-8");
			inputstring = URLDecoder.decode(inputstring, "utf-8");
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputstring;
	}

}
