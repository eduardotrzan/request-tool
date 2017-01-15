package com.request.tool.controller.git.exceptions;

public class GitAPIException extends Exception {

	private static final long serialVersionUID = 1584439905196346011L;

	public GitAPIException() {
		super();
	}

	public GitAPIException(String message) {
		super(message);
	}

	public GitAPIException(Throwable cause) {
		super(cause);
	}

	public GitAPIException(String message, Throwable cause) {
		super(message, cause);
	}
}
