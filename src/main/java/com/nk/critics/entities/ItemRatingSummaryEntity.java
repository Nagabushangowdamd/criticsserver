package com.nk.critics.entities;


import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity 
@Table(name = "ItemRatingSummary")
public class ItemRatingSummaryEntity {

	private double avrgRating;
	private long   noOfRatings;
	private long   createdDate;
	private long   updatedDate;
	private String itemId;
	private String itemDescription;
	private String itemCategoryType;
	private String itemSubCategoryType;
	private String itemAttributesColour;
	private String itemAttributesModelNo;
	private String itemTitle;
	private long   itemPrice;
	private String itemCurrency;
	private String itemOwnerName;
	private String itemOwnerId;
	private String itemOwnerDescription;
	private String itemOwnerCategoryType;
	private String itemOwnerSubCategoryType;
	private String txnId;
	private String clientId;
	private double lastRating;
	private String lastComment;
	private long noOfReviews;
	
	
	

	@Column(name="lastComment")
    public String getLastComment() {
		return lastComment;
	}
	@Column(name="noOfReviews")
	public long getNoOfReviews() {
		return noOfReviews;
	}

	public void setNoOfReviews(long noOfReviews) {
		this.noOfReviews = noOfReviews;
	}

	public void setLastComment(String lastComment) {
		this.lastComment = lastComment;
	}

	@Column(name="lastRating")
	public double getLastRating() {
		return lastRating;
	}

	public void setLastRating(double lastRating) {
		this.lastRating = lastRating;
	}

	private Map<String, String> attributes = new HashMap<String, String>();

	@Column(name = "clientId")
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	@ElementCollection
	@Column(name = "attributes")
	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
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
	@Column(name = "itemId")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@Column(name = "itemDescription")
	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	@Column(name = "itemCategoryType")
	public String getItemCategoryType() {
		return itemCategoryType;
	}

	public void setItemCategoryType(String itemCategoryType) {
		this.itemCategoryType = itemCategoryType;
	}

	@Column(name = "itemSubCategoryType")
	public String getItemSubCategoryType() {
		return itemSubCategoryType;
	}

	public void setItemSubCategoryType(String itemSubCategoryType) {
		this.itemSubCategoryType = itemSubCategoryType;
	}

	@Column(name = "itemAttributesColour")
	public String getItemAttributesColour() {
		return itemAttributesColour;
	}

	public void setItemAttributesColour(String itemAttributesColour) {
		this.itemAttributesColour = itemAttributesColour;
	}

	@Column(name = "itemAttributesModelNo")
	public String getItemAttributesModelNo() {
		return itemAttributesModelNo;
	}

	public void setItemAttributesModelNo(String itemAttributesModelNo) {
		this.itemAttributesModelNo = itemAttributesModelNo;
	}

	@Column(name = "itemTitle")
	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	@Column(name = "itemPrice")
	public long getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(long itemPrice) {
		this.itemPrice = itemPrice;
	}

	@Column(name = "itemCurrency")
	public String getItemCurrency() {
		return itemCurrency;
	}

	public void setItemCurrency(String itemCurrency) {
		this.itemCurrency = itemCurrency;
	}

	@Column(name = "itemOwnerName")
	public String getItemOwnerName() {
		return itemOwnerName;
	}

	public void setItemOwnerName(String itemOwnerName) {
		this.itemOwnerName = itemOwnerName;
	}

	@Column(name = "itemOwnerId")
	public String getItemOwnerId() {
		return itemOwnerId;
	}

	public void setItemOwnerId(String itemOwnerId) {
		this.itemOwnerId = itemOwnerId;
	}

	@Column(name = "itemOwnerDescription")
	public String getItemOwnerDescription() {
		return itemOwnerDescription;
	}

	public void setItemOwnerDescription(String itemOwnerDescription) {
		this.itemOwnerDescription = itemOwnerDescription;
	}

	@Column(name = "itemOwnerCategoryType")
	public String getItemOwnerCategoryType() {
		return itemOwnerCategoryType;
	}

	public void setItemOwnerCategoryType(String itemOwnerCategoryType) {
		this.itemOwnerCategoryType = itemOwnerCategoryType;
	}

	@Column(name = "itemOwnerSubCategoryType")
	public String getItemOwnerSubCategoryType() {
		return itemOwnerSubCategoryType;
	}

	public void setItemOwnerSubCategoryType(String itemOwnerSubCategoryType) {
		this.itemOwnerSubCategoryType = itemOwnerSubCategoryType;
	}

	@Column(name = "txnId")
	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	@Column(name = "avrgRating")
	public double getAvrgRating() {
		return avrgRating;
	}

	public void setAvrgRating(double avrgRating) {
		this.avrgRating = avrgRating;
	}

	@Column(name = "noOfRatings")
	public long getNoOfRatings() {
		return noOfRatings;
	}

	public void setNoOfRatings(long noOfRatings) {
		this.noOfRatings = noOfRatings;
	}

}
