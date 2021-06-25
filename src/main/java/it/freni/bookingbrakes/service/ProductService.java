package it.freni.bookingbrakes.service;

import it.freni.bookingbrakes.domain.Product;
import it.freni.bookingbrakes.domain.Purchase;
import it.freni.bookingbrakes.repository.ProductRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> saveProducts(List<Product> products) {


        return  productRepository.saveAll(products);
    }






    public void deleteAllProductsByPurchase(Purchase purchase) {
        productRepository.deleteProductByPurchase(purchase);
    }
}
