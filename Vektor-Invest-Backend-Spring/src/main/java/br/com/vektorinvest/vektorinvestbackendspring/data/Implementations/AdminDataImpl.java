package br.com.vektorinvest.vektorinvestbackendspring.data.Implementations;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.AllStocks;
import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Users;
import br.com.vektorinvest.vektorinvestbackendspring.data.mapper.AllStocksMapper;
import br.com.vektorinvest.vektorinvestbackendspring.data.repository.AllStocksRepository;
import br.com.vektorinvest.vektorinvestbackendspring.data.repository.IAResponseRepository;
import br.com.vektorinvest.vektorinvestbackendspring.data.repository.StocksRepository;
import br.com.vektorinvest.vektorinvestbackendspring.data.repository.UsersRepository;
import br.com.vektorinvest.vektorinvestbackendspring.infra.security.ConfigSecurity;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.AllStockDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersSignUpDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.ActivityStatus;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.Role;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.gateway.AdminGateway;
import br.com.vektorinvest.vektorinvestbackendspring.utils.UserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import java.math.BigDecimal;

@Component
@AllArgsConstructor
@Log4j2
public class AdminDataImpl implements AdminGateway {

    private final UsersRepository usersRepository;

    private final StocksRepository stocksRepository;

    private final AllStocksRepository allStocksRepository;

    private final IAResponseRepository iaResponseRepository;

    @Override
    public ModelAndView dashboard(UsersSignUpDomain dashboard, ModelAndView mv, AllStockDomain allStockDomain) {

        var nameCookie = UserUtils.getUserOrThrow(usersRepository);

        if (nameCookie == null) return mv;

        mv.addObject("name", nameCookie.getName());

        mv.addObject("allStockDomain", allStockDomain);

        mv.addObject("roles", Role.values());

        mv.addObject("usersActive", usersActive());

        mv.addObject("analyseStocks", analyseStocks());

        mv.addObject("stocksMoreAnalysed", stocksMoreAnalysed());

        mv.addObject("allStocks", allStocksRepository.findAll());

        mv.addObject("allUsers", usersRepository.findAll());

        mv.addObject("stockDomain", new AllStocks());

        mv.addObject("stocksMoreAnalysedCount", stocksRepository.findTopStockCount());

        mv.addObject("getMonthlyGrowthRate", calculateMonthOverMonthGrowth());

        mv.addObject("allStocksMoreAnalysed", stocksRepository.findTopStocks(Pageable.ofSize(3)));

        mv.addObject("countNewUsersCurrentMonth", usersRepository.countNewUsersCurrentMonth());

        return mv;
    }

    @Override
    public AllStocks createStocks(AllStockDomain stock, BindingResult bindingResult) {

        var reallyStock = AllStocksMapper.convert(stock);

        var stockAnalyzed = allStocksRepository.findByStockCode(reallyStock.getStockCode());

        if (bindingResult.hasFieldErrors("stockCode") || stockAnalyzed.isPresent()) return null;

        return allStocksRepository.save(reallyStock);
    }


    @Override
    public void deleteStocks(String stockCode) {

        var stockCodeId = allStocksRepository.findByStockCode(stockCode);

        if (stockCodeId.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        var stock = stockCodeId.get();

        allStocksRepository.delete(stock);
    }

    public BigDecimal analyseStocks() {
        return stocksRepository.getAnalyseStocks();
    }

    @Override
    public BigDecimal usersActive() {
        return usersRepository.getEnabledTrue(ActivityStatus.ACTIVE);
    }

    @Override
    public Object stocksMoreAnalysed() {
        return stocksRepository.stockMoreAnalysed();
    }

    @Override
    public void deleteUser(String username) {
        var userOpt = usersRepository.findByEmail(username);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        var user = userOpt.get();

        // 🔥 Limpa relações antes
        user.getStocks().clear();
        user.getIaResponses().clear();

        usersRepository.delete(user);
    }

    @Override
    public AllStocks editStock(AllStocks stocks, BindingResult bindingResult) {

        var stockTyped = allStocksRepository.findByStockCode(stocks.getStockCode());

        if (stockTyped.isEmpty()) {
            log.warn("Código de ação não encontrado no banco de dados: {}", stocks.getStockCode());

            return null;
        }
        AllStocks newStock = AllStocks.builder().id(stockTyped.get().getId()).stockCode(stocks.getStockCode()).sector(stocks.getSector()).companyName(stocks.getCompanyName()).build();

        return allStocksRepository.save(newStock);
    }

    @Override
    public ModelAndView profilePageAdmin(ModelAndView mv, String email) {

        var id = usersRepository.findByEmail(email);

        var optionalUser = usersRepository.findById(id.get().getId());

        if (optionalUser.isPresent()) {
            Users existingUser = optionalUser.get();

            mv.addObject("usuario", existingUser);

        }
        return mv;
    }

    public String calculateMonthOverMonthGrowth() {
        Long currentMonth = iaResponseRepository.currentMonth();
        Long previousMonth = iaResponseRepository.previousMonth();

        if (previousMonth == 0) {
            if (currentMonth == 0) return "0%";
            return "+100%";
        }

        double percentage = ((currentMonth - previousMonth) * 100.0) / previousMonth;

        return String.format("%.1f%%", percentage);
    }
}