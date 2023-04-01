package ua.sirkostya009.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.sirkostya009.userservice.model.Token;

public interface TokenRepository extends JpaRepository<Token, String> {
}
