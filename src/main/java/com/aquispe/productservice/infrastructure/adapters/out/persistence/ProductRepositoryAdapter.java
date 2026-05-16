package com.aquispe.productservice.infrastructure.adapters.out.persistence;

import com.aquispe.productservice.domain.model.Product;
import com.aquispe.productservice.domain.port.out.ProductRepositoryPort;
import com.aquispe.productservice.infrastructure.adapters.out.persistence.entity.ProductEntity;
import com.aquispe.productservice.infrastructure.adapters.out.persistence.repository.ProductR2dbcRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final ProductR2dbcRepository repository;

    public ProductRepositoryAdapter(ProductR2dbcRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Product> save(Product product) {
        ProductEntity entity = ProductEntity.builder()
                .id(product.id())
                .name(product.name())
                .description(product.description())
                .price(product.price())
                .stock(product.stock())
                .build();

        return repository.save(entity).map(this::toDomain);
    }

    @Override
    public Mono<Product> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<Product> findAll() {
        return repository.findAll().map(this::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }

    private Product toDomain(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStock()
        );
    }
}
