package com.apapedia.user.dto;

import org.mapstruct.Mapper;

import com.apapedia.user.dto.request.CreateCustomerRequestDTO;
import com.apapedia.user.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer createCustomerRequestDTOToCustomer(CreateCustomerRequestDTO userRequestDTO);
}
