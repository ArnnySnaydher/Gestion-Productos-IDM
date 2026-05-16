package com.aquispe.productservice.infrastructure.adapters.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {
    @Id
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
}
