package com.request.tool.controller.git.interfaces;

import com.request.tool.model.lib.ws.events.PullRequestEventWS;

public interface GitManagerApi {
	
    void createdPullRequest(PullRequestEventWS pullRequestEvent);

}