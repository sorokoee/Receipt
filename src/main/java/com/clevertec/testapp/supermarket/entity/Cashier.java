package com.clevertec.testapp.supermarket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cashier extends BaseModel  {
    private String name;
    private char gender;
    private double salary;
    private String email;

    public Cashier(Long id, String name) {
        super(id);
        this.name = name;
    }
}