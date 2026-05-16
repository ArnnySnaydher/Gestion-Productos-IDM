package com.aquispe.productservice.infrastructure.adapters.in.web;

import com.aquispe.productservice.domain.model.Product;
import com.aquispe.productservice.domain.port.in.ProductUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> createProduct(@RequestBody Product product) {
        return productUseCase.createProduct(product);
    }

    @GetMapping("/{id}")
    public Mono<Product> getProductById(@PathVariable Long id) {
        return productUseCase.getProductById(id);
    }

    @GetMapping
    public Flux<Product> getAllProducts() {
        return productUseCase.getAllProducts();
    }

    @PutMapping("/{id}")
    public Mono<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productUseCase.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(@PathVariable Long id) {
        return productUseCase.deleteProduct(id);
    }
}
