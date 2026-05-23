package br.com.vektorinvest.vektorinvestbackendspring.data.repository;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.IAResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IAResponseRepository extends JpaRepository<IAResponse, UUID> {

    @Query("SELECT COUNT(r) FROM IAResponse r WHERE r.user.id = :userId")
    BigDecimal countUserResponses(@Param("userId") UUID userId);

    @Query("""
SELECT s.stock
FROM Stocks s
WHERE s.user.id = :userId
GROUP BY s.stock
ORDER BY COUNT(s.stock) DESC
""")
    List<String> findTopStockCodesByUser(
            @Param("userId") UUID userId,
            Pageable pageable
    );

    Page<IAResponse> findByUserId(@Param("userId") UUID userId, Pageable pageable);

    @Query(value = """
    SELECT COUNT(*) 
    FROM IAResponse
    WHERE created_at >= DATE_FORMAT(CURRENT_DATE(), '%Y-%m-01')
      AND created_at < DATE_ADD(DATE_FORMAT(CURRENT_DATE(), '%Y-%m-01'), INTERVAL 1 MONTH)
""", nativeQuery = true)
    Long currentMonth();

    @Query(value = """
    SELECT COUNT(*) 
    FROM IAResponse
    WHERE created_at >= DATE_FORMAT(DATE_SUB(CURRENT_DATE(), INTERVAL 1 MONTH), '%Y-%m-01')
      AND created_at < DATE_FORMAT(CURRENT_DATE(), '%Y-%m-01')
""", nativeQuery = true)
    Long previousMonth();


}