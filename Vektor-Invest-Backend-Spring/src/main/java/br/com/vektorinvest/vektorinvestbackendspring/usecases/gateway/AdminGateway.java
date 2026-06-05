package br.com.vektorinvest.vektorinvestbackendspring.usecases.gateway;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.AllStocks;
import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Users;
import br.com.vektorinvest.vektorinvestbackendspring.infra.security.ConfigSecurity;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.AllStockDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersSignUpDomain;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

public interface AdminGateway {


    ModelAndView dashboard(UsersSignUpDomain dashboard, ModelAndView mv, AllStockDomain allStockDomain);

    AllStocks createStocks(AllStockDomain stock, BindingResult bindingResult);

    void deleteStocks(String stockCode);

    BigDecimal analyseStocks();

    BigDecimal usersActive(); // usuarios ativos

    Object stocksMoreAnalysed(); // acao mais analisada

    void deleteUser(String username);

    AllStocks editStock(AllStocks stocks, BindingResult bindingResult);

    ModelAndView profilePageAdmin(ModelAndView mv, String email);

}
