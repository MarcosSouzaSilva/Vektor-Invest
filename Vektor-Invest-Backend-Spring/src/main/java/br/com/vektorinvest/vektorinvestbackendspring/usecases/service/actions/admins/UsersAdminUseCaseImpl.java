package br.com.vektorinvest.vektorinvestbackendspring.usecases.service.actions.admins;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.AllStocks;
import br.com.vektorinvest.vektorinvestbackendspring.data.mapper.StocksMapper;
import br.com.vektorinvest.vektorinvestbackendspring.data.mapper.UserDataMapper;
import br.com.vektorinvest.vektorinvestbackendspring.data.repository.AllStocksRepository;
import br.com.vektorinvest.vektorinvestbackendspring.infra.security.ConfigSecurity;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.AllStockDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersInfoDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersSignUpDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.gateway.AdminGateway;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces.AdminDashboardUseCase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Service
public class UsersAdminUseCaseImpl implements AdminDashboardUseCase {

    private final AdminGateway adminGateway;

    private final AllStocksRepository allStocksRepository;


    @Override
    public ModelAndView showDashboard(UsersSignUpDomain dashboard, ConfigSecurity userDetails, AllStockDomain allStockDomain) {

        ModelAndView mv = new ModelAndView("adminDashboard");

        adminGateway.dashboard(dashboard, userDetails, mv, allStockDomain);

        return mv;
    }

    @Override
    public ModelAndView processCreateStock(AllStockDomain stockCode, BindingResult bindingResult) {

        var stock = adminGateway.createStocks(stockCode, bindingResult);

        if (bindingResult.hasFieldErrors("stockCode") || stock == null)  new ModelAndView("redirect:/");

        return new ModelAndView("redirect:/dashboard");
    }

    @Override
    public ModelAndView processDeleteStock(String stockCode) {

        adminGateway.deleteStocks(stockCode);

        return new ModelAndView("redirect:/dashboard");
    }

    public ModelAndView processDeleteUser(String username) {

        adminGateway.deleteUser(username);

        return new ModelAndView("redirect:/dashboard");
    }


    @Override
    public ModelAndView processEditStock(AllStockDomain stockDomain, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("editStock");

        var stockValidator = StocksMapper.convertToEntity(stockDomain);

        adminGateway.editStock(stockValidator, bindingResult);

        if (bindingResult.hasErrors()) {

            return modelAndView;
        } else {

            return new ModelAndView("redirect:/dashboard");
        }

    }

    @Override
    public ModelAndView showEditStockPage(String stockCode) {
        ModelAndView mv = new ModelAndView("editStock");

        var optionalUser = allStocksRepository.findByStockCode(stockCode);

        if (optionalUser.isPresent()) {
            AllStocks existingUser = optionalUser.get();

            mv.addObject("stockDomain", existingUser);

            return mv;

        } else {

            return new ModelAndView("redirect:/");
        }

    }

    @Override
    public ModelAndView showEditUserAdminPage(ConfigSecurity userDetails, String email) {

        ModelAndView mv = new ModelAndView("editProfilesAdmin");

        adminGateway.profilePageAdmin(mv, userDetails, email);

        return mv;
    }


}