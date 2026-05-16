package com.aquispe.productservice.domain.model;

import java.math.BigDecimal;

public record Product(
    Long id,
    String name,
    String description,
    BigDecimal price,
    Integer stock
) {
    public Product {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        if (stock != null && stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }
}
