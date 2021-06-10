package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.*;
import it.freni.bookingbrakes.domain.CreditCard;
import it.freni.bookingbrakes.domain.CreditCardTransaction;
import it.freni.bookingbrakes.domain.Customer;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.CreditCardMapper;
import it.freni.bookingbrakes.mapper.CreditCardTransactionMapper;
import it.freni.bookingbrakes.mapper.CustomerMapper;
import it.freni.bookingbrakes.service.CreditCardService;
import it.freni.bookingbrakes.service.CreditCardTransactionService;
import it.freni.bookingbrakes.service.CustomerService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@RestController
@RequestMapping("/creditcardtransactions")
@Log
public class CreditCardTransactionController {
    public static final String CREDITCARD_NOT_FOUND = "Credit Card not found";
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final CreditCardService creditCardService;
    private final CreditCardMapper creditCardMapper;
    private final CreditCardTransactionService creditCardTransactionService;
    private final CreditCardTransactionMapper creditCardTransactionMapper;

    public CreditCardTransactionController(CustomerService customerService, CustomerMapper customerMapper, CreditCardService creditCardService, CreditCardMapper creditCardMapper, CreditCardTransactionService creditCardTransactionService, CreditCardTransactionMapper creditCardTransactionMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.creditCardService = creditCardService;
        this.creditCardMapper = creditCardMapper;
        this.creditCardTransactionService = creditCardTransactionService;
        this.creditCardTransactionMapper = creditCardTransactionMapper;
    }
    @GetMapping
    public ResponseEntity<List<CreditCardTransactionWithCustomerDto>> getAllCreditCardTransactions(){
        List<CreditCardTransactionWithCustomerDto> dtos = creditCardTransactionMapper.toDtosList(creditCardTransactionService.findAll());
        return ResponseEntity.status(HttpStatus.OK)
                .body(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCardTransactionWithCustomerDto> getCreditCardTransaction(@PathVariable("id") Long id){
        Optional<CreditCardTransaction> creditCardTransactionServiceById = creditCardTransactionService.findById(id);
        if(creditCardTransactionServiceById.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(creditCardTransactionMapper.toDtoWithCustomer(creditCardTransactionServiceById.get(), creditCardTransactionServiceById.get().getCreditcard().getCustomer()));
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }



    @PostMapping
    public ResponseEntity<CreditCardTransactionDto> postCreditCardTransacton(@RequestBody CreditCardTransactionDto CreditCardTransactionDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(creditCardTransactionService.saveCreditCardTransaction(CreditCardTransactionDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditCardTransactionDto> putCreditCardTransaction(@PathVariable("id") Long id,@RequestBody CreditCardTransactionDto CreditCardTransactionDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(creditCardTransactionService.replaceCreditCardTransaction(id, CreditCardTransactionDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public void deleteCreditCardTransactionById(@PathVariable("id") Long id){
        creditCardTransactionService.deleteCreditCardTransactionById(id);
    }


}