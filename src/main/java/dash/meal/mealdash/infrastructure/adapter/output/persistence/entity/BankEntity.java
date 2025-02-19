package dash.meal.mealdash.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankEntity {
    @Id
    private String id;
    private String bankName;
    private String cvv;
    private String accountNumber;
    private String accountName;
    private String cardNumber;
    private String pin;
}
