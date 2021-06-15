package com.api.streaming.specification;
import com.api.streaming.model.Video;
import org.springframework.data.jpa.domain.Specification;


public class VideoSpecification {

    private VideoSpecification(){}

    public static Specification<Video> videoHasTitle(String title){
        return (root, query, criteriaBuilder) ->{
            return criteriaBuilder.like(root.get("titulo"), "%"+title+"%");
        };
    }

    public static Specification<Video> videoHasDescription(String description){
        return (root, query, criteriaBuilder) ->{
            return criteriaBuilder.like(root.get("description"), "%"+description+"%");
        };
    }
    
}
