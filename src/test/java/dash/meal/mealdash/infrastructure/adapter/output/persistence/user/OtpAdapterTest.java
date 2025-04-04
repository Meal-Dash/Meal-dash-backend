package dash.meal.mealdash.infrastructure.adapter.output.persistence.user;

import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.exception.OtpAdapterException;
import dash.meal.mealdash.domain.model.Otp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OtpAdapterTest {

    @Autowired
    private OtpAdapter otpAdapter;

    private Otp otp;

    @BeforeEach
    void setUp() {
        otp = Otp.builder()
                .token("124345")
                .createdAt(LocalDateTime.now())
                .email("test@test.com")
                .build();

    }

    @AfterAll
    void tearDown() {
        otpAdapter.delete(otp);
    }

    @Test
    void saveOtp() throws OtpAdapterException {
        Otp savedOtp = otpAdapter.save(otp);
        assertEquals(otp.getToken(), savedOtp.getToken());
        assertNotNull(savedOtp);
    }

    @Test
    void saveOtpThrowExceptionWhenTokenIsNull(){
        otp.setToken(null);

        OtpAdapterException exception = assertThrows(OtpAdapterException.class, ()-> otpAdapter.save(otp));
        assertEquals(ErrorMessage.TOKEN_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void saveOtpThrowExceptionWhenTokenIsEmpty(){
        otp.setToken("");

        OtpAdapterException exception = assertThrows(OtpAdapterException.class, ()-> otpAdapter.save(otp));
        assertEquals(ErrorMessage.TOKEN_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void saveOtpThrowExceptionWhenEmailIsNull(){
        otp.setEmail(null);

        OtpAdapterException exception = assertThrows(OtpAdapterException.class, ()-> otpAdapter.save(otp));
        assertEquals(ErrorMessage.OTP_EMAIL_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void saveOtpThrowExceptionWhenEmailIsEmpty(){
        otp.setEmail("");

        OtpAdapterException exception = assertThrows(OtpAdapterException.class, ()-> otpAdapter.save(otp));
        assertEquals(ErrorMessage.OTP_EMAIL_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void findOtpByEmail() throws OtpAdapterException {
        String email = otp.getEmail();
        otpAdapter.save(otp);
        Optional<Otp> foundOtp = otpAdapter.findByEmail(email);
        log.info("found otp ------> {}", foundOtp.get().getEmail());
        assertNotNull(foundOtp);
        assertEquals(foundOtp.get().getToken(), otp.getToken());
    }

    @Test
    void findOtpByEmailThrowExceptionWhenOtpNotFound() {
        String email = "otpgetEmail()";

        OtpAdapterException otpAdapterException = assertThrows(OtpAdapterException.class, ()-> otpAdapter.findByEmail(email));
        assertEquals(ErrorMessage.OTP_NOT_FOUND, otpAdapterException.getMessage());
    }


    @Test
    void deleteOtpAndThrowExceptionWhenOtpIsNull() throws OtpAdapterException {
        OtpAdapterException exception = assertThrows(OtpAdapterException.class, ()-> otpAdapter.delete(null));
        assertEquals(ErrorMessage.OTP_NOT_FOUND, exception.getMessage());
    }
}
