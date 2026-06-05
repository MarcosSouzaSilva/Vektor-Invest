package br.com.vektorinvest.vektorinvestbackendspring.usecases.service.actions.users;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Users;
import br.com.vektorinvest.vektorinvestbackendspring.infra.security.ConfigSecurity;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersEditDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.gateway.UserGateway;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces.ProfileUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Service
@AllArgsConstructor
@Log4j2
public class ProfileUseCaseImpl implements ProfileUseCase {

    private final UserGateway userGateway;

    @Override
    public ModelAndView showProfile(int page, Users user) {
        ModelAndView mv = new ModelAndView("profile");

        return userGateway.loadUserProfile(page, mv);
    }



    @Override
    public ModelAndView showEditProfilePage() {
        ModelAndView mv = new ModelAndView("editProfiles");

        return userGateway.loadProfileEditPage(mv);
    }

    @Override
    public ModelAndView processEditProfile(UsersEditDomain user, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView("editProfiles");

        var users = userGateway.updateUserProfile(user, bindingResult);

        if (bindingResult.hasErrors()) {
            mv.addObject("usuario", user);
            return mv;
        }

        if (users == null) return new ModelAndView("redirect:/");

        return new ModelAndView("redirect:/profile");
    }

    @Override
    public ModelAndView generatedContent(UUID uuid) {

        userGateway.generatedContent(uuid);

        return userGateway.generatedContent(uuid);
    }


}