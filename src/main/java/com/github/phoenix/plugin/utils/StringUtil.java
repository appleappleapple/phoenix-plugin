package com.github.phoenix.plugin.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	@SuppressWarnings("null")
	public static boolean isEmpty (String value) {
		
		return (value == null || value.length() == 0);
	}
	
	@SuppressWarnings("null")
	public static boolean isBlank(String value) {

		return (value == null || value.trim().length() == 0);
	}
	
	public static final char UNDERLINE = '_';

	public static String camelToUnderline(String param) {
		if (param == null || param.isEmpty()) {
			return null;
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				if (i > 0 ) {
					sb.append(UNDERLINE);
				}
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String underlineToCamel(String param) {
		if (param == null || param.isEmpty()) {
			return null;
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String underlineToCamel2(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		StringBuilder sb = new StringBuilder(param);
		Matcher mc = Pattern.compile("_").matcher(param);
		int i = 0;
		while (mc.find()) {
			int position = mc.end() - (i++);
			sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
		}
		return sb.toString();
	}

//	public static void main(String[] args) {
//
//	}

	
	public static void main(String[] agrs) {
		
		System.out.println(" ".length());
		System.out.println(StringUtil.isEmpty(""));
		System.out.println(StringUtil.isEmpty(" "));
		System.out.println(StringUtil.isEmpty("123"));
		System.out.println(StringUtil.isBlank(""));
		System.out.println(StringUtil.isBlank("   "));
		System.out.println(StringUtil.isBlank("123"));
		
		System.out.println(StringUtil.camelToUnderline("VisTorTkk"));
		
		System.out.println(StringUtil.underlineToCamel("vid_ddd_ji"));
	}
	

}
