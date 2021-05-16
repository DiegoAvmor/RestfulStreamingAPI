package com.api.streaming.repository;

import com.api.streaming.model.User;
import com.api.streaming.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NamedNativeQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface VideoRepository extends JpaRepository<Video,Integer> {
    Optional<Video> findByIdSerializable(String idSerializable);

    void deleteVideoByIdSerializable(String idSerializable);

    Optional<Video> findByAutor(User autor);

}
