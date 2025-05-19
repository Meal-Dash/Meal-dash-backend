package dash.meal.mealdash.application.output.user;

import dash.meal.mealdash.domain.exception.OtpAdapterException;
import dash.meal.mealdash.domain.model.Otp;

import java.util.Optional;

public interface OtpOutputPort {
    Otp save(Otp otp) throws OtpAdapterException;
    Otp findByEmail(String email) throws OtpAdapterException;
    void delete(Otp otp);
    void deleteAll();
    Optional<Otp> findByEmailTokenIgnoreCase(String emailToken);
}
