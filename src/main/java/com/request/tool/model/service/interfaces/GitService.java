package com.request.tool.model.service.interfaces;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.request.tool.model.lib.ws.LabelWS;
import com.request.tool.model.lib.ws.PullRequestWS;

public interface GitService {
	
	List<LabelWS> applyDefaultLabelsUponCreation(@NotNull PullRequestWS pullRequest);
	
	@Deprecated
	void changeLabel();
}
