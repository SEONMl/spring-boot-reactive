package com.example.springbootreactive.domain;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cart {
    private @Id String id;
    private List<CartItem> cartItems;

    private Cart() {
    }

    public Cart(String id) {
        this(id, new ArrayList<>());
    }

    public Cart(String id, List<CartItem> cartItems) {
        this.id = id;
        this.cartItems = cartItems;
    }

    public List<CartItem> getCartItems() {
        return this.cartItems;
    }

    public String getId() {
        return id;
    }
}
