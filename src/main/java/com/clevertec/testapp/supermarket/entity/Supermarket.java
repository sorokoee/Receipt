package com.clevertec.testapp.supermarket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Supermarket  {

    private Long id;
    private String name; // название магазина
    private String address; // адрес магазина
    private String phone; // телефон магазина

}
