package dash.meal.mealdash.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpEntity {
    @Id
    @UuidGenerator
    private String id;

    private String token;

    private String email;

    private String emailToken;

    private LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);

    public OtpEntity(String token, String email, LocalDateTime createdAt) {
        this.token = token;
        this.email = email;
        this.createdAt = createdAt;
    }
}
