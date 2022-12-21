package com.clevertec.testapp.supermarket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product  {

    private Long id;
    private String name; // название или описание
    private Double price; // цена
    private Boolean promotion; // акционный товар или нет

}
