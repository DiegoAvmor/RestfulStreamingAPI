package com.api.streaming.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "videos")
public class Video {

    @Id
    @Column(name = "id_video")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_serialize")
    private String idSerializable;

    @Column
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor")
    @JsonManagedReference
    private User autor;

    @Lob
    @Column
    private String description;

    @Column(name = "average_rating")
    private float rating;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime createdAt;

    @Lob
    @Column(name = "video_location")
    @JsonIgnore
    private String location;

    @OneToMany(mappedBy = "video",cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<VideoClasification> videosClasification;

}