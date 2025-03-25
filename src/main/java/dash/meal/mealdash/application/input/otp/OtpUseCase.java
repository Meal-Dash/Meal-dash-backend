package dash.meal.mealdash.application.input.otp;

import dash.meal.mealdash.domain.exception.OtpAdapterException;
import dash.meal.mealdash.infrastructure.adapter.input.data.response.OtpResponse;

public interface OtpUseCase {
    OtpResponse generateOtp(String email) throws OtpAdapterException;
}
