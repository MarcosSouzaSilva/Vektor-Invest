package br.com.vektorinvest.vektorinvestbackendspring.data.repository;

import br.com.vektorinvest.vektorinvestbackendspring.data.entity.Users;
import br.com.vektorinvest.vektorinvestbackendspring.usecases.enums.ActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository <Users, UUID> {

    @Query("SELECT u FROM Users u WHERE u.email = :email")
    Optional<Users> findByEmail(@Param("email") String email);

    @Query("SELECT FUNCTION('YEAR', u.createdAt) FROM Users u WHERE u.id = :userId")
    Integer findUserRegistrationYear(@Param("userId") UUID userId);

    @Query(value = "SELECT TIMESTAMPDIFF(MONTH, u.created_at, CURRENT_DATE()) FROM users u WHERE u.id = :userId", nativeQuery = true)
    Integer findMonthsSinceRegistration(@Param("userId") UUID userId);

    @Query("SELECT COUNT(*) AS s FROM Users u WHERE u.enabled = :enabled")
    BigDecimal getEnabledTrue(@Param("enabled") ActivityStatus enabled);

    @Query(value = """
    SELECT CONCAT(
        UPPER(LEFT(SUBSTRING_INDEX(TRIM(u.name), ' ', 1), 1)),
        UPPER(LEFT(SUBSTRING_INDEX(TRIM(u.name), ' ', -1), 1))
    )
    FROM users u
    WHERE u.id = :id
""", nativeQuery = true)
    String findIniciaisByUserId(@Param("id") UUID id);

    @Query("""
    SELECT COUNT(r)
    FROM IAResponse r
    WHERE r.user.id = :id
    AND r.createdAt >= :startOfWeek
    AND r.createdAt < :endOfWeek
""")
    Long countRequestsByUserInWeek(UUID id, LocalDateTime startOfWeek, LocalDateTime endOfWeek);

    @Query(value = """
    SELECT COUNT(*) 
    FROM users 
    WHERE created_at >= DATE_FORMAT(CURRENT_DATE(), '%Y-%m-01')
    AND created_at < DATE_ADD(DATE_FORMAT(CURRENT_DATE(), '%Y-%m-01'), INTERVAL 1 MONTH)
""", nativeQuery = true)
    Long countNewUsersCurrentMonth();


}