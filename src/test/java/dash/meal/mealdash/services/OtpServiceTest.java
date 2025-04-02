package dash.meal.mealdash.services;

import dash.meal.mealdash.application.output.OtpOutputPort;
import dash.meal.mealdash.domain.exception.OtpAdapterException;
import dash.meal.mealdash.domain.model.Otp;
import dash.meal.mealdash.domain.service.notification.otp.OtpService;
import dash.meal.mealdash.infrastructure.adapter.output.mapper.MealDashMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class OtpServiceTest {

    @InjectMocks
    private OtpService otpService;

    @Mock
    private OtpOutputPort otpOutputPort;
    @Mock
    private MealDashMapper mealDashMapper;

    private Otp otp;

    @BeforeEach
    public void setUp() {
        otp = Otp.builder()
                .email("email@email.com")
                .token("234123")
                .build();
    }

    @Test
    void testGenerateOtp_successful() throws OtpAdapterException {
        when(otpOutputPort.save(otp)).thenReturn(new Otp());
        when(otpOutputPort.findByEmail(otp.getEmail())).thenReturn(Optional.of(otp));

        Otp response = otpService.generateOtp(otp.getEmail());
        assertNotNull(response);
        assertNotNull(response.getToken());


    }

    @Test
    void testGenerateOtp_findOtpByEmail_ifPresentDeleteOtp() throws OtpAdapterException {
        when(otpOutputPort.save(otp)).thenReturn(new Otp());
        when(otpOutputPort.findByEmail(otp.getEmail())).thenReturn(Optional.of(otp));
        doNothing().when(otpOutputPort).delete(otp);

        Otp response = otpService.generateOtp(otp.getEmail());
        assertNotNull(response);
        assertNotNull(response.getToken());


    }
}
