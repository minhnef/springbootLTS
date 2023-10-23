package com.example.demo.entity.respon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Respon <T>{
    private int status;
    private String error;
    private T data;

    public Respon(int status, String error, T data) {
        this.status = status;
        this.error = error;
        this.data = data;
    }
}
