package com.nk.critics.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nk.critics.configs.ResponseJsonBeansConfig;
import com.nk.critics.entities.ClientDetailsEntity;
import com.nk.critics.entities.ClientSummaryEntity;
import com.nk.critics.entities.ItemOwnerRatingSummaryEntity;
import com.nk.critics.entities.ItemRatingSummaryEntity;
import com.nk.critics.entities.RatingReviewEntity;
import com.nk.critics.repositories.ClientDetailsRepository;
import com.nk.critics.repositories.ClientSummaryRepository;
import com.nk.critics.util.CriticsConstants;

@Service
public class ClientDetailsService {

	private final Logger LOGGER = LoggerFactory.getLogger(ClientDetailsService.class);

	public ClientDetailsService() {
		System.out.println("ClientDetailsService bean created");
	}

	private ItemRatingSummaryEntity itemEntity;

	private ItemOwnerRatingSummaryEntity itemOwnerEntity;

	public ItemRatingSummaryEntity getItemEntity() {
		return itemEntity;
	}

	public void setItemEntity(ItemRatingSummaryEntity itemEntity) {
		this.itemEntity = itemEntity;
	}

	public ItemOwnerRatingSummaryEntity getItemOwnerEntity() {
		return itemOwnerEntity;
	}

	public void setItemOwnerEntity(ItemOwnerRatingSummaryEntity itemOwnerEntity) {
		this.itemOwnerEntity = itemOwnerEntity;
	}

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private ClientSummaryRepository clientSummaryRepository;

	@Autowired
	private ItemRatingSummaryService itemRatingSummaryService;

	@Autowired
	private ItemOwnerRatingSummaryService itemOwnerRatingSummaryService;

	HashMap<String, ClientDetailsEntity> clientCredentials = new HashMap();

	public ClientDetailsEntity addClient(ClientDetailsEntity client) {

		ClientDetailsEntity clientDetailsEntity = null;
		try {
			clientDetailsEntity = clientDetailsRepository.save(client);

		} catch (Exception e) {
			LOGGER.error(e.toString());
			return null;
		}
		return clientDetailsEntity;
	}

	public ClientDetailsEntity getClientDetails(String clientId) {

		ClientDetailsEntity dbclientEntity = null;
		System.out.println("calling db");
		try {
			dbclientEntity = clientDetailsRepository.findById(clientId).orElse(null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbclientEntity;
	}

	/*
	 * Function that verifies the headers from request and returns by initializing
	 * the clientDetailsEntity
	 */
	public JSONObject verifyAndInitialize(Map<String, String> headers) {
		/* To store rseponseJsonObject snd ClientDetailsEntity */

		JSONObject verifyAndInitializeResults = ResponseJsonBeansConfig.getJsonBean();
		int apiId = CriticsConstants.addReview;
		Integer httpStatus = 200;
		// Response content
		JSONObject resJson = ResponseJsonBeansConfig.getJsonBean();
		// Response Header
		JSONObject resHeader = ResponseJsonBeansConfig.getJsonBean();
		// Response Data
		JSONObject resData = ResponseJsonBeansConfig.getJsonBean();
		boolean primaryKey = false;
		ClientDetailsEntity clientDetailsEntity = new ClientDetailsEntity();
		if (headers.containsKey("apikey") && headers.get("apikey") != null) {
			if (headers.containsKey("secretkey") && headers.get("secretkey") != null) {
				if (headers.containsKey("clientid") && headers.get("clientid") != null) {
					if (headers.containsKey("version") && headers.get("version") != null) {
						if (headers.containsKey("parentclientid") && headers.get("parentclientid") != null) {

							clientDetailsEntity.setApiKey(headers.get("apikey"));
							clientDetailsEntity.setSecretKey(headers.get("secretkey"));
							clientDetailsEntity.setClientId(headers.get("clientid"));
							clientDetailsEntity.setVersion(headers.get("version"));
							clientDetailsEntity.setParentClientId(headers.get("parentclientid"));
						} else {
							resHeader.put("result", "8004");
							resHeader.put("status", "Invalid parentClientId ");
							resHeader.put("txnId", headers.get("txnid"));
							httpStatus = 401;
						}
					} else {
						resHeader.put("result", "8005");
						resHeader.put("status", "Invalid version ");
						resHeader.put("txnId", headers.get("txnid"));
						httpStatus = 401;
					}
				} else {
					resHeader.put("result", "8006");
					resHeader.put("status", "Invalid clientId  ");
					resHeader.put("txnId", headers.get("txnid"));
					httpStatus = 401;
				}
			} else {
				resHeader.put("result", "8007");
				resHeader.put("status", "Invalidsecret Key ");
				resHeader.put("txnId", headers.get("txnid"));
				httpStatus = 401;
			}
		} else {
			resHeader.put("result", "8008");
			resHeader.put("status", "Invalid apiKey");
			resHeader.put("txnId", headers.get("txnid"));
			httpStatus = 401;
		}
		resJson.put("header", resHeader);
		resJson.put("resData", resData);
		LOGGER.info("[1a] -> " + resHeader.toString());
		LOGGER.info("[1b] -> " + resData.toString());
		verifyAndInitializeResults.put("resJson", resJson);
		verifyAndInitializeResults.put("clientDetailsEntity", clientDetailsEntity);
		LOGGER.info("[2] -> " + verifyAndInitializeResults.toString());
		return verifyAndInitializeResults;
	}

	public JSONObject validateClient(Map<String, String> headers) {
		/* Retrieving clientDetailsEntity from the map object */
		int apiId = CriticsConstants.addReview;
		JSONObject verifyAndInitializeResults = verifyAndInitialize(headers);
		ClientDetailsEntity clientDetailsEntity = (ClientDetailsEntity) verifyAndInitializeResults
				.get("clientDetailsEntity");

		if (clientDetailsEntity != null) {
			String clientId = clientDetailsEntity.getClientId();
			ClientSummaryEntity clientSummaryEntity = null;
			ClientDetailsEntity dbclientEntity = (ClientDetailsEntity) clientCredentials.get(clientId);/* doubt */
			if (dbclientEntity == null) {
				dbclientEntity = getClientDetails(clientId);
				clientCredentials.put(clientId, dbclientEntity);
			}
			if (dbclientEntity != null) {

				boolean result = validator(clientDetailsEntity, dbclientEntity);
				if (result == false) {
					dbclientEntity = getClientDetails(clientId);
					result = validator(clientDetailsEntity, dbclientEntity);
					clientCredentials.put(clientId, dbclientEntity);
				}
				if (result == true && (dbclientEntity.isActive() == true)) {
					clientSummaryEntity = clientSummaryRepository
							.getClientSummaryByClientIdAndApiId(dbclientEntity.getClientId(), apiId);

					if (clientSummaryEntity != null) {
						clientSummaryEntity.setApiCalls(clientSummaryEntity.getApiCalls() + 1);
						clientSummaryEntity.setUpdatedDate(System.currentTimeMillis());
						clientSummaryRepository.save(clientSummaryEntity);
						LOGGER.info("[3] -> " + verifyAndInitializeResults.toString());
						// clientSummaryRepository. updateClient(clientSummaryEntity);
					} else {
						clientSummaryEntity = new ClientSummaryEntity();
						clientSummaryEntity.setCreatedDate(System.currentTimeMillis());
						clientSummaryEntity.setApiCalls(1);
						clientSummaryEntity.setClientId(clientId);
						clientSummaryEntity.setApiId(apiId);
						clientSummaryEntity.setUpdatedDate(System.currentTimeMillis());
						clientSummaryRepository.save(clientSummaryEntity);
					}
					verifyAndInitializeResults.put("validationres", true);
					LOGGER.info("[3] -> " + verifyAndInitializeResults.toString());
					return verifyAndInitializeResults;
				} else {
					verifyAndInitializeResults.put("validationres", false);
					LOGGER.info("[3] -> " + verifyAndInitializeResults.toString());
					return verifyAndInitializeResults;
				}
			} else {
				verifyAndInitializeResults.put("validationres", false);
				LOGGER.info("[3] -> " + verifyAndInitializeResults.toString());
				return verifyAndInitializeResults;
			}
		} else {
			verifyAndInitializeResults.put("validationres", false);
			LOGGER.info("[3] -> " + verifyAndInitializeResults.toString());
			return verifyAndInitializeResults;
		}
	}

	public boolean validator(ClientDetailsEntity clientDetailsEntity, ClientDetailsEntity dbclientEntity) {
		String db = dbclientEntity.getApiKey() + dbclientEntity.getClientId() + dbclientEntity.getParentClientId()
				+ dbclientEntity.getSecretKey() + dbclientEntity.getVersion();
		int hash = db.hashCode();
		String client = clientDetailsEntity.getApiKey() + clientDetailsEntity.getClientId()
				+ clientDetailsEntity.getParentClientId() + clientDetailsEntity.getSecretKey()
				+ clientDetailsEntity.getVersion();
		int hash1 = client.hashCode();
		if (hash1 == hash) {
			return true;
		} else
			return false;
	}

	public void sendSavedDataToClient(String itemId, String clientId, RatingReviewEntity ratingReview)
			throws RestClientException, URISyntaxException, JsonProcessingException {

		List<ItemRatingSummaryEntity> reviews = new ArrayList<ItemRatingSummaryEntity>();
		RestTemplate restTemplate = new RestTemplate();
		ClientDetailsEntity clientEntity = clientCredentials.get(clientId);
		Map<String, String> credentials = clientEntity.getClientCredentials();

		final String baseUrl = clientCredentials.get(clientId).getWebHook();
		if (baseUrl != null && baseUrl.length() > 1) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("clientId", credentials.get("clientId"));
			headers.set("apiKey", credentials.get("apiKey"));
			headers.set("secretKey", credentials.get("secretKey"));
			// itemRatingSummaryService.getAverageReviewByItemId(itemId);
			headers.set("txnId", System.currentTimeMillis() + itemId);
			reviews.add(itemRatingSummaryService.getAverageReviewByItemId(itemId));
			JSONObject resJson1 = new JSONObject();
			// Success
			resJson1.put("data", reviews);
			JSONObject clientBody = resJson1.getJSONArray("data").getJSONObject(0);
			clientBody.put("itemOrderId", ratingReview.getItemOrderId());
			clientBody.put("ratingType", ratingReview.getRatingType());
			clientBody.put("itemId", ratingReview.getItemId());
			clientBody.put("reviewId", ratingReview.getReviewId());
			/*
			 * ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			 * String json = ow.writeValueAsString(getItemEntity());
			 */
			HttpEntity<String> request = new HttpEntity<String>(clientBody.toString(), headers);
			LOGGER.info("request --- for client" + clientBody.toString());
			ResponseEntity<Object> responseEntityStr = null;
			try {
				responseEntityStr = restTemplate.postForEntity(new URI(baseUrl), request, Object.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (responseEntityStr != null) {
				LOGGER.info("response from client---" + responseEntityStr.getBody().toString());
			}
		}
	}

	public void sendSavedDataToClient1(String itemOwnerId, String clientId, RatingReviewEntity ratingReview)
			throws RestClientException, URISyntaxException, JsonProcessingException {

		List<ItemOwnerRatingSummaryEntity> reviews = new ArrayList<ItemOwnerRatingSummaryEntity>();
		RestTemplate restTemplate = new RestTemplate();
		ClientDetailsEntity clientEntity = clientCredentials.get(clientId);
		Map<String, String> credentials = clientEntity.getClientCredentials();

		final String baseUrl = clientCredentials.get(clientId).getWebHook();
		if (baseUrl != null && baseUrl.length() > 1) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("clientId", credentials.get("clientId"));
			headers.set("apiKey", credentials.get("apiKey"));
			headers.set("secretKey", credentials.get("secretKey"));
			// itemRatingSummaryService.getAverageReviewByItemId(itemId);
			headers.set("txnId", System.currentTimeMillis() + itemOwnerId);
			reviews.add(itemOwnerRatingSummaryService.getAverageReviewByItemOwnerId(itemOwnerId));
			JSONObject resJson1 = new JSONObject();
			// Success
			resJson1.put("data", reviews);
			JSONObject clientBody = resJson1.getJSONArray("data").getJSONObject(0);
			clientBody.put("itemOrderId", ratingReview.getItemOrderId());
			clientBody.put("ratingType", ratingReview.getRatingType());
			clientBody.put("itemId", ratingReview.getItemId());
			clientBody.put("reviewId", ratingReview.getReviewId());
			/*
			 * ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			 * String json = ow.writeValueAsString(getItemEntity());
			 */
			LOGGER.info("request --- for client" + clientBody.toString());
			HttpEntity<String> request = new HttpEntity<String>(clientBody.toString(), headers);
			ResponseEntity<Object> responseEntityStr = null;
			try {
				responseEntityStr = restTemplate.postForEntity(new URI(baseUrl), request, Object.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (responseEntityStr != null) {
				LOGGER.info("response from client---" + responseEntityStr.getBody().toString());
			}
		}
	}

}
