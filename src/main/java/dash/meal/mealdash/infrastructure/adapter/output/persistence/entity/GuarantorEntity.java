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
public class GuarantorEntity {
    @Id
    private String id;
    private String guarantorPhoneNumber;
    private String guarantorName;
    private String guarantorEmail;
    private GuarantorRelation guarantorRelation;
}
