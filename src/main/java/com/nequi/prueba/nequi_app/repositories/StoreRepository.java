package com.nequi.prueba.nequi_app.repositories;

import com.nequi.prueba.nequi_app.models.Store;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends ReactiveMongoRepository<Store, String> {
}
