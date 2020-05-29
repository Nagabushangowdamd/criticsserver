package com.nk.critics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nk.critics.entities.ClientSummaryEntity;
import com.nk.critics.repositories.ClientSummaryRepository;


@Service
public class ClientSummaryService {

	@Autowired
	ClientSummaryRepository clientSummaryRepository;

	
	/*
	 * public long getNoOfApiByClient(String clientId) { long count;
	 * 
	 * count = clientSummaryRepository. return count; }
	 */

}
