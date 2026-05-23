package br.com.vektorinvest.vektorinvestbackendspring.usecases.service.actions.users;

import br.com.vektorinvest.vektorinvestbackendspring.data.mapper.ConfigSecurityMapper;
import br.com.vektorinvest.vektorinvestbackendspring.infra.security.ConfigSecurity;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.StockDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.gateway.UserGateway;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces.IAResponseUseCase;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces.IndexUseCase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Service
@AllArgsConstructor
@Log4j2
public class IndexUseCaseImpl implements IndexUseCase {

    private final UserGateway userGateway;

    private final IAResponseUseCase iaResponseUseCase;


    @Override
    public ModelAndView showHomePage(ConfigSecurity userDetails) {
        return userGateway.loadHomePage(userDetails);
    }

    @Override
    public ModelAndView stockAnalysis(ConfigSecurity userDetails, StockDomain stockDomain) {
        var id = SecurityContextHolder.getContext().getAuthentication();

        if (id == null) {
            log.warn("Id não encontrado");
            return new ModelAndView("redirect:/signUp");
        }

        var stocks = userGateway.analyzeStock(stockDomain);

        log.info("Redirecionando pra pagamentos");
        //return new ModelAndView("redirect:/payment");
        // aqui preciso verificar se o pagamento foi aprovado, se sim, retornar a acao analisada

        //aqui pra baixo ele foi aprovado o pagamento, ou seja, a acao sera analisada

        log.warn("Pagamento aprovado ! Gerando resposta agora");
        return iaResponseUseCase.showPage(userDetails, stocks.getStock());
    }

}