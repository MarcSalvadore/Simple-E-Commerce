package com.apapedia.user.dto;

import org.mapstruct.Mapper;

import com.apapedia.user.dto.request.CreateSellerRequestDTO;
import com.apapedia.user.model.Seller;

@Mapper(componentModel = "spring")
public interface SellerMapper {
    Seller createSellerRequestDTOToSeller(CreateSellerRequestDTO createSellerRequestDTO);
}
