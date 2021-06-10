package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.CustomerDto;
import it.freni.bookingbrakes.controller.dto.CustomerWithIdDto;
import it.freni.bookingbrakes.controller.dto.PurchaseDto;
import it.freni.bookingbrakes.domain.Customer;
import it.freni.bookingbrakes.domain.Purchase;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {

    PurchaseDto toDto(Purchase purchase);
    Iterable<PurchaseDto> toDtos(Iterable<Purchase> purchases);
 }
