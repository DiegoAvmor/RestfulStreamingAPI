package com.api.streaming.repository;

import java.util.List;
import java.util.Optional;

import com.api.streaming.model.UserRecommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends JpaRepository<UserRecommendation,Integer>{
    Optional<List<UserRecommendation>> findByIdUser(Integer id);
}
