package com.example.springbootreactive.domain;

import org.springframework.data.annotation.Id;

public class Item {

    private @Id String id;
    private String name;
    private double price;

    private Item(){}

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
