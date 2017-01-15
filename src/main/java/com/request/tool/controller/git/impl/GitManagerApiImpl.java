package com.request.tool.controller.git.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.request.tool.controller.git.interfaces.GitManagerApi;
import com.request.tool.model.lib.ws.LabelWS;
import com.request.tool.model.lib.ws.PullRequestWS;
import com.request.tool.model.lib.ws.enums.PullRequestActionType;
import com.request.tool.model.lib.ws.events.PullRequestEventWS;
import com.request.tool.model.service.interfaces.GitService;

@RestController
@RequestMapping("/gitManager")
public class GitManagerApiImpl implements GitManagerApi {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GitManagerApiImpl.class);
	
	@Autowired
    private GitService gitService;
	
	@RequestMapping(value="/createdPullRequest"
			, method = RequestMethod.POST
			, consumes = "application/json"
			)
    public void createdPullRequest(@RequestBody PullRequestEventWS pullRequestEvent) {
		PullRequestActionType actionType = pullRequestEvent.getAction();
		if (PullRequestActionType.NEED_LABEL_CHECK.contains(actionType)) {
			LOGGER.info("Need label check for action {}.", actionType);
			PullRequestWS pullRequest = pullRequestEvent.getPullRequest();
			List<LabelWS> prLabels = this.gitService.applyDefaultLabelsUponCreation(pullRequest);
		}
    }
}