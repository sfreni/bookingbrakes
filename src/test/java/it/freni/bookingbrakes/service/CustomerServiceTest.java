package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.creditcard.CustomerDto;
import it.freni.bookingbrakes.controller.dto.customer.CreditCardDto;
import it.freni.bookingbrakes.controller.dto.customer.CustomerControllerDto;
import it.freni.bookingbrakes.controller.dto.customer.PurchaseDto;
import it.freni.bookingbrakes.domain.CreditCard;
import it.freni.bookingbrakes.domain.Customer;
import it.freni.bookingbrakes.domain.Purchase;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.*;
import it.freni.bookingbrakes.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper = new CustomerMapperImpl();
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
    private CustomerService customerService;

    private Customer customer;
    private List<Customer> customerList;
    private CustomerControllerDto customerDto;
    private List<CustomerDto> customerDtoList;
    private CreditCard creditCard;
    private List<CreditCard> creditCards;
    private CreditCardDto creditCardDto;
    private List<CreditCardDto> creditCardDtoList;

    private Purchase purchase;
    private List<Purchase> purchases;
    private List<PurchaseDto> purchaseDtoList;
    private PurchaseDto purchaseDto;


    @BeforeEach
    void setUp() throws Exception {
        customerList= new ArrayList<>();
        customer = new Customer();
        creditCard = new CreditCard();
        creditCard.setId(1L);

        purchase = new Purchase();
        purchases = new ArrayList<>();
        purchase.setId(1L);
        creditCards = new ArrayList<>();
        creditCards.add(creditCard);
        purchases.add(purchase);
        customer.setId(1L);
        customer.setCity("Florence");
        customer.setBirthDay(new SimpleDateFormat("yyyy-MM-dd").parse("1977-07-30"));
        customer.setCreditCard(creditCards);
        customer.setStreetAddress("Via A. Ponchielli n. 151");
        customer.setFirstName("Stefano");
        customer.setLastName("Freni");
        customer.setPurchases(purchases);
        customerDto = new CustomerControllerDto();
        creditCardDto = new CreditCardDto();
        creditCardDtoList = new ArrayList<>();

        purchaseDto = new PurchaseDto();
        purchaseDtoList = new ArrayList<>();


        customerDto.setId(1L);
        customerDto.setCity("Florence");
        customerDto.setBirthDay(new SimpleDateFormat("yyyy-MM-dd").parse("1977-07-30"));
        customerDto.setCreditCard(creditCardDtoList);
        customerDto.setStreetAddress("Via A. Ponchielli n. 151");
        customerDto.setFirstName("Stefano");
        customerDto.setLastName("Freni");
        customerDto.setPurchases(purchaseDtoList);

    }

    @Test
    void findCustomer() {

        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(customer));
        assertThat(customerService.findById(customer.getId())).get().isEqualTo(customer);
        verify(customerRepository, times(1)).findById(any());
    }

    @Test
    void findAllCustomers() {
        when(customerRepository.findAll()).thenReturn(customerList);
        List<Customer> customers2 = (List<Customer>) customerService.findAll();
        assertEquals(customerList,customers2);
        verify(customerRepository, times(1)).findAll();

    }

    @Test
    void saveCustomer() {
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toCustomerControllerDto(customer)).thenReturn(customerDto);
        CustomerControllerDto customer2= customerService.saveCustomer(customer);
        assertEquals(customer2,customerDto);
        verify(customerRepository, times(1)).save(customer);

    }

    @Test
    void replaceCustomer() {
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.toCustomerControllerDto(customer)).thenReturn(customerDto);
        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(customer));
        CustomerControllerDto customer2= customerService.replaceCustomer(customer.getId(),customer);
        assertEquals(customer2,customerDto);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void deleteCustomer() {

//        doNothing().when(customerRepository).deleteById(anyLong());
        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(customer));
      //  customer.setPurchases(null);
        //z   when(customer.getPurchases().isEmpty()).thenReturn(true);
        assertThrows(NotObjectFound.class,() ->customerService.deleteCustomerById(1L));
     //   customerService.deleteCustomerById(1L);
        verify(customerRepository, times(1)).findById(any());
       // verify(customerRepository, times(1)).deleteById(anyLong());
    }


}