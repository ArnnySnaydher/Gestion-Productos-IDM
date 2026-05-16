package com.aquispe.productservice.infrastructure.adapters.in.web;

import com.aquispe.productservice.domain.model.Product;
import com.aquispe.productservice.domain.port.in.ProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductUseCase productUseCase;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "Mouse", "Mouse Optico", BigDecimal.valueOf(25.0), 50);
    }

    @Test
    void createProduct() {
        when(productUseCase.createProduct(any(Product.class))).thenReturn(Mono.just(product));

        webTestClient.post()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Mouse");
    }

    @Test
    void getProductById() {
        when(productUseCase.getProductById(1L)).thenReturn(Mono.just(product));

        webTestClient.get()
                .uri("/api/products/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Mouse");
    }

    @Test
    void getAllProducts() {
        when(productUseCase.getAllProducts()).thenReturn(Flux.just(product));

        webTestClient.get()
                .uri("/api/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .hasSize(1);
    }

    @Test
    void updateProduct() {
        when(productUseCase.updateProduct(eq(1L), any(Product.class))).thenReturn(Mono.just(product));

        webTestClient.put()
                .uri("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Mouse");
    }

    @Test
    void deleteProduct() {
        when(productUseCase.deleteProduct(1L)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/products/1")
                .exchange()
                .expectStatus().isNoContent();
    }
}
