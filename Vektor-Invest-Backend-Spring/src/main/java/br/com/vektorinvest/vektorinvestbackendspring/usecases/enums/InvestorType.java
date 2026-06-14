package br.com.vektorinvest.vektorinvestbackendspring.usecases.enums;

import lombok.Getter;

@Getter
public enum InvestorType {

    CONSERVATIVE("Conservador - Baixo risco"),
    MODERATE("Moderado - Risco médio"),
    AGGRESSIVE("Agressivo - Alto risco");

    private final String investorType;

    InvestorType(String investorType) {
        this.investorType = investorType;
    }

}