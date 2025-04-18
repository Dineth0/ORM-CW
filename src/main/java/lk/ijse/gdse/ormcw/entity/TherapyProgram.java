package lk.ijse.gdse.ormcw.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "Therapy_Program")
public class TherapyProgram implements SuperEntity{
    @Id
    private String programId;
    private String programName;
    private String duration;
    private double cost;
    private String Description;

    @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Patient_Registration> patientRegistrations;

    public TherapyProgram(String programId, String programName, String duration, double cost, String Description) {
        this.programId = programId;
        this.programName = programName;
        this.duration = duration;
        this.cost = cost;
        this.Description = Description;
    }


}
