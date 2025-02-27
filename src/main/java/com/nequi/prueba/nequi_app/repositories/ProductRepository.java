package com.nequi.prueba.nequi_app.repositories;

import com.nequi.prueba.nequi_app.models.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

}
