package dash.meal.mealdash.domain.service.notification.email;

import com.sendgrid.SendGrid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {
    private RestTemplate restTemplate;
    private final TemplateEngine templateEngine;


    @Bean
    public RestTemplate restTemplate(){
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
        return restTemplate;
    }




}
