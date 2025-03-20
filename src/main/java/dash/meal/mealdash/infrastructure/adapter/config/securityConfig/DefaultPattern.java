package dash.meal.mealdash.infrastructure.adapter.config.securityConfig;

import org.springframework.stereotype.Component;

@Component
public class DefaultPattern implements AllowedHost{
    @Override
    public String[] getPatterns() {
        return new String[]{"http://localhost:3000", "http://localhost:3000/"};
    }
}
