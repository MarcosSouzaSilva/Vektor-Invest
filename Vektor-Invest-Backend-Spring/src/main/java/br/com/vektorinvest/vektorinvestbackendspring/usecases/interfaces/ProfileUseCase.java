package br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Users;
import br.com.vektorinvest.vektorinvestbackendspring.infra.security.ConfigSecurity;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersEditDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersSignUpDomain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

public interface ProfileUseCase {

    ModelAndView showProfile(ConfigSecurity userDetails, int page, Users foundUser);

    ModelAndView showEditProfilePage(ConfigSecurity userDetails);

    ModelAndView processEditProfile(ConfigSecurity userDetails, UsersEditDomain user, BindingResult bindingResult);

    ModelAndView generatedContent(UUID uuid, ConfigSecurity userDetails);
}
