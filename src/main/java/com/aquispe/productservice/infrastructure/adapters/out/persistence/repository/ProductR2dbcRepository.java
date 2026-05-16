package com.aquispe.productservice.infrastructure.adapters.out.persistence.repository;

import com.aquispe.productservice.infrastructure.adapters.out.persistence.entity.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductR2dbcRepository extends ReactiveCrudRepository<ProductEntity, Long> {
}
