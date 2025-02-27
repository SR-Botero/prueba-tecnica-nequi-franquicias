package com.nequi.prueba.nequi_app.mappers;

import com.nequi.prueba.nequi_app.dtos.ProductDTO;
import com.nequi.prueba.nequi_app.dtos.StoreDTO;
import com.nequi.prueba.nequi_app.models.Product;
import com.nequi.prueba.nequi_app.models.Store;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StoreMapper {

    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);

    @Mapping(source = "id", target = "idDTO")
    @Mapping(source = "storeName", target = "storeNameDTO")
    @Mapping(source = "productList", target = "productDTOList")
    StoreDTO storeToStoreDTO(Store store);

    @InheritInverseConfiguration
    Store storeDTOToStore(StoreDTO storeDTO);

    @Mapping(target = "idDTO", source = "id")
    @Mapping(target = "productNameDTO", source = "productName")
    @Mapping(target = "stockDTO", source = "stock")
    ProductDTO productToProductDTO(Product product);

    @InheritInverseConfiguration
    Product productDTOToProduct(ProductDTO productDTO);
}
