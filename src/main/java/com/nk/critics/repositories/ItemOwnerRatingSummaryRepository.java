package com.nk.critics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nk.critics.entities.ItemOwnerRatingSummaryEntity;
@Repository
public interface ItemOwnerRatingSummaryRepository extends JpaRepository<ItemOwnerRatingSummaryEntity, String> {

}
