package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.CustomerDto;
import it.freni.bookingbrakes.mapper.CustomerMapper;
import it.freni.bookingbrakes.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerService(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CustomerDto> findAll() {
           return mapper.toDtos(repository.findAll());
    }
}
