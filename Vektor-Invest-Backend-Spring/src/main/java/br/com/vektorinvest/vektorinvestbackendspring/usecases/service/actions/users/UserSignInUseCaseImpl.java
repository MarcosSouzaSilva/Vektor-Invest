package br.com.vektorinvest.vektorinvestbackendspring.usecases.service.actions.users;

import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersSignUpDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersSignInDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.gateway.UserGateway;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces.UserSignInUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Service
public class UserSignInUseCaseImpl implements UserSignInUseCase {

    private final UserGateway userGateway;

    @Override
    public ModelAndView showSignInPage(UsersSignUpDomain usersSignUpDomain) {
        ModelAndView modelAndView = new ModelAndView("login");

        modelAndView.addObject("signIn", usersSignUpDomain);

        return modelAndView;
    }


    @Override
    public ModelAndView processSignIn(UsersSignInDomain userDomain, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("login");

        var user = userGateway.authenticateUser(userDomain, bindingResult);

        if (bindingResult.hasErrors() || user == null) {
            return modelAndView;
        }

        return new ModelAndView("redirect:/profile");
    }

}