package com.request.tool.model.lib.ws;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentWS {

	private Long id;
	private String body;
	
	@JsonProperty("html_url")
	private String url;

	@JsonProperty("created_at")
	private Date dateCreated;

	@JsonProperty("updated_at")
	private Date dateUpdated;

	private UserWS user;
	
	public CommentWS() { }
	
	public CommentWS(String body) {
		this.body = body;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public UserWS getUser() {
		return user;
	}

	public void setUser(UserWS user) {
		this.user = user;
	}
}
