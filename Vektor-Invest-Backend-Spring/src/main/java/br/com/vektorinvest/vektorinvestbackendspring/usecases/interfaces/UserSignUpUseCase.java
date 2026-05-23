package br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces;

import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersSignUpDomain;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;


public interface UserSignUpUseCase {

    ModelAndView processSignUp(UsersSignUpDomain userDomain, BindingResult bindingResult, HttpServletResponse response);

    ModelAndView showSignUpPage(UsersSignUpDomain usersSignUpDomain);


}