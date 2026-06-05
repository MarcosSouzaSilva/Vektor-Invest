package br.com.vektorinvest.vektorinvestbackendspring.usecases.service.actions.users;

import br.com.vektorinvest.vektorinvestbackendspring.data.mapper.UserDataMapper;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersSignUpDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.AuthProvider;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.gateway.UserGateway;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces.UserSignUpUseCase;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Service
public class UserSignUpUseCaseImpl implements UserSignUpUseCase {

    private final UserGateway userGateway;

    private final PasswordEncoder passwordEncoder;

    @Override
    public ModelAndView processSignUp(UsersSignUpDomain userDomain, BindingResult bindingResult, HttpServletResponse response)  {
        ModelAndView modelAndView = new ModelAndView("signUp");

        var user = UserDataMapper.convert(userDomain);

        if (bindingResult.hasErrors()) return modelAndView;

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userGateway.registerUser(user);

        return new ModelAndView("redirect:/profile");
    }

    @Override
    public ModelAndView showSignUpPage(UsersSignUpDomain usersSignUpDomain) {

        ModelAndView modelAndView = new ModelAndView("signUp.html");

        modelAndView.addObject("signUp", usersSignUpDomain);

        return modelAndView;
    }



}