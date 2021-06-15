package com.api.streaming.controller;

import java.util.List;

import javax.validation.Valid;

import com.api.streaming.model.Rating;
import com.api.streaming.model.request.RatingRequest;
import com.api.streaming.service.RatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RatingController {

    @Autowired
    private RatingService ratingService;


    @PostMapping("/ratings/videos/{id}")
    public ResponseEntity<Rating> rateVideo(@PathVariable Integer id, @RequestBody @Valid RatingRequest request){
        return ResponseEntity.ok().body(ratingService.crearRating(id, request));
    }

    @PutMapping("/ratings/videos/{id}")
    public ResponseEntity<Rating> updateRating(@PathVariable Integer id, @RequestBody @Valid RatingRequest request){
        return ResponseEntity.ok().body(ratingService.updateRating(id, request));
    }

    @GetMapping("/ratings/videos/{videoId}/users/{userId}")
    public ResponseEntity<Rating> getVideoRating(@PathVariable Integer userId, @PathVariable Integer videoId){
        return ResponseEntity.ok().body(ratingService.getRating(userId, videoId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/ratings/{ratingId}")
    public ResponseEntity<Rating> deleteRating(@PathVariable Integer ratingId){
        return ResponseEntity.ok().body(ratingService.deleteRating(ratingId));
    }
    
    @GetMapping("/ratings/top")
    public ResponseEntity<List<Object>> topRatings(){
        return ResponseEntity.ok().body(ratingService.topRatings());
    }
}
