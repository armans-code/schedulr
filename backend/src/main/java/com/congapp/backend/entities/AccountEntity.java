package com.congapp.backend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class AccountEntity extends  BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

}
