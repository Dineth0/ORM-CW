package lk.ijse.gdse.ormcw.entity;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "Patient")
public class Patient implements SuperEntity{
    @Id
    @Column(name = "PatientID")
    private String patientId;
    private String name;
    private String age;
    private int contactNumber;
    private String medicalHistory;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Patient_Registration> registrations;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Therapy_Session> therapy_sessions;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;



    public Patient(String patientId, String name, String age, Integer contactNumber, String medicalHistory) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.contactNumber = (contactNumber != null) ? contactNumber : 0; // Null නම් Default Value දෙන්න
        this.medicalHistory = medicalHistory;
    }


//    @OneToMany(mappedBy = "patient")
//    private List<Appointment> appointments;




//    public Patient(String patientId, String name, String birthday, int contactNumber, String medicalHistory) {
//        System.out.println("Inside Patient Constructor - ID: " + patientId); // Debugging
//        this.patientId = patientId;
//        this.name = name;
//        this.birthday = Date.valueOf(birthday);
//        this.contactNumber = contactNumber;
//        this.medicalHistory = medicalHistory;
//    }


}
