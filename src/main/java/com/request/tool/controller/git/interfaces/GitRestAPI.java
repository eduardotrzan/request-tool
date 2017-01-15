package com.request.tool.controller.git.interfaces;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.request.tool.controller.git.exceptions.GitAPIException;
import com.request.tool.model.lib.OAuthData;
import com.request.tool.model.lib.ws.CommentWS;
import com.request.tool.model.lib.ws.LabelWS;
import com.request.tool.model.lib.ws.PullRequestWS;
import com.request.tool.model.lib.ws.RepositoryWS;

public interface GitRestAPI {
	
	RepositoryWS getRepository(@NotNull OAuthData oAuthData, @NotNull String repositoryName);
	
	List<RepositoryWS> getUserRepositories(@NotNull OAuthData oAuthData);
	List<PullRequestWS> getPullRequests(@NotNull OAuthData oAuthData, @NotNull RepositoryWS repository);
	List<LabelWS> getPullRequestLabels(@NotNull OAuthData oAuthData, @NotNull PullRequestWS pullRequest);
	List<LabelWS> getLabels(@NotNull OAuthData oAuthData, @NotNull RepositoryWS repository);
	
	boolean addLabelsToPullRequest(@NotNull OAuthData oAuthData, @NotNull PullRequestWS pullRequest, @NotNull List<LabelWS> labels);
	boolean addLabelToPullRequest(@NotNull OAuthData oAuthData, @NotNull PullRequestWS pullRequest, @NotNull LabelWS label);
	
	LabelWS createLabel(@NotNull OAuthData oAuthData, @NotNull RepositoryWS repository, @NotNull LabelWS label);
	List<LabelWS> createLabels(@NotNull OAuthData oAuthData, @NotNull RepositoryWS repository, @NotNull List<LabelWS> labels);
	
	boolean deleteAllLabels(@NotNull OAuthData oAuthData, @NotNull RepositoryWS repository) throws GitAPIException;
	
	CommentWS createComment(@NotNull OAuthData oAuthData, @NotNull PullRequestWS pullRequest, @NotNull String comment);
	
}
