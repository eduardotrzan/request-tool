package com.request.tool.controller.git.enums;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public enum HttpHeaderType {
	JSON(1),
	XML(2);
	
	private HttpHeaders headers;
	
	private HttpHeaderType(int type) {
		switch (type) {
		case 1:
			this.headers = createHttpHeaders(MediaType.APPLICATION_JSON);
			break;
		case 2:
			this.headers = createHttpHeaders(MediaType.APPLICATION_XML);
			break;
		default:
			break;
		}
	}
	
	public HttpHeaders httpHeaders() {
		return this.headers;
	}

	private HttpHeaders createHttpHeaders(MediaType mediaType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);
		headers.setAccept(Arrays.asList(mediaType));
		return headers;
	}
}
