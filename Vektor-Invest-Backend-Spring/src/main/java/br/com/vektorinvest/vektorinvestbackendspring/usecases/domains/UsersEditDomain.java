package br.com.vektorinvest.vektorinvestbackendspring.usecases.domains;

import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.InvestorType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UsersEditDomain {


    @NotNull(message = "O campo nome é obrigatório")
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    private String email;

    @NotNull(message = "O campo investorType é obrigatório")
    private InvestorType investorType;

    private String password;




}
