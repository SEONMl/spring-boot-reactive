package com.example.springbootreactive.service;

import com.example.springbootreactive.domain.Cart;
import com.example.springbootreactive.domain.CartItem;
import com.example.springbootreactive.repository.CartRepository;
import com.example.springbootreactive.repository.ItemRepository;
import jdk.internal.org.objectweb.asm.tree.ModuleNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CartService {
    @Autowired
    private final ItemRepository itemRepository;
    @Autowired
    private final CartRepository cartRepository;

    public CartService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    Mono<Cart> addToCart(String cartId, String id){
        return this.cartRepository.findById(cartId)
                .defaultIfEmpty(new Cart(cartId))
                .flatMap(cart-> cart.getCartItems().stream()
                        .filter(cartItem -> cartItem.getItem()
                        .getId().equals(id))
                .findAny()
                        .map(cartItem -> {
                            cartItem.increment();
                            return Mono.just(cart);
                        })
                        .orElseGet(()->
                                this.itemRepository.findById(id)
                                        .map(CartItem::new)
                                        .doOnNext(cartItem-> cart.getCartItems().add(cartItem))
                                        .map(cartItem-> cart)))
                .flatMap(this.cartRepository::save);
    }
}
