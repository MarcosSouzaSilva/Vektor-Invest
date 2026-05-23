package br.com.vektorinvest.vektorinvestbackendspring.usecases.domains;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserProfileDomain {

    @Column(nullable = false)
    @NotNull(message = "O campo nome é obrigatório")
    private String name;

    @NotNull(message = "O campo Email é obrigatório !")
    private String email;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private LocalDate createdAt;


}