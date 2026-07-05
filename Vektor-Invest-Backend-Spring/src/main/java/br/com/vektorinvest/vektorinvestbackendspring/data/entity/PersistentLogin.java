package br.com.vektorinvest.vektorinvestbackendspring.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "persistent_logins")
public class PersistentLogin {

    @Column(name = "username", nullable = false, length = 64)
    private String username;

    @Id
    @Column(name = "series", nullable = false, length = 64)
    private String series;

    @Column(name = "token", nullable = false, length = 64)
    private String token;

    @Column(name = "last_used", nullable = false)
    private LocalDateTime lastUsed;

}