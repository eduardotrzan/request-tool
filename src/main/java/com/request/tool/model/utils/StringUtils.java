package com.request.tool.model.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public final class StringUtils {
	
	private final static String DEFAULT_ENCODING = "UTF-8";
	
	private StringUtils() {}
	
	public static String encodeStringForURL(String rawString) throws UnsupportedEncodingException {
		return encodeStringForURL(rawString, DEFAULT_ENCODING);
	}
	
	public static String encodeStringForURL(String rawString, String encoding) throws UnsupportedEncodingException {
		return URLEncoder.encode(rawString, encoding).replace("+", "%20");
	}
}
