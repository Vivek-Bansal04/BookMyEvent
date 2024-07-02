package com.bookmyshow.api.models;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

import static com.vladmihalcea.hibernate.type.array.internal.AbstractArrayType.SQL_ARRAY_TYPE;

@Entity
@Getter
@Setter
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class Movie extends BaseModel {
    private String name;

    @Type(
            type = "com.vladmihalcea.hibernate.type.array.ListArrayType",
            parameters = {@org.hibernate.annotations.Parameter(name = SQL_ARRAY_TYPE, value = "varchar")}
    )
    @Column(columnDefinition = "varchar(10)[]")
    @Enumerated(EnumType.STRING)
    private List<Language> languages;

    @ManyToMany
    private List<Actor> actors;

    private int length;
    private double rating;

    @Type(
            type = "com.vladmihalcea.hibernate.type.array.ListArrayType",
            parameters = {@org.hibernate.annotations.Parameter(name = SQL_ARRAY_TYPE, value = "varchar")}
    )
    @Column(columnDefinition = "varchar(10)[]")
    @Enumerated(EnumType.STRING)
    private List<MovieFeature> movieFeatures;
}
