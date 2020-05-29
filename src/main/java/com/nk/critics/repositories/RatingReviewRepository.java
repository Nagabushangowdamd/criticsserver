package com.nk.critics.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nk.critics.entities.RatingReviewEntity;
@Repository
public interface RatingReviewRepository extends JpaRepository<RatingReviewEntity, String> {

	@Query(value = "SELECT * FROM rating_review WHERE item_id=?1 AND rating_type=?2  \\n-- #pageable \\",nativeQuery = true)
	public List<RatingReviewEntity> getReviewsByItemId(
     String itemId,
	String ratingType,Pageable pageable);
	

	
	@Query(value = "SELECT * FROM RatingReview WHERE  itemOrderId:itemOrderId AND ratingType:ratingType",nativeQuery = true)
	public List<RatingReviewEntity> getReviewByItemOrderId
	(@Param("itemOrderId") String itemOrderId,
    @Param("ratingType") String ratingType);
}
