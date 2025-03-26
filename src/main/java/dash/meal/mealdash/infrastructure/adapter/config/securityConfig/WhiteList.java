package dash.meal.mealdash.infrastructure.adapter.config.securityConfig;

public class WhiteList {
    public static final String[] patterns = {
            "/api/v1/user/signup",

            //swagger ui
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/v3/api-docs",
            "/v3/api-docs.yaml",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-resources",
            "/swagger-resources/**",
            "/webjars/**"
    };
}
