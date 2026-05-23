package br.com.vektorinvest.vektorinvestbackendspring.data.repository;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Stocks;
import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Users;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.StockAnalysisDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.StockDomain;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StocksRepository extends JpaRepository<Stocks, UUID> {


    @Query("SELECT COUNT(*) AS stock FROM Stocks")
    BigDecimal getAnalyseStocks();

    @Query("SELECT s.stock FROM Stocks s GROUP BY s.stock ORDER BY COUNT(s.stock) DESC LIMIT 1")
    Object stockMoreAnalysed();

    @Query(value = "SELECT COUNT(stock) FROM stocks GROUP BY stock ORDER BY COUNT(stock) DESC LIMIT 1", nativeQuery = true)
    Long findTopStockCount();

    @Query("""
SELECT new br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.StockAnalysisDomain(
    s.stock,
    COUNT(s.stock)
)
FROM Stocks s
GROUP BY s.stock
ORDER BY COUNT(s.stock) DESC
""")
    List<StockAnalysisDomain> findTopStocks(Pageable pageable);


}