package com.nequi.prueba.nequi_app.services;

import com.nequi.prueba.nequi_app.dtos.FranchiseDTO;
import com.nequi.prueba.nequi_app.dtos.NewNameDTO;
import com.nequi.prueba.nequi_app.dtos.ProductWithMostStockDTO;
import com.nequi.prueba.nequi_app.dtos.StoreDTO;
import com.nequi.prueba.nequi_app.models.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface FranchiseService {

    Mono<Franchise> findFranchiseById(String franchiseId);

    Flux<Franchise> findAllFranchises();

    Mono<List<ProductWithMostStockDTO>>findProductWithMostStockByStore(String franchiseId);

    Mono<Franchise> saveFranchise(FranchiseDTO franchiseDTO);

    Mono<Franchise> saveStoreByFranchise(String franchiseId, StoreDTO storeDTO);

    Mono<Franchise> updateFranchiseName(String franchiseId, NewNameDTO newNameDTO);

    Mono<Void> deleteFranchiseById(String franchiseId);
}
