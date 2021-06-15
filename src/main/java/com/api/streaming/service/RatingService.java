package com.api.streaming.service;

import java.util.List;

import com.api.streaming.model.Rating;
import com.api.streaming.model.request.RatingRequest;


public interface RatingService {

    public Rating crearRating(Integer id, RatingRequest request);

    public Rating getRating(int userId, int videoId);

    public Rating updateRating(Integer id, RatingRequest request);

    public Rating deleteRating(Integer id);

    public List<Object> topRatings();

}