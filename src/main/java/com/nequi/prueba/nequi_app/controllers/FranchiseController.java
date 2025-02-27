package com.nequi.prueba.nequi_app.controllers;

import com.nequi.prueba.nequi_app.dtos.FranchiseDTO;
import com.nequi.prueba.nequi_app.dtos.NewNameDTO;
import com.nequi.prueba.nequi_app.dtos.ProductWithMostStockDTO;
import com.nequi.prueba.nequi_app.dtos.StoreDTO;
import com.nequi.prueba.nequi_app.mappers.FranchiseMapper;
import com.nequi.prueba.nequi_app.models.Franchise;
import com.nequi.prueba.nequi_app.services.FranchiseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/franchises")
@RequiredArgsConstructor
public class FranchiseController {

    private static final Logger logger = LoggerFactory.getLogger(FranchiseController.class);

    private final FranchiseService franchiseService;

    private final FranchiseMapper franchiseMapper;

    @GetMapping("/{franchiseId}")
    public Mono<ResponseEntity<FranchiseDTO>> getFranchiseById(@PathVariable String franchiseId) {

        logger.info("Request received to get franchise with ID: {}", franchiseId);

        return franchiseService.findFranchiseById(franchiseId)
                .map(franchise -> {
                    logger.info("Franchise found: {}", franchiseId);
                    return ResponseEntity.ok(franchiseMapper.INSTANCE.franchiseToFranchiseDTO(franchise));
                })

                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Flux<FranchiseDTO>> getAllFranchises() {

        logger.info("Request received to get all franchises");

        Flux<FranchiseDTO> franchises = franchiseService.findAllFranchises()
                .map(franchise -> {
                    logger.info("Franchise found with ID: {}", franchise.getId());
                    return franchiseMapper.franchiseToFranchiseDTO(franchise);
                });

        return ResponseEntity.ok(franchises);
    }

    @GetMapping("/{franchiseId}/products/most-stock")
    public Mono<ResponseEntity<List<ProductWithMostStockDTO>>> getProductWithMostStockByStore(@PathVariable String franchiseId) {

        logger.info("Request received to get products with most stock for franchise ID: {}", franchiseId);

        return franchiseService.findProductWithMostStockByStore(franchiseId)
                .defaultIfEmpty(Collections.emptyList())
                .flatMap(products -> {
                    if (products.isEmpty()) {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                    return Mono.just(ResponseEntity.ok(products));
                });
    }

    @PostMapping
    public Mono<ResponseEntity<FranchiseDTO>> saveFranchise(@Valid @RequestBody FranchiseDTO franchiseDTO) {

        logger.info("Request received to save a new franchise");

        return franchiseService.saveFranchise(franchiseDTO)
                .map(savedFranchise -> {
                    logger.info("Franchise saved with ID: {}", savedFranchise.getId());

                    return ResponseEntity.ok(FranchiseMapper.INSTANCE.franchiseToFranchiseDTO(savedFranchise));
                })

                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/{franchiseId}/stores")
    public Mono<ResponseEntity<FranchiseDTO>> addStoreToFranchise(@PathVariable String franchiseId, @Valid @RequestBody StoreDTO storeDTO) {

        logger.info("Request received to add a store to franchise ID: {}", franchiseId);

        return franchiseService.saveStoreByFranchise(franchiseId, storeDTO)
                .map(franchise -> {
                    logger.info("Store added to franchise with ID: {}", franchiseId);

                    return ResponseEntity.ok(franchiseMapper.franchiseToFranchiseDTO(franchise));
                })

                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{franchiseId}")
    public Mono<ResponseEntity<Franchise>> updateFranchiseName(@PathVariable String franchiseId, @Valid @RequestBody NewNameDTO newNameDTO) {

        logger.info("Request received to update franchise name for ID: {}", franchiseId);

        return franchiseService.updateFranchiseName(franchiseId, newNameDTO)
                .map(updatedFranchise -> {
                    logger.info("Franchise name updated for ID: {}", franchiseId);

                    return ResponseEntity.ok().body(updatedFranchise);
                })

                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{franchiseId}")
    public Mono<ResponseEntity<Void>> deleteFranchiseById(@PathVariable String franchiseId) {

        logger.info("Request received to delete franchise with ID: {}", franchiseId);

        return franchiseService.deleteFranchiseById(franchiseId)
                .then(Mono.fromRunnable(() -> logger.info("Franchise deleted successfully: {}", franchiseId)))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
