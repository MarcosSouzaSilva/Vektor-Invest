package br.com.vektorinvest.vektorinvestbackendspring.usecases.domains;


import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class IAGenerateMessageDomain {

    private String ticker;

    private String empresa;

    private String precoAtual;

    private String variacaoDia;

    private String resumo;

    private String pl;

    private String pvp;

    private String dividendYield;

    private String roe;

    private List<String> pontosPositivos;

    private List<String> riscos;

    private String precoJusto;

    private String potencialValorizacao;

    private String score;

    private String classificacao;



}