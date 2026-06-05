package br.com.vektorinvest.vektorinvestbackendspring.data.Implementations;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Stocks;
import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Users;
import br.com.vektorinvest.vektorinvestbackendspring.data.mapper.IAResponseMapper;
import br.com.vektorinvest.vektorinvestbackendspring.data.mapper.StocksMapper;
import br.com.vektorinvest.vektorinvestbackendspring.data.repository.*;
import br.com.vektorinvest.vektorinvestbackendspring.infra.security.ConfigSecurity;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.*;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.AuthProvider;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.Role;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.gateway.UserGateway;
import br.com.vektorinvest.vektorinvestbackendspring.utils.UserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static br.com.vektorinvest.vektorinvestbackendspring.data.mapper.UserDataMapper.getAnoAtualComoLocalDate;

@Component
@AllArgsConstructor
@Log4j2
public class UsersDataImpl implements UserGateway, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UsersRepository usersRepository;

    private final StocksRepository stocksRepository;

    private final IAResponseRepository iaResponseRepository;

    private final AllStocksRepository allStocksRepository;

    private final PasswordEncoder passwordEncoder;

    private ModelAndView safeExecute(Supplier<ModelAndView> action) {
        try {
            return action.get();
        } catch (Exception e) {
            log.error("Erro", e);
            return redirect();
        }
    }

    private ModelAndView redirect() {
        return new ModelAndView("redirect:/");
    }


    @Override
    public ModelAndView loadHomePage() {

        ModelAndView mv = new ModelAndView("index");

        mv.addObject("stockDomain", new StockDomain());
        mv.addObject("allStockIndex", allStocksRepository.findAll());

        Users user = UserUtils.getUserOrThrow(usersRepository);

        if (user != null) {
            mv.addObject("name", user.getName());
        }

        return mv;
    }


    @Override
    public Users registerUser(Users user) {
        return usersRepository.save(user);
    }

    @Override
    public Users authenticateUser(UsersSignInDomain userDomain, BindingResult bindingResult) {

        var userOpt = usersRepository.findByEmail(userDomain.getEmail());


        Users user = userOpt.get();

        user.setUpdatedAt(LocalDate.now());
        return usersRepository.save(user);
    }


    @Override
    public Stocks analyzeStock(StockDomain stockDomain) {

        Stocks stock = StocksMapper.convert(stockDomain);

        var id = UserUtils.getUserOrThrow(usersRepository);

        Users user = usersRepository.findById(id.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        stock.setUser(user);
        return stocksRepository.save(stock);
    }

    @Override
    public ModelAndView loadUserProfile(int page, ModelAndView mv) {

        return safeExecute(() -> {

            var userSession = UserUtils.getUserOrThrow(usersRepository);

            var userDomain = UserProfileDomain.builder().name(userSession.getName()).email(userSession.getEmail()).birthDate(userSession.getBirthDate()).createdAt(userSession.getCreatedAt()).build();

            mv.addObject("user", userDomain);

            mv.addObject("created", usersRepository.findUserRegistrationYear(userSession.getId()));
            mv.addObject("createdMonths", usersRepository.findMonthsSinceRegistration(userSession.getId()));
            mv.addObject("analyseStocks", iaResponseRepository.countUserResponses(userSession.getId()));

            mv.addObject("infosIaResponse", iaResponseRepository.findByUserId(userSession.getId(), PageRequest.of(page, 4, Sort.by("createdAt").descending())));

            Pageable top3 = PageRequest.of(0, 3);

            mv.addObject("analyseTop3Stocks", String.join(", ", iaResponseRepository.findTopStockCodesByUser(userSession.getId(), top3)));

            mv.addObject("iniciais", usersRepository.findIniciaisByUserId(userSession.getId()));

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startOfWeek = now.with(DayOfWeek.MONDAY);
            LocalDateTime endOfWeek = startOfWeek.plusDays(7);

            mv.addObject("countRequestsByUserInWeek", usersRepository.countRequestsByUserInWeek(userSession.getId(), startOfWeek, endOfWeek));

            return mv;
        });
    }

    @Override
    public ModelAndView loadProfileEditPage(ModelAndView mv) {

        Users user = UserUtils.getUserOrThrow(usersRepository);

        return safeExecute(() -> {

            UsersEditDomain edit = new UsersEditDomain();

            edit.setName(user.getName());
            edit.setBirthDate(user.getBirthDate());

            mv.addObject("usuario", edit);
            return mv;
        });
    }

    @Override
    public Users updateUserProfile(UsersEditDomain edit, BindingResult bindingResult) {

        Users user = UserUtils.getUserOrThrow(usersRepository);

        user.setName(edit.getName());
        user.setBirthDate(edit.getBirthDate());
        user.setUpdatedAt(LocalDate.now());

        return usersRepository.save(user);
    }


    @Override
    public ModelAndView generateIAResponsePage(String stock, IAGenerateMessageDomain response) {

        return safeExecute(() -> {

            Users user = UserUtils.getUserOrThrow(usersRepository);

            var stockOpt = allStocksRepository.findByStockCode(stock);
            if (stockOpt.isEmpty()) return redirect();

            var iaResponse = IAResponseMapper.convert(response);
            iaResponse.setUser(user);
            iaResponse.setStocks(stockOpt.get());

            iaResponseRepository.save(iaResponse);

            ModelAndView mv = new ModelAndView("IAResponse");
            mv.addObject("result", response);

            return mv;
        });
    }

    @Override
    public ModelAndView generatedContent(UUID uuid) {

        return safeExecute(() -> {

            var response = iaResponseRepository.findById(uuid)
                    .orElseThrow(() -> new RuntimeException("Response not found"));


            if (!response.getUser().getId().equals(UserUtils.getUserOrThrow(usersRepository).getId())) {
                throw new RuntimeException("Acesso negado");
            }

            ModelAndView mv = new ModelAndView("IAResponse");
            mv.addObject("result", IAResponseMapper.convertToDTO(response));

            return mv;
        });
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {

        OAuth2User oauthUser = new DefaultOAuth2UserService().loadUser(request);

        String email = oauthUser.getAttribute("email");

        Users user = usersRepository.findByEmail(email)
                .orElseGet(() -> {
                    Users newUser = new Users();
                    newUser.setEmail(email);
                    newUser.setName(oauthUser.getAttribute("name"));
                    newUser.setBirthDate(LocalDate.of(2000, 5, 10));
                    newUser.setEnabled(true);
                    newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    newUser.setProvider(AuthProvider.GOOGLE);
                    newUser.setUpdatedAt(getAnoAtualComoLocalDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()));
                    newUser.setRole(Role.USER);
                    return usersRepository.save(newUser);
                });

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("USER")),
                oauthUser.getAttributes(),
                "email"
        );
    }
}