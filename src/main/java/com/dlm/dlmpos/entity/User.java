package com.dlm.dlmpos.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name ="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private boolean admin;
}
