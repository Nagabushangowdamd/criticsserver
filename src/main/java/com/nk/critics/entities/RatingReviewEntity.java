package com.nk.critics.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RatingReview")
public class RatingReviewEntity {
	private String userId;
	private String userName;
	private String userEmailId;
	private String userLocationLat;
	private String userLocationLong;
	private String review;
	private long createdDate;
	private long updatedDate;
	private double rating;
	private String itemId;
	private String itemDescription;
	private String itemCategoryType;
	private String itemSubCategoryType;
	private String itemAttributesColour;
	private String itemAttributesModelNo;
	private String reviewId;
	private String itemTitle;
	private long itemPrice;
	private String itemOrderId;
	private String itemCurrency;
	private String itemOwnerName;
	private String itemOwnerId;
	private String itemOwnerDescription;
	private String itemOwnerCategoryType;
	private String itemOwnerSubCategoryType;
	private String txnId;
	private String clientId;
	private Map<String, String> attributes = new HashMap<String, String>();
	private String ratingType;

	@Column(name = "ratingType")
	public String getRatingType() {
		return ratingType;
	}

	public void setRatingType(String ratingType) {
		this.ratingType = ratingType;
	}

	@Column(name = "itemOrderId")
	public String getItemOrderId() {
		return itemOrderId;
	}

	public void setItemOrderId(String itemOrderId) {
		this.itemOrderId = itemOrderId;
	}

	@ElementCollection
	@Column(name = "attributes")
	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	@Column(name = "userId")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "userName")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "getUserEmailId")
	public String getUserEmailId() {
		return userEmailId;
	}

	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}

	@Column(name = "userLocationLat")
	public String getUserLocationLat() {
		return userLocationLat;
	}

	public void setUserLocationLat(String userLocationLat) {
		this.userLocationLat = userLocationLat;
	}

	@Column(name = "userLocationLong")
	public String getUserLocationLong() {
		return userLocationLong;
	}

	public void setUserLocationLong(String userLocationLong) {
		this.userLocationLong = userLocationLong;
	}

	@Column(name = "review")
	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
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

	@Column(name = "rating")
	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

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

	@Id
	@Column(name = "reviewId")
	public String getReviewId() {
		return reviewId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
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

	@Column(name = "clientId")
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return "RatingReviewEntity [userId=" + userId + ", userName=" + userName + ", userEmailId=" + userEmailId
				+ ", userLocationLat=" + userLocationLat + ", userLocationLong=" + userLocationLong + ", review="
				+ review + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", rating=" + rating
				+ ", itemId=" + itemId + ", itemDescription=" + itemDescription + ", itemCategoryType="
				+ itemCategoryType + ", itemSubCategoryType=" + itemSubCategoryType + ", itemAttributesColour="
				+ itemAttributesColour + ", itemAttributesModelNo=" + itemAttributesModelNo + ", reviewId=" + reviewId
				+ ", itemTitle=" + itemTitle + ", itemPrice=" + itemPrice + ", itemOrderId=" + itemOrderId
				+ ", itemCurrency=" + itemCurrency + ", itemOwnerName=" + itemOwnerName + ", itemOwnerId=" + itemOwnerId
				+ ", itemOwnerDescription=" + itemOwnerDescription + ", itemOwnerCategoryType=" + itemOwnerCategoryType
				+ ", itemOwnerSubCategoryType=" + itemOwnerSubCategoryType + ", txnId=" + txnId + ", clientId="
				+ clientId + ", attributes=" + attributes + ", ratingType=" + ratingType + "]";
	}

}
