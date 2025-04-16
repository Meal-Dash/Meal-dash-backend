package dash.meal.mealdash.domain.service.notification.email;

import dash.meal.mealdash.application.input.email.EmailUseCase;
import dash.meal.mealdash.domain.model.Otp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;

@Service
@Slf4j
@AllArgsConstructor
//@Profile("default")
public class MailgunEmailService implements EmailUseCase {

    private final RestTemplate restTemplate;
    private final TemplateEngine templateEngine;

    private static final String MAILGUN_DOMAIN = "sandbox76af239476c3475bb47ddec825079b21.mailgun.org";
    private static final String MAILGUN_API_KEY = "yba2ff0c431dc44fce0820136096044c3-17c877d7-5654c451";
    private static final String MAILGUN_BASE_URL = "https://api.mailgun.net/v3/" + MAILGUN_DOMAIN;


    @Async
    public void sendMail(String recipient, String templateName, String subject, Context context) {
        String processedTemplate = templateEngine.process(templateName, context);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("api", MAILGUN_API_KEY);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("from", "Mailgun Sandbox <postmaster@sandbox76af239476c3475bb47ddec825079b21.mailgun.org>");
        form.add("to", recipient);
        form.add("subject", subject);
        form.add("html", processedTemplate);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    MAILGUN_BASE_URL + "/messages",
                    request,
                    String.class
            );
            log.info("Mailgun response: {}", response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Mailgun error: " + e.getMessage());
        }
    }

    @Override
    public void sendOtp(Otp otp, String username) {
        Context context = new Context();
        context.setVariable("user_name", username);
        context.setVariable("user_code", otp.getToken());
        context.setVariable("user_email", otp.getEmail());
        context.setVariable("year", LocalDate.now().getYear());
        sendMail(otp.getEmail(),
                "emailverification",
                "Meal Dash Verification code", context);
    }

    @Override
    public String verifyEmail(String email) {
        return "";
    }
}
