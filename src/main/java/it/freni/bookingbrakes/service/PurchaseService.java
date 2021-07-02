package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.creditcardtransaction.CreditCardTransactionDto;
import it.freni.bookingbrakes.controller.dto.purchase.ProductDto;
import it.freni.bookingbrakes.controller.dto.purchase.ProductSeatDto;
import it.freni.bookingbrakes.controller.dto.purchase.PurchaseDto;
import it.freni.bookingbrakes.domain.*;
import it.freni.bookingbrakes.error.IdAlreadyExists;
import it.freni.bookingbrakes.error.NotObjectFound;
import it.freni.bookingbrakes.mapper.CreditCardTransactionMapper;
import it.freni.bookingbrakes.mapper.PurchaseMapper;
import it.freni.bookingbrakes.repository.PurchaseRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;

@Service
@Log
public class PurchaseService {

     private static final String PRODUCTS_NOT_FOUND = "Products Not Found";
    private static final String TRIP_NOT_FOUND = "Trip Not Found";
    private static final String TRIP_MISMATCH = "Trip Mismatch";
    private static final String CUSTOMER_MISMATCH = "Customer Mismatch";

    public static final String SEAT_ALREADY_BOOKED = "Seat already Booked";

    public static final String SEAT_DUPLICATION = "Seats are duplicated";
    public static final String ERROR_REFUND ="The amount of refund is higher then amount of puchase";
    private static final String PURCHASE_NOT_FOUND = "Purchase is not found";
    public static final String PURCHASE_WITH_TRANSACTIONS = "You can't delete this purchase because it's got transactions";
    private final PurchaseRepository repository;
    private final PurchaseMapper purchaseMapper;
    private final CreditCardTransactionMapper creditCardTransactionMapper;
    private final TripService tripService;

    public PurchaseService(PurchaseRepository repository, PurchaseMapper purchaseMapper, CreditCardTransactionMapper creditCardTransactionMapper, TripService tripService) {
        this.repository = repository;
        this.purchaseMapper = purchaseMapper;
        this.creditCardTransactionMapper = creditCardTransactionMapper;
        this.tripService = tripService;
    }

    public Iterable<Purchase> findAll() {
        return repository.findAll();
    }

    public Optional<Purchase> findById(Long id) {
        return repository.findById(id);
    }
    public Purchase findByIdWithoutOptional(Long id) {
        Optional<Purchase> purchase = repository.findById(id);
        if(purchase.isPresent()){
            return purchase.get();
        }
        log.log(Level.SEVERE, PURCHASE_NOT_FOUND);
        throw new NotObjectFound(PURCHASE_NOT_FOUND);
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


    public void checkDtoBeforeSaving(PurchaseDto purchaseDto) {
        if (purchaseDto.getProducts().isEmpty()) {
            log.log(Level.SEVERE, PRODUCTS_NOT_FOUND);
            throw new NotObjectFound(PRODUCTS_NOT_FOUND);
        }


        if (purchaseDto.getTrip() == null || tripService.findById(purchaseDto.getTrip().getId()).isEmpty()) {
            log.log(Level.SEVERE, TRIP_NOT_FOUND);
            throw new NotObjectFound(TRIP_NOT_FOUND);
        }


        verifySeatAlreadyBooked(purchaseDto);


        verifySeatDuplication(purchaseDto);
    }

    private void verifySeatDuplication(PurchaseDto purchaseDto) {
        for (ProductDto productDto : purchaseDto.getProducts()) {
            int contEqual = 0;
            if (productDto instanceof ProductSeatDto) {
                checkSeatDuplication(purchaseDto, (ProductSeatDto) productDto, contEqual);
            }
        }
    }

    private void checkSeatDuplication(PurchaseDto purchaseDto, ProductSeatDto productDto, int contEqual) {
        for (ProductDto productDtoExamined : purchaseDto.getProducts()) {
            if (productDtoExamined instanceof ProductSeatDto
                    && (((ProductSeatDto) productDtoExamined).getNrSeat().equals(productDto.getNrSeat()))) {
                if (contEqual == 1) {
                    log.log(Level.SEVERE, SEAT_DUPLICATION);
                    throw new IdAlreadyExists(SEAT_DUPLICATION);
                }
                contEqual++;
            }
        }
    }

    private void verifySeatAlreadyBooked(PurchaseDto purchaseDto) {
        purchaseDto.getProducts().stream().filter(productDto -> productDto instanceof ProductSeatDto).forEach(productDto -> {
            for (Purchase purchase : tripService.findByIdWithoutOptional(purchaseDto.getTrip().getId()).getPurchases()) {
                for (Product product : purchase.getProducts()) {
                    if (product instanceof Seat && (((Seat) product).getNrSeat().equals(((ProductSeatDto) productDto).getNrSeat()))) {
                        log.log(Level.SEVERE, SEAT_ALREADY_BOOKED);
                        throw new IdAlreadyExists(SEAT_ALREADY_BOOKED);
                    }
                }
            }
        });
    }


    public Purchase checkDtoBeforeUpdating(PurchaseDto purchaseDto) {
            Optional<Purchase> purchaseDb = findById(purchaseDto.getId());
        if ( purchaseDto.getId()==null || purchaseDb.isEmpty()) {
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


        if (!purchaseDb.get().getTrip().getId().equals(purchaseDto.getTrip().getId())) {
            log.log(Level.SEVERE, TRIP_MISMATCH);
            throw new NotObjectFound(TRIP_MISMATCH);
        }


        if (!purchaseDb.get().getCustomer().getId().equals(purchaseDto.getCustomer().getId())) {
            log.log(Level.SEVERE, CUSTOMER_MISMATCH);
            throw new NotObjectFound(CUSTOMER_MISMATCH);
        }


        verifySeatBookedOnUpdate(purchaseDto);


        verifySeatDuplication(purchaseDto);

        return purchaseDb.get();
        }

    private void verifySeatBookedOnUpdate(PurchaseDto purchaseDto) {
        purchaseDto.getProducts().stream().filter(productDto -> productDto instanceof ProductSeatDto).forEach(productDto -> {
            for (Purchase purchase : tripService.findByIdWithoutOptional(purchaseDto.getTrip().getId()).getPurchases()) {
                for (Product product : purchase.getProducts()) {
                    if (product instanceof Seat && (((Seat) product).getNrSeat()
                            .equals(((ProductSeatDto) productDto).getNrSeat()))
                            && (!purchaseDto.getId().equals(purchase.getId()))) {
                        log.log(Level.SEVERE, SEAT_ALREADY_BOOKED);
                        throw new IdAlreadyExists(SEAT_ALREADY_BOOKED);
                    }
                }
            }
        });
    }


    public void updatePurchaseTransactions(CreditCardTransactionDto creditCardTransactionDto) {
        Purchase purchase = findByIdWithoutOptional(creditCardTransactionDto.getPurchase().getId());
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
        Purchase purchase = findByIdWithoutOptional(creditCardTransactionDto.getPurchase().getId());
        purchase.getCreditCardTransactions().add(creditCardTransactionMapper.dtoToCreditCardTransaction(creditCardTransactionDto));
        double totalPaid = 0;
        for (CreditCardTransaction creditCardTransactionAmount : purchase.getCreditCardTransactions()) {
            if (creditCardTransactionAmount.getTransactionStatus().equals(CreditCardTransactionStatus.PAID)) {
                totalPaid += creditCardTransactionAmount.getTotalePriceAmount();
            }
        }
        double totalRefund = 0;
        for (CreditCardTransaction creditCardTransactionAmount : purchase.getCreditCardTransactions()) {
            if (creditCardTransactionAmount.getTransactionStatus().equals(CreditCardTransactionStatus.REFUND)) {
                totalRefund += creditCardTransactionAmount.getTotalePriceAmount();
            }
        }
        double totalPurchase = 0;
        for (Product product : purchase.getProducts()) {
            totalPurchase += product.getPriceAmount();
        }

        if (totalPaid < totalRefund) {
            log.log(Level.SEVERE, ERROR_REFUND);
            throw new IdAlreadyExists(ERROR_REFUND);
        }
        if (totalPurchase > (totalPaid - totalRefund)) {

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

            log.log(Level.SEVERE, PURCHASE_WITH_TRANSACTIONS);
            throw new IdAlreadyExists(PURCHASE_WITH_TRANSACTIONS);

    }

}