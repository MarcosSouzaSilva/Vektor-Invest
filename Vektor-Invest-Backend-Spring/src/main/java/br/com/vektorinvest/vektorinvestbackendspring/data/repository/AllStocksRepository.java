package br.com.vektorinvest.vektorinvestbackendspring.data.repository;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.AllStocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AllStocksRepository extends JpaRepository<AllStocks, UUID> {

    @Query("SELECT u FROM AllStocks u WHERE u.stockCode = :stockCode")
    Optional<AllStocks> findByStockCode(@Param("stockCode") String stockCode);

}