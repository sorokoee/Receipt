package com.clevertec.testapp.supermarket.entity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class BaseModel {
    private Long id;

    public BaseModel(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
