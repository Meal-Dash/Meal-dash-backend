package dash.meal.mealdash.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MealDashException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleMealDashException(MealDashException exception){
        return new ResponseEntity<>(errorResponseBuilder(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OtpAdapterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleOtpException(OtpAdapterException exception){
        return new ResponseEntity<>(errorResponseBuilder(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private static ExceptionResponse errorResponseBuilder(String message) {
        return ExceptionResponse.builder()
                .message(message)
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.BAD_REQUEST.name())
                .build();
    }
}
