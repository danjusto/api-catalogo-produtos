package informatica.support.estagio.desafio.domain.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByTitleAndBrand(String title, String brand);
    Optional<Product> findByTitleAndBrandAndIdNot(String title, String brand, UUID id);
    List<Product> findAllByCategory(Category category);
}
