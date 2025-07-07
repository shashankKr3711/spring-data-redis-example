package com.shashank.redis.controller;

import com.shashank.redis.model.Product;
import com.shashank.redis.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Slf4j
public class ProductController {

    ProductService productService;

    private static <T> ResponseEntity<T> buildResponseEntity(T product) {
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PostMapping("/v1/product")
    public Mono<ResponseEntity<Product>> addNewProduct(@RequestHeader("X-Correlation-Id") UUID xCorrelationId,
                                                       @RequestBody Product product
    ) {
        return Mono.just(product)
                .doOnEach(a -> log.info("Request received for adding new product : , X-Correlation-Id - {}", xCorrelationId))
                .flatMap(prod -> productService.addProduct(prod))
                .map(ProductController::buildResponseEntity)
                .doOnSuccess(a -> log.info("Successfully added new product : "));

    }

    @GetMapping("/v1/product")
    public Mono<ResponseEntity<List<Product>>> getProducts(@RequestHeader("X-Correlation-Id") UUID xCorrelationId) {
        return Mono.just(xCorrelationId)
                .doOnEach(a -> log.info("Request received for retrieving product : , X-Correlation-Id - {}", xCorrelationId))
                .flatMap(aa -> productService.getProducts(0, 10))
                .map(ProductController::buildResponseEntity)
                .doOnSuccess(a -> log.info("Successfully retrieving all the products : "));

    }

    @GetMapping("/v1/product/{id}")
    public Mono<ResponseEntity<Product>> getProductsById(@RequestHeader("X-Correlation-Id") UUID xCorrelationId,
                                                         @PathVariable UUID id) {
        return Mono.just(xCorrelationId)
                .doOnEach(a -> log.info("Request received for retrieving product by id : , X-Correlation-Id - {}", xCorrelationId))
                .flatMap(aa -> productService.getProductsById(id))
                .map(ProductController::buildResponseEntity)
                .doOnSuccess(a -> log.info("Successfully retrieving all the products : "));

    }
}
