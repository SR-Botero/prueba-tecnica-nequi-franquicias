package com.nequi.prueba.nequi_app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductWithMostStock {

    private String storeNameDTO;

    private String productNameDTO;

    private int stockDTO;
}
