package com.nk.critics.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.nk.critics.util.ClientSummaryEntityCompositeKeyClass;

@Entity
@Table(name = "ClientSummary")
@IdClass(ClientSummaryEntityCompositeKeyClass.class)// composite key class
public class ClientSummaryEntity  {

	private String clientId;
	private int apiId;
	private long apiCalls;
	private long createdDate;
	private long updatedDate;
	
	@Id
	@Column(name = "clientId")
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	@Id
	@Column(name = "apiId")
	public int getApiId() {
		return apiId;
	}
	public void setApiId(int apiId) {
		this.apiId = apiId;
	}
	
	@Column(name = "apiCalls")
	public long getApiCalls() {
		return apiCalls;
	}
	public void setApiCalls(long apiCalls) {
		this.apiCalls = apiCalls;
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
}
