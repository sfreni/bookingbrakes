package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.creditcard.CustomerDto;
import it.freni.bookingbrakes.controller.dto.customer.CustomerControllerDto;
import it.freni.bookingbrakes.domain.Customer;
import it.freni.bookingbrakes.mapper.CustomerMapper;
import it.freni.bookingbrakes.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerService service, CustomerMapper customerMapper) {
        this.service = service;
        this.customerMapper = customerMapper;
    }

    @GetMapping
    public ResponseEntity<Iterable<CustomerControllerDto>> getAllCustomer(){
        Iterable<CustomerControllerDto> dtos = customerMapper.toCustomerControllerDtos(service.findAll());

        return ResponseEntity.status(HttpStatus.OK)
                .body(dtos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerControllerDto> getCustomer(@PathVariable("id") Long id){
        Optional<Customer> customer = service.findById(id);
        if(customer.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(customerMapper.toCustomerControllerDto(customer.get()));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<CustomerControllerDto> postCustomer(@RequestBody CustomerDto customerDto) {

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(service.saveCustomer(customerMapper.dtoToCustomer(customerDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerControllerDto> putCustomer(@PathVariable("id") Long id,@RequestBody CustomerDto customerDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.replaceCustomer(id, customerMapper.dtoToCustomer(customerDto)));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") Long id){
         service.deleteCustomerById(id);
        }

}
