package com.api.streaming.repository;

import com.api.streaming.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface VideoRepository extends JpaRepository<Video,Integer>, JpaSpecificationExecutor<Video> {
    Optional<Video> findByIdSerializable(String idSerializable);

    void deleteVideoByIdSerializable(String idSerializable);

}
