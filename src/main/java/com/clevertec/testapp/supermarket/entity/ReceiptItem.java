package com.clevertec.testapp.supermarket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptItem  {

    private Long id;
    private Product product; // ссылка на товар
    private Integer quantity; // кол-во купленного товара - отражается в чеке
    private String info = ""; // доп. инфо, куда можно добавлять любой текст (например, что сработала доп. скидка за акционные товары)
    private Double sum; // сумма по строке чека (по всем товарам, с учетом скидочных товаров, если такие есть)

}