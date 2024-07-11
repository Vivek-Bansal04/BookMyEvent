package com.bookmyshow.api.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseModel {
    private String email;
    private String name;
}

// Will be starting at 9:10 PM
