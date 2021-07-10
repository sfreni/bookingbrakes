package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.creditcardtransaction.PurchaseTransactionsDto;
import it.freni.bookingbrakes.controller.dto.customer.PurchaseCustomerDto;
import it.freni.bookingbrakes.controller.dto.purchase.*;
import it.freni.bookingbrakes.controller.dto.trip.PurchaseWithoutTripDto;
import it.freni.bookingbrakes.domain.*;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PurchaseMapper {

    public PurchaseDto toDto(Purchase purchase){
        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setId(purchase.getId());
        purchaseDto.setPurchaseStatus(purchase.getPurchaseStatus());
        purchaseDto.setDatePurchase(purchase.getDatePurchase());
        purchaseDto.setCreditCardTransactions(toDtoCreditCardTransaction(purchase.getCreditCardTransactions()));
        purchaseDto.setTrip(tripDto(purchase.getTrip()));
        purchaseDto.setProducts(getProductDtos(purchase));
        purchaseDto.setCustomer(customerPurchaseToDto(purchase.getCustomer()));
        return purchaseDto;
    }



    public PurchaseTransactionsDto toPurchaseTransactionsDto(Purchase purchase){
        PurchaseTransactionsDto purchaseDto = new PurchaseTransactionsDto();
        purchaseDto.setId(purchase.getId());
        purchaseDto.setPurchaseStatus(purchase.getPurchaseStatus());
        purchaseDto.setDatePurchase(purchase.getDatePurchase());
        purchaseDto.setProducts(getProductDtos(purchase));
        return purchaseDto;
    }

    public PurchaseWithoutTripDto purchaseWithoutTripDto(Purchase purchase){
        PurchaseWithoutTripDto purchaseDto = new PurchaseWithoutTripDto();
        purchaseDto.setId(purchase.getId());
        purchaseDto.setPurchaseStatus(purchase.getPurchaseStatus());
        purchaseDto.setDatePurchase(purchase.getDatePurchase());
        purchaseDto.setCreditCardTransactions(toDtoCreditCardTransaction(purchase.getCreditCardTransactions()));
        purchaseDto.setProducts(getProductDtos(purchase));
        purchaseDto.setCustomer(customerPurchaseToDto(purchase.getCustomer()));
        return purchaseDto;
    }

    public PurchaseCustomerDto purchaseCustomerDto(Purchase purchase){
        PurchaseCustomerDto purchaseDto = new PurchaseCustomerDto();
        purchaseDto.setId(purchase.getId());
        purchaseDto.setPurchaseStatus(purchase.getPurchaseStatus());
        purchaseDto.setDatePurchase(purchase.getDatePurchase());
        purchaseDto.setProducts(getProductDtos(purchase));
        purchaseDto.setTrip(tripDto(purchase.getTrip()));
        return purchaseDto;
    }




    private List<ProductDto> getProductDtos(Purchase purchase) {
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product: purchase.getProducts()) {
            if (product instanceof Seat) {
                ProductSeatDto productSeatDto = new ProductSeatDto();
                productSeatDto.setId(product.getId());
                productSeatDto.setNrSeat(((Seat) product).getNrSeat());
                productSeatDto.setPriceAmount(product.getPriceAmount());
                productSeatDto.setFirstNamePassenger(((Seat) product).getFirstNamePassenger());
                productSeatDto.setLastNamePassenger(((Seat) product).getLastNamePassenger());
                productSeatDto.setDateOfBirth(((Seat) product).getDateOfBirth());
                productDtos.add(productSeatDto);
            }
            if (product instanceof AdditionalService) {

                ProductAdditionalServiceDto productAdditionalServiceDto = new ProductAdditionalServiceDto();
                productAdditionalServiceDto.setId(product.getId());
                productAdditionalServiceDto.setPriceAmount(product.getPriceAmount());
                productAdditionalServiceDto.setAdditionalServiceType(((AdditionalService) product).getAdditionalServiceType());
                productDtos.add(productAdditionalServiceDto);
            }


        }
        return productDtos;
    }

    public Purchase purchaseDtoToPurchase(PurchaseDto purchaseDto){

        Purchase purchase = new Purchase();
        purchase.setId(purchaseDto.getId());
        purchase.setPurchaseStatus(purchaseDto.getPurchaseStatus());
        purchase.setDatePurchase(purchaseDto.getDatePurchase());
        purchase.setCreditCardTransactions(creditCardTransactionToDto(purchaseDto.getCreditCardTransactions()));
        purchase.setTrip(dtoToTrip(purchaseDto.getTrip()));
        List<Product> products = getProductsFromDtos(purchaseDto.getProducts());
        purchase.setProducts(products);
        purchase.setCustomer(customerPurchaseDtoToCustomer(purchaseDto.getCustomer()));

        return purchase;

    }

    public Purchase purchaseWithoutTripDtoToPurchase(PurchaseWithoutTripDto purchaseDto){

        Purchase purchase = new Purchase();
        purchase.setId(purchaseDto.getId());
        purchase.setPurchaseStatus(purchaseDto.getPurchaseStatus());
        purchase.setDatePurchase(purchaseDto.getDatePurchase());
        purchase.setCreditCardTransactions(creditCardTransactionToDto(purchaseDto.getCreditCardTransactions()));

        List<Product> products = getProductsFromDtos(purchaseDto.getProducts());
        purchase.setProducts(products);
        purchase.setCustomer(customerPurchaseDtoToCustomer(purchaseDto.getCustomer()));

        return purchase;

    }


    public Purchase purchaseTransactionsDtoToPurchase(PurchaseTransactionsDto purchaseDto){

        Purchase purchase = new Purchase();
        purchase.setId(purchaseDto.getId());
        purchase.setPurchaseStatus(purchaseDto.getPurchaseStatus());
        purchase.setDatePurchase(purchaseDto.getDatePurchase());
        List<Product> products = getProductsFromDtos(purchaseDto.getProducts());
        purchase.setProducts(products);
        return purchase;

    }
    public Purchase purchaseCustomerDtoToPurchaseCustomer(PurchaseCustomerDto purchaseDto){

        Purchase purchase = new Purchase();
        purchase.setId(purchaseDto.getId());
        purchase.setPurchaseStatus(purchaseDto.getPurchaseStatus());
        purchase.setDatePurchase(purchaseDto.getDatePurchase());
        List<Product> products = getProductsFromDtos(purchaseDto.getProducts());
        purchase.setProducts(products);
        purchase.setTrip(dtoToTrip(purchaseDto.getTrip()));
        return purchase;

    }


    private List<Product> getProductsFromDtos(List<ProductDto> productDtos) {
        List<Product> products = new ArrayList<>();
        for(ProductDto productDto: productDtos) {
            if (productDto instanceof ProductSeatDto) {
                Seat seat = new Seat();
                seat.setId(productDto.getId());
                seat.setNrSeat(((ProductSeatDto) productDto).getNrSeat());
                seat.setPriceAmount(productDto.getPriceAmount());
                seat.setFirstNamePassenger(((ProductSeatDto) productDto).getFirstNamePassenger());
                seat.setLastNamePassenger(((ProductSeatDto) productDto).getLastNamePassenger());
                seat.setDateOfBirth(((ProductSeatDto) productDto).getDateOfBirth());
                products.add(seat);
            }
            if (productDto instanceof ProductAdditionalServiceDto) {

                AdditionalService additionalService = new AdditionalService();
                additionalService.setId(productDto.getId());
                additionalService.setPriceAmount(productDto.getPriceAmount());
                additionalService.setAdditionalServiceType(((ProductAdditionalServiceDto) productDto).getAdditionalServiceType());
                products.add(additionalService);
            }


        }
        return products;
    }

    public abstract Iterable<PurchaseDto> toDtos(Iterable<Purchase> purchases);
    public abstract List<PurchaseCreditCardTransactionDto> toDtoCreditCardTransaction(List<CreditCardTransaction> creditCardTransaction);
    public abstract List<CreditCardTransaction> creditCardTransactionToDto(List<PurchaseCreditCardTransactionDto> creditCardTransactionDtos);
    public abstract Trip dtoToTrip(TripDto tripDto);
    public abstract TripDto tripDto(Trip trip);
    public abstract CustomerPurchaseDto customerPurchaseToDto(Customer customer);
    public abstract Customer customerPurchaseDtoToCustomer(CustomerPurchaseDto customerPurchaseDto);
    public abstract List<PurchaseDto> toDtosList(List<Purchase> purchases);
    public abstract List<Purchase> dtosListToPurchases(List<PurchaseDto> purchases);

}
