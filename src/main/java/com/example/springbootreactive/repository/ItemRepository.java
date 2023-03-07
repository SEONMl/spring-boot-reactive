package com.example.springbootreactive.repository;

import com.example.springbootreactive.domain.Item;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ItemRepository extends ReactiveCrudRepository<Item, String> {
    /**
     * save(), saveAll()
     * findById(), findAll(), findAllById()
     * existsById(), count()
     * deleteById(), delete(), deleteAll()
     */
}
