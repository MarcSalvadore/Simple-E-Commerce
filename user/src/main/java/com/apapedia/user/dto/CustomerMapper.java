package com.apapedia.user.dto;

import org.mapstruct.Mapper;

import com.apapedia.user.dto.request.CreateUserRequestDTO;
import com.apapedia.user.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer createUserRequestDTOToCustomer(CreateUserRequestDTO userRequestDTO);
}
