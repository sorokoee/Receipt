package com.clevertec.testapp.supermarket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DiscountCard  {

    private Long id;
    private String name; // название карты
    private Integer amount; // размер скидки в % - влияет на общую сумму в чеке

}
