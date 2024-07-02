package com.bookmyshow.api.models;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

import static com.vladmihalcea.hibernate.type.array.internal.AbstractArrayType.SQL_ARRAY_TYPE;

// 1:M
// 1:1

@Entity
@Getter
@Setter
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class Auditorium extends BaseModel {
    private String name;

    @OneToMany(targetEntity = Seat.class, cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    @JoinColumn(name = "fk_auditorium_id",referencedColumnName = "id")
    private List<Seat> seats;
    private int capacity;

    @Type(
            type = "com.vladmihalcea.hibernate.type.array.ListArrayType",
            parameters = {@org.hibernate.annotations.Parameter(name = SQL_ARRAY_TYPE, value = "varchar")}
    )
    @Column(columnDefinition = "varchar(10)[]")
    @Enumerated(EnumType.STRING)
    private List<AuditoriumFeature> auditoriumFeatures;

    @ManyToOne
    @JoinColumn(name = "fk_theatre_id")
    private Theatre theatre;
}
