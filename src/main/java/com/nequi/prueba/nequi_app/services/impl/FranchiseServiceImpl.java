package com.nequi.prueba.nequi_app.services.impl;

import com.nequi.prueba.nequi_app.dtos.FranchiseDTO;
import com.nequi.prueba.nequi_app.dtos.NewNameDTO;
import com.nequi.prueba.nequi_app.dtos.ProductWithMostStockDTO;
import com.nequi.prueba.nequi_app.dtos.StoreDTO;
import com.nequi.prueba.nequi_app.models.Franchise;
import com.nequi.prueba.nequi_app.models.Product;
import com.nequi.prueba.nequi_app.models.Store;
import com.nequi.prueba.nequi_app.repositories.FranchiseRepository;
import com.nequi.prueba.nequi_app.services.FranchiseService;
import com.nequi.prueba.nequi_app.validators.FranchiseValidations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FranchiseServiceImpl implements FranchiseService {

    private final FranchiseRepository franchiseRepository;

    @Override
    public Mono<Franchise> findFranchiseById(String franchiseId) {

        FranchiseValidations.validateFranchiseId(franchiseId);

        return franchiseRepository.findById(franchiseId);
    }

    @Override
    public Flux<Franchise> findAllFranchises() {

        return franchiseRepository.findAll();
    }

    @Override
    public Mono<List<ProductWithMostStockDTO>> findProductWithMostStockByStore(String franchiseId) {

        return franchiseRepository.findById(franchiseId)
                .flatMap(franchise -> {

                    List<ProductWithMostStockDTO> productsWithMaxStock = franchise.getStoreList().stream()
                            .map(store -> store.getProductList().stream()
                                    .max(Comparator.comparingInt(Product::getStock))
                                    .map(product -> new ProductWithMostStockDTO(
                                            product.getProductName(),
                                            store.getStoreName(),
                                            product.getStock()))
                            )
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .toList();

                    return Mono.just(productsWithMaxStock);
                });
    }

    @Override
    public Mono<Franchise> saveFranchise(FranchiseDTO franchiseDTO) {

        FranchiseValidations.validateFranchiseDTO(franchiseDTO);

        Franchise franchise = new Franchise();
        franchise.setId(franchiseDTO.getIdDTO());
        franchise.setFranchiseName(franchiseDTO.getFranchiseNameDTO());

        List<Store> stores = Optional.ofNullable(franchiseDTO.getStoreDTOList())
                .orElse(Collections.emptyList())
                .stream()
                .map(storeDTO -> {
                    FranchiseValidations.validateStoreDTO(storeDTO);

                    Store store = new Store();
                    store.setId(storeDTO.getIdDTO());
                    store.setStoreName(storeDTO.getStoreNameDTO());

                    List<Product> products = Optional.ofNullable(storeDTO.getProductDTOList())
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(productDTO -> {
                                Product product = new Product();
                                product.setId(productDTO.getIdDTO());
                                product.setProductName(productDTO.getProductNameDTO());
                                product.setStock(productDTO.getStockDTO());
                                return product;
                            })
                            .toList();

                    store.setProductList(products);
                    return store;
                })
                .toList();

        franchise.setStoreList(stores);

        return franchiseRepository.save(franchise);
    }

    @Override
    public Mono<Franchise> saveStoreByFranchise(String franchiseId, StoreDTO storeDTO) {

        FranchiseValidations.validateFranchiseId(franchiseId);
        FranchiseValidations.validateStoreDTO(storeDTO);

        return franchiseRepository.findById(franchiseId)
                .flatMap(franchise -> {

                    Store store = new Store();
                    store.setId(storeDTO.getIdDTO());
                    store.setStoreName(storeDTO.getStoreNameDTO());

                    List<Product> products = Optional.ofNullable(storeDTO.getProductDTOList())
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(productDTO -> {
                                Product product = new Product();
                                product.setId(productDTO.getIdDTO());
                                product.setProductName(productDTO.getProductNameDTO());
                                product.setStock(productDTO.getStockDTO());
                                return product;
                            })
                            .toList();

                    store.setProductList(products);

                    franchise.getStoreList().add(store);

                    return franchiseRepository.save(franchise);
                });
    }

    @Override
    public Mono<Franchise> updateFranchiseName(String franchiseId, NewNameDTO newNameDTO) {

        FranchiseValidations.validateFranchiseId(franchiseId);

        FranchiseValidations.validateNewNameDTO(newNameDTO);

        return franchiseRepository.findById(franchiseId)
                .flatMap(oldName -> {
                    oldName.setFranchiseName(newNameDTO.getNewNameDTO());

                    return franchiseRepository.save(oldName);
                });
    }

    @Override
    public Mono<Void> deleteFranchiseById(String franchiseId) {

        FranchiseValidations.validateFranchiseId(franchiseId);

        return franchiseRepository.deleteById(franchiseId);
    }
}
