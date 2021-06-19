package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.CreditCardTransaction.CreditCardTransactionDto;
import it.freni.bookingbrakes.domain.*;
import it.freni.bookingbrakes.error.IdAlreadyExists;
import it.freni.bookingbrakes.error.NotObjectFound;
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
    public static final String CUSTOMER_NOT_CONSISTENCY = "Customer can't be modified";
    public static final String PURCHASE_NOT_FOUND = "Purchase not found or mismatch";
    public static final String ID_ALREADY_EXISTS = "Id already exists";
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

        if(creditCardTransactionDto.getCreditcard().getId()== null || creditCardService.findById(creditCardTransactionDto.getCreditcard().getId()).isEmpty()){
            log.log(Level.SEVERE, CREDITCARD_NOT_FOUND);
            throw new IdAlreadyExists( CREDITCARD_NOT_FOUND);
        }

        if(creditCardTransactionDto.getCreditcard().getId()== null || purchaseService.findById(creditCardTransactionDto.getPurchase().getId()).isEmpty()){
            log.log(Level.SEVERE, PURCHASE_NOT_FOUND);
            throw new IdAlreadyExists( PURCHASE_NOT_FOUND);
        }




        creditCardTransactionDto.setCreditcard(creditCardMapper.toDtoCreditCardNoTransaction(creditCardService.findById(creditCardTransactionDto.getCreditcard().getId()).get()));
        creditCardTransactionDto.setPurchase(purchaseMapper.toPurchaseTransactionsDto(purchaseService.findById(creditCardTransactionDto.getPurchase().getId()).get()));

        return creditCardTransactionMapper.toDto(creditCardTransactionRepository.save(creditCardTransactionMapper.dtoToCreditCardTransaction(creditCardTransactionDto)));
    }


    public CreditCardTransactionDto replaceCreditCardTransaction(Long id, CreditCardTransactionDto creditCardTransactionDto) {

        if (id == null || creditCardTransactionRepository.findById(id).isEmpty() ) {
            log.log(Level.SEVERE, CREDITCARD_TRANSACTION_NOT_FOUND);
            throw new IdAlreadyExists( CREDITCARD_TRANSACTION_NOT_FOUND);
        }

        if(creditCardService.findById(creditCardTransactionDto.getCreditcard().getId()).isEmpty()
        ||
                creditCardTransactionDto.getCreditcard().getId() != creditCardTransactionRepository.findById(id).get().getCreditcard().getId())
        {
            log.log(Level.SEVERE, CREDITCARD_NOT_FOUND);
            throw new IdAlreadyExists( CREDITCARD_NOT_FOUND);
        }


        if(creditCardService.findById(creditCardTransactionDto.getPurchase().getId()).isEmpty()
                ||
                creditCardTransactionDto.getPurchase().getId() != creditCardTransactionRepository.findById(id).get().getPurchase().getId())
        {    log.log(Level.SEVERE, PURCHASE_NOT_FOUND);
            throw new IdAlreadyExists( PURCHASE_NOT_FOUND);
        }
        creditCardTransactionDto.setId(id);
        creditCardTransactionDto.setCreditcard(creditCardMapper.toDtoCreditCardNoTransaction(creditCardService.findById(creditCardTransactionDto.getCreditcard().getId()).get()));
        creditCardTransactionDto.setPurchase(purchaseMapper.toPurchaseTransactionsDto(purchaseService.findById(creditCardTransactionDto.getPurchase().getId()).get()));
        return creditCardTransactionMapper.toDto(creditCardTransactionRepository.save(creditCardTransactionMapper.dtoToCreditCardTransaction(creditCardTransactionDto)));
    }

      public List<CreditCardTransaction> saveCreditCardTransactionList(List<CreditCardTransaction> creditCardTransactions) {

          return creditCardTransactionRepository.saveAll(creditCardTransactions);


        }

    public void checkDtoBeforeSaving(List<CreditCardTransaction> creditCardTransactions) {
        for(CreditCardTransaction creditCardTransaction : creditCardTransactions) {
            if (creditCardTransaction.getId() != null) {
                creditCardTransaction.setId(null);
            }

            if (creditCardTransaction.getCreditcard() == null ||
            creditCardTransaction.getCreditcard().getId() == null ||
            creditCardService.findById(creditCardTransaction.getCreditcard().getId()).isEmpty())
            {
                log.log(Level.SEVERE, CREDITCARD_NOT_FOUND);
                throw new IdAlreadyExists(CREDITCARD_NOT_FOUND);
            }


        }
    }

    public void deleteCreditCardTransactionById(Long id) {
        if (id == null || findById(id).isEmpty()) {
            log.log(Level.SEVERE, CREDITCARD_TRANSACTION_NOT_FOUND);
            throw new NotObjectFound( CREDITCARD_TRANSACTION_NOT_FOUND);
        }
        creditCardTransactionRepository.deleteById(id);
    }



}