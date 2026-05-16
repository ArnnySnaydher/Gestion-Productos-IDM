package com.aquispe.productservice.application.service;

import com.aquispe.productservice.domain.model.Product;
import com.aquispe.productservice.domain.port.out.ProductRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepositoryPort productRepositoryPort;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "Laptop", "Laptop Gamer", BigDecimal.valueOf(1500.0), 10);
    }

    @Test
    void createProduct() {
        when(productRepositoryPort.save(any(Product.class))).thenReturn(Mono.just(product));

        Mono<Product> result = productService.createProduct(product);

        StepVerifier.create(result)
                .expectNextMatches(p -> p.name().equals("Laptop"))
                .verifyComplete();

        verify(productRepositoryPort, times(1)).save(any(Product.class));
    }

    @Test
    void getProductById_Success() {
        when(productRepositoryPort.findById(1L)).thenReturn(Mono.just(product));

        Mono<Product> result = productService.getProductById(1L);

        StepVerifier.create(result)
                .expectNextMatches(p -> p.id().equals(1L))
                .verifyComplete();
    }

    @Test
    void getProductById_NotFound() {
        when(productRepositoryPort.findById(1L)).thenReturn(Mono.empty());

        Mono<Product> result = productService.getProductById(1L);

        StepVerifier.create(result)
                .expectErrorMessage("Producto no encontrado")
                .verify();
    }

    @Test
    void getAllProducts() {
        when(productRepositoryPort.findAll()).thenReturn(Flux.just(product));

        Flux<Product> result = productService.getAllProducts();

        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void updateProduct_Success() {
        Product updatedProduct = new Product(1L, "Laptop Updated", "Updated Desc", BigDecimal.valueOf(1600.0), 15);
        
        when(productRepositoryPort.findById(1L)).thenReturn(Mono.just(product));
        when(productRepositoryPort.save(any(Product.class))).thenReturn(Mono.just(updatedProduct));

        Mono<Product> result = productService.updateProduct(1L, updatedProduct);

        StepVerifier.create(result)
                .expectNextMatches(p -> p.name().equals("Laptop Updated"))
                .verifyComplete();
    }

    @Test
    void deleteProduct_Success() {
        when(productRepositoryPort.findById(1L)).thenReturn(Mono.just(product));
        when(productRepositoryPort.deleteById(1L)).thenReturn(Mono.empty());

        Mono<Void> result = productService.deleteProduct(1L);

        StepVerifier.create(result)
                .verifyComplete();

        verify(productRepositoryPort, times(1)).deleteById(1L);
    }
}
