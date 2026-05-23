package br.com.vektorinvest.vektorinvestbackendspring.usecases.service.actions.users;

import br.com.vektorinvest.vektorinvestbackendspring.usecases.interfaces.PaymentUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Service
public class PaymentUseCaseImpl implements PaymentUseCase {

    @Override
    public ModelAndView showPaymentPage() {

        return new ModelAndView("payment");
    }

    @Override
    public void payment() {

    }


}