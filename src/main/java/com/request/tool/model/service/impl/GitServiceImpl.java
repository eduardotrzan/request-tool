package com.request.tool.model.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.request.tool.controller.git.interfaces.GitRestAPI;
import com.request.tool.model.configuration.GitConfiguration;
import com.request.tool.model.lib.OAuthData;
import com.request.tool.model.lib.ws.LabelWS;
import com.request.tool.model.lib.ws.PullRequestWS;
import com.request.tool.model.lib.ws.RepositoryWS;
import com.request.tool.model.service.interfaces.GitService;
import com.request.tool.model.utils.WSMapperUtils;

@Service
public class GitServiceImpl implements GitService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GitServiceImpl.class);

	@Autowired
	private GitRestAPI gitRest;
	@Autowired
	private GitConfiguration gitConfiguration;

	public List<LabelWS> applyDefaultLabelsUponCreation(@NotNull PullRequestWS pullRequest) {
		OAuthData oAuthData = this.gitConfiguration.toOAuthData();
		List<LabelWS> defaultAppDirectLabels = WSMapperUtils.toLabelWSs(this.gitConfiguration.getRepoLabels());

		List<LabelWS> prLabels = this.gitRest.getPullRequestLabels(oAuthData, pullRequest);
		List<LabelWS> missingDefaultLabels = this.diffLabelsPR(oAuthData, prLabels, defaultAppDirectLabels);
		this.gitRest.addLabelsToPullRequest(oAuthData, pullRequest, missingDefaultLabels);
		if (!missingDefaultLabels.isEmpty()) {
			prLabels.addAll(missingDefaultLabels);
		}
		return prLabels;
	}

	private List<LabelWS> diffLabelsPR(OAuthData oAuthData, List<LabelWS> prLabels, List<LabelWS> labels) {

		List<LabelWS> diffLabels = new ArrayList<>(labels);
		for (LabelWS prLabel : prLabels) {
			diffLabels.stream().filter(diffLabel -> diffLabel.getName().equals(prLabel.getName())).findFirst()
					.ifPresent(prLabelRemove -> diffLabels.remove(prLabelRemove));
		}
		return diffLabels;
	}

	@Deprecated
	public void changeLabel() {
		try {
			OAuthData oAuthData = this.gitConfiguration.toOAuthData();

			List<RepositoryWS> repositories = this.gitRest.getUserRepositories(oAuthData);

			LOGGER.info("Repositories: " + repositories.toString());

			Optional<RepositoryWS> repository = repositories.stream().findFirst();
			List<PullRequestWS> pullRequests = repository.map(repo -> this.gitRest.getPullRequests(oAuthData, repo))
					.get();

			LOGGER.info("Pull Requests: " + pullRequests.toString());

			Optional<PullRequestWS> pullRequest = pullRequests.stream().findFirst();
			List<LabelWS> prLabels = pullRequest.map(pr -> this.gitRest.getPullRequestLabels(oAuthData, pr)).get();

			LOGGER.info("Labels: " + prLabels.toString());

			List<LabelWS> allLabels = repository.map(repo -> this.gitRest.getLabels(oAuthData, repo)).get();

			List<LabelWS> remainingLabels = new ArrayList<>(allLabels);
			for (LabelWS prLabel : prLabels) {
				List<LabelWS> commonLabels = allLabels.stream()
						.filter(allLabel -> prLabel.getName().equals(allLabel.getName())).collect(Collectors.toList());
				remainingLabels.removeAll(commonLabels);
			}

			if (!remainingLabels.isEmpty()) {
				LabelWS firstLbl = remainingLabels.get(0);
				boolean isAddedLbl = pullRequest.map(pr -> this.gitRest.addLabelToPullRequest(oAuthData, pr, firstLbl))
						.get();
				LOGGER.info("Added lbl is " + isAddedLbl);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
