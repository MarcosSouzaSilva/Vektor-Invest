package br.com.vektorinvest.vektorinvestbackendspring.usecases.gateway;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Stocks;
import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Users;
import br.com.vektorinvest.vektorinvestbackendspring.infra.security.ConfigSecurity;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

public interface UserGateway {

    ModelAndView loadHomePage(ConfigSecurity userDetails);

    Users registerUser(Users userDomain);

    Users authenticateUser(UsersSignInDomain userDomain, BindingResult bindingResult);

    Stocks analyzeStock(StockDomain userDomain);

    ModelAndView loadUserProfile(ConfigSecurity userDetails,int page, ModelAndView modelAndView);

    ModelAndView loadProfileEditPage(ConfigSecurity userDetails, ModelAndView mv);

    Users updateUserProfile(ConfigSecurity userDetails, UsersEditDomain user, BindingResult bindingResult);

    ModelAndView generateIAResponsePage(ConfigSecurity userDetails,String stock, IAGenerateMessageDomain response);

    ModelAndView generatedContent(UUID uuid, ConfigSecurity userDetails);
}