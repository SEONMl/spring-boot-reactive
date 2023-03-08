package com.example.springbootreactive.repository;

import com.example.springbootreactive.domain.Item;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ItemRepository extends ReactiveCrudRepository<Item, String> {
    /**
     * save(), saveAll()
     * findById(), findAll(), findAllById()
     * existsById(), count()
     * deleteById(), delete(), deleteAll()
     */

    Flux<Item> findByNameContaining(String partialName);

    @Query("{'name':  ?0, 'age': ?1}")
    Flux<Item> findItemForCustomerMonthlyReport(String name, int age);

    @Query(sort="{'age': -1}")
    Flux<Item> findSortedStuffForWeeklyReport();
}
