package com.example.springbootreactive.domain;

public class CartItem {
    private Item item;
    private int quantity;

    private CartItem() {
    }

    public CartItem(Item item) {
        this.item = item;
        this.quantity = 1;
    }
}
