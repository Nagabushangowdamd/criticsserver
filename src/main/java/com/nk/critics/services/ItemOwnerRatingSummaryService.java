package com.nk.critics.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.nk.critics.entities.ItemOwnerRatingSummaryEntity;
import com.nk.critics.repositories.ItemOwnerRatingSummaryRepository;
import com.nk.critics.util.MathUtil;


@Service
public class ItemOwnerRatingSummaryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ItemOwnerRatingSummaryService.class);
	
	@Autowired
	private ItemOwnerRatingSummaryRepository itemOwnerRatingSummaryRepository;
	
	public ItemOwnerRatingSummaryEntity getAverageReviewByItemOwnerId(String itemOwnerId) {
		ItemOwnerRatingSummaryEntity dbitemEntity = itemOwnerRatingSummaryRepository.findById(itemOwnerId).orElse(null);
		if (dbitemEntity != null) {
			return dbitemEntity;
		} else {
			return null;
		}
	}
	
	public void upadateItemOwnerRatingSummary(ItemOwnerRatingSummaryEntity itemOwnerEntity) {
		long noOfRatings = 0;
		double avrgRating = 0;
		long noOfReviews=0;
		ItemOwnerRatingSummaryEntity dbitemEntity = itemOwnerRatingSummaryRepository
				.findById(itemOwnerEntity.getItemOwnerId()).orElse(null);

		try {
			if (dbitemEntity != null) {
				avrgRating = dbitemEntity.getAvrgRating();
				noOfRatings = dbitemEntity.getNoOfRatings();
				avrgRating = ((avrgRating * noOfRatings) + itemOwnerEntity.getAvrgRating()) / (noOfRatings + 1);
				noOfRatings++;
				if (itemOwnerEntity.getLastComment()==(null) || itemOwnerEntity.getLastComment().equals("")) {
					itemOwnerEntity.setNoOfReviews(dbitemEntity.getNoOfReviews());
				} else {
					noOfReviews = dbitemEntity.getNoOfReviews();
					itemOwnerEntity.setNoOfReviews(++noOfReviews);
				}
				itemOwnerEntity.setUpdatedDate(System.currentTimeMillis());
				itemOwnerEntity.setLastRating(itemOwnerEntity.getAvrgRating());
				itemOwnerEntity.setLastComment(itemOwnerEntity.getLastComment());
				itemOwnerEntity.setAvrgRating(MathUtil.doublePrecision(avrgRating, 1));
				itemOwnerEntity.setCreatedDate(dbitemEntity.getCreatedDate());
				itemOwnerEntity.setNoOfRatings(noOfRatings);
				saveItemOwner(itemOwnerEntity);
			} else {
				itemOwnerEntity.setNoOfRatings(++noOfRatings);
				itemOwnerEntity.setLastRating(itemOwnerEntity.getAvrgRating());
				itemOwnerEntity.setLastComment(itemOwnerEntity.getLastComment());
				itemOwnerEntity.setCreatedDate(System.currentTimeMillis());
				itemOwnerEntity.setUpdatedDate(System.currentTimeMillis());
				saveItemOwner(itemOwnerEntity);
			}
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
		return;

	}
	
	public ItemOwnerRatingSummaryEntity saveItemOwner(ItemOwnerRatingSummaryEntity itemOwnerRatingSummaryEntity) {
		try {
			itemOwnerRatingSummaryRepository.save(itemOwnerRatingSummaryEntity);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return null;
		}
		return itemOwnerRatingSummaryEntity;
	}

}
