package com.api.streaming.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.api.streaming.exception.AlreadyRegistered;
import com.api.streaming.exception.NotFoundException;
import com.api.streaming.repository.RatingRepository;
import com.api.streaming.repository.UserRepository;
import com.api.streaming.repository.VideoRepository;
import com.api.streaming.service.RatingService;
import com.api.streaming.model.Rating;
import com.api.streaming.model.User;
import com.api.streaming.model.Video;
import com.api.streaming.model.request.RatingRequest;


@Service
public class RatingServiceImpl implements RatingService{

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VideoRepository videoRepository;

    @Override
    public Rating crearRating(Integer videoId, RatingRequest request){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Rating rating = new Rating();

        validateCreateRating(user.getId(), videoId);
        
        rating.setIdUser(user.getId());
        rating.setVideo(videoId);
        rating.setRating(request.getRating());
        Rating rate = ratingRepository.save(rating);

        actualizarVideoAvarageRating(videoId);
        
        return rate;
    }

    @Override
    public Rating getRating(int userId, int videoId){
        Optional<Rating> rating = ratingRepository.findByIdUserAndVideo(userId, videoId);
        if(rating.isPresent()){
            return rating.get();
        }
        throw new NotFoundException("No se encontro la calificación asignado por el usuario indicado");
    }

    private void actualizarVideoAvarageRating(int videoId){
        Video video = videoRepository.findById(videoId).get();
        float avarageRating = ratingRepository.getVideoRatingAvarage(videoId);

        video.setRating(avarageRating);
        videoRepository.save(video);
    }

    private void validateCreateRating(int userId, int videoId){
        if( !videoRepository.existsById(videoId)){
            throw new NotFoundException("No se encontro el video");
        }
        if( !userRepository.existsById(userId)){
            throw new NotFoundException("No se encontro el usuario");
        }

        if(ratingRepository.existsByIdUserAndVideo(userId, videoId)){
            throw new AlreadyRegistered("Ya existe un calificación asignada al video por este usuario");
        }
        
    }

    @Override
    public Rating updateRating(Integer videoId, RatingRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Rating> rating = ratingRepository.findByIdUserAndVideo(user.getId(), videoId);
        if(rating.isPresent()){
            Rating editedRating = rating.get();
            editedRating.setRating(request.getRating());
            ratingRepository.save(editedRating);
            actualizarVideoAvarageRating(videoId);
            return editedRating;
        }
        throw new NotFoundException("No se encontro la calificación asignado por el usuario indicado");
    }

    @Override
    public Rating deleteRating(Integer id) {
        Optional<Rating> rating = ratingRepository.findById(id);
        if(rating.isPresent()){
            ratingRepository.deleteById(id);
            return rating.get();
        }
        throw new NotFoundException("La calificacion no existe");
    }

    @Override
    public List<Object> topRatings(){
        Optional<List<Object>> topR=videoRepository.getSortedVideos();
        if(topR.isPresent()){
            return topR.get();
        }
        throw new NotFoundException ("No se han encontrado videos calificados.");

    }
}