package br.com.vektorinvest.vektorinvestbackendspring.usecases.domains;

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

    @Email(message = "Email inválido, tente novamente !",  regexp = "^[A-Za-z0-9._%+-]{2,}@[A-Za-z0-9.-]+\\.[A-Za-z]{1,}$")
    @NotNull(message = "O campo Email é obrigatório !")
    @UniqueEmail
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$", message = "A senha deve conter letras maiúsculas, minúsculas, números e símbolos.")
    private String password;

    private Boolean enabled;

    @NotNull(message = "Sua idade não cumpre o requisito mínimo de 18 anos.")
    @MinAge(18)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}