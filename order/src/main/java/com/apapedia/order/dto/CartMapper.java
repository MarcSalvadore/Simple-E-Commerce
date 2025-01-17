package com.apapedia.order.dto;

import org.mapstruct.Mapper;

import com.apapedia.order.dto.request.CreateCartRequestDTO;
import com.apapedia.order.model.Cart;

@Mapper(componentModel = "spring")
public interface CartMapper {
    Cart createCartRequestDTOToCart(CreateCartRequestDTO createCartRequestDTO);
} 