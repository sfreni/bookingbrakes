package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.creditcardtransaction.CreditCardTransactionDto;
import it.freni.bookingbrakes.controller.dto.purchase.PurchaseCreditCardTransactionDto;
import it.freni.bookingbrakes.domain.CreditCardTransaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = { PurchaseMapper.class })
public interface  CreditCardTransactionMapper{


     CreditCardTransaction dtoToCreditCardTransaction(CreditCardTransactionDto creditCardTransactionDto);
     CreditCardTransactionDto toDto(CreditCardTransaction creditCardTransaction);
     PurchaseCreditCardTransactionDto toPurchaseCreditCardTransactionDto(CreditCardTransaction creditCardTransaction);
     List<CreditCardTransactionDto> toDtosList(List<CreditCardTransaction> creditCardTransactions );
}
