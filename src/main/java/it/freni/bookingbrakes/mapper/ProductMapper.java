package it.freni.bookingbrakes.mapper;

import it.freni.bookingbrakes.controller.dto.purchase.ProductAdditionalServiceDto;
import it.freni.bookingbrakes.controller.dto.purchase.ProductDto;
import it.freni.bookingbrakes.controller.dto.purchase.ProductSeatDto;
import it.freni.bookingbrakes.domain.AdditionalService;
import it.freni.bookingbrakes.domain.Product;
import it.freni.bookingbrakes.domain.Seat;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    public ProductDto toDto(Product product){

            if (product instanceof Seat) {
                ProductSeatDto productDto = new ProductSeatDto();
                productDto.setId(product.getId());
                productDto.setNrSeat(((Seat) product).getNrSeat());
                productDto.setPriceAmount(product.getPriceAmount());
                productDto.setFirstNamePassenger(((Seat) product).getFirstNamePassenger());
                productDto.setLastNamePassenger(((Seat) product).getLastNamePassenger());
                productDto.setDateOfBirth(((Seat) product).getDateOfBirth());
                return productDto;
            }
            if (product instanceof AdditionalService) {
                ProductAdditionalServiceDto productDto = new ProductAdditionalServiceDto();
                productDto.setId(product.getId());
                productDto.setPriceAmount(product.getPriceAmount());
                productDto.setAdditionalServiceType(((AdditionalService) product).getAdditionalServiceType());
                return productDto;
            }



        return null;
    }

    public Product DtotoProduct(ProductDto productDto){

        if (productDto instanceof ProductSeatDto) {
            Seat product = new Seat();
            product.setId(productDto.getId());
            product.setNrSeat(((ProductSeatDto) productDto).getNrSeat());
            product.setPriceAmount(productDto.getPriceAmount());
            product.setFirstNamePassenger(((ProductSeatDto) productDto).getFirstNamePassenger());
            product.setLastNamePassenger(((ProductSeatDto) productDto).getLastNamePassenger());
            product.setDateOfBirth(((ProductSeatDto) productDto).getDateOfBirth());
            return product;
        }
        if (productDto instanceof ProductAdditionalServiceDto) {
            AdditionalService product = new AdditionalService();
            product.setId(productDto.getId());
            product.setPriceAmount(productDto.getPriceAmount());
            product.setAdditionalServiceType(((ProductAdditionalServiceDto) productDto).getAdditionalServiceType());
            return product;
        }

        return null;
    }
        public abstract List<Product> DtosToProducts(List<ProductDto> productDtos);
    public abstract List<ProductDto> toDtos(List<Product> products);
 }
