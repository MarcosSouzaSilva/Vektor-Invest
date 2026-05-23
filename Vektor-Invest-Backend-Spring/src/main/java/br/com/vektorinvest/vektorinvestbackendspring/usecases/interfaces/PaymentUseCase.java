package br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces;

import org.springframework.web.servlet.ModelAndView;

public interface PaymentUseCase {

    ModelAndView showPaymentPage();

    void payment();

}