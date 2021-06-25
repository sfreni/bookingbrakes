package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.creditcardtransaction.CreditCardTransactionDto;
import it.freni.bookingbrakes.domain.CreditCard;
import it.freni.bookingbrakes.domain.CreditCardTransaction;
import it.freni.bookingbrakes.domain.Purchase;
import it.freni.bookingbrakes.error.IdAlreadyExists;
import it.freni.bookingbrakes.mapper.CreditCardMapper;
import it.freni.bookingbrakes.mapper.CreditCardTransactionMapper;
import it.freni.bookingbrakes.mapper.PurchaseMapper;
import it.freni.bookingbrakes.repository.CreditCardTransactionRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Service
@Log
public class CreditCardTransactionService {

    public static final String CREDITCARD_NOT_FOUND = "CreditCard not found or mismatch";
    public static final String CREDITCARD_TRANSACTION_NOT_FOUND = "Transaction Not Found";
    public static final String PURCHASE_NOT_FOUND = "Purchase not found or mismatch";
    private final CreditCardService creditCardService;
    private final PurchaseService purchaseService;
    private final CreditCardMapper creditCardMapper;
    public final CreditCardTransactionRepository creditCardTransactionRepository;
    public final CreditCardTransactionMapper creditCardTransactionMapper;
    private final PurchaseMapper purchaseMapper;

    public CreditCardTransactionService(CreditCardService creditCardService, PurchaseService purchaseService, CreditCardMapper creditCardMapper, CreditCardTransactionRepository creditCardTransactionRepository, CreditCardTransactionMapper creditCardTransactionMapper, PurchaseMapper purchaseMapper) {
        this.creditCardService = creditCardService;
        this.purchaseService = purchaseService;
        this.creditCardMapper = creditCardMapper;
        this.creditCardTransactionRepository = creditCardTransactionRepository;
        this.creditCardTransactionMapper = creditCardTransactionMapper;
        this.purchaseMapper = purchaseMapper;
    }

    public List<CreditCardTransaction> findAll() {
        return creditCardTransactionRepository.findAll();
    }

    public List<CreditCardTransaction> findAllBy(CreditCard creditCard) {
        return creditCardTransactionRepository.findByCreditcard(creditCard);
    }

    public Optional<CreditCardTransaction> findById(Long id) {
        return creditCardTransactionRepository.findById(id);
    }


    public CreditCardTransactionDto saveCreditCardTransaction(CreditCardTransactionDto creditCardTransactionDto) {


        if (creditCardTransactionDto.getId() != null) {
            creditCardTransactionDto.setId(null);
        }

        Optional<CreditCard> creditCard = creditCardService.findById(creditCardTransactionDto.getCreditcard().getId());
        Optional<Purchase> purchase =purchaseService.findById(creditCardTransactionDto.getPurchase().getId());
        if(creditCardTransactionDto.getCreditcard().getId()== null || creditCard.isEmpty()){
            log.log(Level.SEVERE, CREDITCARD_NOT_FOUND);
            throw new IdAlreadyExists( CREDITCARD_NOT_FOUND);
        }

        if(creditCardTransactionDto.getPurchase().getId()== null || purchase.isEmpty()){
            log.log(Level.SEVERE, PURCHASE_NOT_FOUND);
            throw new IdAlreadyExists( PURCHASE_NOT_FOUND);
        }

        creditCardTransactionDto.setCreditcard(creditCardMapper.toDtoCreditCardNoTransaction(creditCard.get()));
        creditCardTransactionDto.setPurchase(purchaseMapper.toPurchaseTransactionsDto(purchase.get()));

        return creditCardTransactionMapper.toDto(creditCardTransactionRepository.save(creditCardTransactionMapper.dtoToCreditCardTransaction(creditCardTransactionDto)));
    }





}