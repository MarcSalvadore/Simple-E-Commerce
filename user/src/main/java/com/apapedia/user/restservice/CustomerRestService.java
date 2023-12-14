package com.apapedia.user.restservice;

import java.util.UUID;

import com.apapedia.user.model.Customer;

public interface CustomerRestService {
    void createRestCustomer(Customer customer);
    Customer topUp(UUID id, Long amount);
}
