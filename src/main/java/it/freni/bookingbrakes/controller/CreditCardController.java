package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.CreditCardDto;
import it.freni.bookingbrakes.controller.dto.CreditCardDtoList;
import it.freni.bookingbrakes.controller.dto.CreditCardDtoSingle;
import it.freni.bookingbrakes.controller.dto.CustomerDto;
import it.freni.bookingbrakes.domain.CreditCard;
import it.freni.bookingbrakes.domain.Customer;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.CreditCardMapper;
import it.freni.bookingbrakes.mapper.CustomerMapper;
import it.freni.bookingbrakes.repository.CreditCardRepository;
import it.freni.bookingbrakes.service.CreditCardService;
import it.freni.bookingbrakes.service.CustomerService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@RestController
@RequestMapping("/creditcards")
@Log
public class CreditCardController {
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final CreditCardService creditCardService;
    private final CreditCardMapper creditCardMapper;

    public CreditCardController(CustomerService customerService, CustomerMapper customerMapper, CreditCardService creditCardRepository, CreditCardMapper creditCardMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.creditCardService = creditCardRepository;
        this.creditCardMapper = creditCardMapper;
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CreditCardDtoList> getAllCreditCardOfCustomers(@PathVariable("id") Long id){
        Optional<Customer> customer =customerService.findById(id);
                if(customer.isEmpty()){
                    log.log(Level.SEVERE, CUSTOMER_NOT_FOUND);
                    throw new NotObjectFound(CUSTOMER_NOT_FOUND);
                }

                return ResponseEntity.status(HttpStatus.OK)
                .body(creditCardMapper.toDtosList(creditCardService.findAllBy(customer.get()),customerMapper.toDto(customer.get())));

}

    @GetMapping("/{id}")
    public ResponseEntity<CreditCardDto> getCreditCard(@PathVariable("id") Long id){
        Optional<CreditCard> creditCard = creditCardService.findById(id);
        if(creditCard.isPresent()) {

            return ResponseEntity.status(HttpStatus.OK)
                    .body(creditCardMapper.toDto(creditCard.get()));
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<CreditCardDto> postCreditCard(@RequestBody CreditCardDto creditCardDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(creditCardService.saveCreditCard(creditCardDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditCardDto> putCreditCard(@PathVariable("id") Long id,@RequestBody CreditCardDto creditCardDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(creditCardService.replaceCreditCard(id, creditCardMapper.dtoToCreditCard(creditCardDto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteCreditCard(@PathVariable("id") Long id){
        creditCardService.deleteCreditCardById(id);
    }

}