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
    private Otp otp2;

    @BeforeEach
    void setUp() {
        otp = Otp.builder()
                .token("124345")
                .id("123_AFB")
                .createdAt(LocalDateTime.now())
                .email("test@test.com")
                .build();

        otp2 = Otp.builder()
                .token("124345")
                .id("123_AFB")
                .createdAt(LocalDateTime.now())
                .email("test@test.com")
                .build();
    }

    @AfterAll
    void tearDown() {
        otpAdapter.delete(otp);
    }

    @Test
    void testSaveOtp_successful() throws OtpAdapterException {
        Otp savedOtp = otpAdapter.save(otp);
        assertEquals(otp.getToken(), savedOtp.getToken());
        assertNotNull(savedOtp);
    }

    @Test
    void testSaveOtp_throwExceptionWhenTokenIsNull(){
        otp.setToken(null);

        OtpAdapterException exception = assertThrows(OtpAdapterException.class, ()-> otpAdapter.save(otp));
        assertEquals(ErrorMessage.TOKEN_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void testSaveOtp_throwExceptionWhenTokenIsEmpty(){
        otp.setToken("");

        OtpAdapterException exception = assertThrows(OtpAdapterException.class, ()-> otpAdapter.save(otp));
        assertEquals(ErrorMessage.TOKEN_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void testSaveOtp_throwExceptionWhenEmailIsNull(){
        otp.setEmail(null);

        OtpAdapterException exception = assertThrows(OtpAdapterException.class, ()-> otpAdapter.save(otp));
        assertEquals(ErrorMessage.OTP_EMAIL_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void testSaveOtp_throwExceptionWhenEmailIsEmpty(){
        otp.setEmail("");

        OtpAdapterException exception = assertThrows(OtpAdapterException.class, ()-> otpAdapter.save(otp));
        assertEquals(ErrorMessage.OTP_EMAIL_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void testFindOtpByEmail_successful() throws OtpAdapterException {
        String email = otp.getEmail();
        otpAdapter.save(otp);
        Optional<Otp> foundOtp = otpAdapter.findByEmail(email);
        log.info("found otp ------> {}", foundOtp.get().getEmail());
        assertNotNull(foundOtp);
        assertEquals(foundOtp.get().getToken(), otp.getToken());
    }

    @Test
    void testFindOtpByEmail_throwExceptionWhenOtpNotFound() {
        String email = "otp.getEmail()";

        OtpAdapterException otpAdapterException = assertThrows(OtpAdapterException.class, ()-> otpAdapter.findByEmail(email));
        assertEquals(ErrorMessage.OTP_NOT_FOUND, otpAdapterException.getMessage());
    }

    @Test
    void testDeleteOtp_successful() {
        otpAdapter.delete(otp2);

        OtpAdapterException exception = assertThrows(OtpAdapterException.class, ()-> otpAdapter.findByEmail(otp2.getEmail()));
        assertEquals(ErrorMessage.OTP_NOT_FOUND, exception.getMessage());
    }
}
