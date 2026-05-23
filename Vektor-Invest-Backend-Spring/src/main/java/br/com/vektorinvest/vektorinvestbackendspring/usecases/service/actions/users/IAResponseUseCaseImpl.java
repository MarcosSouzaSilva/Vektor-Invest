package br.com.vektorinvest.vektorinvestbackendspring.usecases.service.actions.users;

import br.com.vektorinvest.vektorinvestbackendspring.infra.security.ConfigSecurity;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.IAGenerateMessageDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.gateway.UserGateway;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces.IAResponseUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

@Service
@Log4j2
@RequiredArgsConstructor
public class IAResponseUseCaseImpl implements IAResponseUseCase {

    private final WebClient webClient;

    private final UserGateway userGateway;

    @Value("${python.api.url}")
    private String urlPythonApi;

    @Override
    public ModelAndView showPage(ConfigSecurity userDetails, String stock) {
        IAGenerateMessageDomain response = webClient.get()
                .uri(urlPythonApi +"/analise/"+ stock)
                .retrieve()
                .bodyToMono(IAGenerateMessageDomain.class)
                .block();

        var iaResponse = userGateway.generateIAResponsePage(userDetails, stock, response);

        log.info("Resposta da IA gerada com sucesso: {}", response);

        return iaResponse;
    }


}