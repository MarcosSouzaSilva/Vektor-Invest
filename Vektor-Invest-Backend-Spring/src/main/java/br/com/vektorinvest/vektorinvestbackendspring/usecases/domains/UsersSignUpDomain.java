package br.com.vektorinvest.vektorinvestbackendspring.usecases.domains;

import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.InvestorType;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.Role;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.validation.MinAge;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.validation.UniqueEmail;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UsersSignUpDomain {

    @Column(nullable = false)
    @NotNull(message = "O campo nome é obrigatório")
    private String name;

    @NotNull(message = "O campo Email é obrigatório !")
    //@Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "Email inválido, tente novamente !")
    @Email(message = "jnkweffef")
    @UniqueEmail
    private String email;

    @NotNull(message = "O campo investorType é obrigatório !")
    @Enumerated(EnumType.STRING)
    private InvestorType investorType;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,18}$", message = "A senha deve conter letras maiúsculas, minúsculas, números e símbolos.")
    private String password;

    private Boolean enabled;

    @NotNull(message = "Idade mínima não atendida")
    @MinAge(18)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}