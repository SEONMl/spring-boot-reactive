package com.example.springbootreactive;

import com.example.springbootreactive.domain.Item;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
public class TemplateDatabaseLoader {
    @Bean
    CommandLineRunner initialize(MongoOperations mongo){
        return args -> {
            mongo.save(new Item("item","Alf alarm clock", 19.99));
            mongo.save(new Item("Smurf Tv tray", 24.99));
        };
    }
}
