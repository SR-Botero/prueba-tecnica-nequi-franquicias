package com.nequi.prueba.nequi_app.services;

import com.nequi.prueba.nequi_app.dtos.NewNameDTO;
import com.nequi.prueba.nequi_app.dtos.NewStockDTO;
import com.nequi.prueba.nequi_app.dtos.ProductDTO;
import com.nequi.prueba.nequi_app.models.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ProductService {

    Mono<Product> findProductById(String productId);

    Flux<Product> findAllProducts();

    Mono<ProductDTO> saveProduct(ProductDTO productDTO);

    Mono<Product> modifyProductStock(String productId, NewStockDTO newStockDTO);

    Mono<Product> updateProductName(String productId, NewNameDTO newNameDTO);

    Mono<Void> deleteProductById(String productId);
}
