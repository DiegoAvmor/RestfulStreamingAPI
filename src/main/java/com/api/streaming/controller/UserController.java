package com.api.streaming.controller;

import java.util.List;

import javax.validation.Valid;

import com.api.streaming.model.User;
import com.api.streaming.model.Rating;
import com.api.streaming.model.UserRecommendation;
import com.api.streaming.model.dto.TokenDto;
import com.api.streaming.model.request.LoginUserRequest;
import com.api.streaming.model.request.RegisterUserRequest;
import com.api.streaming.model.request.RatingRequest;
import com.api.streaming.service.UserService;
import com.api.streaming.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginUserRequest request) {
        return ResponseEntity.ok().body(userService.loadUser(request));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterUserRequest request){
        return ResponseEntity.ok().body(userService.registerUser(request));
    }

    /*Ejemplos de seguridad por roles*/

    //Cualquiera puede hacer uso del endpoint
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id){
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    //Solo USER puede usarlo
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/roleUser")
    public ResponseEntity<String> userExample(){
        return ResponseEntity.ok().body("Only Users can see this");
    }

    //Solo ADMIN puede usarlo
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roleAdmin")
    public ResponseEntity<String> adminExample(){
        return ResponseEntity.ok().body("Only Admins can see this");
    }

    //Endpoint Recomendaciones
    @GetMapping("/users/{id}/recomendations")
    public ResponseEntity<List<UserRecommendation>> getRecommendation(@PathVariable Integer id){
        return ResponseEntity.ok().body(userService.getRecommendations(id));
    }

    @PostMapping("/rate/{id}")
    public ResponseEntity<Rating> rateVideo(@PathVariable Integer id, @RequestBody @Valid RatingRequest request){
        
        return ResponseEntity.ok().body(ratingService.crearRating(id, request));
    }

    @GetMapping("/rate/{userId}/{videoId}")
    public ResponseEntity<Rating> rateVideo(@PathVariable Integer userId, @PathVariable Integer videoId){
        
        return ResponseEntity.ok().body(ratingService.getRating(userId, videoId));
    }

    
    
}
