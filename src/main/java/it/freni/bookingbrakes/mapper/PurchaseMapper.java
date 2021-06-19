package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.CreditCardTransaction.PurchaseTransactionsDto;
import it.freni.bookingbrakes.controller.dto.purchase.*;
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
        purchaseDto.setBooking(BookingtoBookingDto(purchase.getBooking()));
        purchaseDto.setProducts(getProductDtos(purchase));
        return purchaseDto;
    }

    public PurchaseTransactionsDto toPurchaseTransactionsDto(Purchase purchase){
        PurchaseTransactionsDto purchaseDto = new PurchaseTransactionsDto();
        purchaseDto.setId(purchase.getId());
        purchaseDto.setPurchaseStatus(purchase.getPurchaseStatus());
        purchaseDto.setDatePurchase(purchase.getDatePurchase());
        purchaseDto.setBooking(BookingtoBookingDto(purchase.getBooking()));
        purchaseDto.setProducts(getProductDtos(purchase));
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
        purchase.setCreditCardTransactions(CreditCardTransactionToDto(purchaseDto.getCreditCardTransactions()));
        purchase.setBooking(dtoToBooking(purchaseDto.getBooking()));
        List<Product> products = getProductsFromDtos(purchaseDto.getProducts());
        purchase.setProducts(products);
        return purchase;

    }

    public Purchase purchaseTransactionsDtoToPurchase(PurchaseTransactionsDto purchaseDto){

        Purchase purchase = new Purchase();
        purchase.setId(purchaseDto.getId());
        purchase.setPurchaseStatus(purchaseDto.getPurchaseStatus());
        purchase.setDatePurchase(purchaseDto.getDatePurchase());
        purchase.setBooking(dtoToBooking(purchaseDto.getBooking()));
        List<Product> products = getProductsFromDtos(purchaseDto.getProducts());
        purchase.setProducts(products);
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
    public abstract List<CreditCardTransaction> CreditCardTransactionToDto(List<PurchaseCreditCardTransactionDto> creditCardTransactionDtos);
    public abstract Booking dtoToBooking(BookingDto bookingDto);
    public abstract BookingDto BookingtoBookingDto(Booking booking);
    public abstract Trip dtoToBooking(TripDto tripDto);
    public abstract TripDto BookingtoBookingDto(Trip trip);

}
