package informatica.support.estagio.desafio.controller;

import informatica.support.estagio.desafio.domain.product.ProductService;
import informatica.support.estagio.desafio.domain.product.dto.ProductDto;
import informatica.support.estagio.desafio.domain.product.dto.UpdateProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(@RequestBody ProductDto dto) {
        return this.productService.executeCreate(dto);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> findAll(@RequestParam(required = false) String category) {
        return this.productService.executeFindAll(category);
    }
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto findOne(@PathVariable UUID id) {
        return this.productService.executeFindOne(id);
    }
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable UUID id) {
        this.productService.executeRemove(id);
    }
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto update(@PathVariable UUID id, @RequestBody UpdateProductDto dto) {
        return this.productService.executeUpdate(id, dto);
    }
}
