package dash.meal.mealdash.domain.service.notification.otp;

import dash.meal.mealdash.application.input.otp.OtpUseCase;
import dash.meal.mealdash.application.output.user.OtpOutputPort;
import dash.meal.mealdash.domain.exception.OtpAdapterException;
import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.model.Otp;
import dash.meal.mealdash.infrastructure.adapter.output.mapper.MealDashMapper;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.OtpEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpService implements OtpUseCase {
    private final OtpOutputPort otpOutputPort;
    private final MealDashMapper mealDashMapper;


    @Override
    public Otp generateOtp(String email) throws OtpAdapterException {
        SecureRandom secureRandom = new SecureRandom();
        int token = secureRandom.nextInt(999999);

        StringBuilder otp = new StringBuilder(String.valueOf(token));
        while (otp.length() < 6) {
            otp.insert(0, "0");
        }

        OtpEntity otpEntity = new OtpEntity(otp.toString(), email, LocalDateTime.now(), email + otp);
        log.info("Mapped OTP: {}", otpEntity);

        otpOutputPort.save(mealDashMapper.toOtp(otpEntity));
        log.info("Saved OTP: {}", otpEntity);

        return new Otp(otpEntity.getToken(), otpEntity.getEmail(),  otpEntity.getCreatedAt(), otpEntity.getEmailToken());
    }

    @Override
    public Otp findByEmailAndToken(String emailToken) {
        return otpOutputPort.findByEmailTokenIgnoreCase(emailToken).orElseThrow(() -> new OtpAdapterException(ErrorMessage.INVALID_CODE));
    }

    @Override
    public void deleteOtp(Otp otp) {
        otpOutputPort.delete(otp);
    }

    private String generateOtpToken() {
        return String.format("%06d", new SecureRandom().nextInt(999999));
    }

}
