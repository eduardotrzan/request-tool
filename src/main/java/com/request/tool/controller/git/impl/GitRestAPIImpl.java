package com.request.tool.controller.git.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.request.tool.controller.git.enums.HttpHeaderType;
import com.request.tool.controller.git.exceptions.GitAPIException;
import com.request.tool.controller.git.interfaces.GitRestAPI;
import com.request.tool.model.lib.OAuthData;
import com.request.tool.model.lib.ws.CommentWS;
import com.request.tool.model.lib.ws.LabelWS;
import com.request.tool.model.lib.ws.PullRequestWS;
import com.request.tool.model.lib.ws.RepositoryWS;
import com.request.tool.model.utils.StringUtils;

@Service
public class GitRestAPIImpl implements GitRestAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitRestAPIImpl.class);
    
    private static final String GIT_BASE_URL = "https://api.github.com";
    
    private static final String ACCESS_TOKEN_PARAM = "?access_token=%s";
    
    private static final String USER_REPOS_URL = GIT_BASE_URL + "/users/%s/repos" + ACCESS_TOKEN_PARAM;
    
    private static final String REPO_BASE_URL = GIT_BASE_URL + "/repos/%s/%s";
    
    private static final String REPO_LABEL = REPO_BASE_URL + "/labels/%s" + ACCESS_TOKEN_PARAM;
    
    private static final String REPO_LABELS = REPO_BASE_URL + "/labels" + ACCESS_TOKEN_PARAM;
    
    private static final String REPO_PR_URL = REPO_BASE_URL + "/pulls" + ACCESS_TOKEN_PARAM;
    
    /* /repos/:owner/:repo/issues/:number */
    private static final String REPO_PR_ISSUES_BASE_URL = REPO_BASE_URL + "/issues/%d";
    
    private static final String REPO_PR_LABELS_URL = REPO_PR_ISSUES_BASE_URL + "/labels" + ACCESS_TOKEN_PARAM;
    
    private static final String REPO_PR_COMMENTS_URL = REPO_PR_ISSUES_BASE_URL + "/comments" + ACCESS_TOKEN_PARAM;

    private RestTemplate restTemplate;
    
    public GitRestAPIImpl() {
        this.restTemplate = new RestTemplate();
    }
    
    public List<RepositoryWS> getUserRepositories(@NotNull OAuthData oAuthData) {
        String url = String.format(USER_REPOS_URL,
                oAuthData.getUsername(),
                oAuthData.getAccessToken()
        );

        return this.getEntities(url, RepositoryWS[].class);
    }
    
    public RepositoryWS getRepository(@NotNull OAuthData oAuthData, @NotNull String repositoryName) {
    	String repoUrl = REPO_BASE_URL + ACCESS_TOKEN_PARAM;
    	String url = String.format(repoUrl,
    			oAuthData.getUsername(),
    			repositoryName,
    			oAuthData.getAccessToken()
    			);
    	
    	return this.getEntity(url, RepositoryWS.class);
    }
    
	public List<PullRequestWS> getPullRequests(@NotNull OAuthData oAuthData, @NotNull RepositoryWS repository) {
        String url = String.format(REPO_PR_URL,
                oAuthData.getUsername(),
                repository.getName(),
                oAuthData.getAccessToken()
        );

        return this.getEntities(url, PullRequestWS[].class);
    }
    
    public List<LabelWS> getPullRequestLabels(@NotNull OAuthData oAuthData, @NotNull PullRequestWS pullRequest) {
        RepositoryWS targetRepo = pullRequest.getTarget().getRepository(); 
                
        String url = String.format(REPO_PR_LABELS_URL,
                oAuthData.getUsername(),
                targetRepo.getName(),
                pullRequest.getNumber(),
                oAuthData.getAccessToken()
        );

        return this.getEntities(url, LabelWS[].class);
    }

	public List<LabelWS> getLabels(@NotNull OAuthData oAuthData, @NotNull RepositoryWS repository) {
        String url = String.format(REPO_LABELS,
                oAuthData.getUsername(),
                repository.getName(),
                oAuthData.getAccessToken()
        );

        return this.getEntities(url, LabelWS[].class);
    }
	
	public boolean addLabelsToPullRequest(@NotNull OAuthData oAuthData, @NotNull PullRequestWS pullRequest, @NotNull List<LabelWS> labels) {
		RepositoryWS targetRepo = pullRequest.getTarget().getRepository();
        
        String url = String.format(REPO_PR_LABELS_URL,
                oAuthData.getUsername(),
                targetRepo.getName(),
                pullRequest.getNumber(),
                oAuthData.getAccessToken()
        );
        
		String[] labelNames = labels
    			.stream()
    			.map(label -> label.getName())
    			.toArray(size -> new String[size])
    			;
		
		try {
            ResponseEntity<String> responseEntity = postEntities(url, labelNames);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return true;
            }
        
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
	}
    
    public boolean addLabelToPullRequest(@NotNull OAuthData oAuthData, @NotNull PullRequestWS pullRequest, @NotNull LabelWS label) {
        RepositoryWS targetRepo = pullRequest.getTarget().getRepository();
        
        String url = String.format(REPO_PR_LABELS_URL,
                oAuthData.getUsername(),
                targetRepo.getName(),
                pullRequest.getNumber(),
                oAuthData.getAccessToken()
        );

        try {
            ResponseEntity<String> responseEntity = postEntities(url, label.getName());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return true;
            }
        
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }
    
    public LabelWS createLabel(@NotNull OAuthData oAuthData, @NotNull RepositoryWS repository, @NotNull LabelWS label) {
    	String labelName = label.getName();
    	String repoName = repository.getName();
    	
    	String url = String.format(REPO_LABELS,
                oAuthData.getUsername(),
                repoName,
                oAuthData.getAccessToken()
        );
    	
    	LOGGER.info("Creating label with name {} and color {} in repo {} with url {}.", labelName, label.getColor(), repoName, url);
    	LabelWS newLabel = postAsJson(url, LabelWS.class, label);
    	if (newLabel != null) {
    		LOGGER.info("Created new label with name {} and color {}.", newLabel.getName(), newLabel.getColor());
    		return newLabel;
    	} else {
    		LOGGER.error("Couldn't create label.");
    		return null;
    	}
    }
    
    public List<LabelWS> createLabels(@NotNull OAuthData oAuthData, @NotNull RepositoryWS repository, @NotNull List<LabelWS> labels) {
    	List<LabelWS> labelWSs = new ArrayList<>();
    	for (LabelWS label : labels) {
    		LabelWS newLabel = this.createLabel(oAuthData, repository, label);
			if (newLabel != null) {
				labelWSs.add(newLabel);
			}
		}
    	return labelWSs;
    }
    
    public boolean deleteAllLabels(@NotNull OAuthData oAuthData, @NotNull RepositoryWS repository) throws GitAPIException {
    	List<LabelWS> labels = this.getLabels(oAuthData, repository);
    	
    	for (LabelWS label : labels) {
    		String labelName = label.getName();
    		String repoName = repository.getName();
    		try {
	    		String encodedLabelName = StringUtils.encodeStringForURL(labelName);
	    		String url = String.format(REPO_LABEL,
	                    oAuthData.getUsername(),
	                    repository.getName(),
	                    encodedLabelName,
	                    oAuthData.getAccessToken()
	            );
	    		
	    		LOGGER.info("Deleting label {} from repo {} by url {}", labelName, repoName, url);
	    		
	    		delete(url);
			} catch (Exception e) {
				String errorMessage = String.format("Can't remove label %s from repository %s.", labelName, repoName);
				handleError(errorMessage, e, false);
			}
		}
    	return true;
    }
    
    public CommentWS createComment(@NotNull OAuthData oAuthData, @NotNull PullRequestWS pullRequest, @NotNull String commentText) {
    	RepositoryWS targetRepo = pullRequest.getTarget().getRepository(); 
    	String repoName = targetRepo.getName();
        
        String url = String.format(REPO_PR_COMMENTS_URL,
                oAuthData.getUsername(),
                repoName,
                pullRequest.getNumber(),
                oAuthData.getAccessToken()
        );
        
        CommentWS comment = new CommentWS(commentText);

        LOGGER.info("Creating comment with text {} in repo {} with url {}.", commentText, repoName, url);
        CommentWS newComment = postAsJson(url, CommentWS.class, comment);
    	if (newComment != null) {
    		LOGGER.info("Created new comment with name {}.", comment.getBody());
    		return newComment;
    	} else {
    		LOGGER.error("Couldn't create comment.");
    		return null;
    	}
    }
    
    /* #############################################################################
     * ##                             Generic Methods                              ##
     * #############################################################################
     */
    
    private <T> T postAsJson(String url, Class<T> clazz, T object) {
    	try {
    		ResponseEntity<T> responseEntity = this.postEntity(HttpHeaderType.JSON, url, clazz, object);
    		if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
    			T newObject = responseEntity.getBody();
                return newObject;
            }
        } catch (Exception e) {
            LOGGER.error("JSON post exception.", e);
        }
        return null;
    }

    /**
     * Retrieves a list of entities in a given url, by rest access.
     * 
     * @param url - url to access
     * @param clazz - array of classes to retrieve
     * @param <T> - generic object type
     * @return list of entities
     */
    private <T> List<T> getEntities(String url, Class<T[]> clazz) {
        ResponseEntity<T[]> responseEntity = this.restTemplate.getForEntity(url, clazz);
        List<T> list = new ArrayList<>();
        if (responseEntity != null && responseEntity.getBody() != null) {
            T[] entities = responseEntity.getBody();
            list.addAll(Arrays.asList(entities));
        }
        return list;
    }
    
    /**
     * Retrieves an entity in a given url, by rest access.
     * 
     * @param url - url to access
     * @param clazz - array of classes to retrieve
     * @param <T> - generic object type
     * @return entity
     */
    private <T> T getEntity(String url, Class<T> clazz) {
    	ResponseEntity<T> responseEntity = this.restTemplate.getForEntity(url, clazz);
    	if (responseEntity != null && responseEntity.getBody() != null) {
    		T entity = responseEntity.getBody();
    		return entity;
    	} else {
    		return null;
    	}
    }
    
    private <T> ResponseEntity<T> postEntity(HttpHeaderType httpHeaderType, String url, Class<T> clazz, T entity) {
        HttpEntity<T> httpEntity = new HttpEntity<>(entity, httpHeaderType.httpHeaders());
        return this.restTemplate.postForEntity(url, httpEntity, clazz);
    }
    
    private ResponseEntity<String> postEntities(String url, String... data) {
        String formattedBody = gitAcceptedBodyFormat(data);
        HttpEntity<String> httpEntity = new HttpEntity<>(formattedBody);
        return this.restTemplate.postForEntity(url, httpEntity, String.class);
    }
    
    private void delete(String url) throws RestClientException {
    	URI uri = URI.create(url);
    	this.restTemplate.delete(uri);
    }

    /**
     * Formats the data, separating them by comma and adding quotes for each and encapsulating the body with []. <BR><BR>
     * E.g.:
     * ["bug", "fix"]
     * 
     * @param data - information to be formatted
     * @return formatted git body
     */
    private String gitAcceptedBodyFormat(String... data) {
        String gitAcceptedBodyFormat = "";
        if (data != null) {
            for (String item : data) {
                if (!gitAcceptedBodyFormat.isEmpty()) {
                    gitAcceptedBodyFormat += ",";
                }
                gitAcceptedBodyFormat += "\"" + item + "\"";
            }
        }
        
        gitAcceptedBodyFormat = "[" + gitAcceptedBodyFormat + "]";
        return gitAcceptedBodyFormat;
    }

	private void handleError(String errorMessage, Exception e, boolean isThrow) throws GitAPIException {
		LOGGER.error(errorMessage, e);
		if (isThrow) {
			throw new GitAPIException(errorMessage, e);
		}
	}
}