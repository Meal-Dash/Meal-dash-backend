package dash.meal.mealdash.application.input.email;

import dash.meal.mealdash.domain.model.Otp;

public interface EmailUseCase {
    void sendOtp(Otp otp, String username);
    String verifyEmail(String email);
}
