package dash.meal.mealdash.application.input.otp;

import dash.meal.mealdash.domain.exception.OtpAdapterException;
import dash.meal.mealdash.domain.model.Otp;

public interface OtpUseCase {
    Otp generateOtp(String email) throws OtpAdapterException;
}
