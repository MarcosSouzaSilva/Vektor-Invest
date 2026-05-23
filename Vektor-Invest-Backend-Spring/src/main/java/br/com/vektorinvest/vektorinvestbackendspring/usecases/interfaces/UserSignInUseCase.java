package br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces;

import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersSignInDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersSignUpDomain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

public interface UserSignInUseCase {


    ModelAndView processSignIn(UsersSignInDomain userDomain, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response);

    ModelAndView showSignInPage(UsersSignUpDomain sigInModelRequest);

}