package it.freni.bookingbrakes.controller;

import it.freni.bookingbrakes.controller.dto.purchase.PurchaseDto;
import it.freni.bookingbrakes.domain.Product;
import it.freni.bookingbrakes.domain.Purchase;
import it.freni.bookingbrakes.domain.PurchaseStatus;
import it.freni.bookingbrakes.mapper.ProductMapper;
import it.freni.bookingbrakes.mapper.PurchaseMapper;
import it.freni.bookingbrakes.service.*;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/purchases")
@Log
@Transactional
public class PurchaseController {

    public final PurchaseService purchaseService;
    public final PurchaseMapper purchaseMapper;
    public final ProductMapper productMapper;
    public final ProductService productService;
    public final CreditCardTransactionService creditCardTransactionService;
    public final CustomerService customerService;
    public final TripService tripService;

    public PurchaseController(PurchaseService purchaseService, PurchaseMapper purchaseMapper, ProductMapper productMapper, ProductService productService, CreditCardTransactionService creditCardTransactionService, CustomerService customerService, TripService tripService) {
        this.purchaseService = purchaseService;
        this.purchaseMapper = purchaseMapper;
        this.productMapper = productMapper;
        this.productService = productService;
        this.creditCardTransactionService = creditCardTransactionService;
        this.customerService = customerService;
        this.tripService = tripService;
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


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<PurchaseDto> postPurchase(@RequestBody PurchaseDto purchaseDto) {
        purchaseService.checkDtoBeforeSaving(purchaseDto);
        purchaseDto.setPurchaseStatus(PurchaseStatus.NOT_COMPLETE);
        Purchase purchase =purchaseService.savePurchase(purchaseDto);
        List<Product> products = productMapper.dtosToProducts(purchaseDto.getProducts());
        for (Product product : products) {
            product.setId(null);
            product.setPurchase(purchase);
        }

        purchase.setProducts(productService.saveProducts(products));
        purchase.setCustomer(customerService.findByIdWithoutOptional(purchase.getCustomer().getId()));
        purchase.setTrip(tripService.findByIdWithoutOptional(purchase.getTrip().getId()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchaseMapper.toDto(purchase));
    }


    @PutMapping("/{id}")
    public ResponseEntity<PurchaseDto> putPurchase(@PathVariable("id") Long id, @RequestBody PurchaseDto purchaseDto) {

        purchaseDto.setId(id);
        Purchase purchaseDb = purchaseService.checkDtoBeforeUpdating(purchaseDto);
        List<Product> products = productMapper.dtosToProducts(purchaseDto.getProducts());


        Purchase purchase=purchaseMapper.purchaseDtoToPurchase(purchaseDto);

        for (Product product : products) {
            product.setId(null);
            product.setPurchase(purchase);
        }
        
        productService.deleteAllProductsByPurchase(purchaseDb);
        purchase.setProducts(productService.saveProducts(products));
        purchase.setPurchaseStatus(purchaseService.updatePurchaseStatus(purchaseDb));
        purchase.setDatePurchase(new Date(System.currentTimeMillis()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(purchaseMapper.toDto(purchaseService.updatePurchase(purchase)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePurchase(@PathVariable("id") Long id){
        Optional<Purchase> purchase = purchaseService.findById(id);
        if(purchase.isPresent() && purchase.get().getCreditCardTransactions().isEmpty()) {
            productService.deleteAllProductsByPurchase(purchaseService.findByIdWithoutOptional(id));
            purchaseService.deletePurchase(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        if(purchase.isPresent() && !purchase.get().getCreditCardTransactions().isEmpty()) {
            purchaseService.errorDeletePurchaseWithTransaction();
        }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
