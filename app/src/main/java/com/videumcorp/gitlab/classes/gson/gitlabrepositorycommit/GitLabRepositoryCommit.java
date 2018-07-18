package com.videumcorp.gitlab.classes.gson.gitlabrepositorycommit;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GitLabRepositoryCommit {

	@SerializedName("author_name")
	private String authorName;

	@SerializedName("authored_date")
	private String authoredDate;

	@SerializedName("committer_email")
	private String committerEmail;

	@SerializedName("committed_date")
	private String committedDate;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("author_email")
	private String authorEmail;

	@SerializedName("id")
	private String id;

	@SerializedName("short_id")
	private String shortId;

	@SerializedName("title")
	private String title;

	@SerializedName("parent_ids")
	private List<Object> parentIds;

	@SerializedName("message")
	private String message;

	@SerializedName("committer_name")
	private String committerName;

	public void setAuthorName(String authorName){
		this.authorName = authorName;
	}

	public String getAuthorName(){
		return authorName;
	}

	public void setAuthoredDate(String authoredDate){
		this.authoredDate = authoredDate;
	}

	public String getAuthoredDate(){
		return authoredDate;
	}

	public void setCommitterEmail(String committerEmail){
		this.committerEmail = committerEmail;
	}

	public String getCommitterEmail(){
		return committerEmail;
	}

	public void setCommittedDate(String committedDate){
		this.committedDate = committedDate;
	}

	public String getCommittedDate(){
		return committedDate;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setAuthorEmail(String authorEmail){
		this.authorEmail = authorEmail;
	}

	public String getAuthorEmail(){
		return authorEmail;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setShortId(String shortId){
		this.shortId = shortId;
	}

	public String getShortId(){
		return shortId;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setParentIds(List<Object> parentIds){
		this.parentIds = parentIds;
	}

	public List<Object> getParentIds(){
		return parentIds;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setCommitterName(String committerName){
		this.committerName = committerName;
	}

	public String getCommitterName(){
		return committerName;
	}

	@Override
 	public String toString(){
		return 
			"GitLabRepositoryCommit{" +
			"author_name = '" + authorName + '\'' + 
			",authored_date = '" + authoredDate + '\'' + 
			",committer_email = '" + committerEmail + '\'' + 
			",committed_date = '" + committedDate + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",author_email = '" + authorEmail + '\'' + 
			",id = '" + id + '\'' + 
			",short_id = '" + shortId + '\'' + 
			",title = '" + title + '\'' + 
			",parent_ids = '" + parentIds + '\'' + 
			",message = '" + message + '\'' + 
			",committer_name = '" + committerName + '\'' + 
			"}";
		}
}