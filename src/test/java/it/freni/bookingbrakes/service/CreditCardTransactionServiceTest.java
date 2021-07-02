package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.creditcardtransaction.CreditCardNoTransactionsDto;
import it.freni.bookingbrakes.controller.dto.creditcardtransaction.CreditCardTransactionDto;
import it.freni.bookingbrakes.controller.dto.creditcardtransaction.PurchaseTransactionsDto;
import it.freni.bookingbrakes.domain.CreditCard;
import it.freni.bookingbrakes.domain.CreditCardTransaction;
import it.freni.bookingbrakes.domain.CreditCardTransactionStatus;
import it.freni.bookingbrakes.domain.Purchase;
import it.freni.bookingbrakes.mapper.*;
import it.freni.bookingbrakes.repository.CreditCardTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditCardTransactionServiceTest {

    @Mock
    private CreditCardTransactionRepository creditCardTransactionRepository;

    @Mock
    private CreditCardTransactionMapper creditCardTransactionMapper = new CreditCardTransactionMapperImpl();
    @Mock
    private CreditCardMapper creditCardMapper = new CreditCardMapperImpl();
    @Mock
    private PurchaseMapper purchaseMapper = new PurchaseMapperImpl();
    @Mock
    private CreditCardService creditCardService;
    @Mock
    private PurchaseService purchaseService;

    @Autowired
    @InjectMocks
    private CreditCardTransactionService creditCardTransactionService;

    private CreditCardTransaction creditCardTransaction;
    private List<CreditCardTransaction> creditCardTransactionList;
    private CreditCardTransactionDto creditCardTransactionDto;
    private CreditCard creditCard;
    private Purchase purchase;
    private CreditCardNoTransactionsDto creditCardNoTransactionsDto;
    private PurchaseTransactionsDto purchaseTransactionsDto;

    @BeforeEach
    void setUp() {


        creditCardTransaction = new CreditCardTransaction();
        creditCard = new CreditCard();
                purchase    = new Purchase();
    creditCardTransaction.setId(1L);
        creditCard.setId(1L);
        purchase.setId(1L);
        creditCardTransaction.setCreditcard(creditCard);
        creditCardTransaction.setTransactionStatus(CreditCardTransactionStatus.PAID);
        creditCardTransaction.setPurchase(purchase);

        creditCardTransaction.setTotalePriceAmount(100D);
        creditCardTransactionList = new ArrayList<>();
        creditCardTransactionList.add(creditCardTransaction);

        creditCardTransactionDto = new CreditCardTransactionDto();
        creditCardNoTransactionsDto= new CreditCardNoTransactionsDto();
        purchaseTransactionsDto = new PurchaseTransactionsDto();
        creditCardTransactionDto.setId(1L);
        creditCardNoTransactionsDto.setId(1L);
        purchaseTransactionsDto.setId(1L);

        creditCardTransactionDto.setCreditcard(creditCardNoTransactionsDto);
        creditCardTransactionDto.setTransactionStatus(CreditCardTransactionStatus.PAID);
        creditCardTransactionDto.setPurchase(purchaseTransactionsDto);
        creditCardTransactionDto.setTotalePriceAmount(100D);
        creditCardTransactionList.add(creditCardTransaction);


    }

    @Test
    void findAll() {

        when(creditCardTransactionRepository.findAll()).thenReturn(creditCardTransactionList);
        List<CreditCardTransaction> creditCardTransactions = creditCardTransactionService.findAll();
        assertEquals(creditCardTransactions,creditCardTransactionList);
        verify(creditCardTransactionRepository, times(1)).findAll();
         }

    @Test
    void findAllBy() {

        when(creditCardTransactionRepository.findByCreditcard(creditCard)).thenReturn(creditCardTransactionList);
        List<CreditCardTransaction> creditCardTransactions = creditCardTransactionService.findAllBy(creditCard);
        assertEquals(creditCardTransactions,creditCardTransactionList);
        verify(creditCardTransactionRepository, times(1)).findByCreditcard(creditCard);
    }

    @Test
    void findById() {
        when(creditCardTransactionRepository.findById(1L)).thenReturn(Optional.ofNullable(creditCardTransaction));
        assertThat(creditCardTransactionService.findById(creditCardTransaction.getId())).get().isEqualTo(creditCardTransaction);
        verify(creditCardTransactionRepository, times(1)).findById(any());

    }

    @Test
    void saveCreditCardTransaction() {
        when(creditCardTransactionRepository.save(creditCardTransaction)).thenReturn(creditCardTransaction);
        when(creditCardService.findById(creditCardTransactionDto.getCreditcard().getId())).thenReturn(Optional.ofNullable(creditCard));
        when(purchaseService.findById(creditCardTransactionDto.getPurchase().getId())).thenReturn(Optional.ofNullable(purchase));
        when(creditCardMapper.toDtoCreditCardNoTransaction(creditCard)).thenReturn(creditCardNoTransactionsDto);
        when(purchaseMapper.toPurchaseTransactionsDto(purchase)).thenReturn(purchaseTransactionsDto);
        when(creditCardTransactionMapper.dtoToCreditCardTransaction(creditCardTransactionDto)).thenReturn(creditCardTransaction);
        when(creditCardTransactionMapper.toDto(creditCardTransaction)).thenReturn(creditCardTransactionDto);
        CreditCardTransactionDto creditCardTransactionDto2= creditCardTransactionService.saveCreditCardTransaction(creditCardTransactionDto);
        assertEquals(creditCardTransactionDto,creditCardTransactionDto2);
        verify(creditCardTransactionRepository, times(1)).save(creditCardTransaction);
        verify(creditCardService, times(1)).findById(creditCardTransactionDto.getCreditcard().getId());
        verify(purchaseService, times(1)).findById(creditCardTransactionDto.getPurchase().getId());

    }
}