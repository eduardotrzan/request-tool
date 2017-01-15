package com.request.tool.model.lib.ws;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchWS {
    
    @JsonProperty("ref")
    private String name;
    
    private UserWS user;

    @JsonProperty("repo")
    @NotNull
    private RepositoryWS repository;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserWS getUser() {
		return user;
	}

	public void setUser(UserWS user) {
		this.user = user;
	}

	public RepositoryWS getRepository() {
		return repository;
	}

	public void setRepository(RepositoryWS repository) {
		this.repository = repository;
	}
}
