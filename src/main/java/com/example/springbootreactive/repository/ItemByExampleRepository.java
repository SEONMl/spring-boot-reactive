package com.example.springbootreactive.repository;

import com.example.springbootreactive.domain.Item;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface ItemByExampleRepository extends ReactiveQueryByExampleExecutor<Item> {
}
