package vn.io.rento.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.io.rento.auth.entity.ValidToken;

import java.util.Optional;

@Repository
public interface ValidTokenRepository extends JpaRepository<ValidToken, String> {
    Optional<ValidToken> findByRefreshTokenId(String refreshTokenId);

    Optional<ValidToken> findByPrevTokenId(String prevTokenId);
}
