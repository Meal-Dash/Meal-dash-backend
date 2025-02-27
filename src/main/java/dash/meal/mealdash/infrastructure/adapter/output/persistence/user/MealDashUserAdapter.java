package dash.meal.mealdash.infrastructure.adapter.output.persistence.user;

import dash.meal.mealdash.application.output.UserOutputPort;
import dash.meal.mealdash.domain.exception.ErrorMessage;
import dash.meal.mealdash.domain.exception.MealDashUserAdapterException;
import dash.meal.mealdash.domain.model.User;
import dash.meal.mealdash.infrastructure.adapter.mapper.MealDashMapper;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.UserEntity;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public User save(User user) throws MealDashUserAdapterException {
       if (user == null) throw new MealDashUserAdapterException(ErrorMessage.USER_CANNOT_BE_NULL);
       log.info("user {}",  user);
       user.validateFields(user);
        log.info("Attempting to save user with email: {}", user.getEmail());
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.warn("User with email {} already exists", user.getEmail());
            throw new MealDashUserAdapterException(ErrorMessage.USER_ALREADY_EXIST);
        }
       UserEntity userEntity = mealDashMapper.toUserEntity(user);
       UserEntity savedUserEntity = userRepository.save(userEntity);
       return mealDashMapper.toUser(savedUserEntity);

    }

    @Override
    public User findById(String id) throws MealDashUserAdapterException {
        return userRepository.findById(id)
                             .map(mealDashMapper::toUser)
                             .orElseThrow(() -> new MealDashUserAdapterException(ErrorMessage.USER_NOT_FOUND));
    }

    @Override
    public User findByEmail(String email) throws MealDashUserAdapterException {
        return userRepository.findByEmail(email)
                             .map(mealDashMapper::toUser)
                             .orElseThrow(() -> new MealDashUserAdapterException(ErrorMessage.USER_NOT_FOUND));

    }

    @Override
    public List<User> findAll() throws MealDashUserAdapterException {
       List<User> users = userRepository.findAll()
                             .stream()
                             .map(mealDashMapper::toUser)
                             .collect(Collectors.toList());

       if (users.isEmpty()) throw new MealDashUserAdapterException(ErrorMessage.NO_USERS_FOUND);
       return users;
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
    public void deleteAll() {
        log.info("Deleting all users...");
        userRepository.deleteAll();
        log.info("All users have been deleted");    }
}
