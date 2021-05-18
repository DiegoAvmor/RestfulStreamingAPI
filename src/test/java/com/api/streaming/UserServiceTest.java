package com.api.streaming;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.api.streaming.exception.AccessDeniedException;
import com.api.streaming.exception.NotFoundException;
import com.api.streaming.model.request.LoginUserRequest;
import com.api.streaming.repository.RatingRepository;
import com.api.streaming.repository.RecommendationRepository;
import com.api.streaming.repository.UserPreferencesTagsRepository;
import com.api.streaming.repository.UserRepository;
import com.api.streaming.repository.VideoRepository;
import com.api.streaming.service.impl.UserServiceImpl;
import com.api.streaming.util.JwtTokenUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    RecommendationRepository recommendationRepository;

    @Autowired
    UserPreferencesTagsRepository userPreferencesTagsRepository;
    
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setup(){
        LoginUserRequest request = new LoginUserRequest();
        request.setEmail("carlos@outlook.es");
        request.setPassword("12345678");
        userServiceImpl.loadUser(request);
    }

    @Test
    @DisplayName("Test the Fucking sesion")
    public void testSesion(){
        assertTrue(userServiceImpl.testSesion());
    }


}
