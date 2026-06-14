package br.com.vektorinvest.vektorinvestbackendspring.data.entity;

import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.ActivityStatus;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.AuthProvider;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.InvestorType;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Users implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityStatus enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stocks> stocks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IAResponse> iaResponses = new ArrayList<>();

    @Column(nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider; // LOCAL ou GOOGLE

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvestorType investorType;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate createdAt;

    @Column(nullable = false)
    private LocalDate updatedAt;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

}