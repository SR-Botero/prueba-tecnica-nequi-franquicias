package com.nequi.prueba.nequi_app.services;

import com.nequi.prueba.nequi_app.dtos.NewNameDTO;
import com.nequi.prueba.nequi_app.dtos.ProductDTO;
import com.nequi.prueba.nequi_app.dtos.StoreDTO;
import com.nequi.prueba.nequi_app.models.Store;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StoreService {

    Mono<Store> findStoreById(String storeId);

    Flux<Store> findAllStores();

    Mono<StoreDTO> saveStore(StoreDTO storeDTO);

    Mono<Store> saveProductToStore(String storeId, ProductDTO productDTO);

    Mono<Store> deleteProductFromStore(String storeId, String productId);

    Mono<Store> updateStoreName(String storeId, NewNameDTO newNameDTO);

    Mono<Void> deleteStoreById(String storeId);
}
