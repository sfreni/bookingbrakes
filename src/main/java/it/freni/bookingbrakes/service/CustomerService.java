package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.customer.CustomerControllerDto;
import it.freni.bookingbrakes.domain.Customer;
import it.freni.bookingbrakes.domain.Purchase;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.CustomerMapper;
import it.freni.bookingbrakes.repository.CustomerRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Service
@Log
public class CustomerService {

    public static final String OBJECT_NOT_FOUND = "Object not found";
    public static final String ID_ALREADY_EXISTS = "Id already exists";
    public static final String CUSTOMER_NOT_DELETE = "You can't delete this customer because it has got bookings";
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerService(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Iterable<Customer> findAll() {
        return repository.findAll();
    }

    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    public Customer findByIdWithoutOptional(Long id) {
        Optional<Customer> customer = repository.findById(id);
        if(customer.isPresent()){
            return customer.get();
        }
        log.log(Level.SEVERE, OBJECT_NOT_FOUND);
        throw new NotObjectFound( OBJECT_NOT_FOUND);
    }

    public CustomerControllerDto saveCustomer(Customer customer) {


        customer.setId(null);
        return mapper.toCustomerControllerDto(repository.save(customer));
    }

    public CustomerControllerDto replaceCustomer(Long id, Customer customer) {
        if (id == null || findById(id).isEmpty()) {
            log.log(Level.SEVERE, OBJECT_NOT_FOUND);
            throw new NotObjectFound( OBJECT_NOT_FOUND);
        }
        customer.setId(id);
        return mapper.toCustomerControllerDto(repository.save(customer));
    }

    public void deleteCustomerById(Long id) {
        Optional<Customer> customer=findById(id);
        if (id == null || customer.isEmpty()) {
            log.log(Level.SEVERE, OBJECT_NOT_FOUND);
            throw new NotObjectFound( OBJECT_NOT_FOUND);
        }
        List<Purchase> purchasesLocal=customer.get().getPurchases();

            if (purchasesLocal.size()>0) {
                log.log(Level.SEVERE, CUSTOMER_NOT_DELETE);
            throw new NotObjectFound( CUSTOMER_NOT_DELETE);
        }
        repository.deleteById(id);

    }



}