package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.creditcardtransaction.CreditCardTransactionDto;
import it.freni.bookingbrakes.controller.dto.purchase.ProductDto;
import it.freni.bookingbrakes.controller.dto.purchase.ProductSeatDto;
import it.freni.bookingbrakes.controller.dto.purchase.PurchaseDto;
import it.freni.bookingbrakes.domain.*;
import it.freni.bookingbrakes.error.IdAlreadyExists;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.CreditCardTransactionMapper;
import it.freni.bookingbrakes.mapper.ProductMapper;
import it.freni.bookingbrakes.mapper.PurchaseMapper;
import it.freni.bookingbrakes.mapper.TripMapper;
import it.freni.bookingbrakes.repository.PurchaseRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    private static final String TRIP_NOT_FOUND = "Trip Not Found";
    private static final String TRIP_MISMATCH = "Trip Mismatch";
    private static final String CUSTOMER_MISMATCH = "Customer Mismatch";

    public static final String SEAT_ALREADY_BOOKED = "Seat already Booked";

    public static final String SEAT_DUPLICATION = "Seats are duplicated";
    public static final String ERROR_REFUND ="The amount of refund is higher then amount of puchase";
    private static final String TRANSACTION_PRICE_AMOUNT_ERROR ="Payment is lower then total purchase" ;
    private static final String PURCHASE_NOT_FOUND = "Purchase is not found";
    public static final String PURCHASE_WITH_TRANSACTIONS = "You can't delete this purchase because it's got transactions";
    private final PurchaseRepository repository;
    private final PurchaseMapper purchaseMapper;
    private final ProductMapper productMapper;
    private final CreditCardTransactionMapper creditCardTransactionMapper;
    private final ProductService productService;
    private final TripService tripService;
    private final TripMapper tripMapper;

    public PurchaseService(PurchaseRepository repository, PurchaseMapper purchaseMapper, ProductMapper productMapper, CreditCardTransactionMapper creditCardTransactionMapper, ProductService productService, TripService tripService, TripMapper tripMapper) {
        this.repository = repository;
        this.purchaseMapper = purchaseMapper;
        this.productMapper = productMapper;
        this.creditCardTransactionMapper = creditCardTransactionMapper;
        this.productService = productService;
        this.tripService = tripService;
        this.tripMapper = tripMapper;
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
    public Purchase updatePurchase(Purchase purchase) {
        return repository.save(purchase);
    }

    public void deletePurchase(Long id) {
         repository.deleteById(id);
    }


    public PurchaseDto checkDtoBeforeSaving(PurchaseDto purchaseDto) {
        if (purchaseDto.getProducts().isEmpty()) {
            log.log(Level.SEVERE, PRODUCTS_NOT_FOUND);
            throw new NotObjectFound(PRODUCTS_NOT_FOUND);
        }


        if (purchaseDto.getTrip() == null || tripService.findById(purchaseDto.getTrip().getId()).isEmpty()) {
            log.log(Level.SEVERE, TRIP_NOT_FOUND);
            throw new NotObjectFound(TRIP_NOT_FOUND);
        }


        for (ProductDto productDto : purchaseDto.getProducts()) {
            if (productDto instanceof ProductSeatDto) {
                    for (Purchase purchase : tripService.findById(purchaseDto.getTrip().getId()).get().getPurchases()) {
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


    public PurchaseDto checkDtoBeforeUpdating(PurchaseDto purchaseDto) {

        if ( purchaseDto.getId()==null || findById(purchaseDto.getId()).isEmpty()) {
            log.log(Level.SEVERE, PURCHASE_NOT_FOUND);
            throw new NotObjectFound( PURCHASE_NOT_FOUND);
        }





        if (purchaseDto.getProducts().isEmpty()) {
            log.log(Level.SEVERE, PRODUCTS_NOT_FOUND);
            throw new NotObjectFound(PRODUCTS_NOT_FOUND);
        }


        if (purchaseDto.getTrip() == null
                || tripService.findById(purchaseDto.getTrip().getId()).isEmpty()) {
            log.log(Level.SEVERE, TRIP_NOT_FOUND);
            throw new NotObjectFound(TRIP_NOT_FOUND);
        }


        if (repository.findById(purchaseDto.getId()).get().getTrip().getId()
                !=purchaseDto.getTrip().getId()) {
            log.log(Level.SEVERE, TRIP_MISMATCH);
            throw new NotObjectFound(TRIP_MISMATCH);
        }


        if (repository.findById(purchaseDto.getId()).get().getCustomer().getId()
                !=purchaseDto.getCustomer().getId()) {
            log.log(Level.SEVERE, CUSTOMER_MISMATCH);
            throw new NotObjectFound(CUSTOMER_MISMATCH);
        }




        for (ProductDto productDto : purchaseDto.getProducts()) {
            if (productDto instanceof ProductSeatDto) {
                for (Purchase purchase : tripService.findById(purchaseDto.getTrip().getId()).get().getPurchases()) {
                    for (Product product : purchase.getProducts()) {
                            if (product instanceof Seat) {
                                if (((Seat) product).getNrSeat().equals(((ProductSeatDto) productDto).getNrSeat())) {
                                    if(purchaseDto.getId()!=purchase.getId()) {
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
            creditCardTransactionDto.getPurchase().setPurchaseStatus(purchase.getPurchaseStatus());
            repository.save(purchase);
        }
    }


    public void updateRefusedPurchaseTransactions(CreditCardTransactionDto creditCardTransactionDto) {
        Purchase purchase = repository.findById(creditCardTransactionDto.getPurchase().getId()).get();
        purchase.getCreditCardTransactions().add(creditCardTransactionMapper.dtoToCreditCardTransaction(creditCardTransactionDto));
        double totalPaid = 0;
        for (CreditCardTransaction creditCardTransactionAmount : purchase.getCreditCardTransactions()) {
            if (creditCardTransactionAmount.getTransactionStatus().equals(CreditCardTransactionStatus.PAID)) {
                totalPaid += creditCardTransactionAmount.getTotalePriceAmount();
            }
        }
        double totalRefused = 0;
        for (CreditCardTransaction creditCardTransactionAmount : purchase.getCreditCardTransactions()) {
            if (creditCardTransactionAmount.getTransactionStatus().equals(CreditCardTransactionStatus.REFUND)) {
                totalRefused += creditCardTransactionAmount.getTotalePriceAmount();
            }
        }
        double totalPurchase = 0;
        for (Product product : purchase.getProducts()) {
            totalPurchase += product.getPriceAmount();
        }

        if (totalPaid < totalRefused) {
            log.log(Level.SEVERE, ERROR_REFUND);
            throw new IdAlreadyExists(ERROR_REFUND);
        }
        if (totalPurchase > (totalPaid - totalRefused)) {

            purchase.setPurchaseStatus(PurchaseStatus.NOT_COMPLETE);
            creditCardTransactionDto.getPurchase().setPurchaseStatus(purchase.getPurchaseStatus());
            repository.save(purchase);
        }


    }

    public PurchaseStatus updatePurchaseStatus(Purchase purchase) {
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
            return PurchaseStatus.COMPLETE;
        }
        return PurchaseStatus.NOT_COMPLETE;

    }

    public void errorDeletePurchaseWithTransaction() {
        {
            log.log(Level.SEVERE, PURCHASE_WITH_TRANSACTIONS);
            throw new IdAlreadyExists(PURCHASE_WITH_TRANSACTIONS);
        }
    }

}