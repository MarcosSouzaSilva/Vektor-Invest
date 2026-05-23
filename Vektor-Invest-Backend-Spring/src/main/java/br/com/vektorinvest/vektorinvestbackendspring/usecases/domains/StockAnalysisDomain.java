package br.com.vektorinvest.vektorinvestbackendspring.usecases.domains;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@Component
@NoArgsConstructor
public class StockAnalysisDomain {

    private String stockCode;

    private Long total;

}