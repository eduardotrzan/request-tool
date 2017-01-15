package com.request.tool.model.lib.ws;

import java.util.Date;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PullRequestWS {

    private Long id;
    private String state;
    private String title;
    private String body;
    private Long number;
    
    @JsonProperty("created_at")
    private Date dateCreated;
    
    @JsonProperty("html_url")
    private String url;
    
    @JsonProperty("updated_at")
    private Date dateUpdated;

    @JsonProperty("merged_at")
    private Date dateMerged;
    
    @JsonProperty("closed_at")
    private Date dateClosed;
    
    private UserWS user;

    @JsonProperty("base")
    @NotNull
    private BranchWS target;
    
    @JsonProperty("head")
    @NotNull
    private BranchWS source;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public Date getDateMerged() {
		return dateMerged;
	}

	public void setDateMerged(Date dateMerged) {
		this.dateMerged = dateMerged;
	}

	public Date getDateClosed() {
		return dateClosed;
	}

	public void setDateClosed(Date dateClosed) {
		this.dateClosed = dateClosed;
	}

	public UserWS getUser() {
		return user;
	}

	public void setUser(UserWS user) {
		this.user = user;
	}

	public BranchWS getTarget() {
		return target;
	}

	public void setTarget(BranchWS target) {
		this.target = target;
	}

	public BranchWS getSource() {
		return source;
	}

	public void setSource(BranchWS source) {
		this.source = source;
	}
}
