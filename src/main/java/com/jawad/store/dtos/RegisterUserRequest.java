package com.jawad.store.dtos;


import lombok.Data;

//this class for user dto to create and register in db from the request
@Data //getters+setters+toString+.....
public class RegisterUserRequest {
    private String name;
    private String email;
    private String password;
}
