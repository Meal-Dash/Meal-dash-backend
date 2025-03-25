package dash.meal.mealdash.domain.service.notification.otp;

import dash.meal.mealdash.application.input.otp.OtpUseCase;
import dash.meal.mealdash.application.output.OtpOutputPort;
import dash.meal.mealdash.domain.exception.OtpAdapterException;
import dash.meal.mealdash.infrastructure.adapter.input.data.response.OtpResponse;
import dash.meal.mealdash.infrastructure.adapter.mapper.MealDashMapper;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.OtpEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class OtpService implements OtpUseCase {
    private final OtpOutputPort otpOutputPort;
    private final MealDashMapper mealDashMapper;

    @Override
    public OtpResponse generateOtp(String email) throws OtpAdapterException {
        otpOutputPort.findByEmail(email).ifPresent(otpOutputPort::delete);

        String otpToken = generateOtpToken();
        log.info("Generated OTP: {}", otpToken);

        OtpEntity otpEntity = new OtpEntity(otpToken, email, LocalDateTime.now());
        log.info("Mapped OTP: {}", otpEntity);

        otpOutputPort.save(mealDashMapper.toOtp(otpEntity));
        log.info("Saved OTP: {}", otpEntity);

        return new OtpResponse(otpEntity.getToken(), otpEntity.getEmail(),  otpEntity.getCreatedAt());
    }

    private String generateOtpToken() {
        return String.format("%06d", new SecureRandom().nextInt(999999));
    }

}
