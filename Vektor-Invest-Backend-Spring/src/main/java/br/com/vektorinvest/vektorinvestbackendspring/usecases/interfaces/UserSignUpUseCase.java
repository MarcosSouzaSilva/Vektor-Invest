package br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces;

import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersSignUpDomain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;


public interface UserSignUpUseCase {

    ModelAndView processSignUp(UsersSignUpDomain userDomain, BindingResult bindingResult, HttpSession httpSession);

    ModelAndView showSignUpPage(UsersSignUpDomain usersSignUpDomain, HttpSession httpSession);


}