package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.purchase.*;
import it.freni.bookingbrakes.domain.*;
import it.freni.bookingbrakes.error.IdAlreadyExists;
import it.freni.bookingbrakes.mapper.PurchaseMapper;
import it.freni.bookingbrakes.mapper.PurchaseMapperImpl;
import it.freni.bookingbrakes.repository.ProductRepository;
import it.freni.bookingbrakes.repository.PurchaseRepository;
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
class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private PurchaseMapper purchaseMapper = new PurchaseMapperImpl();
    @Mock
    private TripService tripService;

    @Mock
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    @Autowired
    @InjectMocks
    private PurchaseService purchaseService;

    private Purchase purchase;
    private List<Purchase> purchaseList;
    private List<PurchaseDto> purchaseDtoList;
    private PurchaseDto purchaseDto;

    private Trip trip;
    private TripDto tripDto;
    private CreditCardSeatDto creditCardSeatDto;
    private CustomerPurchaseDto customerPurchaseDto;

    private CreditCardTransaction creditCardTransaction;
    private List<CreditCardTransaction> creditCardTransactionList;
    private PurchaseCreditCardTransactionDto purchaseCreditCardTransactionDto;
    private List<PurchaseCreditCardTransactionDto> purchaseCreditCardTransactionDtoList;

    private Seat seat;
    private ProductSeatDto productSeatDto;
    private AdditionalService additionalService;
    private ProductAdditionalServiceDto productAdditionalServiceDto;
    private List<Product> productList;
    private List<ProductDto> productDtoList;
    private Customer customer;


    @BeforeEach
    void setUp() throws Exception { //Set up SimpleDateFormat
        purchase = new Purchase();
        purchaseList = new ArrayList<>();
        trip = new Trip();
        customer=new Customer();
        creditCardTransactionList= new ArrayList<>();
        creditCardTransaction = new CreditCardTransaction();
        seat = new Seat();
        additionalService=new AdditionalService();
        purchase.setId(1L);
        purchase.setPurchaseStatus(PurchaseStatus.NOT_COMPLETE);
        purchase.setDatePurchase(new SimpleDateFormat("yyyy-MM-dd").parse("2021-06-28"));
        trip.setId(1L);
        purchase.setTrip(trip);
        creditCardTransaction.setId(1L);
        creditCardTransactionList.add(creditCardTransaction);
        purchase.setCreditCardTransactions(creditCardTransactionList);
        seat.setId(1L);
        seat.setNrSeat("1A");
        productList= new ArrayList<>();
        productList.add(seat);
        additionalService.setId(1L);
        productList.add(additionalService);
        purchase.setProducts(productList);
        customer.setId(1L);
        trip.setPurchases(purchaseList);

        purchase.setCustomer(customer);
        purchaseList.add(purchase);
        purchaseDto = new PurchaseDto();
        purchaseDtoList = new ArrayList<>();
        tripDto = new TripDto();
        customerPurchaseDto=new CustomerPurchaseDto();
        purchaseCreditCardTransactionDtoList= new ArrayList<>();
        purchaseCreditCardTransactionDto = new PurchaseCreditCardTransactionDto() ;
        productSeatDto = new ProductSeatDto() ;
        productAdditionalServiceDto=new ProductAdditionalServiceDto();


        purchaseDto.setId(1L);
        purchaseDto.setPurchaseStatus(PurchaseStatus.NOT_COMPLETE);
        purchaseDto.setDatePurchase(new SimpleDateFormat("yyyy-MM-dd").parse("2021-06-28"));

        tripDto.setId(1L);
        purchaseDto.setTrip(tripDto);
        purchaseCreditCardTransactionDto.setId(1L);
        purchaseCreditCardTransactionDtoList.add(purchaseCreditCardTransactionDto);
        purchaseDto.setCreditCardTransactions(purchaseCreditCardTransactionDtoList);
        productSeatDto.setId(1L);
        productSeatDto.setNrSeat("1A");

        productDtoList= new ArrayList<>();
        productDtoList.add(productSeatDto);
        productAdditionalServiceDto.setId(1L);
        productDtoList.add(productAdditionalServiceDto);
        purchaseDto.setProducts(productDtoList);
        customerPurchaseDto.setId(1L);

        purchaseDto.setCustomer(customerPurchaseDto);
        purchaseDtoList.add(purchaseDto);

    }

    @Test
    void findPurchases() {

        when(purchaseRepository.findById(1L)).thenReturn(Optional.ofNullable(purchase));
        assertThat(purchaseService.findById(purchase.getId())).get().isEqualTo(purchase);
        verify(purchaseRepository, times(1)).findById(any());
    }

    @Test
    void findAllPurchases() {
        when(purchaseRepository.findAll()).thenReturn(purchaseList);
        List<Purchase> purchaseList2 = (List<Purchase>) purchaseService.findAll();
        assertEquals(purchaseList2,purchaseList);
        verify(purchaseRepository, times(1)).findAll();
    }


    @Test
    void savePurchase() {
        when(purchaseRepository.save(purchase)).thenReturn(purchase);
        when(purchaseMapper.purchaseDtoToPurchase(purchaseDto)).thenReturn(purchase);
        when(tripService.findById(purchaseDto.getTrip().getId())).thenReturn(Optional.ofNullable(trip));
        when(tripService.findByIdWithoutOptional(purchaseDto.getTrip().getId())).thenReturn(trip);
        assertThrows(IdAlreadyExists.class,() ->purchaseService.checkDtoBeforeSaving(purchaseDto));
        productSeatDto.setNrSeat("2B");
        ProductSeatDto productSeatDto2 = new ProductSeatDto();
        productSeatDto2.setId(2L);
        productSeatDto2.setNrSeat("2B");
        productDtoList.add(productSeatDto2);
        assertThrows(IdAlreadyExists.class,() ->purchaseService.checkDtoBeforeSaving(purchaseDto));
        Purchase purchase2= purchaseService.savePurchase(purchaseDto);
        assertEquals(purchase,purchase2);
        verify(purchaseRepository, times(1)).save(purchase);
        verify(purchaseMapper, times(1)).purchaseDtoToPurchase(purchaseDto);

    }

    @Test
    void replacePurchase() {
        purchaseDto.setId(3L);
        when(purchaseRepository.save(purchase)).thenReturn(purchase);
        when(purchaseRepository.findById(3L)).thenReturn(Optional.ofNullable(purchase));
        when(purchaseMapper.purchaseDtoToPurchase(purchaseDto)).thenReturn(purchase);
        when(tripService.findById(purchaseDto.getTrip().getId())).thenReturn(Optional.ofNullable(trip));
        when(tripService.findByIdWithoutOptional(purchaseDto.getTrip().getId())).thenReturn(trip);
        purchaseDto.setId(3L);
        assertThrows(IdAlreadyExists.class,() ->purchaseService.checkDtoBeforeUpdating(purchaseDto));
        productSeatDto.setNrSeat("2B");
        ProductSeatDto productSeatDto2 = new ProductSeatDto();
        productSeatDto2.setId(2L);
        productSeatDto2.setNrSeat("2B");
        productDtoList.add(productSeatDto2);
        assertThrows(IdAlreadyExists.class,() ->purchaseService.checkDtoBeforeUpdating(purchaseDto));
        Purchase purchase2= purchaseService.savePurchase(purchaseDto);
        assertEquals(purchase,purchase2);
        verify(purchaseRepository, times(1)).save(purchase);
        verify(purchaseMapper, times(1)).purchaseDtoToPurchase(purchaseDto);
    }

    @Test
    void deleteCustomer() {
        when(purchaseRepository.findById(1L)).thenReturn(Optional.ofNullable(purchase));
//        doNothing().when(productRepository).deleteProductByPurchase(purchase);
        doNothing().when(purchaseRepository).deleteById(1L);
        Optional<Purchase> purchaseOptional = purchaseService.findById(1L);
        if(purchaseOptional.isPresent() && !creditCardTransactionList.isEmpty()) {
            assertThrows(IdAlreadyExists.class,() ->purchaseService.errorDeletePurchaseWithTransaction());         }

        purchaseOptional.get().setCreditCardTransactions(null);
        purchaseOptional.get().setTrip(null);
        Optional<List<CreditCardTransaction>> creditCardTransactionList = Optional.ofNullable(purchaseOptional.get().getCreditCardTransactions());

        if(purchaseOptional.isPresent() && creditCardTransactionList.isEmpty()) {
         //   assertThrows(IdAlreadyExists.class,() ->productService.deleteAllProductsByPurchase(purchase));
            productService.deleteAllProductsByPurchase(purchase);
            purchaseService.deletePurchase(1L);
            verify(purchaseRepository, times(1)).deleteById(purchase.getId());
        }



    }


}