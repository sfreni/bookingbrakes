package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.purchase.PurchaseCreditCardTransactionDto;
import it.freni.bookingbrakes.controller.dto.purchase.PurchaseDto;
import it.freni.bookingbrakes.domain.CreditCardTransaction;
import it.freni.bookingbrakes.domain.Product;
import it.freni.bookingbrakes.domain.Purchase;
import it.freni.bookingbrakes.domain.PurchaseStatus;
import it.freni.bookingbrakes.mapper.*;
import it.freni.bookingbrakes.service.*;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/purchases")
@Log
@Transactional
public class PurchaseController {
    public final SeatService seatService;
    public final AdditionalServiceService customerService;
    public final PurchaseService purchaseService;
    public final SeatMapper seatMapper;
    public final AdditionalServiceService additionalServiceMapper;
    public final PurchaseMapper purchaseMapper;
    public final ProductMapper productMapper;
    public final BookingService bookingService;
    public final ProductService productService;
    public final CreditCardTransactionService creditCardTransactionService;
    public final CreditCardTransactionMapper creditCardTransactionMapper;

    public PurchaseController(SeatService seatService, AdditionalServiceService customerService, PurchaseService purchaseService, SeatMapper seatMapper, AdditionalServiceService additionalServiceMapper, PurchaseMapper purchaseMapper, ProductMapper productMapper, BookingService bookingService, ProductService productService, CreditCardTransactionService creditCardTransactionService, CreditCardTransactionMapper creditCardTransactionMapper) {
        this.seatService = seatService;
        this.customerService = customerService;
        this.purchaseService = purchaseService;
        this.seatMapper = seatMapper;
        this.additionalServiceMapper = additionalServiceMapper;
        this.purchaseMapper = purchaseMapper;
        this.productMapper = productMapper;
        this.bookingService = bookingService;
        this.productService = productService;
        this.creditCardTransactionService = creditCardTransactionService;
        this.creditCardTransactionMapper = creditCardTransactionMapper;
    }

    @GetMapping
    public ResponseEntity<Iterable<PurchaseDto>> getBooking(){

        Iterable<PurchaseDto> purchaseDtos = purchaseMapper.toDtos(purchaseService.findAll());
        return ResponseEntity.status(HttpStatus.OK)
                .body(purchaseDtos);

    }


    @GetMapping("/{id}")
    public ResponseEntity<PurchaseDto> getBooking(@PathVariable("id") Long id){

    Optional<Purchase> purchase = purchaseService.findById(id);

        if(purchase.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(purchaseMapper.toDto(purchase.get()));
        }


        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<PurchaseDto> postPurchase(@RequestBody PurchaseDto purchaseDto) {
        purchaseService.checkDtoBeforeSaving(purchaseDto);
        productService.checkDtoBeforeSaving(purchaseDto);
        purchaseDto.setPurchaseStatus(PurchaseStatus.NOT_COMPLETE);
        Purchase purchase =purchaseService.savePurchase(purchaseDto);
        List<Product> products = productMapper.DtosToProducts(purchaseDto.getProducts());
        for (Product product : products) {
            product.setPurchase(purchase);
        }

        purchase.setBooking(bookingService.findById(purchase.getBooking().getId()).get());
        purchase.setProducts(productService.saveProducts(products));

/*        List<CreditCardTransaction> creditCardTransactionList  = creditCardTransactionMapper.purchaseCreditCardDtoToCreditCardTransactionList(purchaseDto.getCreditCardTransactions());
        for (CreditCardTransaction creditCardTransaction : creditCardTransactionList) {
            creditCardTransaction.setPurchase(purchase);
        }

        creditCardTransactionService.checkDtoBeforeSaving(creditCardTransactionList);
        purchase.setCreditCardTransactions(creditCardTransactionService.saveCreditCardTransactionList(creditCardTransactionList));
      */  return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchaseMapper.toDto(purchase));
    }
//
//
//    @PutMapping("/{id}")
//    public ResponseEntity<BookingDtoOut> putBooking(@PathVariable("id") Long Id, @RequestBody  BookingDtoIn bookingDtoIn) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(bookingService.replaceBooking(Id, bookingDtoIn));
//    }
//
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(code=HttpStatus.NO_CONTENT)
//    public void deleteSeat(@PathVariable("id") Long id){
//        bookingService.deleteBookingById(id);
//    }
}
