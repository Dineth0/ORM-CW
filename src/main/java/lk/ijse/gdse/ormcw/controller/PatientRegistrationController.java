package lk.ijse.gdse.ormcw.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.ormcw.bo.BOFactory;
import lk.ijse.gdse.ormcw.bo.custom.PatientBO;
import lk.ijse.gdse.ormcw.bo.custom.PatientRegistrationBO;
import lk.ijse.gdse.ormcw.bo.custom.TherapyProgramBO;
import lk.ijse.gdse.ormcw.dto.PatientDTO;
import lk.ijse.gdse.ormcw.dto.PatientRegistrationDTO;
import lk.ijse.gdse.ormcw.dto.TherapyProgramDTO;
import lk.ijse.gdse.ormcw.tm.PatientRegistrationTM;
import lk.ijse.gdse.ormcw.util.Regex;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PatientRegistrationController implements Initializable {

    @FXML
    private TableView<PatientRegistrationTM> RegistrationTable;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<PatientRegistrationTM, Date> coldate;

    @FXML
    private TableColumn<PatientRegistrationTM,String> colpatid;

    @FXML
    private TableColumn<PatientRegistrationTM,String> colproid;

    @FXML
    private TableColumn<PatientRegistrationTM,String> colregid;

    @FXML
    private TableColumn<PatientRegistrationTM,Integer> colcount;

    @FXML
    private TableColumn<PatientRegistrationTM, Double> colbalance;

    @FXML
    private TableColumn<PatientRegistrationTM, Double> colfee;

    @FXML
    private ComboBox<String> combopatientid;

    @FXML
    private ComboBox<String> comboprogramId;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Label lbldate;

    @FXML
    private Label lblid;

    @FXML
    private Label lblpatientid;

    @FXML
    private Label lblprogramid;

    @FXML
    private Label lbltherapistname;

    @FXML
    private Label lblcount;

    @FXML
    private Label lblamount;

    @FXML
    private AnchorPane patientpage;

    @FXML
    private TextField txtfee;

    PatientRegistrationBO patientRegistrationBO = (PatientRegistrationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT_REGISTRATION);
    PatientBO patientBO = (PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT);
    TherapyProgramBO therapyProgramBO = (TherapyProgramBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPYOROGRAM);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colregid.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        colpatid.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colproid.setCellValueFactory(new PropertyValueFactory<>("programId"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        colfee.setCellValueFactory(new PropertyValueFactory<>("registerFee"));
        colbalance.setCellValueFactory(new PropertyValueFactory<>("balance"));

        try {
            LoadNextID();
            loadProgramIDs();
            loadPatientIDs();
            loadTableData();
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load LeaveID").show();
        }
    }

    @FXML
    void ComboPatientIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedID = combopatientid.getValue();
        PatientDTO patientDTO = patientBO.findById(selectedID);

        if (patientDTO != null) {
            lblpatientid.setText(patientDTO.getName());
        }
    }

    @FXML
    void ComboProgramIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedID = comboprogramId.getValue();
        TherapyProgramDTO therapyProgramDTO = therapyProgramBO.findById(selectedID);

        if (therapyProgramDTO != null) {
            lblprogramid.setText(therapyProgramDTO.getProgramName());
            lblamount.setText(String.valueOf(therapyProgramDTO.getCost()));
        }
    }

    @FXML
    void DatePickerOnAction(ActionEvent event) {
        LocalDate localDate = datepicker.getValue();
        String pattern = "yyyy-MM-dd";
        String datePattern = localDate.format(DateTimeFormatter.ofPattern(pattern));
        lbldate.setText(datePattern);
    }

    @FXML
    void DeleteOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        String ID = lblid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDelete = patientRegistrationBO.delete(ID);
            if (isDelete) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "PatientRegistration deleted...!").show();

            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete PatientRegistration...!").show();

            }
        }
    }

    @FXML
    void ResetOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void SaveOnAction(ActionEvent event) {
        String registrationId = lblid.getText();
        String patientId = combopatientid.getValue();
        String programId = comboprogramId.getValue();
        String registrationDate = lbldate.getText();
        double registerFee = Double.parseDouble(txtfee.getText());

        double amount = Double.parseDouble(lblamount.getText());

        double balance = amount-registerFee;

        if (isValid()) {

            try {
                PatientRegistrationDTO patientRegistrationDTO = new PatientRegistrationDTO(
                        registrationId, patientId, programId, registrationDate, registerFee, balance
                );
                boolean isRegistered = patientRegistrationBO.save(patientRegistrationDTO);

                if (isRegistered) {
                    refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, "PatientRegistration Saved SUCCESSFULLY 😎").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "PLEASE TRY AGAIN 😥").show();
                }
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Duplicate ID").show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    void TableOnClicked(MouseEvent event) {
        PatientRegistrationTM patientRegistrationTM = RegistrationTable.getSelectionModel().getSelectedItem();
        if (patientRegistrationTM != null) {
            lblid.setText(patientRegistrationTM.getRegistrationId());
            combopatientid.setValue(patientRegistrationTM.getPatientId());
            comboprogramId.setValue(patientRegistrationTM.getProgramId());
            lbldate.setText(String.valueOf(patientRegistrationTM.getRegistrationDate()));

            txtfee.setText(String.valueOf(patientRegistrationTM.getRegisterFee()));



           // btndelete.setDisable(false);
            btnsave.setDisable(true);
            //btnupdate.setDisable(false);
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) {
        String registrationId = lblid.getText();
        String patientId = combopatientid.getValue();
        String programId = comboprogramId.getValue();
        String registrationDate = lbldate.getText();
        double registerFee = Double.parseDouble(txtfee.getText());

        double amount = Double.parseDouble(lblamount.getText());

        double balance = amount-registerFee;

        try{
            PatientRegistrationDTO patientRegistrationDTO = new PatientRegistrationDTO(
                    registrationId,patientId,programId,registrationDate,registerFee,balance
            );
            boolean isRegistered = patientRegistrationBO.update(patientRegistrationDTO);

            if (isRegistered) {
                refreshPage();  // UI එක refresh කරන්න
                new Alert(Alert.AlertType.INFORMATION, "PatientRegistration Updated SUCCESSFULLY 😎").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "PLEASE TRY AGAIN 😥").show();
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Duplicate ID").show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadProgramIDs() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<String> programIds = therapyProgramBO.getAllProgramIDs();
        comboprogramId.getItems().addAll(programIds);
    }
    private void loadPatientIDs() throws SQLException, IOException, ClassNotFoundException {
        ArrayList<String> patientIds = patientBO.getAllPatientIds();
        combopatientid.getItems().addAll(patientIds);
    }
    private void LoadNextID() throws SQLException, IOException {
        String nextID = patientRegistrationBO.getNextId();
        lblid.setText(nextID);
    }
    private void loadTableData() {
        try {

            List<PatientRegistrationDTO> patientRegistrations = patientRegistrationBO.getAll();
            ObservableList<PatientRegistrationTM> registrationList = FXCollections.observableArrayList();

            for (PatientRegistrationDTO registrationDTO : patientRegistrations) {

                registrationList.add(new PatientRegistrationTM(
                                        registrationDTO.getRegistrationId(),
                                        registrationDTO.getPatientId(),
                                        registrationDTO.getProgramId(),
                        registrationDTO.getRegistrationDate(),
                        registrationDTO.getRegisterFee(),
                        registrationDTO.getBalance()
                                ));
            }


            Platform.runLater(() -> {
                RegistrationTable.setItems(registrationList);
            });

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    void refreshPage() throws SQLException, ClassNotFoundException, IOException {
        LoadNextID();
        loadTableData();

       // btndelete.setDisable(true);
        btnsave.setDisable(false);
      //  btnupdate.setDisable(true);

        lblpatientid.setText("");
        lblprogramid.setText("");
        lbldate.setText("");



    }
    @FXML
    void txtfeeKeyReleasedOnAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.gdse.ormcw.util.TextField.FEE, txtfee);

    }
    public boolean isValid(){
        if(!Regex.setTextColor(lk.ijse.gdse.ormcw.util.TextField.FEE, txtfee)) return false;

        return true;
    }

}

