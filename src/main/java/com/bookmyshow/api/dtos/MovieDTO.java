package com.bookmyshow.api.dtos;

import com.bookmyshow.api.models.Language;
import com.bookmyshow.api.models.MovieFeature;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieDTO {
    private String name;
    private List<Language> languages;
    private List<Long> actors;
    private int length;
    private double rating;
    private List<MovieFeature> movieFeatures;
}
