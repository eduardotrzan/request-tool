package com.request.tool.model.lib.ws;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.request.tool.model.lib.ws.UserWS;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryWS {
    
    private Long id;
    private String name;

    @JsonProperty("full_name")
    private String fullName;
    
    @JsonProperty("owner")
    private UserWS user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public UserWS getUser() {
		return user;
	}

	public void setUser(UserWS user) {
		this.user = user;
	}
}
