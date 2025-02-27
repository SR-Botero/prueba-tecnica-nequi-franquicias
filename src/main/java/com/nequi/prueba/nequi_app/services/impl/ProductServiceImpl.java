package com.nequi.prueba.nequi_app.services.impl;

import com.nequi.prueba.nequi_app.dtos.NewNameDTO;
import com.nequi.prueba.nequi_app.dtos.NewStockDTO;
import com.nequi.prueba.nequi_app.dtos.ProductDTO;
import com.nequi.prueba.nequi_app.mappers.ProductMapper;
import com.nequi.prueba.nequi_app.models.Product;
import com.nequi.prueba.nequi_app.repositories.ProductRepository;
import com.nequi.prueba.nequi_app.services.ProductService;
import com.nequi.prueba.nequi_app.validators.ProductValidations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Mono<Product> findProductById(String productId) {

        ProductValidations.validateProductId(productId);

        return productRepository.findById(productId);
    }

    @Override
    public Flux<Product> findAllProducts() {

        return productRepository.findAll();
    }

    @Override
    public Mono<ProductDTO> saveProduct(ProductDTO productDTO) {

        ProductValidations.validateProductDTO(productDTO);

        Product product = ProductMapper.INSTANCE.productDTOToProduct(productDTO);

        return productRepository.save(product)
                .map(ProductMapper.INSTANCE::productToProductDTO);
    }

    @Override
    public Mono<Product> modifyProductStock(String productId, NewStockDTO newStockDTO) {

        ProductValidations.validateProductId(productId);

        ProductValidations.validateNewStockDTO(newStockDTO);

        return productRepository.findById(productId)
                .flatMap(product -> {
                    product.setStock(newStockDTO.getNewStockDTO());

                    return productRepository.save(product);
                });
    }

    @Override
    public Mono<Product> updateProductName(String productId, NewNameDTO newNameDTO) {

        ProductValidations.validateProductId(productId);

        ProductValidations.validateNewNameDTO(newNameDTO);

        return productRepository.findById(productId)
                .flatMap(oldName -> {
                    oldName.setProductName(newNameDTO.getNewNameDTO());

                    return productRepository.save(oldName);
                });
    }

    @Override
    public Mono<Void> deleteProductById(String productId) {

        ProductValidations.validateProductId(productId);

        return productRepository.deleteById(productId);
    }
}
