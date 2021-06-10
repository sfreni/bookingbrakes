package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.CustomerDto;
import it.freni.bookingbrakes.domain.Customer;
import it.freni.bookingbrakes.domain.Purchase;
import it.freni.bookingbrakes.error.IdAlreadyExists;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.repository.PurchaseRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;

@Service
@Log
public class PurchaseService {

    public static final String OBJECT_NOT_FOUND = "Object not found";
    public static final String ID_ALREADY_EXISTS = "Id already exists";
    private final PurchaseRepository repository;

    public PurchaseService(PurchaseRepository repository) {
        this.repository = repository;
    }

    /*    public Iterable<CustomerDto> findAll() {
            return mapper.toDtos(repository.findAll());
        }
    */
    public Optional<Purchase> findById(Long id) {
        return repository.findById(id);
    }
//
//    public CustomerDto saveCustomer(Customer customer) {
//
//
//            if (customer.getId() != null && findById(customer.getId()).isPresent()) {
//                log.log(Level.SEVERE, ID_ALREADY_EXISTS);
//                throw new IdAlreadyExists( ID_ALREADY_EXISTS);
//            }
//
//        return mapper.toDto(repository.save(customer));
//    }
//
//    public CustomerDto replaceCustomer(Long id, Customer customer) {
//        if (id == null || findById(id).isEmpty()) {
//            log.log(Level.SEVERE, OBJECT_NOT_FOUND);
//            throw new NotObjectFound( OBJECT_NOT_FOUND);
//        }
//        customer.setId(id);
//        return mapper.toDto(repository.save(customer));
//    }
//
//    public void deleteCustomerById(Long id) {
//        if (id == null || findById(id).isEmpty()) {
//            log.log(Level.SEVERE, OBJECT_NOT_FOUND);
//            throw new NotObjectFound( OBJECT_NOT_FOUND);
//        }
//        repository.deleteById(id);
//    }



}