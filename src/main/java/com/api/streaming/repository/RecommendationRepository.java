package com.api.streaming.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.api.streaming.model.UserRecommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RecommendationRepository extends JpaRepository<UserRecommendation,Integer>{
    Optional<List<UserRecommendation>> findByIdUser(Integer id);

    @Transactional
    @Query(value = "SELECT videos.id_video, videos.titulo, videos.autor, videos.description, videos.average_rating, videos.video_location FROM videos JOIN user_recommendations ON videos.id_video = user_recommendations.id_video WHERE user_recommendations.id_user = :uID AND videos.average_rating>5", nativeQuery= true)
    Optional<List<Object>> getRecommendations(@Param("uID") Integer userId);
}
