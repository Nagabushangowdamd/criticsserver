package com.nk.critics.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nk.critics.entities.ClientDetailsEntity;
import com.nk.critics.services.ClientDetailsService;

@RestController
@RequestMapping("critics/v1/client")
public class ClientController {

	@Autowired
	private ClientDetailsService clientDetailsService;

	@GetMapping
	@RequestMapping("/test/{id}")
	public String test(@PathVariable(name = "id") String clientId) {
		System.out.println("Test successfull");
		return clientId + "st Test successfull";
	}

	@PostMapping(value = "/add")
	public Object addClient(@RequestBody ClientDetailsEntity clientDetailsEntityRequested) {

		ClientDetailsEntity clientDetailsEntity = clientDetailsEntityRequested;
		Integer httpStatus = 200;
		// Response content
		JSONObject resJson = new JSONObject();
		// Response Header
		JSONObject resHeader = new JSONObject();
		// Response Data
		JSONObject resData = new JSONObject();
		try {
			if (clientDetailsEntityRequested != null) {
				clientDetailsEntity.setActive(true);
				clientDetailsEntity.setCreatedDate(System.currentTimeMillis());
				clientDetailsEntity.setUpdatedDate(System.currentTimeMillis());
				clientDetailsEntity = clientDetailsService.addClient(clientDetailsEntity);
				if (clientDetailsEntity != null) {
					// operation success
					resHeader.put("result", "8000");
					resHeader.put("status", "success");
					resHeader.put("txnId", System.currentTimeMillis());
					resJson.put("data", resData);
					resData.put("clientId", clientDetailsEntity.getClientId());
				} else {
					// Not Save
					resHeader.put("result", "8002");
					resHeader.put("status", "Internal Server Error");
					httpStatus = 403;
				}
			} else {
				// Wrong Request
				resHeader.put("result", "8002");
				resHeader.put("status", "Wrong Request");
				httpStatus = 403;

			}
			// Final Response Json
			resJson.put("header", resHeader);
			resJson.put("data", resData);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return resJson.toString();
	}
}
