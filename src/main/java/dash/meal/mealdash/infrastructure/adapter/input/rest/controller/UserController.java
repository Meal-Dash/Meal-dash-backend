package dash.meal.mealdash.infrastructure.adapter.input.rest.controller;

import dash.meal.mealdash.application.input.user.UserUseCase;
import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.infrastructure.adapter.input.data.request.SignupRequest;
import dash.meal.mealdash.infrastructure.adapter.input.rest.mapper.UserRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
@Validated
public class UserController {
    private final UserUseCase userUseCase;
    private final UserRestMapper userRestMapper;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Valid SignupRequest signupRequest) throws MealDashException {
        MealDashUser user = userRestMapper.map(signupRequest);
        String response = userUseCase.signUp(user);

        return ResponseEntity.ok(response);
    }

}
