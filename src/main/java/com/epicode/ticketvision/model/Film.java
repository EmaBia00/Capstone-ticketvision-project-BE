package com.epicode.ticketvision.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String synopsis;
    private String posterUrl;
    private String bannerUrl;
    private int duration;
    private double price;
    private boolean featured;

    @ElementCollection
    @CollectionTable(name = "film_actors", joinColumns = @JoinColumn(name = "film_id"))
    @Column(name = "actor")
    private List<String> actors;

    private int oscarNominations;
}