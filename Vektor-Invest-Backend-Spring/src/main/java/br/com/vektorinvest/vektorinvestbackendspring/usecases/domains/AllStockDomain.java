package br.com.vektorinvest.vektorinvestbackendspring.usecases.domains;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AllStockDomain {


    @NotEmpty
    @Pattern(regexp = "^[A-Z]{4}(3|4|5|6|7|8|11)$", message = "Código de ação inválido, tente novamente")
    private String stockCode;

    @NotEmpty
    private String companyName;

    @NotEmpty
    private String sector;

}
