package com.apapedia.user.dto;

import org.mapstruct.Mapper;

import com.apapedia.user.dto.request.CreateUserRequestDTO;
import com.apapedia.user.model.Seller;

@Mapper(componentModel = "spring")
public interface SellerMapper {
    Seller createSellerRequestDTOToSeller(CreateUserRequestDTO createSellerRequestDTO);
}
