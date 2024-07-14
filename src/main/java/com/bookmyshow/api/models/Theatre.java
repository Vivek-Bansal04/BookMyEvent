package com.bookmyshow.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Theatre extends BaseModel {
    private String name;
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "theatre",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Auditorium> auditoriums;

    //TODO give feature for upcoming shows
//    @OneToMany(targetEntity = Show.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
//    @JoinColumn(name = "fk_theatre_id",referencedColumnName = "id")
//    private List<Show> upcomingShows;

    @ManyToOne
    @JoinColumn(name = "fk_city_id")
    private City city;

}
