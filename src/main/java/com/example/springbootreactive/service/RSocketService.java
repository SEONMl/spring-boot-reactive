package com.example.springbootreactive.service;

import com.example.springbootreactive.domain.Item;
import com.example.springbootreactive.repository.ItemRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Service;
import reactor.core.publisher.*;

@Service
public class RSocketService {
    private  final ItemRepository repository;
//    private final EmitterProcessor<Item> itemEmitterProcessor;
//    private final FluxSink<Item> itemFluxSink;
    private final Sinks.Many<Item> itemsSink;

    public RSocketService(ItemRepository repository) {
        this.repository = repository;

//        this.itemEmitterProcessor = EmitterProcessor.create();
//        this.itemFluxSink = this.itemEmitterProcessor.sink();
        this.itemsSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @MessageMapping("newItems.request-response")
    public Mono<Item> processNewItemsViaRSocketRequestResponse(Item item){
        return this.repository.save(item)
//                .doOnNext(saved -> this.itemEmitterProcessor.next(saved))
                .doOnNext(saved -> this.itemsSink.tryEmitNext(saved));
    }

    @MessageMapping("newItems.request-stream")
    public Flux<Item> findItemsViaRSocketRequestStream(){
        return this.repository.findAll()
//                .doOnNext(itemEmitterProcessor::next)
                .doOnNext(this.itemsSink::tryEmitNext);
    }

    @MessageMapping("newItems.fire-and-forget")
    public Mono<Void> processNewItemsViaRSocketFireAndForget(Item item){
        return this.repository.save(item)
//                .doOnNext(saved -> this.itemEmitterProcessor.next(saved))
                .doOnNext(this.itemsSink::tryEmitNext)
                .then();
    }

    @MessageMapping("newItems.monitor")
    public Flux<Item> monitorNewItems(){
//        return this.itemProcessor;
        return this.itemsSink.asFlux();
    }

}
