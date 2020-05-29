package com.nk.critics.util;

import java.io.Serializable;

public class ClientSummaryEntityCompositeKeyClass implements Serializable{
	//composite key class
	/**
	 * 
	 */
	private static final long serialVersionUID = 5113081678280841488L;
	
	private String clientId;
	private int apiId;

	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public int getApiId() {
		return apiId;
	}
	public void setApiId(int apiId) {
		this.apiId = apiId;
	}
}
