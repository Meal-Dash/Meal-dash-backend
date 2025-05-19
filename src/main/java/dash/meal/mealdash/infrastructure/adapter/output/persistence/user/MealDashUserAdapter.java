package dash.meal.mealdash.infrastructure.adapter.output.persistence.user;

import dash.meal.mealdash.application.output.user.UserOutputPort;
import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.exception.UserAdapterException;
import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.validation.MealDashValidator;
import dash.meal.mealdash.infrastructure.adapter.output.mapper.MealDashMapper;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.MealDashEntity;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class MealDashUserAdapter implements UserOutputPort {
    private final UserRepository userRepository;
    private final MealDashMapper mealDashMapper;


    @Override
    public MealDashUser save(MealDashUser mealDashUser) throws MealDashException {
        MealDashValidator.validateObjectInstance(mealDashUser, ErrorMessage.USER_CANNOT_BE_NULL);
        mealDashUser.validate();

        if (ObjectUtils.isEmpty(mealDashUser.getId()) &&
                userRepository.existsByEmail(mealDashUser.getEmail())) {
            throw new UserAdapterException(ErrorMessage.EMAIL_ALREADY_EXIST);
        }

          log.info("Attempting to save user with ID: {} and email: {}", mealDashUser.getId(), mealDashUser.getEmail());

            log.info("User object before conversion: {}", mealDashUser);

            MealDashEntity mealDashEntity = mealDashMapper.toUserEntity(mealDashUser);
            log.info("Entity object before save: {}", mealDashEntity);

            MealDashEntity savedMealDashEntity = userRepository.save(mealDashEntity);
            log.info("Entity saved with ID: {}", savedMealDashEntity.getId());

            return mealDashMapper.toUser(savedMealDashEntity);

    }

    @Override
    public MealDashUser findById(String id) throws UserAdapterException {
        return userRepository.findById(id)
                             .map(mealDashMapper::toUser)
                             .orElseThrow(() -> new UserAdapterException(ErrorMessage.USER_NOT_FOUND));
    }

    @Override
    public MealDashUser findByEmail(String email) throws UserAdapterException {
        return userRepository.findByEmail(email)
                             .map(mealDashMapper::toUser)
                             .orElseThrow(() -> new UserAdapterException(ErrorMessage.USER_NOT_FOUND));

    }

    @Override
    public List<MealDashUser> findAll() throws UserAdapterException {
       List<MealDashUser> mealDashUsers = userRepository.findAll()
                             .stream()
                             .map(mealDashMapper::toUser)
                             .collect(Collectors.toList());

       if (mealDashUsers.isEmpty()) throw new UserAdapterException(ErrorMessage.NO_USERS_FOUND);
       return mealDashUsers;
    }

    @Override
    public void deleteById(String id) throws UserAdapterException {
        log.info("Checking if user with ID {} exists", id);

        boolean exists = userRepository.existsById(id);
        log.info("User exists: {}", exists);

        if (!exists) {
            throw new UserAdapterException(ErrorMessage.USER_NOT_FOUND);
        }

        log.info("Deleting user with ID: {}", id);
        userRepository.deleteById(id);
        log.info("User with ID {} has been deleted", id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void deleteAll() {
        log.info("Deleting all users...");
        userRepository.deleteAll();
        log.info("All users have been deleted");    }
}
