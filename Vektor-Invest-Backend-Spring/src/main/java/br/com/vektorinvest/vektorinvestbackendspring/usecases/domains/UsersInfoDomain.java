package br.com.vektorinvest.vektorinvestbackendspring.usecases.domains;

import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.Role;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.validation.UniqueEmail;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UsersInfoDomain {

    @Column(nullable = false)
    @NotNull(message = "O campo nome é obrigatório")
    private String name;

    @Email(message = "Email inválido, tente novamente !",  regexp = "^[A-Za-z0-9._%+-]{2,}@[A-Za-z0-9.-]+\\.[A-Za-z]{1,}$")
    @NotNull(message = "O campo Email é obrigatório !")
    @UniqueEmail
    private String email;

    @Column(nullable = false)
    private String updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

}
