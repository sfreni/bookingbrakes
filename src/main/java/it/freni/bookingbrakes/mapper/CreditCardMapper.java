package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.*;
import it.freni.bookingbrakes.domain.CreditCard;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CreditCardMapper {

        public abstract CreditCardDto toDto(CreditCard creditCard);
        public abstract List<CreditCardDtoSingle> todtos(List<CreditCard> creditCards);
        public abstract CreditCard dtoToCreditCard(CreditCardDto creditCardDto);
        public abstract CreditCardNoTransactionsDto toDtoCreditCardNoTransaction(CreditCard creditCard);

        public CreditCardDtoList toDtosList(List<CreditCard> creditCards , CustomerDto customerDto){
                List<CreditCardDtoSingle>   creditCardDtoSingles = todtos(creditCards);
                CreditCardDtoList creditCardDtoList = new CreditCardDtoList();
                creditCardDtoList.setCreditCardDtoList(creditCardDtoSingles);
                creditCardDtoList.setCustomerCreditCardDto(customerDto);
                return creditCardDtoList;
        }
}