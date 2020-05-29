package com.nk.critics.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ItemOwnerRatingSummary")
public class ItemOwnerRatingSummaryEntity {

	private double avrgRating;
	private long noOfRatings;
	private String clientId;
	private long updatedDate;
	private long createdDate;
	private String itemOwnerName;
	private String itemOwnerId;
	private String itemOwnerDescription;
	private String itemOwnerCategoryType;
	private String itemOwnerSubCategoryType;
	private double lastRating;
	private String lastComment;
	private long noOfReviews;
	
	
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

	

	@Column(name = "itemOwnerName")
	public String getItemOwnerName() {
		return itemOwnerName;
	}

	public void setItemOwnerName(String itemOwnerName) {
		this.itemOwnerName = itemOwnerName;
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

	@Column(name = "updatedDate")
	public long getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(long updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Column(name = "createdDate")
	public long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}

	@Id
	@Column(name = "itemOwnerId")
	public String getItemOwnerId() {
		return itemOwnerId;
	}

	public void setItemOwnerId(String itemOwnerId) {
		this.itemOwnerId = itemOwnerId;
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

	@Column(name = "clientId")
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return "ItemOwnerRatingSummaryEntity [avrgRating=" + avrgRating + ", noOfRatings=" + noOfRatings + ", clientId="
				+ clientId + ", updatedDate=" + updatedDate + ", createdDate=" + createdDate + ", itemOwnerName="
				+ itemOwnerName + ", itemOwnerId=" + itemOwnerId + ", itemOwnerDescription=" + itemOwnerDescription
				+ ", itemOwnerCategoryType=" + itemOwnerCategoryType + ", itemOwnerSubCategoryType="
				+ itemOwnerSubCategoryType + "]";
	}

}
