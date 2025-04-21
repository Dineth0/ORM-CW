package lk.ijse.gdse.ormcw.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse.ormcw.bo.BOFactory;
import lk.ijse.gdse.ormcw.bo.custom.PatientBO;
import lk.ijse.gdse.ormcw.dto.PatientDTO;
import lk.ijse.gdse.ormcw.tm.PatientTM;
import lk.ijse.gdse.ormcw.tm.UserTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class PatientFormController implements Initializable {

    @FXML
    private TableView<PatientTM> PatientTable;


    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<PatientTM, String> colage;

    @FXML
    private TableColumn<PatientTM,Integer> colcontact;

    @FXML
    private TableColumn<PatientTM,String> colid;

    @FXML
    private TableColumn<PatientTM,String> colmedical;

    @FXML
    private TableColumn<PatientTM,String> colname;

    @FXML
    private TableColumn<PatientTM,Date> colregister;

    @FXML
    private Label lblId;

    @FXML
    private Label lblbirthday;

    @FXML
    private Label lblregister;

    @FXML
    private Label lblrole;

    @FXML
    private AnchorPane patientpage;

    @FXML
    private DatePicker registerdatepicker;

    @FXML
    private TextField txtcontactnumber;

    @FXML
    private TextField txtmedical;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtsearch;

    @FXML
    private Button register;

    @FXML
    private TextField txtage;

    PatientBO patientBO = (PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colname.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colage.setCellValueFactory(new PropertyValueFactory<>("Age"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("ContactNumber"));
        colmedical.setCellValueFactory(new PropertyValueFactory<>("MedicalHistory"));


        try {
            LoadNextID();
            loadTableData();
            refreshPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void AppointmentOnAction(ActionEvent event) {

    }



    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        String ID = lblId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDelete = patientBO.delete(ID);
            if (isDelete) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Labor deleted...!").show();

            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Labor...!").show();

            }
        }
    }

    @FXML
    void RegisterDatePickerOnAction(ActionEvent event) {

            LocalDate localDate = registerdatepicker.getValue();
            String pattern = "yyyy-MM-dd";
            String datePattern = localDate.format(DateTimeFormatter.ofPattern(pattern));
            lblregister.setText(datePattern);

        }

    @FXML
    void ResetOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void SaveOnAction(ActionEvent event) {
        String patientId = lblId.getText();
        String name = txtname.getText();
        String age = txtage.getText();
        int contactNumber = Integer.parseInt(txtcontactnumber.getText());
        String medicalHistory = txtmedical.getText();

        PatientDTO patientDTO = new PatientDTO(patientId, name, age, contactNumber, medicalHistory);
        try {
            boolean isSaved = patientBO.save( patientDTO);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"User Saved SUCCESSFULLY 😎").show();
                refreshPage();
            }
            else {
                new Alert(Alert.AlertType.ERROR,"PLEASE TRY AGAIN 😥").show();
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"duplicate Id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void SearchOnAction(ActionEvent event) {

    }

    @FXML
    void TableOnClicked(MouseEvent event) {
        PatientTM patientTM = PatientTable.getSelectionModel().getSelectedItem();
        if (patientTM != null) {
            lblId.setText(patientTM.getPatientId());
            txtname.setText(patientTM.getName());
            txtage.setText(patientTM.getAge());
            txtcontactnumber.setText(String.valueOf(Integer.valueOf(patientTM.getContactNumber())));
            txtmedical.setText(patientTM.getMedicalHistory());



            btndelete.setDisable(false);
            btnsave.setDisable(true);
            btnupdate.setDisable(false);
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) {
        String patientId = lblId.getText();
        String name = txtname.getText();
        String birthday = lblbirthday.getText();
        int contactNumber = Integer.parseInt(txtcontactnumber.getText());
        String medicalHistory = txtmedical.getText();


        PatientDTO patientDTO = new PatientDTO(patientId, name, birthday, contactNumber, medicalHistory);
        try {
            boolean isUpdated = patientBO.update( patientDTO);
            if(isUpdated){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION,"User Saved SUCCESSFULLY 😎").show();

            }
            else {
                new Alert(Alert.AlertType.ERROR,"PLEASE TRY AGAIN 😥").show();
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"duplicate Id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void LoadNextID() throws SQLException, IOException {
        String nextID = patientBO.getNextId();
        lblId.setText(nextID);
    }
    private void loadTableData() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<PatientDTO> patientDTOS = (ArrayList<PatientDTO>) patientBO.getAll();
        ObservableList<PatientTM> patientTMs = FXCollections.observableArrayList();

        for (PatientDTO patientDTO : patientDTOS) {
            PatientTM patientTM = new PatientTM(
                    patientDTO.getPatientId(),
                    patientDTO.getName(),
                    patientDTO.getAge(),
                    patientDTO.getContactNumber(),
                    patientDTO.getMedicalHistory()


            );
            patientTMs.add(patientTM);
        }
        PatientTable.setItems(patientTMs);
    }
    void refreshPage() throws SQLException, ClassNotFoundException, IOException {
        LoadNextID();
        loadTableData();

        btndelete.setDisable(true);
        btnsave.setDisable(false);
        btnupdate.setDisable(true);

        txtname.setText("");
        txtage.setText("");
        txtcontactnumber.setText("");
        txtmedical.setText("");

    }
    @FXML
    void PatientRegistrationsOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PatientRegistration.fxml"));
        Parent load = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.setTitle("Registration");

        stage.initModality(Modality.APPLICATION_MODAL);

        Window underWindow = register.getScene().getWindow();
        stage.initOwner(underWindow);

        stage.showAndWait();
    }

}

