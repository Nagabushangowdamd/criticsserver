package com.nk.critics.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nk.critics.configs.ResponseJsonBeansConfig;
import com.nk.critics.entities.ItemOwnerRatingSummaryEntity;
import com.nk.critics.entities.ItemRatingSummaryEntity;
import com.nk.critics.entities.RatingReviewEntity;
import com.nk.critics.services.ClientDetailsService;
import com.nk.critics.services.ItemOwnerRatingSummaryService;
import com.nk.critics.services.ItemRatingSummaryService;
import com.nk.critics.services.ReviewService;
import com.nk.critics.util.CriticsConstants;

@RestController
@RequestMapping("critics/v1/review")
public class ReviewController {

	private final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private ItemRatingSummaryService itemRatingSummaryService;

	@Autowired
	private ItemOwnerRatingSummaryService itemOwnerRatingSummaryService;

	@Produces("application/json")
	@Consumes("application/json")
	@PostMapping(value = "/add")
	public ResponseEntity<String> addReview(@RequestHeader Map<String, String> headers, @RequestBody String body)
			throws JsonMappingException, JsonProcessingException, RestClientException, URISyntaxException {

		LOGGER.info("Enter addReview Enter");
		Integer httpStatus = 200;
		LOGGER.info("request from client app----" + body.toString());
		boolean primaryKey = false;

		JSONObject validateClientRes = clientDetailsService.validateClient(headers);
		JSONObject resJson = (JSONObject) validateClientRes.get("resJson");
		JSONObject resHeader = (JSONObject) resJson.get("header");
		JSONObject resData = (JSONObject) resJson.get("resData");
		boolean validateClient = (boolean) validateClientRes.get("validationres");
		System.out.println("Existing client! " + validateClient);
		if (validateClient) {

			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			RatingReviewEntity ratingReview = mapper.readValue(body, RatingReviewEntity.class);
			LOGGER.info("[4] -> " + ratingReview);
			// preparing itemEntity and itemOwnerEntity
			if (ratingReview.getRatingType().equalsIgnoreCase("order") && ratingReview.getItemId() != null
					&& !ratingReview.getItemId().equals("")) {
				ItemRatingSummaryEntity itemEntity = mapper.readValue(body, ItemRatingSummaryEntity.class);
				// ItemOwnerRatingSummaryEntity itemOwnerEntity = mapper.readValue ( body,
				// ItemOwnerRatingSummaryEntity.class );
				itemEntity.setAvrgRating(ratingReview.getRating());
				itemEntity.setLastComment(ratingReview.getReview());
				itemEntity.setClientId(headers.get("clientid"));
				itemEntity.setCreatedDate(System.currentTimeMillis());
				itemRatingSummaryService.updateItemRatingSummary(itemEntity);
				primaryKey = true;
				/*
				 * itemOwnerEntity.setAvrgRating ( ratingReview.getRating () );
				 * itemOwnerEntity.setClientId ( headers.get ( "clientid" ) );
				 * itemOwnerRatingSummaryService.upadateItemOwner ( itemOwnerEntity );
				 */
			} else if (ratingReview.getRatingType().equalsIgnoreCase("user") && ratingReview.getItemOwnerId() != null
					&& !ratingReview.getItemOwnerId().equals("")) {
				ItemOwnerRatingSummaryEntity itemOwnerEntity = mapper.readValue(body,
						ItemOwnerRatingSummaryEntity.class);
				itemOwnerEntity.setAvrgRating(ratingReview.getRating());
				itemOwnerEntity.setLastComment(ratingReview.getReview());
				itemOwnerEntity.setClientId(headers.get("clientid"));
				itemOwnerRatingSummaryService.upadateItemOwnerRatingSummary(itemOwnerEntity);
				primaryKey = true;
			}

			if (primaryKey) {
				if (ratingReview != null) {
					ratingReview.setTxnId(headers.get("txnid"));
					ratingReview.setCreatedDate(System.currentTimeMillis());
					ratingReview.setUpdatedDate(System.currentTimeMillis());
					ratingReview.setClientId(headers.get("clientid"));

					ratingReview = reviewService.addReview(ratingReview);

					if (ratingReview != null) {
						if (ratingReview.getRatingType().equalsIgnoreCase("order")) {
							clientDetailsService.sendSavedDataToClient(ratingReview.getItemId(),
									ratingReview.getClientId(), ratingReview);
						} else {
							clientDetailsService.sendSavedDataToClient1(ratingReview.getItemOwnerId(),
									ratingReview.getClientId(), ratingReview);
						}
						// Success
						resHeader.put("result", "8000");
						resHeader.put("status", "Success");
						resHeader.put("txnId", headers.get("txnid"));
						resJson.put("data", resData);
						resData.put("reviewId", ratingReview.getReviewId());
					} else {
						// Not Save
						resHeader.put("result", "8002");
						resHeader.put("status", "Review not saved");
						resHeader.put("txnId", headers.get("txnid"));
						httpStatus = 403;
					}

				}
			} else {
				// Not Save
				resHeader.put("result", "8002");
				resHeader.put("status", "mandatory fields are invalid");
				resHeader.put("txnId", headers.get("txnid"));
				httpStatus = 400;

			}
		} else {
			resHeader.put("result", "8003");
			resHeader.put("status", "Invalid client credentials");
			resHeader.put("txnId", headers.get("txnid"));
			httpStatus = 401;
		}

		resJson.put("header", resHeader);
		LOGGER.info(resJson.toString());
		String result = resJson.toString();
		return ResponseEntity.status(httpStatus).body(result);

	}

	@Produces("application/json")
	@Consumes("application/json")
	@PostMapping(value = "/get/reviews/item/{itemId}")
	public ResponseEntity<String> getReviewsByItem(@RequestHeader Map<String, String> headers,
			@PathVariable("itemId") String itemId, @RequestParam Map<String, String> queryParam)
			throws IllegalArgumentException, JSONException, JsonParseException, JsonMappingException, IOException {

		LOGGER.info("Enter addReview Enter");
		Integer httpStatus = 200;
		JSONObject validateClientRes = clientDetailsService.validateClient(headers);
		JSONObject resJson = (JSONObject) validateClientRes.get("resJson");
		JSONObject resHeader = (JSONObject) resJson.get("header");
		boolean validateClient = (boolean) validateClientRes.get("validationres");
		System.out.println("Existing client! " + validateClient);
		int apiId = CriticsConstants.getReviewsByItem;
		Integer limit = CriticsConstants.defaultLimit;
		JSONObject reviews = new JSONObject();
		JSONObject startKey = new JSONObject();
		// Response content
		String ratingType = null;

		if (queryParam.containsKey("exclusiveStartKey") && (queryParam.get("exclusiveStartKey").length() != 0)) {
			String exclusiveStartKeyString = queryParam.get("exclusiveStartKey");
			int exclusiveStartKeyInt = Integer.parseInt(exclusiveStartKeyString);
			startKey.put("exclusiveStartKey", exclusiveStartKeyInt);
		}

		if (queryParam.containsKey("limit") && (queryParam.get("limit") != null)) {
			limit = Integer.parseInt(queryParam.get("limit"));
		}

		if (queryParam.containsKey("limit") && (queryParam.get("limit") != null)) {
			limit = Integer.parseInt(queryParam.get("limit"));
		}

		if (queryParam.containsKey("ratingType") && (queryParam.get("ratingType") != null)) {

			ratingType = (queryParam.get("ratingType"));
		}

		if (validateClient) {
			reviews = reviewService.getReviewsByItemId(itemId, startKey, limit, ratingType);
			if (reviews != null && reviews.length() > 0) {
				// Success
				resJson.put("data", reviews);
				resHeader.put("result", "8000");
				resHeader.put("txnId", headers.get("txnid"));
				resHeader.put("status", "Success");
			} else {
				// Not Save
				resHeader.put("result", "8002");
				resHeader.put("status", "No reviews for the given itemId");
				resHeader.put("txnId", headers.get("txnid"));
				httpStatus = 403;
			}
		} else {
			resHeader.put("result", "8003");
			resHeader.put("status", "Invalid client credentials");
			resHeader.put("txnId", headers.get("txnid"));
			httpStatus = 401;
		}

		resJson.put("header", resHeader);
		String result = resJson.toString();
		return ResponseEntity.status(httpStatus).body(result);
	}

	@Produces("application/json")
	@Consumes("application/json")
	@PostMapping(value = "/get/reviews/itemOrderId/{itemOrderId}")
	public ResponseEntity<String> getReviewsByItemOrderId(@RequestHeader Map<String, String> headers,
			@PathVariable("itemOrderId") String itemOrderId, @RequestParam Map<String, String> queryParam)
			throws IllegalArgumentException, JSONException, JsonParseException, JsonMappingException, IOException {
		LOGGER.info("Enter addReview Enter");
		Integer httpStatus = 200;
		JSONObject validateClientRes = clientDetailsService.validateClient(headers);
		JSONObject resJson = (JSONObject) validateClientRes.get("resJson");
		JSONObject resHeader = (JSONObject) resJson.get("header");
		boolean validateClient = (boolean) validateClientRes.get("validationres");
		System.out.println("Existing client! " + validateClient);
		int apiId = CriticsConstants.getReviewsByItem;
		Integer limit = CriticsConstants.defaultLimit;
		JSONObject reviews = new JSONObject();
		JSONObject startKey = new JSONObject();
		// Response content
		String ratingType = null;

		if (queryParam.containsKey("exclusiveStartKey") && (queryParam.get("exclusiveStartKey").length() != 0)) {
			String exclusiveStartKeyString = queryParam.get("exclusiveStartKey");
			int exclusiveStartKeyInt = Integer.parseInt(exclusiveStartKeyString);
			startKey.put("exclusiveStartKey", exclusiveStartKeyInt);
		}

		if (queryParam.containsKey("limit") && (queryParam.get("limit") != null)) {
			limit = Integer.parseInt(queryParam.get("limit"));
		}

		if (queryParam.containsKey("limit") && (queryParam.get("limit") != null)) {
			limit = Integer.parseInt(queryParam.get("limit"));
		}

		if (queryParam.containsKey("ratingType") && (queryParam.get("ratingType") != null)) {

			ratingType = (queryParam.get("ratingType"));
		}

		if (validateClient) {

			reviews = reviewService.getReviewByItemOrderId(itemOrderId, ratingType);
			if (reviews != null && reviews.length() > 0) {
				// Success
				resJson.put("data", reviews);
				resHeader.put("result", "8000");
				resHeader.put("txnId", headers.get("txnid"));
				resHeader.put("status", "Success");
			} else {
				// Not Save
				resHeader.put("result", "8002");
				resHeader.put("status", "No reviews for the given itemOrderId");
				resHeader.put("txnId", headers.get("txnid"));
				httpStatus = 403;
			}
		} else {
			resHeader.put("result", "8003");
			resHeader.put("status", "Invalid client credentials");
			resHeader.put("txnId", headers.get("txnid"));
			httpStatus = 401;
		}
		resJson.put("header", resHeader);
		LOGGER.info(resJson.toString());
		String result = resJson.toString();
		return ResponseEntity.status(httpStatus).body(result);
	}
}
