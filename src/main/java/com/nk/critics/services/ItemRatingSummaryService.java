package com.nk.critics.services;

import java.util.ArrayList; 
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nk.critics.entities.ItemRatingSummaryEntity;
import com.nk.critics.entities.RatingReviewEntity;
import com.nk.critics.repositories.ItemRatingSummaryRepository;
import com.nk.critics.util.MathUtil;

@Service
public class ItemRatingSummaryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemRatingSummaryService.class);

	@Autowired
	ItemRatingSummaryRepository itemRatingSummaryRepository;

	public ItemRatingSummaryEntity saveItemSummary(ItemRatingSummaryEntity itemRatingSummaryEntity) {
		ItemRatingSummaryEntity dbEntity = itemRatingSummaryRepository.save(itemRatingSummaryEntity);
		return dbEntity;
	}
	

	
	public void updateItemRatingSummary(ItemRatingSummaryEntity itemEntity) {

		long noOfRatings = 0;
		double avrgRating = 0;
		long noOfReviews = 0;
		try {
			ItemRatingSummaryEntity dbitemEntity = itemRatingSummaryRepository
					.findById(itemEntity.getItemId()).orElse(null);
			if (dbitemEntity != null) {
				avrgRating = dbitemEntity.getAvrgRating();
				noOfRatings = dbitemEntity.getNoOfRatings();
				avrgRating = ((avrgRating * noOfRatings) + itemEntity.getAvrgRating()) / (noOfRatings + 1);
				noOfRatings++;
				if (itemEntity.getLastComment()==(null) || itemEntity.getLastComment().equals("")) {
					itemEntity.setNoOfReviews(dbitemEntity.getNoOfReviews());
				} else {
					noOfReviews = dbitemEntity.getNoOfReviews();
					itemEntity.setNoOfReviews(++noOfReviews);
				}
				itemEntity.setLastComment(itemEntity.getLastComment());
				itemEntity.setLastRating(itemEntity.getAvrgRating());
				itemEntity.setUpdatedDate(System.currentTimeMillis());
				itemEntity.setAvrgRating(MathUtil.doublePrecision(avrgRating,1));
				itemEntity.setCreatedDate(dbitemEntity.getCreatedDate());
				itemEntity.setNoOfRatings(noOfRatings);
				saveItemSummary(itemEntity);
			} else {

				itemEntity.setNoOfRatings(++noOfRatings);
				itemEntity.setLastRating(itemEntity.getAvrgRating());
				if (itemEntity.getLastComment()==(null)|| itemEntity.getLastComment().equals("")) {
					itemEntity.setNoOfReviews(noOfReviews);
				} else {

					itemEntity.setNoOfReviews(++noOfReviews);
				}
				itemEntity.setCreatedDate(System.currentTimeMillis());
				itemEntity.setUpdatedDate(System.currentTimeMillis());
				saveItemSummary(itemEntity);
			}
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
		return;
	}
        
	public ItemRatingSummaryEntity getAverageReviewByItemId(String itemId) {
		ItemRatingSummaryEntity dbitemEntity = itemRatingSummaryRepository.findById(itemId).orElse(null);
		if (dbitemEntity != null) {
			return dbitemEntity;
		} else {
			return null;
		}
	}
	
	
	public JSONObject getTopItems(String clientId, JSONObject startKey, int limit) {
		JSONObject result = new JSONObject();
		List<ItemRatingSummaryEntity> topItemsByClientId = new ArrayList<ItemRatingSummaryEntity>();
		
		try {
			Pageable pageObj = PageRequest.of(startKey.getInt("exclusiveStartKey"), limit,Sort.by("avrgRating").descending());  
			topItemsByClientId = itemRatingSummaryRepository.getTopItemsByClientId(clientId, pageObj);
			result.put("reviews", topItemsByClientId);
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
		return result;
	}
	
	public ItemRatingSummaryEntity updateItemEntity(ItemRatingSummaryEntity itemEntity) {

		ItemRatingSummaryEntity savedItem = null;
		try {
			ItemRatingSummaryEntity dbitemEntity = itemRatingSummaryRepository
					.findById(itemEntity.getItemId()).orElse(null);

			if (dbitemEntity != null) {
				itemEntity.setUpdatedDate(System.currentTimeMillis());
				itemEntity.setCreatedDate(dbitemEntity.getCreatedDate());
				itemEntity.setAvrgRating(dbitemEntity.getAvrgRating());
				itemEntity.setNoOfRatings(dbitemEntity.getNoOfRatings());
				savedItem = saveItemSummary(itemEntity);
			} else {
				itemEntity.setUpdatedDate(System.currentTimeMillis());
				itemEntity.setCreatedDate(System.currentTimeMillis());
				savedItem = saveItemSummary(itemEntity);
			}

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}

		return savedItem;
	}
	
	public void updateItemRatingSummary1(ItemRatingSummaryEntity itemEntity, RatingReviewEntity dbReview) {

		long noOfRatings = 0;
		double avrgRating = 0;
		long noOfReviews = 0;
		try {
			ItemRatingSummaryEntity dbitemEntity = itemRatingSummaryRepository
					.findById(itemEntity.getItemId()).orElse(null);
			if (dbitemEntity != null) {
				avrgRating = dbitemEntity.getAvrgRating();
				noOfRatings = dbitemEntity.getNoOfRatings();
				avrgRating = ((avrgRating * noOfRatings) + itemEntity.getAvrgRating() - dbReview.getRating())
						/ (noOfRatings);
				itemEntity.setLastComment(itemEntity.getLastComment());
				itemEntity.setLastRating(itemEntity.getAvrgRating());
				if(dbReview.getReview()==(null)||dbReview.getReview().equals(""))
				{
				if (itemEntity.getLastComment()==(null) || itemEntity.getLastComment().equals("")) {
					itemEntity.setNoOfReviews(dbitemEntity.getNoOfReviews());
				} else {
					noOfReviews = dbitemEntity.getNoOfReviews();
					itemEntity.setNoOfReviews(++noOfReviews);
				}
				}else
				{
					if (itemEntity.getLastComment()==(null) || itemEntity.getLastComment().equals("")) {
						noOfReviews = dbitemEntity.getNoOfReviews();
						itemEntity.setNoOfReviews(--noOfReviews);
					} else {		
						itemEntity.setNoOfReviews(dbitemEntity.getNoOfReviews());					
					}
				}
				itemEntity.setUpdatedDate(System.currentTimeMillis());
				itemEntity.setAvrgRating(MathUtil.doublePrecision(avrgRating, 1));
				itemEntity.setCreatedDate(dbitemEntity.getCreatedDate());
				itemEntity.setNoOfRatings(noOfRatings);
				saveItemSummary(itemEntity);
			} else {

				itemEntity.setNoOfRatings(++noOfRatings);
				itemEntity.setLastRating(itemEntity.getAvrgRating());
				if (itemEntity.getLastComment()==(null) || itemEntity.getLastComment().equals("")) {
					itemEntity.setNoOfReviews(noOfReviews);
				} else {
					itemEntity.setNoOfReviews(++noOfReviews);
				}
				itemEntity.setCreatedDate(System.currentTimeMillis());
				itemEntity.setUpdatedDate(System.currentTimeMillis());
				saveItemSummary(itemEntity);
			}
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
		return;
	}


}
