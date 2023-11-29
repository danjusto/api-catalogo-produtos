package informatica.support.estagio.desafio.domain.products;

import informatica.support.estagio.desafio.domain.products.dto.ProductDto;
import informatica.support.estagio.desafio.domain.products.dto.UpdateProductDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String brand;
    private String category;
    private String image;

    public Product(ProductDto dto) {
        this.title = dto.title();
        this.description = dto.description();
        this.price = dto.price();
        this.stock = dto.stock();
        this.brand = dto.brand();
        this.category = dto.category();
        this.image = dto.image();
    }
    public ProductDto toDto() {
        return new ProductDto(this.id, this.title, this.description, this.price, this.stock, this.brand, this.category, this.image);
    }
    public void update(UpdateProductDto dto) {
        if (dto.title() != null) {
            this.title = dto.title();
        }
        if (dto.description() != null) {
            this.description = dto.description();
        }
        if (dto.price() != null) {
            this.price = dto.price();
        }
        if (dto.stock() != null) {
            this.stock = dto.stock();
        }
        if (dto.brand() != null) {
            this.brand = dto.brand();
        }
        if (dto.category() != null) {
            this.category = dto.category();
        }
        if (dto.image() != null) {
            this.image = dto.image();
        }
    }
}
