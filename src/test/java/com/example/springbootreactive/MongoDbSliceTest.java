package com.example.springbootreactive;

import com.example.springbootreactive.domain.Item;
import com.example.springbootreactive.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class MongoDbSliceTest {
    @Autowired
    ItemRepository repository;

    @Test
    void itemRepositorySavesItems() {
        Item item = new Item("name", "description", 1.99);

        repository.save(item)
                .as(StepVerifier::create)
                .expectNextMatches(item1 -> {
                    assertThat(item.getId()).isNotNull();
                    assertThat(item.getName()).isEqualTo("name");
                    assertThat(item.getDescription()).isEqualTo("description");
                    assertThat(item.getPrice()).isEqualTo(1.99);

                    return true;
                })
                .verifyComplete();
    }
}
