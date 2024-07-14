package com.bookmyshow.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.vladmihalcea.hibernate.type.array.internal.AbstractArrayType.SQL_ARRAY_TYPE;

@Entity
@Getter
@Setter
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class Show extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "fk_movie_id")
    private Movie movie;

    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "fk_auditorium_id")
    private Auditorium auditorium;

    @OneToMany(mappedBy = "show",cascade = CascadeType.ALL)
    private List<ShowSeat> showSeats;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ShowSeatType> showSeatTypes;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Type(
            type = "com.vladmihalcea.hibernate.type.array.ListArrayType",
            parameters = {@org.hibernate.annotations.Parameter(name = SQL_ARRAY_TYPE, value = "varchar")}
    )
    @Column(columnDefinition = "varchar(10)[]")
    @Enumerated(EnumType.STRING)
    private List<ShowFeature> showFeatures;
}
