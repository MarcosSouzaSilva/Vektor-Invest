package br.com.vektorinvest.vektorinvestbackendspring.usecases.service.actions.users;

import br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces.TermsOfUseUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
@AllArgsConstructor
@Log4j2
public class TermsOfUseUseCaseImpl implements TermsOfUseUseCase {

    @Override
    public ModelAndView showTermsOfUsePage() {
        ModelAndView mv = new ModelAndView("termsOfUse.html");

        log.info("Pagina 'Termos de Uso' renderizada com sucesso");

        return mv;
    }

}