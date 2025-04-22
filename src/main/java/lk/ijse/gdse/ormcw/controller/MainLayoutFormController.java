package lk.ijse.gdse.ormcw.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.ormcw.bo.BOFactory;
import lk.ijse.gdse.ormcw.bo.custom.PatientBO;
import lk.ijse.gdse.ormcw.bo.custom.TherapistBO;
import lk.ijse.gdse.ormcw.bo.custom.TherapyProgramBO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainLayoutFormController implements Initializable {

    @FXML
    private AnchorPane MainLayout;

    @FXML
    private Label lbltotalpatient;

    @FXML
    private Label lbltherapists;

    @FXML
    private Label lblprogram;

    PatientBO patientBO = (PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT);
    TherapistBO therapistBO = (TherapistBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPIST);
    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPYOROGRAM);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadPatientCount();
        loadTherapistCount();
        loadProgramCount();
    }

    public void SaveOnAction(ActionEvent actionEvent) {
    }

    public void UpdateOnAction(ActionEvent actionEvent) {
    }

    public void DeleteOnAction(ActionEvent actionEvent) {
    }
    private void loadPatientCount() {
        try {

            int totalLabors = patientBO.getTotalPatients();
            lbltotalpatient.setText(String.valueOf(totalLabors));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadTherapistCount() {
        try {

            int totalTherapists = therapistBO.getTotalTherapists();
            lbltherapists.setText(String.valueOf(totalTherapists));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadProgramCount() {
        try {

            int totalPrograms = therapyProgramBO.getTotalPrograms();
            lblprogram.setText(String.valueOf(totalPrograms));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}