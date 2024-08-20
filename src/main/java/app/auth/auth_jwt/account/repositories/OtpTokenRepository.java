package app.auth.auth_jwt.account.repositories;

import app.auth.auth_jwt.account.entities.OtpToken;
import app.auth.auth_jwt.shared.repositories.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface OtpTokenRepository extends BaseRepository<OtpToken,Long> {
    Optional<OtpToken> findByTokenAndOtp(String token,String otp);

    @Query("SELECT ot FROM OtpToken ot WHERE ot.createdAt > :createdAt AND ot.token = :token AND ot.otp = :otp AND ot.isEnabled = :isEnabled")
    Optional<OtpToken> getOtpToken(String token, String otp, Instant createdAt, boolean isEnabled);
}
