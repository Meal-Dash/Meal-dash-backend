package dash.meal.mealdash.infrastructure.adapter.output.persistence.repositories;

import dash.meal.mealdash.domain.model.Otp;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpEntity, String> {
    Optional<OtpEntity> findByEmail(String email);

    Optional<OtpEntity> findByEmailTokenIgnoreCase(String emailToken);
}
