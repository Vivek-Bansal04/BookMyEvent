package com.bookmyshow.api.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class City extends BaseModel {
    private String name;

    //TODO MAKE LAZY AND USE fetch JOIN
    @OneToMany(targetEntity = Theatre.class, cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    @JoinColumn(name = "fk_city_id",referencedColumnName = "id")
    private List<Theatre> theatres;
}
