package br.com.vektorinvest.vektorinvestbackendspring.unitTests.actions.users;

import br.com.vektorinvest.vektorinvestbackendspring.data.mapper.UserDataMapper;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.domains.UsersSignUpDomain;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserSignInUseCaseImplTest {

    @Test
    void createUser() {
        // Mock do BindingResult
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        // Define que não há erros

        UsersSignUpDomain usersSignUpDomain = new UsersSignUpDomain();
        usersSignUpDomain.setName("Marcos");
        usersSignUpDomain.setEmail("souza@gmail.com");
        usersSignUpDomain.setPassword("bm2002@");
        usersSignUpDomain.setBirthDate(LocalDate.of(2002, 10, 15));

        // Converte para User (suposto mapeamento)
        var user = UserDataMapper.convert(usersSignUpDomain);

        // Checa se BindingResult tem erros
        if (bindingResult.hasErrors()) System.err.println("boa");
        when(bindingResult.hasErrors()).thenReturn(false);

        // Assert das propriedades
        assertEquals("Marcos", user.getName());
        assertEquals("souza@gmail.com", user.getEmail());
        assertEquals("bm2002@", user.getPassword());
        assertEquals(LocalDate.of(2002, 10, 15), user.getBirthDate());
    }


}