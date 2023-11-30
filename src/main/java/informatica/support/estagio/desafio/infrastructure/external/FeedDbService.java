package informatica.support.estagio.desafio.infrastructure.external;

import informatica.support.estagio.desafio.domain.product.Product;
import informatica.support.estagio.desafio.domain.product.ProductRepository;
import informatica.support.estagio.desafio.domain.product.dto.ProductDto;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedDbService {
    private ProductRepository productRepository;
    public FeedDbService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Boolean productTableEmpty() {
        return this.productRepository.findAll().isEmpty();
    }
    public void feedDb() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest requestPhone = HttpRequest.newBuilder()
                .uri(new URI("https://dummyjson.com/products/category/smartphones"))
                .timeout(Duration.of(15, ChronoUnit.SECONDS))
                .GET().build();

        HttpRequest requestLaptop = HttpRequest.newBuilder()
                .uri(new URI("https://dummyjson.com/products/category/laptops"))
                .timeout(Duration.of(15, ChronoUnit.SECONDS))
                .GET().build();

        HttpResponse<String> responsePhone = HttpClient.newBuilder().build().send(requestPhone, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> responseLaptop = HttpClient.newBuilder().build().send(requestLaptop, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonPhone = new JSONObject(responsePhone.body());
        JSONObject jsonLaptop = new JSONObject(responseLaptop.body());
        var listOfPhones = prepareListOfProducts(jsonPhone);
        var listOfLaptops = prepareListOfProducts(jsonLaptop);
        listOfPhones.forEach(product -> this.productRepository.save(new Product(product)));
        listOfLaptops.forEach(product -> this.productRepository.save(new Product(product)));
    }
    private List<ProductDto> prepareListOfProducts(JSONObject json) {
        var listProducts = new ArrayList<ProductDto>();
        for (int i = 0; i < json.getJSONArray("products").length(); i++) {
            var product = json.getJSONArray("products").getJSONObject(i);
            listProducts.add(new ProductDto(
                    null,
                    product.getString("title"),
                    product.getString("description"),
                    product.getBigDecimal("price").multiply(BigDecimal.valueOf(4)),
                    product.getInt("stock"),
                    product.getString("brand"),
                    product.getString("category"),
                    product.getJSONArray("images").getString(0)));
        }
        return listProducts;
    }
}
