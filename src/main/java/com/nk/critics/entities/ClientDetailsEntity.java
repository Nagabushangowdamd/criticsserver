package com.nk.critics.entities;

import java.util.HashMap; 
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity 
@Table(name = "ClientDetails")
public class ClientDetailsEntity {
	private String clientId;
	private String apiKey;
	private String secretKey;
	private boolean isActive;
	private long createdDate;
	private long updatedDate;
	private String parentClientId;
	private String version;
	private String webHook;
	
	private Map<String, String> clientCredentials = new HashMap<String, String>();

	@ElementCollection
	@Column(name="clientCredentials")
	public Map<String, String> getClientCredentials() {
		return clientCredentials;
	}

	public void setClientCredentials(Map<String, String> clientCredentials) {
		this.clientCredentials = clientCredentials;
	}

	@Column(name="webHook")
	public String getWebHook() {
		return webHook;
	}

	public void setWebHook(String webHook) {
		this.webHook = webHook;
	}
	
	@Column(name = "version")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "isActive")
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "parentClientId")
	public String getParentClientId() {
		return parentClientId;
	}

	public void setParentClientId(String parentClientId) {
		this.parentClientId = parentClientId;
	}

	@Column(name = "createdDate")
	public long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "updatedDate")
	public long getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(long updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Id
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Column(name = "apiKey")
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Column(name = "secretKey")
	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	@Override
	public String toString() {
		return "ClientDetailsEntity [clientId=" + clientId + ", apiKey=" + apiKey + ", secretKey=" + secretKey
				+ ", isActive=" + isActive + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate
				+ ", parentClientId=" + parentClientId + ", version=" + version + ", webHook=" + webHook
				+ ", clientCredentials=" + clientCredentials + "]";
	}
	
	

}
