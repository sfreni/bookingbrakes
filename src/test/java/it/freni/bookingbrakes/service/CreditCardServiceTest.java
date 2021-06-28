package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.creditcard.CreditCardDto;
import it.freni.bookingbrakes.controller.dto.creditcard.CreditCardTransactionDto;
import it.freni.bookingbrakes.controller.dto.creditcard.CustomerWithIdDto;
import it.freni.bookingbrakes.domain.CreditCard;
import it.freni.bookingbrakes.domain.CreditCardTransaction;
import it.freni.bookingbrakes.domain.Customer;
import it.freni.bookingbrakes.domain.IssuingNetwork;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.CreditCardMapper;
import it.freni.bookingbrakes.mapper.CreditCardMapperImpl;
import it.freni.bookingbrakes.mapper.CustomerMapper;
import it.freni.bookingbrakes.mapper.CustomerMapperImpl;
import it.freni.bookingbrakes.repository.CreditCardRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceTest {
    @Mock
    private CreditCardRepository creditCardRepository;
    @Mock
    private CreditCardMapper creditCardMapper = new CreditCardMapperImpl();
    @Mock
    private CustomerService customerService;
    @Mock
    private CreditCardTransactionService creditCardTransactionService;
    @Mock
    private CustomerMapper customerMapper = new CustomerMapperImpl();

    @Autowired
    @InjectMocks
    private CreditCardService creditCardService;

    private CreditCard creditCard;
    private List<CreditCard> creditCardList;
    private CreditCardDto creditCardDto;
    private List<CreditCardDto> creditCardListDto;

    private CreditCardTransaction creditCardTransaction;
    private List<CreditCardTransaction> creditCardTransactionList;
    private CreditCardTransactionDto creditCardTransactionDto;
    private List<CreditCardTransactionDto> creditCardTransactionListDto;

    private Customer customer;
    private List<Customer> customerList;
    private CustomerWithIdDto customerDto;

    @BeforeEach
    void setUp() {
        creditCardTransactionList= new ArrayList<>();
        creditCard = new CreditCard();
        creditCardTransaction = new CreditCardTransaction();
        creditCardTransaction.setId(1L);
        creditCardTransactionList.add(creditCardTransaction);
        customer = new Customer();
        customer.setId(1L);
        creditCard.setId(1L);
        creditCard.setCreditCardTransactions(creditCardTransactionList);
        creditCard.setCustomer(customer);
        creditCard.setNumberCard("1111-2222-3333-4444");
        creditCard.setDateExpiration("04/2024");
        creditCard.setFirstName("Freni");
        creditCard.setLastName("Stefano");
        creditCard.setIssuingNetwork(IssuingNetwork.MASTER_CARD);
        creditCardList= new ArrayList<>();
        creditCardList.add(creditCard);

        creditCardDto = new CreditCardDto();
        creditCardTransactionDto = new CreditCardTransactionDto();
        creditCardTransactionListDto = new ArrayList<>();
        creditCardTransactionListDto.add(creditCardTransactionDto);
        creditCardTransactionDto.setId(1L);
        customerDto = new CustomerWithIdDto();
        customerDto.setId(1L);
        creditCardDto.setId(1L);
        creditCardDto.setCreditCardTransactions(creditCardTransactionListDto);
        creditCardDto.setCustomer(customerDto);
        creditCardDto.setNumberCard("1111-2222-3333-4444");
        creditCardDto.setDateExpiration("04/2024");
        creditCardDto.setFirstName("Freni");
        creditCardDto.setLastName("Stefano");
        creditCardDto.setIssuingNetwork(IssuingNetwork.MASTER_CARD.toString());


    }



    @Test
    void findAllBy() {
        when(creditCardRepository.findByCustomer(customer)).thenReturn(creditCardList);
        List<CreditCard> creditCards = creditCardService.findAllBy(customer);
        assertEquals(creditCards,creditCardList);
        verify(creditCardRepository, times(1)).findByCustomer(customer);

    }

    @Test
    void findById() {
        when(creditCardRepository.findById(1L)).thenReturn(Optional.ofNullable(creditCard));
        assertThat(creditCardService.findById(creditCard.getId())).get().isEqualTo(creditCard);
        verify(creditCardRepository, times(1)).findById(any());

    }

    @Test
    void saveCreditCard() {
        when(creditCardRepository.save(creditCard)).thenReturn(creditCard);
        when(customerService.findById(creditCardDto.getCustomer().getId())).thenReturn(Optional.ofNullable(customer));
        when(customerMapper.toDtoWithId(customer)).thenReturn(customerDto);
        when(creditCardMapper.dtoToCreditCard(creditCardDto)).thenReturn(creditCard);
        when(creditCardMapper.toDto(creditCard)).thenReturn(creditCardDto);

        CreditCardDto creditCardDto2= creditCardService.saveCreditCard(creditCardDto);
        assertEquals(creditCardDto,creditCardDto2);
        verify(creditCardRepository, times(1)).save(creditCard);
        verify(customerService, times(1)).findById(creditCardDto.getCustomer().getId());

    }

    @Test
    void replaceCreditCard() {
        when(creditCardRepository.save(creditCard)).thenReturn(creditCard);
        when(creditCardRepository.findById(creditCard.getId())).thenReturn(Optional.ofNullable(creditCard));
        when(creditCardMapper.toDto(creditCard)).thenReturn(creditCardDto);
        when(creditCardTransactionService.findAllBy(creditCardMapper.dtoToCreditCard(creditCardDto))).thenReturn(creditCardTransactionList);
        creditCardService.checkCreditCardId(creditCard.getId());
        creditCardService.checkCreditCardCustomer(creditCard.getId(),creditCardDto);
        assertThrows(NotObjectFound.class,() ->creditCardService.verifyCreditCardTransactionsEmpty(creditCardTransactionService.findAllBy(creditCardMapper.dtoToCreditCard(creditCardDto)).isEmpty()));
        CreditCardDto creditCardDto2= creditCardService.replaceCreditCard(creditCard);
        assertEquals(creditCardDto,creditCardDto2);
        verify(creditCardRepository, times(1)).save(creditCard);
        verify(creditCardRepository, times(2)).findById(any());
        verify(creditCardMapper, times(1)).toDto(creditCard);
    }

    @Test
    void deleteCreditCardById() {
        doNothing().when(creditCardRepository).deleteById(anyLong());
        when(creditCardRepository.findById(creditCard.getId())).thenReturn(Optional.ofNullable(creditCard));
        when(creditCardTransactionService.findAllBy(creditCardMapper.dtoToCreditCard(creditCardDto))).thenReturn(creditCardTransactionList);

        creditCardService.checkCreditCardId(creditCard.getId());
        assertThrows(NotObjectFound.class,() ->creditCardService.verifyCreditCardTransactionsEmpty(creditCardTransactionService.findAllBy(creditCardMapper.dtoToCreditCard(creditCardDto)).isEmpty()));
        creditCardService.deleteCreditCardById(creditCard.getId());
        verify(creditCardRepository, times(1)).deleteById(creditCard.getId());
        verify(creditCardRepository, times(2)).findById(any());
        verify(creditCardTransactionService, times(1)).findAllBy(any());
        verify(creditCardTransactionService, times(1)).findAllBy(creditCardMapper.dtoToCreditCard(creditCardDto));

    }
}