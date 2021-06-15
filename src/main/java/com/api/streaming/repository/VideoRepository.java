package com.api.streaming.repository;

import com.api.streaming.model.User;
import com.api.streaming.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface VideoRepository extends JpaRepository<Video,Integer>, JpaSpecificationExecutor<Video> {
    Optional<Video> findByIdSerializable(String idSerializable);

    void deleteVideoByIdSerializable(String idSerializable);

    Optional<Video> findByAutor(User autor);

    @Transactional
    @Query(value = "SELECT id_video, titulo, average_rating FROM videos ORDER BY average_rating DESC LIMIT 5", nativeQuery= true)
    Optional<List<Object>> getSortedVideos();
    
}
