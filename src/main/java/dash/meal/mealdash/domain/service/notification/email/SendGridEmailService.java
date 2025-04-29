package dash.meal.mealdash.domain.service.notification.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import dash.meal.mealdash.application.input.email.EmailUseCase;
import dash.meal.mealdash.domain.model.Otp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("!default")
public class SendGridEmailService implements EmailUseCase {
    @Value("${sendgrid.api-key}")
    private String apiKey;
    private final TemplateEngine templateEngine;

    @Async
    public void sendEmail(String recipient, String templateName, String subject, Context context) {
        String processedTemplate = templateEngine.process(templateName, context);
        Email from = new Email("joy@semicolon.africa");
        Email toEmail = new Email(recipient);
        Content content = new Content("text/html", processedTemplate);
        Mail mail = new Mail(from, subject, toEmail, content);
        SendGrid sendGrid = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            log.info(response.getBody());
        }catch (IOException e){
            throw new RuntimeException(e.getLocalizedMessage());
        }

    }

    @Override
    public void sendOtp(Otp otp, String username) {
        Context context = new Context();
        context.setVariable("user_name", username);
        context.setVariable("user_code", otp.getToken());
        context.setVariable("user_email", otp.getEmail());
        context.setVariable("year", LocalDate.now().getYear());
        sendEmail(otp.getEmail(),
                "emailverification",
                "Mealdash Verification code", context);
    }

    @Override
    public String verifyEmail(String email) {
        return "";
    }
//    public static void main(String[] args) throws IOException {
//        String SENDGRID_API_KEY = "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";
//
//        Email from = new Email("joy@semicolon.africa");
//        String subject = "Sending with SendGrid is Fun";
//        Email to = new Email("udeme5017@gmail.com");
//        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
//        Mail mail = new Mail(from, subject, to, content);
//
//        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
//        Request request = new Request();
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            Response response = sg.api(request);
//            System.out.println(response.getStatusCode());
//            System.out.println(response.getBody());
//            System.out.println(response.getHeaders());
//        } catch (IOException ex) {
//            throw ex;
//        }
//    }
    }

