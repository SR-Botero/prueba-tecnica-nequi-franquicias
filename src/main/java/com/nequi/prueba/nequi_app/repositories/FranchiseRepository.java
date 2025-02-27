package com.nequi.prueba.nequi_app.repositories;

import com.nequi.prueba.nequi_app.models.Franchise;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranchiseRepository extends ReactiveMongoRepository<Franchise, String> {

}
