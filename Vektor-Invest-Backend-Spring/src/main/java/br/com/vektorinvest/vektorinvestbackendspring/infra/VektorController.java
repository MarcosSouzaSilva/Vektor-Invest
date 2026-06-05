package br.com.vektorinvest.vektorinvestbackendspring.infra;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Users;
import br.com.vektorinvest.vektorinvestbackendspring.infra.security.ConfigSecurity;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.*;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class VektorController {

    private final UserSignUpUseCase userSignUpUseCaseInterface;

    private final IndexUseCase indexUseCaseInterface;

    private final TermsOfUseUseCase termsOfUseUseCaseInterface;

    private final ProfileUseCase profileUseCaseInterface;

    private final UserSignInUseCase userSignInUseCaseInterface;

    private final AdminDashboardUseCase usersAdminUseCaseInterface;

    private final PaymentUseCase paymentUseCaseInterface;


    // ----------------------------
    // PÚBLICO (INDEX)
    // ----------------------------

    @GetMapping
    public ModelAndView showHomePage() {
        return indexUseCaseInterface.showHomePage();
    }

    @GetMapping("/terms")
    public ModelAndView showTermsOfUsePage() {
        return termsOfUseUseCaseInterface.showTermsOfUsePage();
    }

    @PostMapping("/stock")
    public ModelAndView analyzeStock(@ModelAttribute("stockDomain") StockDomain stockDomain) {
        return indexUseCaseInterface.stockAnalysis(stockDomain);
    }

    // ----------------------------
    // AUTENTICAÇÃO (LOGIN / SIGNUP)
    // ----------------------------

    @GetMapping("/signUp")
    public ModelAndView showSignUpPage(UsersSignUpDomain usersSignUpDomain) {
        return userSignUpUseCaseInterface.showSignUpPage(usersSignUpDomain);
    }

    @PostMapping("/signUp")
    public ModelAndView processSignUp(@Valid @ModelAttribute("signUp") UsersSignUpDomain signUp, BindingResult bindingResult, HttpServletResponse response) {
        return userSignUpUseCaseInterface.processSignUp(signUp, bindingResult, response);
    }

    @GetMapping("/login")
    public ModelAndView showSignInPage(UsersSignUpDomain signInModelRequest) {
        return userSignInUseCaseInterface.showSignInPage(signInModelRequest);
    }

    // ----------------------------

    // PERFIL (USER PROFILE)
    // ----------------------------

    @GetMapping("/profile")
    public ModelAndView showProfile(@RequestParam(defaultValue = "0") int page, Users user) {
        return profileUseCaseInterface.showProfile(page, user);
    }

    @GetMapping("/editUser")
    public ModelAndView showEditProfilePage() {
        return profileUseCaseInterface.showEditProfilePage();
    }

    @PostMapping("/editUser")
    public ModelAndView processEditProfile(@ModelAttribute("usuario") @Valid UsersEditDomain usersEditDomain, BindingResult bindingResult) {
        return profileUseCaseInterface.processEditProfile(usersEditDomain, bindingResult);
    }

    @GetMapping("/stock")
    public ModelAndView generatedContent(@RequestParam UUID id){
        return profileUseCaseInterface.generatedContent(id);
    }

    // ----------------------------
    // ADMINISTRATIVO (ADMIN)
    // ----------------------------

    @GetMapping("/dashboard")
    public ModelAndView showDashboard(UsersSignUpDomain dashboard, AllStockDomain allStockDomain) {
        return usersAdminUseCaseInterface.showDashboard(dashboard, allStockDomain);
    }

    @GetMapping("/editUserAdmin/{email}")
    public ModelAndView showEditUserAdminPage(@PathVariable String email) {
        return usersAdminUseCaseInterface.showEditUserAdminPage(email);
    }

    @GetMapping("/editStock/{stockCode}")
    public ModelAndView showEditStockPage(@PathVariable String stockCode) {
        return usersAdminUseCaseInterface.showEditStockPage(stockCode);
    }

    @PostMapping("/editStockAdmin")
    public ModelAndView processEditStock(@ModelAttribute("stockDomain") @Valid AllStockDomain stockDomain, BindingResult bindingResult) {
        return usersAdminUseCaseInterface.processEditStock(stockDomain, bindingResult);
    }

    @PostMapping("/createStocksForm")
    public ModelAndView processCreateStock(@Valid @ModelAttribute("allStockDomain") AllStockDomain allStockDomain, BindingResult bindingResult) {
        return usersAdminUseCaseInterface.processCreateStock(allStockDomain, bindingResult);
    }

    @PostMapping("/deleteStocks/{sector}")
    public ModelAndView processDeleteStock(@PathVariable String sector) {
        return usersAdminUseCaseInterface.processDeleteStock(sector);
    }

    @PostMapping("/deleteUser/{email}")
    public ModelAndView processDeleteUser(@PathVariable String email) {
        return usersAdminUseCaseInterface.processDeleteUser(email);
    }

    // ----------------------------
    // PAGAMENTO
    // ----------------------------

    @GetMapping("/payment")
    public ModelAndView showPaymentPage() {
        return paymentUseCaseInterface.showPaymentPage();
    }

    @PostMapping("/payment")
    public void processPayment() {
        // Implementar futuramente
    }



}