package com.example.springbootreactive.service;

import com.example.springbootreactive.domain.Item;
import com.example.springbootreactive.repository.ItemByExampleRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import reactor.core.publisher.Flux;

public class ExampleQueryService {
    private ItemByExampleRepository exampleRepository;

    Flux<Item> searchByExample(String name, String description, boolean useAnd){
        Item item = new Item(description, 0.0);

        ExampleMatcher matcher = (useAnd
                ? ExampleMatcher.matchingAll()
                : ExampleMatcher.matchingAny())
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                    .withIgnoreCase()
                    .withIgnorePaths("price");

        Example<Item> probe = Example.of(item, matcher);

        return exampleRepository.findAll(probe);
    }
}
