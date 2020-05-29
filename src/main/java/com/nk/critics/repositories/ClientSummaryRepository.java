package com.nk.critics.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nk.critics.entities.ClientSummaryEntity;
@Repository
public interface ClientSummaryRepository extends JpaRepository<ClientSummaryEntity, String> {

	@Query("SELECT * FROM ClientSummaryEntity WHERE clientId = :clientId AND apiId = :apiId ")
	ClientSummaryEntity getClientSummaryByClientIdAndApiId(
			@Param("clientId") String clientId,
			@Param("apiId") int apiId);
}
