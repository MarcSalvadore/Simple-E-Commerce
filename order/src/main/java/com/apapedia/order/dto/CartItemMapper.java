package com.apapedia.order.dto;

import org.mapstruct.Mapper;

import com.apapedia.order.dto.request.CreateCartItemRequestDTO;
import com.apapedia.order.model.CartItem;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItem createCartItemRequestDTOToCartItem(CreateCartItemRequestDTO createCartItemRequestDTO);
} 