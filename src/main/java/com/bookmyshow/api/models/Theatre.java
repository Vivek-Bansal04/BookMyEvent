package com.bookmyshow.api.models;

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

    @OneToMany(mappedBy = "theatre",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Auditorium> auditoriums;

    @OneToMany(targetEntity = Show.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinColumn(name = "fk_theatre_id",referencedColumnName = "id")
    private List<Show> upcomingShows;

    @ManyToOne
    @JoinColumn(name = "fk_city_id")
    private City city;

}
