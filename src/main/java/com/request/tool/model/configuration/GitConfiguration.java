package com.request.tool.model.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.request.tool.model.lib.OAuthData;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "config")
public class GitConfiguration {
	
	private String username;
	private String token;
	
	private List<GitLabel> repoLabels;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public OAuthData toOAuthData() {
		return new OAuthData(this.username, this.token);
	}

	public List<GitLabel> getRepoLabels() {
		return repoLabels;
	}

	public void setRepoLabels(List<GitLabel> repoLabels) {
		this.repoLabels = repoLabels;
	}
}
