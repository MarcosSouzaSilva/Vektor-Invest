package br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces;

import br.com.vektorinvest.vektorinvestbackendspring.infra.security.ConfigSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.servlet.ModelAndView;

public interface UserOAuth2AuthenticationUseCase {

    ModelAndView oAuth2Authentication(@AuthenticationPrincipal OAuth2User principal, ConfigSecurity userDetails);

}
