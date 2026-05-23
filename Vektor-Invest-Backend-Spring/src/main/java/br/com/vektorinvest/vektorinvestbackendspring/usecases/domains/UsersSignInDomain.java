package br.com.vektorinvest.vektorinvestbackendspring.usecases.domains;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UsersSignInDomain {

    @Email(message = "Email inválido, tente novamente !")
    @NotNull(message = "O campo Email é obrigatório !")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$", message = "Senha deve ter maiúsculas, minúsculas, números e símbolos.")
    private String password;


}