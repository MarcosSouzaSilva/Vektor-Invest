package br.com.vektorinvest.vektorinvestbackendspring.usecases.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentStatus {

    PENDING("Pendente"),

    CONFIRMED("Confirmado"),

    FAILED("Falhou");

    private final String label;

}