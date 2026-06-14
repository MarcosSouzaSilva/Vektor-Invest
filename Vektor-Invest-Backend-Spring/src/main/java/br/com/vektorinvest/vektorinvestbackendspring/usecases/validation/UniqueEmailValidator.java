package br.com.vektorinvest.vektorinvestbackendspring.usecases.validation;

import br.com.vektorinvest.vektorinvestbackendspring.data.repository.UsersRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private final UsersRepository usersRepository;

    public UniqueEmailValidator(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        if (usersRepository.findByEmail(email).isPresent()) {

            //context.disableDefaultConstraintViolation(); // substituindo pra mensagem default que coloquei la
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addConstraintViolation();
            return false; // lanca mensagem
        }

        return true;

    }
}
