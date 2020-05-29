package com.nk.critics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nk.critics.entities.ClientDetailsEntity;

@Repository
public interface ClientDetailsRepository extends JpaRepository<ClientDetailsEntity, String> {

}