package com.example.springbootreactive.controller;

import com.example.springbootreactive.domain.Cart;
import com.example.springbootreactive.repository.CartRepository;
import com.example.springbootreactive.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private final ItemRepository itemRepository;
    @Autowired
    private final CartRepository cartRepository;

    public HomeController(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    @GetMapping
    Mono<Rendering> home() {
        return Mono.just(Rendering.view("home.html")
                .modelAttribute("items",
                        this.itemRepository.findAll())
                .modelAttribute("cart",
                        this.cartRepository.findById("My Cart")
                                .defaultIfEmpty(new Cart("My Cart")))
                .build());
    }
}
