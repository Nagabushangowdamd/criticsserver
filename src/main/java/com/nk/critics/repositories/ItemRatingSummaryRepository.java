package com.nk.critics.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nk.critics.entities.ItemRatingSummaryEntity;
@Repository
public interface ItemRatingSummaryRepository extends JpaRepository<ItemRatingSummaryEntity, String> {

@Query(value = "SELECT * FROM ItemRatingSummary WHERE clientId = : clientId \\n-- #pageable\\n")
List<ItemRatingSummaryEntity> getTopItemsByClientId(@Param("clientId") String clientId,
		Pageable pageable);

}
