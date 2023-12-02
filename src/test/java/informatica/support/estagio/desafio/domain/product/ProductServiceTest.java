package informatica.support.estagio.desafio.domain.product;

import informatica.support.estagio.desafio.domain.product.dto.ProductDto;
import informatica.support.estagio.desafio.domain.product.dto.UpdateProductDto;
import informatica.support.estagio.desafio.infrastructure.exception.AlreadyExistException;
import informatica.support.estagio.desafio.infrastructure.exception.InvalidParamException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    public static final String TITLE = "Produto One";
    public static final String DESCRIPTION = "Descrição do Produto One";
    public static final BigDecimal PRICE = BigDecimal.valueOf(1000.50);
    public static final int STOCK = 20;
    public static final String BRAND = "Marca One";
    public static final String CATEGORY = "smartphones";
    public static final String TITLE_EDIT = "Produto Two";
    public static final BigDecimal PRICE_EDIT = BigDecimal.valueOf(2000.75);
    public static final int STOCK_EDIT = 40;
    public static final String CATEGORY_EDIT = "laptops";
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidParams_whenCallCreateProduct_thenShouldReturnACreatedProduct() {
        var dto = new ProductDto(UUID.randomUUID(), TITLE, DESCRIPTION, PRICE, STOCK, BRAND, CATEGORY, null);
        var product = new Product(dto);
        when(productRepository.findByTitleAndBrand(any(), any())).thenReturn(Optional.empty());
        when(productRepository.save(any())).thenReturn(product);
        var response = productService.executeCreate(dto);

        assertNotNull(response);
        assertEquals(ProductDto.class, response.getClass());
        assertEquals(TITLE, response.title());
        assertEquals(Category.SMARTPHONES.toString(), response.category());
        assertEquals(STOCK, response.stock());
    }
    @Test
    void givenAnExistingProductParams_whenCallCreateProduct_thenShouldReturnAnError() {
        var dto = new ProductDto(UUID.randomUUID(), TITLE, DESCRIPTION, PRICE, STOCK, BRAND, CATEGORY, null);
        var product = new Product(dto);
        when(productRepository.findByTitleAndBrand(any(), any())).thenReturn(Optional.of(product));
        var exception = assertThrows(AlreadyExistException.class, () -> productService.executeCreate(dto));
        assertEquals("Product already exist", exception.getMessage());
        verify(productRepository, never()).save(any());
    }
    @Test
    void givenAnInvalidCategoryParam_whenCallCreateProduct_thenShouldReturnAnError() {
        var dto = new ProductDto(UUID.randomUUID(), TITLE, DESCRIPTION, PRICE, STOCK, BRAND, "test", null);
        when(productRepository.findByTitleAndBrand(any(), any())).thenReturn(Optional.empty());
        var exception = assertThrows(InvalidParamException.class, () -> productService.executeCreate(dto));
        assertEquals("Invalid category. Categories available: 'smartphones', 'laptops'", exception.getMessage());
        verify(productRepository, never()).save(any());
    }
    @Test
    void givenASearchCategory_whenCallFindAllProducts_thenShouldSearchByCategory() {
        when(productRepository.findByCategoryUsingLike(any())).thenReturn(List.of());
        productService.executeFindAll(CATEGORY);
        verify(productRepository, never()).findAll();
    }
    @Test
    void givenAnEmptySearchCategory_whenCallFindAllProducts_thenShouldSearchAll() {
        when(productRepository.findAll()).thenReturn(List.of());
        productService.executeFindAll(null);
        verify(productRepository, never()).findByCategoryUsingLike(any());
    }
    @Test
    void givenAValidId_whenCallFindOneProduct_thenShouldReturnAProduct() {
        var dto = new ProductDto(UUID.randomUUID(), TITLE, DESCRIPTION, PRICE, STOCK, BRAND, CATEGORY, null);
        var product = new Product(dto);
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        var response = productService.executeFindOne(product.getId());
        assertNotNull(response);
        assertEquals(ProductDto.class, response.getClass());
        assertEquals(TITLE, response.title());
        assertEquals(Category.SMARTPHONES.toString(), response.category());
        assertEquals(STOCK, response.stock());
    }
    @Test
    void givenAnInvalidId_whenCallFindOneProduct_thenShouldReturnAnError() {
        when(productRepository.findById(any())).thenReturn(Optional.empty());
        var exception = assertThrows(EntityNotFoundException.class, () -> productService.executeFindOne(UUID.randomUUID()));
        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, never()).delete(any());
    }
    @Test
    void givenAValidId_whenCallRemoveProduct_thenShouldBeOk() {
        var dto = new ProductDto(UUID.randomUUID(), TITLE, DESCRIPTION, PRICE, STOCK, BRAND, CATEGORY, null);
        var product = new Product(dto);
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);
        productService.executeRemove(product.getId());
        verify(productRepository, times(1)).delete(any());
    }
    @Test
    void givenAnInvalidId_whenCallRemoveProduct_thenShouldReturnAnError() {
        when(productRepository.findById(any())).thenReturn(Optional.empty());
        var exception = assertThrows(EntityNotFoundException.class, () -> productService.executeRemove(UUID.randomUUID()));
        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, never()).delete(any());
    }
    @Test
    void givenValidParams_whenCallUpdateProduct_thenShouldReturnAnUpdatedProduct() {
        var dto = new ProductDto(UUID.randomUUID(), TITLE, DESCRIPTION, PRICE, STOCK, BRAND, CATEGORY, null);
        var product = new Product(dto);
        var updateDto = new UpdateProductDto(TITLE_EDIT, null, PRICE_EDIT, STOCK_EDIT, null, CATEGORY_EDIT, null);
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(productRepository.findByTitleAndBrandAndIdNot(any(), any(), any())).thenReturn(Optional.empty());
        when(productRepository.save(any())).thenReturn(product);

        var response = productService.executeUpdate(product.getId(), updateDto);

        assertNotNull(response);
        assertEquals(ProductDto.class, response.getClass());
        assertEquals(TITLE_EDIT, response.title());
        assertEquals(PRICE_EDIT, response.price());
        assertEquals(STOCK_EDIT, response.stock());
        assertEquals(BRAND, response.brand());
        assertEquals(DESCRIPTION, response.description());
        assertEquals(Category.LAPTOPS.toString(), response.category());
    }
    @Test
    void givenAnInvalidCategoryParam_whenCallUpdateProduct_thenShouldReturnAnError() {
        var dto = new ProductDto(UUID.randomUUID(), TITLE, DESCRIPTION, PRICE, STOCK, BRAND, CATEGORY, null);
        var product = new Product(dto);
        var updateDto = new UpdateProductDto(TITLE_EDIT, null, PRICE_EDIT, STOCK_EDIT, null, "test", null);
        var exception = assertThrows(InvalidParamException.class, () -> productService.executeUpdate(product.getId(), updateDto));
        assertEquals("Invalid category. Categories available: 'smartphones', 'laptops'", exception.getMessage());
        verify(productRepository, never()).save(any());
    }
    @Test
    void givenAnInvalidIdParam_whenCallUpdateProduct_thenShouldReturnAnError() {
        var updateDto = new UpdateProductDto(TITLE_EDIT, null, PRICE_EDIT, STOCK_EDIT, null, CATEGORY_EDIT, null);
        when(productRepository.findById(any())).thenReturn(Optional.empty());
        var exception = assertThrows(EntityNotFoundException.class, () -> productService.executeUpdate(UUID.randomUUID(), updateDto));
        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, never()).save(any());
    }
    @Test
    void givenAnExistingProductParams_whenCallUpdateProduct_thenShouldReturnAnError() {
        var dto = new ProductDto(UUID.randomUUID(), TITLE, DESCRIPTION, PRICE, STOCK, BRAND, CATEGORY, null);
        var product = new Product(dto);
        var updateDto = new UpdateProductDto(TITLE_EDIT, null, PRICE_EDIT, STOCK_EDIT, null, CATEGORY_EDIT, null);
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(productRepository.findByTitleAndBrandAndIdNot(any(), any(), any())).thenReturn(Optional.of(product));
        var exception = assertThrows(AlreadyExistException.class, () -> productService.executeUpdate(product.getId(), updateDto));
        assertEquals("Other product already have this title and brand", exception.getMessage());
        verify(productRepository, never()).save(any());
    }
}