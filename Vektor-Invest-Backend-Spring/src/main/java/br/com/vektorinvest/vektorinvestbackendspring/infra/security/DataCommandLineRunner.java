package br.com.vektorinvest.vektorinvestbackendspring.infra.security;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Users;
import br.com.vektorinvest.vektorinvestbackendspring.data.repository.UsersRepository;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.ActivityStatus;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.AuthProvider;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.InvestorType;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static br.com.vektorinvest.vektorinvestbackendspring.data.mapper.UserDataMapper.getAnoAtualComoLocalDate;

@Configuration
public class DataCommandLineRunner {

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    private final PasswordEncoder passwordEncoder;

    private final UsersRepository userRepository;

    public DataCommandLineRunner(PasswordEncoder passwordEncoder, UsersRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {

            userRepository.findByEmail(adminEmail)
                    .ifPresentOrElse(
                            user -> {
                                user.setRole(Role.ADMIN);
                                userRepository.save(user);
                            },
                            () -> {
                                Users admin = new Users();
                                admin.setEmail(adminEmail);
                                admin.setName("Admin");
                                admin.setRole(Role.ADMIN);
                                admin.setEnabled(ActivityStatus.ACTIVE);
                                admin.setBirthDate(getAnoAtualComoLocalDate(2002, 1, 2));
                                admin.setInvestorType(InvestorType.MODERATE);
                                admin.setUpdatedAt(LocalDate.now());
                                admin.setProvider(AuthProvider.LOCAL);
                                admin.setPassword(passwordEncoder.encode(adminPassword));
                                userRepository.save(admin);
                            }
                    );

        };
    }

}
