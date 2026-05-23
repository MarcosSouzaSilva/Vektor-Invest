package br.com.vektorinvest.vektorinvestbackendspring.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AllStocks {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String stockCode;

    private String companyName;

    private String sector;

    @OneToMany(mappedBy = "stocks", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IAResponse> iaResponses = new ArrayList<>();


    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }


}