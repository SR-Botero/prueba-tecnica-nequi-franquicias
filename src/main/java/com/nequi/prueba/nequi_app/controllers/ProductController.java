package com.nequi.prueba.nequi_app.controllers;

import com.nequi.prueba.nequi_app.dtos.NewNameDTO;
import com.nequi.prueba.nequi_app.dtos.ProductDTO;
import com.nequi.prueba.nequi_app.dtos.StoreDTO;
import com.nequi.prueba.nequi_app.mappers.StoreMapper;
import com.nequi.prueba.nequi_app.models.Store;
import com.nequi.prueba.nequi_app.services.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(StoreController.class);

    private final StoreService storeService;

    private final StoreMapper storeMapper;

    @GetMapping("/{storeId}")
    public Mono<ResponseEntity<StoreDTO>> getStoreById(@PathVariable String storeId) {

        logger.info("Request received to get store with ID: {}", storeId);

        return storeService.findStoreById(storeId)
                .map(store -> {
                    logger.info("Store found with ID: {}", storeId);

                    return ResponseEntity.ok(storeMapper.storeToStoreDTO(store));
                })

                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Flux<StoreDTO>> getAllStores() {
        logger.info("Request received to get all stores");

        Flux<StoreDTO> stores = storeService.findAllStores()
                .map(store -> {
                    logger.info("Store found with ID: {}", store.getId());
                    return storeMapper.storeToStoreDTO(store);
                });

        return ResponseEntity.ok(stores);
    }

    @PostMapping
    public Mono<ResponseEntity<StoreDTO>> createStore(@Valid @RequestBody StoreDTO storeDTO) {

        logger.info("Request received to save a new store");

        Store store = storeMapper.storeDTOToStore(storeDTO);

        return storeService.saveStore(storeDTO)
                .map(savedStore -> {
                    logger.info("Store saved with ID: {}", savedStore.getIdDTO());

                    return ResponseEntity.status(HttpStatus.CREATED).body(storeMapper.storeToStoreDTO(store));
                })

                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/{storeId}/products")
    public Mono<ResponseEntity<StoreDTO>> addProductToStore(@PathVariable String storeId, @Valid @RequestBody ProductDTO productDTO) {

        logger.info("Request received to add a product to store with ID: {}", storeId);

        return storeService.saveProductToStore(storeId, productDTO)
                .map(store -> {
                    logger.info("Product added to store with ID: {}", storeId);

                    return ResponseEntity.ok(storeMapper.storeToStoreDTO(store));
                })

                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{storeId}/product/{productId}")
    public Mono<ResponseEntity<StoreDTO>> deleteProductFromStore(@PathVariable String storeId, @PathVariable String productId) {

        logger.info("Request received to delete product with ID: {} from store with ID: {}", productId, storeId);

        return storeService.deleteProductFromStore(storeId, productId)
                .map(store -> {
                    logger.info("Product with ID: {} deleted from store with ID: {}", productId, storeId);

                    return ResponseEntity.ok(storeMapper.storeToStoreDTO(store));
                })

                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{storeId}")
    public Mono<ResponseEntity<StoreDTO>> updateStoreName(@PathVariable String storeId, @Valid @RequestBody NewNameDTO newNameDTO) {

        logger.info("Request received to update store name for ID: {}", storeId);

        return storeService.updateStoreName(storeId, newNameDTO)
                .map(store -> {
                    logger.info("Store name updated for ID: {}", storeId);

                    return ResponseEntity.ok(storeMapper.storeToStoreDTO(store));
                })

                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{storeId}")
    public Mono<ResponseEntity<Void>> deleteStoreById(@PathVariable String storeId) {

        logger.info("Request received to delete store with ID: {}", storeId);

        return storeService.deleteStoreById(storeId)
                .then(Mono.fromRunnable(() -> logger.info("Store deleted successfully: {}", storeId)))
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
<<<<<<< HEAD

=======
>>>>>>> 4462eae6686326503f757ab15a8560ee6b9bd42b
}
