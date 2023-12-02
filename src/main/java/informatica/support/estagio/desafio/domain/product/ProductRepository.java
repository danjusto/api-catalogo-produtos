package informatica.support.estagio.desafio.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByTitleAndBrand(String title, String brand);
    Optional<Product> findByTitleAndBrandAndIdNot(String title, String brand, UUID id);
    @Query(
        "SELECT p FROM Product p WHERE CAST(p.category as string) ILIKE :category"
    )
    List<Product> findByCategoryUsingLike(String category);
}
