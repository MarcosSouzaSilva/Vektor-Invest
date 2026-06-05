package br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Users;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersEditDomain;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

public interface ProfileUseCase {

    ModelAndView showProfile( int page, Users foundUser);

    ModelAndView showEditProfilePage();

    ModelAndView processEditProfile(UsersEditDomain user, BindingResult bindingResult);

    ModelAndView generatedContent(UUID uuid);
}
