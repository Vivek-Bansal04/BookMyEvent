package com.bookmyshow.api.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Movie extends BaseModel {
    private String name;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Language> languages;

    @ManyToMany
    private List<Actor> actors;

    private int length;
    private double rating;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<MovieFeature> movieFeatures;
}
