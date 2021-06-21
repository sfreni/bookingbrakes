package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.creditcardtransaction.CreditCardTransactionDto;
import it.freni.bookingbrakes.controller.dto.purchase.PurchaseCreditCardTransactionDto;
import it.freni.bookingbrakes.domain.CreditCardTransaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = { PurchaseMapper.class })
public abstract class CreditCardTransactionMapper{


    public abstract CreditCardTransaction dtoToCreditCardTransaction(CreditCardTransactionDto creditCardTransactionDto);
    public abstract CreditCardTransactionDto toDto(CreditCardTransaction CreditCardTransaction);
    public abstract List<CreditCardTransaction> purchaseCreditCardDtoToCreditCardTransactionList(List<PurchaseCreditCardTransactionDto> purchaseCreditCardTransactionDtoList);
    public abstract List<PurchaseCreditCardTransactionDto> toPurchaseCreditCardTransactionListDto(List<CreditCardTransaction> creditCardTransactions);
    public abstract PurchaseCreditCardTransactionDto toPurchaseCreditCardTransactionDto(CreditCardTransaction creditCardTransaction);
    public abstract List<CreditCardTransactionDto> toDtosList(List<CreditCardTransaction> creditCardTransactions );
}
