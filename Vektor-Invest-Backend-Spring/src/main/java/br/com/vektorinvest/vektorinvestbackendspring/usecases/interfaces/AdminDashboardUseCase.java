package br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces;

import br.com.vektorinvest.vektorinvestbackendspring.infra.security.ConfigSecurity;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.AllStockDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersInfoDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersSignUpDomain;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

public interface AdminDashboardUseCase {


    ModelAndView showDashboard(UsersSignUpDomain dashboard, AllStockDomain allStockDomain);

    ModelAndView processCreateStock(AllStockDomain stock, BindingResult bindingResult);

    ModelAndView processDeleteStock(String stockCode) ;

    ModelAndView processDeleteUser(String username);

    ModelAndView processEditStock(AllStockDomain stockDomain, BindingResult bindingResult);

    ModelAndView showEditStockPage(String stockCode);

    ModelAndView showEditUserAdminPage(String email);


}