package com.example.springbootreactive.controller;

import com.example.springbootreactive.domain.Item;
import com.example.springbootreactive.repository.ItemRepository;
import com.example.springbootreactive.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static reactor.core.publisher.Mono.when;

@WebFluxTest(controllers = ApiItemController.class)
@AutoConfigureRestDocs
class ApiItemControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    InventoryService service;
    @MockBean
    ItemRepository repository;
    private Item sample = new Item("item-1","Alf alarm clock", "nothing I really need",19.99);
    @Test
    void findingAllItems() {
        when(repository.findAll()).thenReturn(
                Flux.just(sample));

        webTestClient.get().uri("/api/itmes")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(document("findAll", preprocessResponse(prettyPrint())));
    }

    @Test
    void postNewItem() {
        when(repository.save(any())).thenReturn(
                Mono.just(sample));

        webTestClient.post().uri("/api/items")
                .bodyValue(sample)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(document("post-new-item", preprocessResponse(prettyPrint())));
    }
}