package dash.meal.mealdash.infrastructure.adapter.input.data.response;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class OtpResponse {

    private String id;

    private String token;

    private String email;

    private String emailToken;

    private LocalDateTime createdAt;

    public OtpResponse(String token, String email, LocalDateTime createdAt) {
        this.token = token;
        this.email = email;
        this.createdAt = createdAt;
    }
}
