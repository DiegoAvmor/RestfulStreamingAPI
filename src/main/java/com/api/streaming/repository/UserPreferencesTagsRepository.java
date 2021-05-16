package com.api.streaming.repository;

import com.api.streaming.model.User;
import com.api.streaming.model.UserPreferencesTags;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserPreferencesTagsRepository extends JpaRepository<UserPreferencesTags,Integer>{

    public Optional<UserPreferencesTags> findById(Integer id);

    @Transactional
    void deleteByUser(User id);
}
  