package com.nequi.prueba.nequi_app.config;

import com.nequi.prueba.nequi_app.mappers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapStructConfig {

    @Bean
    public FranchiseMapper franchiseMapper() {
        return new FranchiseMapperImpl();
    }

    @Bean
    public StoreMapper storeMapper() {
        return new StoreMapperImpl();
    }

    @Bean
    public ProductMapper productMapper() {
        return new ProductMapperImpl();
    }
}
