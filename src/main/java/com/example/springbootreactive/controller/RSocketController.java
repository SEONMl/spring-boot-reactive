package com.example.springbootreactive.controller;

import com.example.springbootreactive.domain.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

import static io.rsocket.metadata.WellKnownMimeType.MESSAGE_RSOCKET_ROUTING;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;
import static org.springframework.util.MimeTypeUtils.parseMimeType;

@RestController("items")
public class RSocketController {
    private final Mono<RSocketRequester> requester;

    public RSocketController(RSocketRequester.Builder builder) {
        this.requester = builder
                .dataMimeType(APPLICATION_JSON)
                .metadataMimeType(parseMimeType(MESSAGE_RSOCKET_ROUTING.getString()))
                .connectTcp("localhost",7000)
                .retry(5)
                .cache();
    }

    @PostMapping("/request-response")
    Mono<ResponseEntity<?>> addNewItemUsingRSocketRequestResponse(@RequestBody Item item){
        return requester
                .flatMap(requester-> requester
                        .route("newItems.request-response")
                        .data(item)
                        .retrieveMono(Item.class))
                .map(saved -> ResponseEntity.created(URI.create("/items/request-response")).body(saved));
    }
}
