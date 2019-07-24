package eu.werriz.estateregistry.repository;

import eu.werriz.estateregistry.model.EstateProperty;
import eu.werriz.estateregistry.model.EstatePropertyType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EstatePropertyRepository extends JpaRepository<EstateProperty, Long> {

    String SEARCH_SIMILAR_QUERY = "from EstateProperty ep " +
            "join ep.address a " +
            "where ep.id != :id and a.city = :city and a.street = :street and ep.type = :type " +
            "order by abs(ep.size - :size) asc";

    List<EstateProperty> findAllByOwnerId(Long id);

    @Query(value = SEARCH_SIMILAR_QUERY)
    List<EstateProperty> findSimilarProperties(
            @Param("id") Long id,
            @Param("city") String city,
            @Param("street") String street,
            @Param("type") EstatePropertyType type,
            @Param("size") BigDecimal size,
            Pageable pageable);
}
