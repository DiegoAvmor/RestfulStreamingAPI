package com.api.streaming.service;

import com.api.streaming.model.User;
import com.api.streaming.model.UserRecommendation;
import com.api.streaming.model.dto.TokenDto;
import com.api.streaming.model.request.LoginUserRequest;
import com.api.streaming.model.request.RegisterUserRequest;
import java.util.List;


public interface UserService {
    public TokenDto loadUser(LoginUserRequest request);

    public User getUser(Integer id);

    public User registerUser(RegisterUserRequest request);

    public List<UserRecommendation> getRecommendations(Integer id);

    public User deleteUser(Integer id);

    public User editUser(Integer id, RegisterUserRequest request);
}
