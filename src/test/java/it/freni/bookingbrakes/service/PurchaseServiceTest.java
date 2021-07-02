package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.creditcardtransaction.CreditCardTransactionDto;
import it.freni.bookingbrakes.controller.dto.creditcardtransaction.PurchaseTransactionsDto;
import it.freni.bookingbrakes.controller.dto.purchase.*;
import it.freni.bookingbrakes.domain.*;
import it.freni.bookingbrakes.error.IdAlreadyExists;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.CreditCardTransactionMapper;
import it.freni.bookingbrakes.mapper.CreditCardTransactionMapperImpl;
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
    private CreditCardTransactionMapper creditCardTransactionMapper = new CreditCardTransactionMapperImpl();
    @Mock
    private TripService tripService;


    @Mock
    private ProductRepository productRepository;

    @Autowired
    @InjectMocks
    private PurchaseService purchaseService;

    @Autowired
    @InjectMocks
    private ProductService productService;

    private Purchase purchase;
    private List<Purchase> purchaseList;
    private List<PurchaseDto> purchaseDtoList;
    private PurchaseDto purchaseDto;
    private PurchaseTransactionsDto purchaseTransactionsDto;

    private Trip trip;
    private Trip trip2;
    private TripDto tripDto;
    private CreditCardSeatDto creditCardSeatDto;
    private CustomerPurchaseDto customerPurchaseDto;

    private CreditCardTransaction creditCardTransaction;
    private List<CreditCardTransaction> creditCardTransactionList;
    private PurchaseCreditCardTransactionDto purchaseCreditCardTransactionDto;
    private List<PurchaseCreditCardTransactionDto> purchaseCreditCardTransactionDtoList;

    private CreditCardTransactionDto creditCardTransactionDto;

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
        creditCardTransaction.setTransactionStatus(CreditCardTransactionStatus.PAID);
        creditCardTransaction.setTotalePriceAmount(100D);
        creditCardTransactionList.add(creditCardTransaction);
        purchase.setCreditCardTransactions(creditCardTransactionList);
        productList= new ArrayList<>();
        seat.setId(1L);
        seat.setNrSeat("1A");
        seat.setPriceAmount(100D);
        additionalService.setId(1L);
        additionalService.setPriceAmount(100D);
        productList.add(seat);
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
        creditCardTransactionDto = new CreditCardTransactionDto();
        creditCardTransactionDto.setId(1L);
        purchaseTransactionsDto= new PurchaseTransactionsDto();
        purchaseTransactionsDto.setId(1L);
        creditCardTransactionDto.setPurchase(purchaseTransactionsDto);
        purchaseDto.setCustomer(customerPurchaseDto);
        purchaseDtoList.add(purchaseDto);
        trip2 = new Trip();

    }

    @Test
    void findPurchases() {

        when(purchaseRepository.findById(1L)).thenReturn(Optional.ofNullable(purchase));
        assertThat(purchaseService.findById(purchase.getId())).get().isEqualTo(purchase);
        verify(purchaseRepository, times(1)).findById(any());
    }

    @Test
    void findByIdWithoutOptional() {

        when(purchaseRepository.findById(1L)).thenReturn(Optional.ofNullable(purchase));
        assertThat(purchaseService.findByIdWithoutOptional(purchase.getId())).isEqualTo(purchase);
        verify(purchaseRepository, times(1)).findById(any());
    }

    @Test
    void findByIdWithoutOptionalWithError() {

        when(purchaseRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        assertThrows(NotObjectFound.class,() ->purchaseService.findByIdWithoutOptional(purchase.getId()));

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
        when(productRepository.saveAll(productList)).thenReturn(productList);
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
        List<Product> productList2 = productService.saveProducts(productList);
        assertEquals(purchase,purchase2);
        assertEquals(productList,productList2);
        verify(purchaseRepository, times(1)).save(purchase);
        verify(purchaseMapper, times(1)).purchaseDtoToPurchase(purchaseDto);
        verify(productRepository, times(1)).saveAll(productList);
    }

    @Test
    void savePurchaseWithError() {
        when(tripService.findById(purchaseDto.getTrip().getId())).thenReturn(Optional.ofNullable(null));
        assertThrows(NotObjectFound.class,() ->purchaseService.checkDtoBeforeSaving(purchaseDto));
        purchaseDto.setProducts(productDtoList);
        purchaseDto.setTrip(null);
        assertThrows(NotObjectFound.class,() ->purchaseService.checkDtoBeforeSaving(purchaseDto));
        List<ProductDto> productDtoList2 = new ArrayList<>();
        purchaseDto.setProducts(productDtoList2);
        assertThrows(NotObjectFound.class,() ->purchaseService.checkDtoBeforeSaving(purchaseDto));

    }

    @Test
    void replacePurchase() {
//        purchaseDto.setId(3L);
        when(purchaseRepository.save(purchase)).thenReturn(purchase);
        when(purchaseRepository.findById(1L)).thenReturn(Optional.ofNullable(purchase));
        when(purchaseRepository.findById(3L)).thenReturn(Optional.ofNullable(purchase));
        when(purchaseMapper.purchaseDtoToPurchase(purchaseDto)).thenReturn(purchase);
        when(tripService.findById(purchaseDto.getTrip().getId())).thenReturn(Optional.ofNullable(trip));
        when(tripService.findByIdWithoutOptional(purchaseDto.getTrip().getId())).thenReturn(trip);
        purchaseService.checkDtoBeforeUpdating(purchaseDto);
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
    void updatePurchaseTransactions() {
        when(purchaseRepository.findById(1L)).thenReturn(Optional.ofNullable(purchase));
        when(purchaseRepository.save(purchase)).thenReturn(purchase);
        when(creditCardTransactionMapper.dtoToCreditCardTransaction(creditCardTransactionDto)).thenReturn(creditCardTransaction);
        doNothing().when(productRepository).deleteProductByPurchase(purchase);

        purchaseService.updatePurchaseTransactions(creditCardTransactionDto);
        purchaseService.updatePurchase(purchase);

        productService.deleteAllProductsByPurchase(purchase);
        assertEquals("COMPLETE",purchase.getPurchaseStatus().name());
        verify(purchaseRepository, times(2)).save(purchase);
        verify(productRepository, times(1)).deleteProductByPurchase(purchase);
    }

    @Test
    void updatePurchaseTransactionsWithError() {
        when(purchaseRepository.findById(1L)).thenReturn(Optional.ofNullable(purchase));
        when(purchaseRepository.findById(2L)).thenReturn(Optional.ofNullable(null));
        when(tripService.findById(1L)).thenReturn(Optional.ofNullable(trip));
        when(tripService.findById(2L)).thenReturn(Optional.ofNullable(trip2));
        when(tripService.findById(3L)).thenReturn(Optional.ofNullable(null));
        CustomerPurchaseDto customerPurchaseDto2 = new CustomerPurchaseDto();
        customerPurchaseDto2.setId(2L);
        purchaseDto.setCustomer(customerPurchaseDto2);
        assertThrows(NotObjectFound.class,() ->purchaseService.checkDtoBeforeUpdating(purchaseDto));
        TripDto tripDto2 = new TripDto();
        tripDto2.setId(2L);
        purchaseDto.setTrip(tripDto2);
        assertThrows(NotObjectFound.class,() ->purchaseService.checkDtoBeforeUpdating(purchaseDto));

        TripDto tripDto3 = new TripDto();
        tripDto3.setId(3L);
        purchaseDto.setTrip(tripDto3);
        assertThrows(NotObjectFound.class,() ->purchaseService.checkDtoBeforeUpdating(purchaseDto));


        List<ProductDto> productDtoList2 = new ArrayList<>();
        purchaseDto.setProducts(productDtoList2);
        assertThrows(NotObjectFound.class,() ->purchaseService.checkDtoBeforeUpdating(purchaseDto));
        purchaseDto.setId(2L);
        assertThrows(NotObjectFound.class,() ->purchaseService.checkDtoBeforeUpdating(purchaseDto));
    }

    @Test
    void updateRefusedPurchaseTransactions() {
        purchase.setPurchaseStatus(PurchaseStatus.COMPLETE);
        CreditCardTransaction creditCardTransaction2 = new CreditCardTransaction();
        creditCardTransaction2.setId(1L);
        creditCardTransaction2.setTransactionStatus(CreditCardTransactionStatus.REFUND);
        creditCardTransaction2.setTotalePriceAmount(100D);
        when(purchaseRepository.findById(1L)).thenReturn(Optional.ofNullable(purchase));
        when(purchaseRepository.save(purchase)).thenReturn(purchase);
        when(creditCardTransactionMapper.dtoToCreditCardTransaction(creditCardTransactionDto)).thenReturn(creditCardTransaction2);
        purchaseService.updateRefusedPurchaseTransactions(creditCardTransactionDto);
        assertEquals("NOT_COMPLETE",purchase.getPurchaseStatus().name());
        verify(purchaseRepository, times(1)).save(purchase);

    }

    @Test
    void updateRefusedPurchaseTransactionsErrorRefund() {
        purchase.setPurchaseStatus(PurchaseStatus.COMPLETE);
        CreditCardTransaction creditCardTransaction2 = new CreditCardTransaction();
        creditCardTransaction2.setId(1L);
        creditCardTransaction2.setTransactionStatus(CreditCardTransactionStatus.REFUND);
        creditCardTransaction2.setTotalePriceAmount(300D);
        when(purchaseRepository.findById(1L)).thenReturn(Optional.ofNullable(purchase));
        when(creditCardTransactionMapper.dtoToCreditCardTransaction(creditCardTransactionDto)).thenReturn(creditCardTransaction2);
        assertThrows(IdAlreadyExists.class,() ->purchaseService.updateRefusedPurchaseTransactions(creditCardTransactionDto));
    }


    @Test
    void deletePurchase() {
        when(purchaseRepository.findById(1L)).thenReturn(Optional.ofNullable(purchase));
        doNothing().when(purchaseRepository).deleteById(1L);
        Optional<Purchase> purchaseOptional = purchaseService.findById(1L);
        if(purchaseOptional.isPresent() && !creditCardTransactionList.isEmpty()) {
            assertThrows(IdAlreadyExists.class,() ->purchaseService.errorDeletePurchaseWithTransaction());         }

        purchaseOptional.get().setCreditCardTransactions(null);
        purchaseOptional.get().setTrip(null);
        Optional<List<CreditCardTransaction>> creditCardTransactionList = Optional.ofNullable(purchaseOptional.get().getCreditCardTransactions());

        if(purchaseOptional.isPresent() && creditCardTransactionList.isEmpty()) {
            productService.deleteAllProductsByPurchase(purchase);
            purchaseService.deletePurchase(1L);
            verify(purchaseRepository, times(1)).deleteById(purchase.getId());
        }
    }

    @Test
    void updatePurchaseStatusNotComplete() {

       PurchaseStatus status = purchaseService.updatePurchaseStatus(purchase);
        assertEquals("NOT_COMPLETE",status.name());

    }
    @Test
    void updatePurchaseStatusComplete() {
        purchase.getCreditCardTransactions().add(creditCardTransaction);
        PurchaseStatus status = purchaseService.updatePurchaseStatus(purchase);
        assertEquals("COMPLETE",status.name());

    }



    @Test
    void dummyTest() {
        assertThrows(IdAlreadyExists.class,() ->purchaseService.errorDeletePurchaseWithTransaction());
    }
}