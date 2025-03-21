package dash.meal.mealdash.infrastructure.adapter.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class KeyCloakConfig {
//    @Value("${keycloak.server-url}")
//    private String KEYCLOAK_SERVER_URL;
//    @Value("${keycloak.auth.user}")
//    private String KEYCLOAK_USERNAME;
//    @Value("${keycloak.auth.password}")
//    private String KEYCLOAK_PASSWORD;
//    @Value("${realm}")
//    private String REALM;
//    @Value("${keycloak.base.client}")
//    private String KEYCLOAK_CLIENT_ID;
//    @Value("{keycloak.client-secret}")
//    private String KEYCLOAK_CLIENT_SECRET;
//
//    @Bean
//    @Primary
//    public Keycloak keycloakConfigResolver() {
//        return Keycloak.getInstance(
//                KEYCLOAK_SERVER_URL,
//                REALM,
//                KEYCLOAK_USERNAME,
//                KEYCLOAK_PASSWORD,
//                KEYCLOAK_CLIENT_SECRET,
//                KEYCLOAK_CLIENT_ID);
//    }

    @Value("${keycloak.server-url}")
    private String authServerUrl;

    @Value("${realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Bean
    public Keycloak keycloakAdminClient() {
        return KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .scope("email profile")
                .build();
    }
}
