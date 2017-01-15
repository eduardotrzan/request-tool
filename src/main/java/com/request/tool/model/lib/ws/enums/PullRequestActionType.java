package com.request.tool.model.lib.ws.enums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PullRequestActionType implements Serializable {
	OPENED,
	CLOSED,
	REOPENED,
	EDITED,
	ASSIGNED,
	UNASSIGNED,
	LABELED,
	UNLABELED,
	SYNCHRONIZED;
	
	public static final List<PullRequestActionType> NEED_LABEL_CHECK = Arrays.asList(
			OPENED,
			REOPENED
			); 
	
	@JsonValue
	final String value() {
		return this.name().toLowerCase();
	}
}
