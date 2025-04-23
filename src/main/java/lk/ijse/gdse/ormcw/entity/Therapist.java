package lk.ijse.gdse.ormcw.entity;

import jakarta.persistence.*;
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
    private int contactNumber;

    @ManyToOne
    @JoinColumn(name = "programId")
    private TherapyProgram therapyProgram;


}
