package com.nequi.prueba.nequi_app.services.impl;

import com.nequi.prueba.nequi_app.dtos.NewNameDTO;
import com.nequi.prueba.nequi_app.dtos.ProductDTO;
import com.nequi.prueba.nequi_app.dtos.StoreDTO;
import com.nequi.prueba.nequi_app.exceptions.InvalidDataException;
import com.nequi.prueba.nequi_app.exceptions.NotFoundException;
import com.nequi.prueba.nequi_app.mappers.ProductMapper;
import com.nequi.prueba.nequi_app.mappers.StoreMapper;
import com.nequi.prueba.nequi_app.models.Product;
import com.nequi.prueba.nequi_app.models.Store;
import com.nequi.prueba.nequi_app.repositories.StoreRepository;
import com.nequi.prueba.nequi_app.services.StoreService;
import com.nequi.prueba.nequi_app.validators.StoreValidations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    public Mono<Store> findStoreById(String storeId) {

        StoreValidations.validateStoreId(storeId);

        return storeRepository.findById(storeId);
    }

    @Override
    public Flux<Store> findAllStores() {

        return storeRepository.findAll();
    }

    @Override
    public Mono<StoreDTO> saveStore(StoreDTO storeDTO) {

        StoreValidations.validateStoreDTO(storeDTO);

        Store store = StoreMapper.INSTANCE.storeDTOToStore(storeDTO);

        return storeRepository.save(store)
                .map(StoreMapper.INSTANCE::storeToStoreDTO);
    }

    @Override
    public Mono<Store> saveProductToStore(String storeId, ProductDTO productDTO) {

        StoreValidations.validateStoreId(storeId);

        return storeRepository.findById(storeId)
                .flatMap(store -> {

                    Product product = ProductMapper.INSTANCE.productDTOToProduct(productDTO);

                    store.getProductList().add(product);

                    return storeRepository.save(store);
                });
    }

    @Override
    public Mono<Store> deleteProductFromStore(String storeId, String productId) {

        StoreValidations.validateStoreId(storeId);

        if (productId == null || productId.isBlank()) {
            throw new InvalidDataException("Product ID must not be null or empty");
        }

        return storeRepository.findById(storeId)
                .flatMap(store -> {

                    boolean deletedProduct = store.getProductList().removeIf(product -> product.getId().equals(productId));

                    if (deletedProduct) {
                        return storeRepository.save(store);
                    } else {
                        return Mono.error(new RuntimeException("The product was not found"));
                    }
                });
    }

    @Override
    public Mono<Store> updateStoreName(String storeId, NewNameDTO newNameDTO) {

        StoreValidations.validateStoreId(storeId);

        StoreValidations.validateNewNameDTO(newNameDTO);

        return storeRepository.findById(storeId)
                .switchIfEmpty(Mono.error(new NotFoundException("The requested store does not exist")))
                .flatMap(store -> {
                    store.setStoreName(newNameDTO.getNewNameDTO());

                    return storeRepository.save(store);
                });
    }

    @Override
    public Mono<Void> deleteStoreById(String storeId) {

        StoreValidations.validateStoreId(storeId);

        return storeRepository.deleteById(storeId);
    }
}
