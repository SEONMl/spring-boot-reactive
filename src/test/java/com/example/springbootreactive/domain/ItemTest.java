package com.example.springbootreactive.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void itemBasicShouldWork() {
        Item sampleItem = new Item("item1","description", 3.9);

        assertEquals(sampleItem.getName(), "itme1");
        assertEquals(sampleItem.getPrice(), 3.9);
    }
}