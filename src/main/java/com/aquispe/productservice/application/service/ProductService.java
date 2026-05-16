package com.aquispe.productservice.application.service;

import com.aquispe.productservice.domain.model.Product;
import com.aquispe.productservice.domain.port.in.ProductUseCase;
import com.aquispe.productservice.domain.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService implements ProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public ProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public Mono<Product> createProduct(Product product) {
        return Mono.just(product)
                .flatMap(productRepositoryPort::save);
    }

    @Override
    public Mono<Product> getProductById(Long id) {
        return productRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado")));
    }

    @Override
    public Flux<Product> getAllProducts() {
        return productRepositoryPort.findAll();
    }

    @Override
    public Mono<Product> updateProduct(Long id, Product product) {
        return productRepositoryPort.findById(id)
                .map(existingProduct -> new Product(
                        existingProduct.id(),
                        product.name(),
                        product.description(),
                        product.price(),
                        product.stock()
                ))
                .flatMap(productRepositoryPort::save)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado")));
    }

    @Override
    public Mono<Void> deleteProduct(Long id) {
        return productRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado")))
                .flatMap(existingProduct -> productRepositoryPort.deleteById(existingProduct.id()));
    }
}
