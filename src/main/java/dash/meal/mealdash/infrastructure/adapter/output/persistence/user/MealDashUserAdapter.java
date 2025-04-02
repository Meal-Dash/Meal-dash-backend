package dash.meal.mealdash.infrastructure.adapter.output.persistence.user;

import dash.meal.mealdash.application.output.UserOutputPort;
import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.exception.MealDashUserAdapterException;
import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.validation.MealDashValidation;
import dash.meal.mealdash.infrastructure.adapter.output.mapper.MealDashMapper;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.MealDashEntity;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class MealDashUserAdapter implements UserOutputPort {
    private final UserRepository userRepository;
    private final MealDashMapper mealDashMapper;


    @Override
    public MealDashUser save(MealDashUser mealDashUser) throws MealDashUserAdapterException, MealDashException {
        MealDashValidation.validateObjectInstance(mealDashUser, ErrorMessage.USER_CANNOT_BE_NULL);
        mealDashUser.validate();

            log.info("Attempting to save user with ID: {} and email: {}",
                    mealDashUser.getId(), mealDashUser.getEmail());


            Optional<MealDashEntity> existingUser = userRepository.findByEmail(mealDashUser.getEmail());
            if (existingUser.isPresent()) {
                log.warn("User with email {} already exists with ID: {}",
                        mealDashUser.getEmail(), existingUser.get().getId());
                throw new MealDashUserAdapterException(ErrorMessage.USER_ALREADY_EXIST);
            }

            log.info("User object before conversion: {}", mealDashUser);

            MealDashEntity mealDashEntity = mealDashMapper.toUserEntity(mealDashUser);
            log.info("Entity object before save: {}", mealDashEntity.toString());

            MealDashEntity savedMealDashEntity = userRepository.save(mealDashEntity);
            log.info("Entity saved with ID: {}", savedMealDashEntity.getId());

            return mealDashMapper.toUser(savedMealDashEntity);

    }

    @Override
    public MealDashUser findById(String id) throws MealDashUserAdapterException {
        return userRepository.findById(id)
                             .map(mealDashMapper::toUser)
                             .orElseThrow(() -> new MealDashUserAdapterException(ErrorMessage.USER_NOT_FOUND));
    }

    @Override
    public MealDashUser findByEmail(String email) throws MealDashUserAdapterException {
        return userRepository.findByEmail(email)
                             .map(mealDashMapper::toUser)
                             .orElseThrow(() -> new MealDashUserAdapterException(ErrorMessage.USER_NOT_FOUND));

    }

    @Override
    public List<MealDashUser> findAll() throws MealDashUserAdapterException {
       List<MealDashUser> mealDashUsers = userRepository.findAll()
                             .stream()
                             .map(mealDashMapper::toUser)
                             .collect(Collectors.toList());

       if (mealDashUsers.isEmpty()) throw new MealDashUserAdapterException(ErrorMessage.NO_USERS_FOUND);
       return mealDashUsers;
    }

    @Override
    public void deleteById(String id) throws MealDashUserAdapterException {
        log.info("Checking if user with ID {} exists", id);

        boolean exists = userRepository.existsById(id);
        log.info("User exists: {}", exists);

        if (!exists) {
            throw new MealDashUserAdapterException(ErrorMessage.USER_NOT_FOUND);
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
