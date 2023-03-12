package com.example.springbootreactive.controller;

import com.example.springbootreactive.domain.Item;
import com.example.springbootreactive.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;
import sun.jvm.hotspot.runtime.Thread;
import sun.jvm.hotspot.runtime.Threads;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureWebTestClient
class RSocketControllerTest {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    ItemRepository repository;

    private Item sample = new Item("Alf alarm clock", "nothing important", 19.99);

    @Test
    void verifyRemoteOperationsThroughRSocketRequestResponse() throws InterruptedException {
        this.repository.deleteAll()
                .as(StepVerifier::create)
                .verifyComplete();

        webTestClient.post().uri("/items/request-response")
                .bodyValue(sample)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Item.class)
                .value(item->{
                    assertThat(item.getId()).isNotNull();
                    assertThat(item.getName()).isEqualTo("Alf alarm clock");
                    assertThat(item.getDescription()).isEqualTo("nothing important");
                    assertThat(item.getPrice()).isEqualTo(19.99);
                });


        this.repository.findAll()
                .as(StepVerifier::create)
                .expectNextMatches(item->{
                    assertThat(item.getId()).isNotNull();
                    assertThat(item.getName()).isEqualTo("Alf alarm clock");
                    assertThat(item.getDescription()).isEqualTo("nothing important");
                    assertThat(item.getPrice()).isEqualTo(19.99);
                    return true;
                }).verifyComplete();
    }
}