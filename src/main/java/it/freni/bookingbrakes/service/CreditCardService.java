package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.CreditCardDto;
import it.freni.bookingbrakes.controller.dto.CustomerDto;
import it.freni.bookingbrakes.domain.CreditCard;
import it.freni.bookingbrakes.domain.Customer;
import it.freni.bookingbrakes.error.IdAlreadyExists;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.CreditCardMapper;
import it.freni.bookingbrakes.mapper.CustomerMapper;
import it.freni.bookingbrakes.repository.CreditCardRepository;
import it.freni.bookingbrakes.repository.CustomerRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Service
@Log
public class CreditCardService {

    public static final String CREDITCARD_NOT_FOUND = "CreditCard not found";
    public static final String CUSTOMER_NOT_CONSISTENCY = "Customer can't be modified";
    public static final String ID_ALREADY_EXISTS = "Id already exists";
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final CreditCardRepository creditCardRepository;
    private final CreditCardMapper creditCardMapper;

    public CreditCardService(CustomerService customerService, CustomerMapper customerMapper, CreditCardRepository creditCardRepository, CreditCardMapper creditCardMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.creditCardRepository = creditCardRepository;
        this.creditCardMapper = creditCardMapper;
    }

    public List<CreditCard> findAllBy(Customer customer) {
        return creditCardRepository.findByCustomer(customer);
    }

    public Optional<CreditCard> findById(Long id) {
        return creditCardRepository.findById(id);
    }


    public CreditCardDto saveCreditCard(CreditCardDto creditCardDto) {


        if (creditCardDto.getCustomer().getId() == null || customerService.findById(creditCardDto.getCustomer().getId()).isEmpty()) {
            log.log(Level.SEVERE, ID_ALREADY_EXISTS);
            throw new IdAlreadyExists( ID_ALREADY_EXISTS);
        }

        if(creditCardDto.getId()!= null && creditCardRepository.findById(creditCardDto.getId()).isPresent()){
            log.log(Level.SEVERE, ID_ALREADY_EXISTS);
            throw new IdAlreadyExists( ID_ALREADY_EXISTS);
        }


        creditCardDto.setCustomer(customerMapper.toDtoWithId(customerService.findById(creditCardDto.getCustomer().getId()).get()));

        return creditCardMapper.toDto(creditCardRepository.save(creditCardMapper.dtoToCreditCard(creditCardDto)));
    }

    public CreditCardDto replaceCreditCard(Long id, CreditCard creditCard) {
        if (id == null || findById(id).isEmpty()) {
            log.log(Level.SEVERE, CREDITCARD_NOT_FOUND);
            throw new NotObjectFound( CREDITCARD_NOT_FOUND);
        }

        if (findById(id).get().getCustomer().getId() != creditCard.getCustomer().getId()) {
            log.log(Level.SEVERE, CUSTOMER_NOT_CONSISTENCY);
            throw new NotObjectFound( CUSTOMER_NOT_CONSISTENCY);
        }

        creditCard.setId(id);
        return creditCardMapper.toDto(creditCardRepository.save(creditCard));
    }


    public void deleteCreditCardById(Long id) {
        if (id == null || findById(id).isEmpty()) {
            log.log(Level.SEVERE, CREDITCARD_NOT_FOUND);
            throw new NotObjectFound( CREDITCARD_NOT_FOUND);
        }
        creditCardRepository.deleteById(id);
    }


}