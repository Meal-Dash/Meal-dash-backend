package dash.meal.mealdash.infrastructure.adapter.output.persistence.user;

import dash.meal.mealdash.application.output.MealDashUserIdentityOutputPort;
import dash.meal.mealdash.domain.exception.ErrorMessage;
import dash.meal.mealdash.domain.exception.MealDashUserAdapterException;
import dash.meal.mealdash.domain.model.UserRole;
import dash.meal.mealdash.infrastructure.adapter.input.rest.data.request.SignupRequest;
import dash.meal.mealdash.infrastructure.adapter.mapper.MealDashMapper;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class KeycloakAdapter implements MealDashUserIdentityOutputPort {

    @Value("${realm}")
    private String realm;
    private final Keycloak instance;
    private final MealDashMapper mapper;
    @Value("${keycloak.client-id}")
    private String baseClient;


    @Override
    public UserRepresentation signUpUser(SignupRequest request) throws MealDashUserAdapterException {
        List<UserRepresentation> existingUsers = instance.realm(realm)
                .users()
                .searchByEmail(request.getEmail(), true);

        if (!existingUsers.isEmpty()) {
            log.info("Existing user -----> {}", existingUsers);
            throw new MealDashUserAdapterException(ErrorMessage.USER_ALREADY_EXIST);
        }
        UserRepresentation userRepresentation = mapper.toUserRepresentation(request);
        userRepresentation.setUsername(request.getEmail());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);
        completeSignup(request.getPassword(), request.getEmail(), request.getRole(), userRepresentation);

        return getUserByEmail(request.getEmail());
    }


    @Override
    public UserRepresentation getUserByEmail(String email) throws MealDashUserAdapterException {
        List<UserRepresentation> users = instance.realm(realm).users().searchByEmail(email, true);
        log.info("found users --------> {}", users);
        if (users.isEmpty()) {
            throw new MealDashUserAdapterException(ErrorMessage.USER_NOT_FOUND);
        }
        return users.get(0);
    }

    @Override
    public void deleteUser(String email) {
        String userId = instance
                .realm(realm).users().search(email)
                .get(0)
                .getId();
        instance
                .realm(realm)
                .users()
                .delete(userId);
    }

    private void completeSignup(String password, String email, UserRole role, UserRepresentation userRepresentation) throws MealDashUserAdapterException {
        if (!Objects.equals(password, "")) setupPassword(password, userRepresentation);
        createUser(userRepresentation);

        String userId = String.valueOf(getUserByEmail(email).getId());
        log.info("Assigning role to user with ID: {}",userId);
        assignRoleToUser(userId, role);
    }

    private void createUser(UserRepresentation user) throws MealDashUserAdapterException {
        try (Response response = instance.realm(realm)
                .users()
                .create(user)) {
            log.info("created user ------> {}", user);
            log.info("response -------> {}", response);
            if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                throw new MealDashUserAdapterException(ErrorMessage.SOMETHING_WENT_WRONG);
            }
        }
    }

    private static void setupPassword(String password, UserRepresentation user) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(false);
        user.setCredentials(Collections.singletonList(credentialRepresentation));
    }

    private void assignRoleToUser(String userId, UserRole roleName) {
        List<ClientRepresentation> clients = instance.realm(realm).clients().findByClientId(baseClient);
        log.info("Clients found for baseClient '{}': {}", baseClient, clients);
        if (clients.isEmpty()) {
            throw new IllegalStateException("Client '" + baseClient + "' not found in realm " + realm);
        }
        ClientRepresentation client = clients.get(0);
        String clientUuid = client.getId();
        log.info("Client UUID for '{}': {}", baseClient, clientUuid);

        RoleRepresentation role = instance.realm(realm)
                .clients()
                .get(clientUuid)
                .roles()
                .get(roleName.name())
                .toRepresentation();

        instance.realm(realm)
                .users()
                .get(userId)
                .roles()
                .clientLevel(clientUuid)
                .add(Collections.singletonList(role));
    }

}