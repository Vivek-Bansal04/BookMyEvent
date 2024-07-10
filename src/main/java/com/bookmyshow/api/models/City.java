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

    @OneToMany(targetEntity = Theatre.class, cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Theatre> theatres;
}
