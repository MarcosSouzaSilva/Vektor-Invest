package br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces;

import br.com.vektorinvest.vektorinvestbackendspring.infra.security.ConfigSecurity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface IAResponseUseCase {

    ModelAndView showPage(String stock);

}
