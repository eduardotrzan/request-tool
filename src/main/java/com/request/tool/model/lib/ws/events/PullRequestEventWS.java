package com.request.tool.model.lib.ws.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.request.tool.model.lib.ws.PullRequestWS;
import com.request.tool.model.lib.ws.RepositoryWS;
import com.request.tool.model.lib.ws.enums.PullRequestActionType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PullRequestEventWS {
	
	private PullRequestActionType action;
	private Long number;
	
	private RepositoryWS repository;
	
	@JsonProperty("pull_request")
	private PullRequestWS pullRequest;

	public PullRequestActionType getAction() {
		return action;
	}

	public void setAction(PullRequestActionType action) {
		this.action = action;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public RepositoryWS getRepository() {
		return repository;
	}

	public void setRepository(RepositoryWS repository) {
		this.repository = repository;
	}

	public PullRequestWS getPullRequest() {
		return pullRequest;
	}

	public void setPullRequest(PullRequestWS pullRequest) {
		this.pullRequest = pullRequest;
	}
}
