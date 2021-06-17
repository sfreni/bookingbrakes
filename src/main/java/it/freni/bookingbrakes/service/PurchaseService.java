package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.CreditCardTransactionDto;
import it.freni.bookingbrakes.controller.dto.purchase.ProductDto;
import it.freni.bookingbrakes.controller.dto.purchase.ProductSeatDto;
import it.freni.bookingbrakes.controller.dto.purchase.PurchaseDto;
import it.freni.bookingbrakes.domain.*;
import it.freni.bookingbrakes.error.IdAlreadyExists;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.CreditCardTransactionMapper;
import it.freni.bookingbrakes.mapper.ProductMapper;
import it.freni.bookingbrakes.mapper.PurchaseMapper;
import it.freni.bookingbrakes.repository.PurchaseRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Service
@Log
public class PurchaseService {

    public static final String OBJECT_NOT_FOUND = "Object not found";
    public static final String ID_ALREADY_EXISTS = "Id already exists";
    private static final String PRODUCTS_NOT_FOUND = "Products Not Found";
    private static final String CREDITCARD_TRANSACTIONS_NOT_FOUND = "Credit Card Transaction not found";
    private static final String PURCHASE_STATUS_NOT_FOUND = "Purchase status not found";
    private static final String BOOKING_NOT_FOUND = "Booking Not Found";
    public static final String SEAT_ALREADY_BOOKED = "Seat already Booked";
    public static final String SEAT_DUPLICATION = "Seats are duplicated";
    public static final String ERROR_REFUND ="The amount of refund is higher then amount of puchase";
    private static final String TRANSACTION_PRICE_AMOUNT_ERROR ="Payment is lower then total purchase" ;

    private final PurchaseRepository repository;
    private final PurchaseMapper purchaseMapper;
    private final ProductMapper productMapper;
    private final CreditCardTransactionMapper creditCardTransactionMapper;
    private final ProductService productService;
    private final BookingService bookingService;

    public PurchaseService(PurchaseRepository repository, PurchaseMapper purchaseMapper, ProductMapper productMapper, CreditCardTransactionMapper creditCardTransactionMapper, ProductService productService, BookingService bookingService) {
        this.repository = repository;
        this.purchaseMapper = purchaseMapper;
        this.productMapper = productMapper;
        this.creditCardTransactionMapper = creditCardTransactionMapper;
        this.productService = productService;
        this.bookingService = bookingService;
    }

    public Iterable<Purchase> findAll() {
        return repository.findAll();
    }

    public Optional<Purchase> findById(Long id) {
        return repository.findById(id);
    }

    public Purchase savePurchase(PurchaseDto purchaseDto) {

        purchaseDto.setId(null);
        purchaseDto.setDatePurchase(new Date(System.currentTimeMillis()));

        return repository.save(purchaseMapper.purchaseDtoToPurchase(purchaseDto));
    }

    public PurchaseDto checkDtoBeforeSaving(PurchaseDto purchaseDto) {
        if (purchaseDto.getProducts().isEmpty()) {
            log.log(Level.SEVERE, PRODUCTS_NOT_FOUND);
            throw new NotObjectFound(PRODUCTS_NOT_FOUND);
        }

        if (purchaseDto.getCreditCardTransactions().isEmpty()) {
            log.log(Level.SEVERE, CREDITCARD_TRANSACTIONS_NOT_FOUND);
            throw new NotObjectFound(CREDITCARD_TRANSACTIONS_NOT_FOUND);
        }

      /*  if (purchaseDto.getPurchaseStatus() == null) {
            log.log(Level.SEVERE, PURCHASE_STATUS_NOT_FOUND);
            throw new NotObjectFound(PURCHASE_STATUS_NOT_FOUND);
        }*/

        if (purchaseDto.getBooking() == null || bookingService.findById(purchaseDto.getBooking().getId()).isEmpty()) {
            log.log(Level.SEVERE, BOOKING_NOT_FOUND);
            throw new NotObjectFound(BOOKING_NOT_FOUND);
        }

/*
        double totalPurchase =0;
        for (ProductDto productDto : purchaseDto.getProducts()) {
            totalPurchase+=productDto.getPriceAmount();
        }
        double totalTransactions =0;
        for (PurchaseCreditCardTransactionDto creditCardTransactionDto  : purchaseDto.getCreditCardTransactions()) {
            if(creditCardTransactionDto.getTransactionStatus().equals(CreditCardTransactionStatus.PAID)){
                totalTransactions+=creditCardTransactionDto.getTotalePriceAmount();
        }
        }
        if(totalPurchase>totalTransactions){
            log.log(Level.SEVERE, TRANSACTION_PRICE_AMOUNT_ERROR);
            throw new IdAlreadyExists(TRANSACTION_PRICE_AMOUNT_ERROR);
        }
        if(totalPurchase<=totalTransactions){
            purchaseDto.setPurchaseStatus(PurchaseStatus.COMPLETE);
        }
*/

        List<Booking> bookings = bookingService.findByBooking(bookingService.findById(purchaseDto.getBooking().getId()).get().getTrip());

        for (ProductDto productDto : purchaseDto.getProducts()) {
            if (productDto instanceof ProductSeatDto) {
                for (Booking booking : bookings) {
                    for (Purchase purchase : booking.getPurchases()) {
                        for (Product product : purchase.getProducts()) {
                            if (product instanceof Seat) {
                                if (((Seat) product).getNrSeat().equals(((ProductSeatDto) productDto).getNrSeat())) {
                                    log.log(Level.SEVERE, SEAT_ALREADY_BOOKED);
                                    throw new IdAlreadyExists(SEAT_ALREADY_BOOKED);
                                }
                            }
                        }
                    }

                }
            }
        }

        for (ProductDto productDto : purchaseDto.getProducts()) {
            int contEqual =0;
            if (productDto instanceof ProductSeatDto) {
                for (ProductDto productDtoExamined : purchaseDto.getProducts()) {
                    if (productDtoExamined instanceof ProductSeatDto) {
                                if ((((ProductSeatDto) productDtoExamined).getNrSeat().equals(((ProductSeatDto) productDto).getNrSeat()))) {
                                    if(contEqual==1){
                                    log.log(Level.SEVERE, SEAT_DUPLICATION);
                                    throw new IdAlreadyExists(SEAT_DUPLICATION);
                                }
                                    contEqual++;
                                }
                        }
                    }
                }
            }
        return purchaseDto;
    }



    public void updatePurchaseTransactions(CreditCardTransactionDto creditCardTransactionDto) {
        Purchase purchase = repository.findById(creditCardTransactionDto.getPurchase().getId()).get();
       purchase.getCreditCardTransactions().add(creditCardTransactionMapper.dtoToCreditCardTransaction(creditCardTransactionDto));
        double totalPurchase = 0;
        for (Product product : purchase.getProducts()) {
            totalPurchase += product.getPriceAmount();
        }
        double totalTransactions = 0;
        for (CreditCardTransaction creditCardTransactionAmount : purchase.getCreditCardTransactions()) {
            if (creditCardTransactionAmount.getTransactionStatus().equals(CreditCardTransactionStatus.PAID)) {
                totalTransactions += creditCardTransactionAmount.getTotalePriceAmount();
            }
        }

        if (totalPurchase <= totalTransactions) {
            purchase.setPurchaseStatus(PurchaseStatus.COMPLETE);
            repository.save(purchase);
        }

    }


    public void updateRefusedPurchaseTransactions(CreditCardTransactionDto creditCardTransactionDto) {
        Purchase purchase = repository.findById(creditCardTransactionDto.getPurchase().getId()).get();
        purchase.getCreditCardTransactions().add(creditCardTransactionMapper.dtoToCreditCardTransaction(creditCardTransactionDto));
        double totalPurchase = 0;
        for (Product product : purchase.getProducts()) {
            totalPurchase += product.getPriceAmount();
        }
        double totalTransactions = 0;
        for (CreditCardTransaction creditCardTransactionAmount : purchase.getCreditCardTransactions()) {
            if (creditCardTransactionAmount.getTransactionStatus().equals(CreditCardTransactionStatus.REFUND)) {
                totalTransactions += creditCardTransactionAmount.getTotalePriceAmount();
            }
        }
        System.out.println(totalPurchase + " "+ totalTransactions);
        if (totalPurchase < totalTransactions) {
            log.log(Level.SEVERE, ERROR_REFUND);
            throw new IdAlreadyExists(ERROR_REFUND);
        }

        if (totalPurchase == totalTransactions) {
            purchase.setPurchaseStatus(PurchaseStatus.REFUND);
            repository.save(purchase);
        }



    }


}