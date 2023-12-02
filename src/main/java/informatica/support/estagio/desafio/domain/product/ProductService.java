package informatica.support.estagio.desafio.domain.product;

import informatica.support.estagio.desafio.domain.product.dto.ProductDto;
import informatica.support.estagio.desafio.domain.product.dto.UpdateProductDto;
import informatica.support.estagio.desafio.infrastructure.exception.AlreadyExistException;
import informatica.support.estagio.desafio.infrastructure.exception.InvalidParamException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@EnableCaching
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Transactional
    public ProductDto executeCreate(ProductDto dto) {
        checkProductExists(dto.title(), dto.brand());
        checkValidCategory(dto.category());
        var product = new Product(dto);
        return this.productRepository.save(product).toDto();
    }
    @Cacheable("products")
    public List<ProductDto> executeFindAll(String category) {
        if (category != null) {
            return this.productRepository.findByCategoryUsingLike("%" + category + "%").stream().map(Product::toDto).toList();
        }
        return this.productRepository.findAll().stream().map(Product::toDto).toList();
    }
    @Cacheable("product")
    public ProductDto executeFindOne(UUID id) {
        var product = getProduct(id);
        return product.toDto();
    }
    @Transactional
    public void executeRemove(UUID id) {
        var product = getProduct(id);
        this.productRepository.delete(product);
    }
    @Transactional
    public ProductDto executeUpdate(UUID id, UpdateProductDto dto) {
        if (dto.category() != null) {
            checkValidCategory(dto.category());
        }
        var product = getProduct(id);
        product.update(dto);
        checkIfTitleAndBrandExistsInOtherProduct(product.getId(), product.getTitle(), product.getBrand());
        return this.productRepository.save(product).toDto();
    }
    private Product getProduct(UUID id) {
        var product = this.productRepository.findById(id);
        if (product.isEmpty()) {
            throw new EntityNotFoundException("Product not found");
        }
        return product.get();
    }
    private void checkIfTitleAndBrandExistsInOtherProduct(UUID id, String title, String brand) {
        var checkProductExists = this.productRepository.findByTitleAndBrandAndIdNot(title, brand, id);
        if (checkProductExists.isPresent()) {
            throw new AlreadyExistException("Other product already have this title and brand");
        }
    }
    private void checkProductExists(String title, String brand) {
        var checkProductExists = this.productRepository.findByTitleAndBrand(title, brand);
        if (checkProductExists.isPresent()) {
            throw new AlreadyExistException("Product already exist");
        }
    }
    private void checkValidCategory(String category) {
        var checkCategory = Category.mapOfCategories.containsKey(category);
        if (checkCategory == Boolean.FALSE) {
            throw new InvalidParamException("Invalid category. Categories available: 'smartphones', 'laptops'");
        }
    }
}
