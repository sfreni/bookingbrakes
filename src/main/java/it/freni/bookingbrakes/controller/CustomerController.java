package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.CustomerDto;
import it.freni.bookingbrakes.domain.Customer;
import it.freni.bookingbrakes.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping(value = "/customer")
    public ResponseEntity<List<CustomerDto>> getAllCustomer(){
        List<CustomerDto> dtos = service.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(dtos);

    }


}
