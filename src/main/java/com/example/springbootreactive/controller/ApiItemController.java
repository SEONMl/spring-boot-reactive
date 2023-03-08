package com.example.springbootreactive.controller;

import com.example.springbootreactive.domain.Item;
import com.example.springbootreactive.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class ApiItemController {
    @Autowired
    private final ItemRepository itemRepository;

    public ApiItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/api/items")
    public Flux<Item> findAll(){
        return itemRepository.findAll();
    }

    @GetMapping("/api/items/{id}")
    public Mono<Item> findById(@PathVariable String id){
        return itemRepository.findById(id);
    }

    @PostMapping("/api/items")
    public Mono<ResponseEntity<?>> addNewItem(@RequestBody Mono<Item> item){
        return item.flatMap(itemRepository::save)
                .map(savedItem -> ResponseEntity
                        .created(URI.create("/api/items/" + savedItem))
                        .body(savedItem));
    }

    @PutMapping("/api/items/{id}")
    public Mono<ResponseEntity> updateItem(
            @RequestBody Mono<Item> item,
            @PathVariable String id
    ) {
        return item
                .map(content-> new Item(id, content.getName(), content.getDescription(), content.getPrice()))
                .flatMap(itemRepository::save)
                .map(ResponseEntity::ok);
    }
}
