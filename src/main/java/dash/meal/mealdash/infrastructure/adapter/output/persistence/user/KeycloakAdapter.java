package dash.meal.mealdash.infrastructure.adapter.output.persistence.user;

import dash.meal.mealdash.application.output.UserIdentityOutputPort;
import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.exception.UserAdapterException;
import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.validation.MealDashValidator;
import dash.meal.mealdash.infrastructure.adapter.output.mapper.MealDashMapper;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class KeycloakAdapter implements UserIdentityOutputPort {

    @Value("${realm}")
    private String REALM;
    private final Keycloak keycloak;
    private final MealDashMapper mapper;
    @Value("${keycloak.client-id}")
    private String CLIENT_ID;
    @Value("${keycloak.client-secret}")
    private String CLIENT_SECRET;
    @Value("${keycloak.server-url}")
    private String SERVER_URL;


    @Override
    public MealDashUser createUser(MealDashUser user) throws MealDashException {
        validateMealDashUserDetails(user);
        log.info("Done validating user details in keycloak adapter: {}", user);
        UserRepresentation userRepresentation = mapper.map(user);
        log.info("Mapped user ------{}", userRepresentation.getEmail());
        try {
            UsersResource users = keycloak.realm(REALM).users();
            try (Response response = users.create(userRepresentation)){
                log.info("Response ----- {}", response);
                if (response.getStatusInfo().equals(Response.Status.CONFLICT)){
                    log.error("{} - {} ---- Error occurred on attempting to create user on keycloak", Response.Status.CONFLICT, ErrorMessage.USER_ALREADY_EXIST);
                    throw new MealDashException(ErrorMessage.USER_ALREADY_EXIST);
                }
            }
            UserRepresentation createdUserRepresentation = getUserRepresentation(user, Boolean.TRUE);
            user.setId(createdUserRepresentation.getId());

            assignRole(user);
            log.info("User created on keycloak, role assigned : {}", createdUserRepresentation.getId());
        }catch (NotFoundException exception){
            log.error("{} - {} --- Error occurred on attempting to create user on keycloak", exception.getClass().getName(), exception.getMessage());
            throw new MealDashException(exception.getMessage());
        }
        return user;
    }

    @Override
    public Optional<MealDashUser> getUserByEmail(String email) throws UserAdapterException {
        MealDashValidator.validateEmail(email);
        List<UserRepresentation> foundUsers = getUserRepresentations(email);
        if (foundUsers.isEmpty()){
            log.warn("Could not find user with email {}", email);
            return Optional.empty();
        }
        UserRepresentation userRepresentation = foundUsers.get(0);
        MealDashUser mealDashUser = mapper.mapUserRepresentationToMealDashUser(userRepresentation);
        log.info("Found user with email {} ", email);
        return Optional.of(mealDashUser);
    }

    @Override
    public void deleteUser(MealDashUser mealDashUser) throws MealDashException {
        MealDashValidator.validateObjectInstance(mealDashUser, ErrorMessage.USER_CANNOT_BE_NULL);
        UserResource userResource = getUserResource(mealDashUser);
        try{
            userResource.remove();
            log.info("User deleted on keycloak: {}", mealDashUser.getId());
        }catch (NotFoundException exception) {
            log.info("deleteUser called with invalid user id: {}", mealDashUser.getId());
            throw new MealDashException(ErrorMessage.USER_DOES_NOT_EXIST);
        }
    }


    @Override
    public MealDashUser enableUserAccount(MealDashUser user) throws MealDashException {
        log.info("Enable user account verification started {} ", user);
        MealDashValidator.validateObjectInstance(user, ErrorMessage.USER_CANNOT_BE_NULL);
        MealDashValidator.validateEmail(user.getEmail());

        MealDashUser foundUser = getUserByEmail(user.getEmail().trim())
                .orElseThrow(() -> new MealDashException(ErrorMessage.USER_NOT_FOUND));

        if (foundUser.isEnabled()) throw new MealDashException(ErrorMessage.ACCOUNT_ALREADY_ENABLED);

        List<UserRepresentation> userRepresentations = getUserRepresentations(user);
        for (UserRepresentation userRepresentation : userRepresentations) {
            userRepresentation.setEnabled(true);
            UserResource userResource = getUserResourceByKeycloakId(userRepresentation.getId());
            userResource.update(userRepresentation);
        }
        UserRepresentation userRepresentation = mapper.map(foundUser);
        userRepresentation.setEnabled(Boolean.TRUE);
        userRepresentation.setEmailVerified(Boolean.TRUE);
        UserResource userResource = getUserResource(user);
        userResource.update(userRepresentation);
        user.setEnabled(Boolean.TRUE);
        user.setEmailVerified(Boolean.TRUE);
        log.info("After enabling on keycloak {}", user);
        return user;
    }

    @Override
    public MealDashUser createPassword(MealDashUser user) throws MealDashException {
        validateEmailAndPassword(user.getEmail(), user.getPassword());
        String email = user.getEmail().trim();
        String password = user.getPassword().trim();

        MealDashUser foundMealDashUser = getUserByEmail(email).
                orElseThrow(() -> new MealDashException(ErrorMessage.USER_NOT_FOUND));
        foundMealDashUser.setNewPassword(password);
        log.info("User ID for user creating password : {}", foundMealDashUser.getId());

//        if (foundMealDashUser.isEmailVerified() && foundMealDashUser.isEnabled()){
//            log.error("User already verified, can not create new password for this user {}", foundMealDashUser.getEmail());
//            throw new MealDashException(ErrorMessage.USER_PREVIOUSLY_VERIFIED);
//        }

//        foundMealDashUser = enableUserAccount(foundMealDashUser);
        setPassword(foundMealDashUser);
        foundMealDashUser.setPassword(password);
        foundMealDashUser.setEmail(email);

//        AccessTokenResponse response = login(foundMealDashUser);
//        foundMealDashUser.setAccessToken(response.getToken());
//        foundMealDashUser.setRefreshToken(response.getRefreshToken());
        return foundMealDashUser;
    }

    @Override
    public void setPassword(MealDashUser user) throws MealDashException {
        MealDashValidator.validateObjectInstance(user, ErrorMessage.USER_CANNOT_BE_NULL);
        MealDashValidator.validatePassword(user.getNewPassword());
        CredentialRepresentation credential = createCredentialRepresentation(user.getNewPassword());
        UserResource userResource = getUserResource(user);
        userResource.resetPassword(credential);
    }

    @Override
    public AccessTokenResponse login(MealDashUser user) throws MealDashException {
        MealDashValidator.validateEmail(user.getEmail());
        MealDashValidator.validatePassword(user.getPassword());
        log.info("User login credentials: {}", user.getEmail());

        try {
            Keycloak keycloakClient = getKeyCloak(user);
            TokenManager tokenManager = keycloakClient.tokenManager();
            log.info("Login successful for user {}", user.getEmail());
            return tokenManager.getAccessToken();
        }catch (NotAuthorizedException | BadRequestException exception){
            log.info("Error logging in user: {}", exception.getMessage());
            throw new MealDashException(ErrorMessage.INVALID_EMAIL_OR_PASSWORD);

        }

    }

    @Override
    public UserRepresentation getUserRepresentation(MealDashUser user, boolean exactMatch) throws MealDashException {
        MealDashValidator.validateObjectInstance(user, ErrorMessage.USER_CANNOT_BE_NULL);
        MealDashValidator.validateEmail(user.getEmail());

        return keycloak
                .realm(REALM)
                .users()
                .search(user.getEmail(), exactMatch)
                .stream()
                .findFirst()
                .orElseThrow(() -> new MealDashException(ErrorMessage.USER_NOT_FOUND));
    }


    private Keycloak getKeyCloak(MealDashUser user) {
        String email = user.getEmail().trim();
        String password = user.getPassword().trim();

        return KeycloakBuilder.builder()
                .grantType(OAuth2Constants.PASSWORD)
                .realm(REALM)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .username(email)
                .password(password)
                .serverUrl(SERVER_URL)
                .build();
    }

    private CredentialRepresentation createCredentialRepresentation(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(Boolean.FALSE);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }

    private void validateEmailAndPassword(String email, String password) throws UserAdapterException {
        MealDashValidator.validateEmail(email);
        MealDashValidator.validatePassword(password);
    }


    private void assignRole(MealDashUser user) throws MealDashException {
        try {
            RoleRepresentation roleRepresentation = getRoleRepresentation(user);
            UserResource userResource = getUserResource(user);
            userResource.roles().realmLevel().add(List.of(roleRepresentation));
        }catch (NotFoundException | MealDashException exception){
            throw new MealDashException(String.format("Resource not found: %s", exception.getMessage()));
        }
    }

    public UserResource getUserResource(MealDashUser user) throws MealDashException {
        MealDashValidator.validateObjectInstance(user, ErrorMessage.USER_CANNOT_BE_NULL);
        return keycloak
                .realm(REALM)
                .users()
                .get(user.getId());
    }

    private void validateMealDashUserDetails(MealDashUser user) throws MealDashException {
        MealDashValidator.validateObjectInstance(user, ErrorMessage.USER_CANNOT_BE_NULL);
        if (StringUtils.isEmpty(user.getEmail())
        || StringUtils.isEmpty(user.getFirstName())
        || StringUtils.isEmpty(user.getLastName())
        || StringUtils.isEmpty(user.getPhoneNumber())
        || user.getRole() == null
        || StringUtils.isEmpty(user.getRole().name()))
            throw new MealDashException(ErrorMessage.INVALID_REGISTRATION_DETAILS);
        getRoleRepresentation(user);
    }

    public RoleRepresentation getRoleRepresentation(MealDashUser user) throws MealDashException {
        if (user.getRole() == null || StringUtils.isEmpty(user.getRole().name())) throw new MealDashException(ErrorMessage.INVALID_ROLE);
        RoleRepresentation roleRepresentation;
        try {
            roleRepresentation = keycloak
                    .realm(REALM)
                    .roles()
                    .get(user.getRole().name().toUpperCase().trim())
                    .toRepresentation();
        }catch (NotFoundException exception){
            throw new MealDashException("Not Found: Role with name "+ user.getRole());
        }
        return roleRepresentation;
    }


    private List<UserRepresentation> getUserRepresentations(String email) {
        return keycloak
                .realm(REALM)
                .users()
                .search(email);
    }

    private UserResource getUserResourceByKeycloakId(String keycloakId) throws MealDashException {
        try {
            return keycloak.realm(REALM).users().get(keycloakId);
        }catch (Exception exception){
            throw new MealDashException(ErrorMessage.ERROR_FETCHING_USER_INFORMATION);
        }
    }

    public List<UserRepresentation> getUserRepresentations(MealDashUser user) {
        return keycloak
                .realm(REALM)
                .users()
                .search(user.getEmail());
    }
}