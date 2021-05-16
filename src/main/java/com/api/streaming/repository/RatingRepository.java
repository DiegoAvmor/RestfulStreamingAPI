package com.api.streaming.repository;

import java.util.List;
import java.util.Optional;

import com.api.streaming.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface RatingRepository extends JpaRepository<Rating,Integer>{

    boolean existsByIdUserAndVideo(Integer userId, Integer video);

    Optional<Rating> findByIdUserAndVideo(Integer userId, Integer video);

    List<Rating> findByVideo(Integer video);

    @Query("SELECT AVG(e.rating) FROM Rating e WHERE e.video = ?1")
    float getVideoRatingAvarage(Integer video);

    @Transactional
    void deleteByIdUser(Integer userId);
}