package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.*;
import it.freni.bookingbrakes.domain.CreditCard;
import it.freni.bookingbrakes.domain.CreditCardTransaction;
import it.freni.bookingbrakes.domain.Customer;
import it.freni.bookingbrakes.service.CreditCardService;
import it.freni.bookingbrakes.service.CreditCardTransactionService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CreditCardTransactionMapper {


    public abstract CreditCardTransaction dtoToCreditCardTransaction(CreditCardTransactionDto creditCardTransactionDto);
    public abstract CreditCardTransactionDto toDto(CreditCardTransaction CreditCardTransaction);
         public abstract List<CreditCardTransactionWithCustomerDto> todtos(List<CreditCardTransaction> CreditCardTransactions);

         @Mapping(target = "id", source = "creditCardTransaction.id")
         @Mapping(target = "totalePriceAmount", source = "creditCardTransaction.totalePriceAmount")
         @Mapping(target = "transactionStatus", source = "creditCardTransaction.transactionStatus")
         @Mapping(target = "creditcard", source = "creditCardTransaction.creditcard")
         @Mapping(target = "purchase", source = "creditCardTransaction.purchase")
         @Mapping(target = "customer", source = "customer")
         public abstract CreditCardTransactionWithCustomerDto toDtoWithCustomer(CreditCardTransaction creditCardTransaction, Customer customer);

    public List<CreditCardTransactionWithCustomerDto> toDtosList(List<CreditCardTransaction> creditCardTransactions ){
        List<CreditCardTransactionWithCustomerDto>   creditCardTransactionDtos = new ArrayList<>();
        for(CreditCardTransaction creditCardTransaction : creditCardTransactions){
            CreditCardTransactionWithCustomerDto creditCardTransactionWithCustomerDto = toDtoWithCustomer(creditCardTransaction,creditCardTransaction.getCreditcard().getCustomer());
            creditCardTransactionDtos.add(creditCardTransactionWithCustomerDto);
        }
        return creditCardTransactionDtos;
    }



}
