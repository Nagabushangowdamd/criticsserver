package com.nk.critics.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nk.critics.entities.RatingReviewEntity;
import com.nk.critics.repositories.RatingReviewRepository;

@Service
public class ReviewService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewService.class);

	@Autowired
	private RatingReviewRepository ratingReviewRepository;

	public RatingReviewEntity addReview(RatingReviewEntity review) {
		try {
			ratingReviewRepository.save(review);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return null;
		}
		return review;
	}

	public JSONObject getReviewsByItemId(String itemId, JSONObject startKey, Integer limit, String ratingType)
			throws JSONException {
		JSONObject result = new JSONObject();
		List<RatingReviewEntity> reviewsByItemId = new ArrayList<RatingReviewEntity>();

		try {
			int startKeyExtracted = startKey.getInt("exclusiveStartKey");
			Pageable pageObj = PageRequest.of(startKeyExtracted, limit);
			reviewsByItemId = ratingReviewRepository.getReviewsByItemId(itemId, ratingType,pageObj);
			result.put("reviews", reviewsByItemId);
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
		return result;
	}

	public JSONObject getReviewByItemOrderId(String itemOrderId, String ratingType) {
		JSONObject result = new JSONObject();
		List<RatingReviewEntity> reviewByItemOrderId = new ArrayList<RatingReviewEntity>();

		try {
			reviewByItemOrderId = ratingReviewRepository.getReviewByItemOrderId(itemOrderId, ratingType);
			result.put("reviews", reviewByItemOrderId);
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}

		return result;

	}

}
