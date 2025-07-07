package com.shashank.redis.repository;

import com.shashank.redis.model.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, UUID> {

    static final String QUERY_GET_ALL_PRODUCT = "SELECT p.* FROM product p ";
    static final String CLAUSE_OFFSET = "OFFSET :offSet LIMIT :limit";

    @Query(QUERY_GET_ALL_PRODUCT + CLAUSE_OFFSET)
    Flux<Product> getAllProducts(int offSet, int limit);

}