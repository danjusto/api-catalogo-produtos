package informatica.support.estagio.desafio.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByTitleAndBrand(String title, String brand);
    Optional<Product> findByTitleAndBrandAndIdNot(String title, String brand, UUID id);
    Page<Product> findAllByCategory(Category category, Pageable pageable);
}
