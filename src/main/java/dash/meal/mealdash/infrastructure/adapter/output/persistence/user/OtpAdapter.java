package dash.meal.mealdash.infrastructure.adapter.output.persistence.user;

import dash.meal.mealdash.application.output.OtpOutputPort;
import dash.meal.mealdash.domain.exception.OtpAdapterException;
import dash.meal.mealdash.domain.model.Otp;
import dash.meal.mealdash.infrastructure.adapter.output.mapper.MealDashMapper;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.OtpEntity;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.repositories.OtpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class OtpAdapter implements OtpOutputPort {
    private final OtpRepository otpRepository;
    private final MealDashMapper mealDashMapper;


    @Override
    public Otp save(Otp otp) throws OtpAdapterException {
        log.info("Found otp {}", otp);
        otp.validations();
        OtpEntity otpEntity = mealDashMapper.toOtpEntity(otp);
        OtpEntity savedOtpEntity = otpRepository.save(otpEntity);
        return mealDashMapper.toOtp(savedOtpEntity);
    }

    @Override
    public Optional<Otp> findByEmail(String email) {
        return otpRepository.findByEmail(email)
                .map(mealDashMapper::toOtp);
    }
//    public Optional<Otp> findByEmail(String email) throws OtpAdapterException {
//        return Optional.of(otpRepository.findByEmail(email).map(mealDashMapper::toOtp)
//                .orElseThrow(() -> new OtpAdapterException(ErrorMessage.OTP_NOT_FOUND)));
//    }


    @Override
    public void delete(Otp otp) {
        otpRepository.delete(mealDashMapper.toOtpEntity(otp));
    }
}
