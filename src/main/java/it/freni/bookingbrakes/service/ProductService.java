package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.controller.dto.purchase.ProductAdditionalServiceDto;
import it.freni.bookingbrakes.controller.dto.purchase.ProductDto;
import it.freni.bookingbrakes.controller.dto.purchase.ProductSeatDto;
import it.freni.bookingbrakes.controller.dto.purchase.PurchaseDto;
import it.freni.bookingbrakes.domain.AdditionalServiceType;
import it.freni.bookingbrakes.domain.CreditCardTransactionStatus;
import it.freni.bookingbrakes.domain.Product;
import it.freni.bookingbrakes.domain.Purchase;
import it.freni.bookingbrakes.error.IdAlreadyExists;
import it.freni.bookingbrakes.mapper.ProductMapper;
import it.freni.bookingbrakes.repository.ProductRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;

@Service
@Log
public class ProductService {
    private static final String ADDITIONAL_SERVICE_TYPE_NOT_FOUND = "CAN'T FIND THE ADDITIONAL SERVICE TYPE FOR THIS PRODUCT";
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<Product> saveProducts(List<Product> products) {


        return  productRepository.saveAll(products);
    }

    public void  checkDtoBeforeSaving(PurchaseDto purchaseDto){

   /*     for (ProductDto productDto : purchaseDto.getProducts()) {
            if (productDto instanceof ProductAdditionalServiceDto) {
                if(!checkAdditionalServiceType((ProductAdditionalServiceDto) productDto)){
                    log.log(Level.SEVERE, ADDITIONAL_SERVICE_TYPE_NOT_FOUND);
                    throw new IdAlreadyExists(ADDITIONAL_SERVICE_TYPE_NOT_FOUND);
                }
            }

        }*/
    }

    private boolean checkAdditionalServiceType(ProductAdditionalServiceDto productDto) {
        for(AdditionalServiceType additionalServiceType: AdditionalServiceType.values()){
            if(productDto.getAdditionalServiceType().name().equals(additionalServiceType.name())){
                return true;
            }
        }
        return false;
    }


}
