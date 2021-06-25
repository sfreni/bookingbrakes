package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.creditcardtransaction.CreditCardTransactionDto;
import it.freni.bookingbrakes.domain.CreditCardTransaction;
import it.freni.bookingbrakes.domain.CreditCardTransactionStatus;
import it.freni.bookingbrakes.mapper.CreditCardTransactionMapper;
import it.freni.bookingbrakes.service.CreditCardTransactionService;
import it.freni.bookingbrakes.service.PurchaseService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/creditcardtransactions")
@Log
@Transactional
public class CreditCardTransactionController {
    public static final String CREDITCARD_NOT_FOUND = "Credit Card not found";

    private final CreditCardTransactionService creditCardTransactionService;
    private final CreditCardTransactionMapper creditCardTransactionMapper;
    private final PurchaseService purchaseService;

    public CreditCardTransactionController(CreditCardTransactionService creditCardTransactionService, CreditCardTransactionMapper creditCardTransactionMapper, PurchaseService purchaseService) {
        this.creditCardTransactionService = creditCardTransactionService;
        this.creditCardTransactionMapper = creditCardTransactionMapper;
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public ResponseEntity<List<CreditCardTransactionDto>> getAllCreditCardTransactions(){
        List<CreditCardTransactionDto> dtos = creditCardTransactionMapper.toDtosList(creditCardTransactionService.findAll());
        return ResponseEntity.status(HttpStatus.OK)
                .body(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCardTransactionDto> getCreditCardTransaction(@PathVariable("id") Long id){
        Optional<CreditCardTransaction> creditCardTransactionServiceById = creditCardTransactionService.findById(id);
        if(creditCardTransactionServiceById.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(creditCardTransactionMapper.toDto(creditCardTransactionServiceById.get()));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @PostMapping
    public ResponseEntity<CreditCardTransactionDto> postCreditCardTransacton(@RequestBody CreditCardTransactionDto creditCardTransactionDto) {

        CreditCardTransactionDto creditCardTransactionDtoSaved = creditCardTransactionService.saveCreditCardTransaction(creditCardTransactionDto);
        if(creditCardTransactionDtoSaved.getTransactionStatus().equals(CreditCardTransactionStatus.PAID)){
            purchaseService.updatePurchaseTransactions(creditCardTransactionDtoSaved);
        }
        if(creditCardTransactionDtoSaved.getTransactionStatus().equals(CreditCardTransactionStatus.REFUND)){
           purchaseService.updateRefusedPurchaseTransactions(creditCardTransactionDtoSaved);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(creditCardTransactionDtoSaved);
    }





}