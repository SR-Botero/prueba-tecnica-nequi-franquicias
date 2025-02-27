package com.nequi.prueba.nequi_app.validators;

import com.nequi.prueba.nequi_app.dtos.FranchiseDTO;
import com.nequi.prueba.nequi_app.dtos.NewNameDTO;
import com.nequi.prueba.nequi_app.dtos.StoreDTO;
import com.nequi.prueba.nequi_app.exceptions.BadRequestException;
import com.nequi.prueba.nequi_app.exceptions.InvalidDataException;

public class FranchiseValidations {

    public static void validateFranchiseId(String franchiseId) {

        if (franchiseId == null || franchiseId.isBlank()) {
            throw new InvalidDataException("Franchise ID must not be null or empty");
        }
    }

    public static void validateFranchiseDTO(FranchiseDTO franchiseDTO) {

        if (franchiseDTO == null) {
            throw new BadRequestException("Franchise must not be null");
        }

        if (franchiseDTO.getFranchiseNameDTO() == null || franchiseDTO.getFranchiseNameDTO().isBlank()) {
            throw new InvalidDataException("Franchise name must not be null or empty");
        }
    }

    public static void validateStoreDTO(StoreDTO storeDTO) {

        if (storeDTO == null) {
            throw new BadRequestException("Store must not be null");
        }

        if (storeDTO.getStoreNameDTO() == null || storeDTO.getStoreNameDTO().isBlank()) {
            throw new InvalidDataException("Store name must not be null or empty");
        }
    }

    public static void validateNewNameDTO(NewNameDTO newNameDTO) {

        if (newNameDTO == null) {
            throw new BadRequestException("New name must not be null");
        }

        if (newNameDTO.getNewNameDTO() == null || newNameDTO.getNewNameDTO().isBlank()) {
            throw new InvalidDataException("New franchise name must not be null or empty");
        }
    }
}
