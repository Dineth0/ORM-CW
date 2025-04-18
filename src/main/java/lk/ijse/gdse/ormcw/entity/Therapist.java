package lk.ijse.gdse.ormcw.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "Therapist")
public class Therapist implements SuperEntity{
    @Id
    private String therapistId;
    private String therapistName;
    private String specialization;
    private String availability;
}
