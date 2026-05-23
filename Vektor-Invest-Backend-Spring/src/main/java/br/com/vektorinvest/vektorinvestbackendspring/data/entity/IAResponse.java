package br.com.vektorinvest.vektorinvestbackendspring.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class IAResponse {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String ticker;

    @Column(nullable = false)
    private String empresa;

    @Column(nullable = false)
    private String precoAtual;

    @Column(nullable = false)
    private String variacaoDia;

    @Column(nullable = false)
    private String resumo;

    @Column(nullable = false)
    private String pl;

    @Column(nullable = false)
    private String pvp;

    @Column(nullable = false)
    private String dividendYield;

    @Column(nullable = false)
    private String roe;

    @Column(nullable = false)
    private List<String> pontosPositivos;

    @Column(nullable = false)
    private List<String> riscos;

    @Column(nullable = false)
    private String precoJusto;

    @Column(nullable = false)
    private String potencialValorizacao;

    @Column(nullable = false)
    private String score;

    @Column(nullable = false)
    private String classificacao;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stocks_id", nullable = false)
    private AllStocks stocks;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }
}