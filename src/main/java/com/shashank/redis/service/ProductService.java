package com.shashank.redis.service;

import com.shashank.redis.model.Product;
import com.shashank.redis.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ReactiveRedisTemplate<UUID, Product> redisTemplate;

    public Mono<Product> addProduct(Product product) {
        return Mono.just(product)
                .doOnEach(signalProduct -> log.info("Request in service class for adding product"))
                .map(prod -> {
                    prod.setId(UUID.randomUUID());
                    return prod;
                })
                .flatMap(productRepository::save)
                .doOnError(throwable -> log.error("-----Error while adding product---> {}", throwable.getMessage()));
    }

    public Mono<List<Product>> getProducts(int offSet, int limit) {
        return productRepository.getAllProducts(offSet, limit).collectList();
    }

    public Mono<Product> getProductsById(UUID id) {
        return redisTemplate.opsForValue()
                .get(id)
                .switchIfEmpty(
                        productRepository.findById(id)
                                .flatMap(product ->
                                        redisTemplate.opsForValue()
                                                .set(id, product, Duration.ofMinutes(10)) // Cache for 10 minutes
                                                .thenReturn(product))
                );
    }
}
