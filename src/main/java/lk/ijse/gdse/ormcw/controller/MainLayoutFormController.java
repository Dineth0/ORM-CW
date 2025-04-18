package lk.ijse.gdse.ormcw.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.ormcw.bo.BOFactory;
import lk.ijse.gdse.ormcw.bo.custom.PatientBO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainLayoutFormController implements Initializable {

    @FXML
    private AnchorPane MainLayout;

    @FXML
    private Label lbltotalpatient;

    PatientBO patientBO = (PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLaborCount();
    }

    public void SaveOnAction(ActionEvent actionEvent) {
    }

    public void UpdateOnAction(ActionEvent actionEvent) {
    }

    public void DeleteOnAction(ActionEvent actionEvent) {
    }
    private void loadLaborCount() {
        try {

            int totalLabors = patientBO.getTotalPatients();
            lbltotalpatient.setText(String.valueOf(totalLabors));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}