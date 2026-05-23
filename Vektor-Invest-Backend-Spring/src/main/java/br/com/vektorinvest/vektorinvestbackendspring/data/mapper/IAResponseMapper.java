package br.com.vektorinvest.vektorinvestbackendspring.data.mapper;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.IAResponse;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.IAGenerateMessageDomain;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class IAResponseMapper {


    public static IAResponse convert(IAGenerateMessageDomain domain) {

        return IAResponse.builder()

                .ticker(domain.getTicker())
                .empresa(domain.getEmpresa())

                .precoAtual(domain.getPrecoAtual())
                .variacaoDia(domain.getVariacaoDia())

                .resumo(domain.getResumo())

                .pl(domain.getPl())
                .pvp(domain.getPvp())
                .dividendYield(domain.getDividendYield())
                .roe(domain.getRoe())

                .pontosPositivos(domain.getPontosPositivos())
                .riscos(domain.getRiscos())

                .precoJusto(domain.getPrecoJusto())
                .potencialValorizacao(domain.getPotencialValorizacao())

                .score(domain.getScore())
                .classificacao(domain.getClassificacao())


                .build();
    }

    public static IAGenerateMessageDomain convertToDTO(IAResponse domain) {

        return IAGenerateMessageDomain.builder()
                .ticker(domain.getTicker())
                .empresa(domain.getEmpresa())
                .precoAtual(domain.getPrecoAtual())
                .variacaoDia(domain.getVariacaoDia())
                .resumo(domain.getResumo())
                .pl(domain.getPl())
                .pvp(domain.getPvp())
                .dividendYield(domain.getDividendYield())
                .roe(domain.getRoe())
                .pontosPositivos(domain.getPontosPositivos())
                .riscos(domain.getRiscos())
                .precoJusto(domain.getPrecoJusto())
                .potencialValorizacao(domain.getPotencialValorizacao())
                .score(domain.getScore())
                .classificacao(domain.getClassificacao())
                .build();
    }


}