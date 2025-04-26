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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse.ormcw.bo.BOFactory;
import lk.ijse.gdse.ormcw.bo.custom.PatientBO;
import lk.ijse.gdse.ormcw.bo.custom.TherapistBO;
import lk.ijse.gdse.ormcw.bo.custom.TherapySessionBO;
import lk.ijse.gdse.ormcw.dto.PatientDTO;
import lk.ijse.gdse.ormcw.dto.TherapistDTO;
import lk.ijse.gdse.ormcw.dto.TherapySessionDTO;
import lk.ijse.gdse.ormcw.entity.Therapy_Session;
import lk.ijse.gdse.ormcw.tm.TherapySessionTM;
import lk.ijse.gdse.ormcw.util.Regex;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TherapySessionController implements Initializable {

    @FXML
    private TableView<TherapySessionTM> SessionTable;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<TherapySessionTM, Date> coldate;

    @FXML
    private TableColumn<TherapySessionTM, String> colid;

    @FXML
    private TableColumn<TherapySessionTM, String> colpatientid;

    @FXML
    private TableColumn<TherapySessionTM, String> colstatus;

    @FXML
    private TableColumn<TherapySessionTM, String> coltherapist;

    @FXML
    private TableColumn<TherapySessionTM, String> coltime;

    @FXML
    private ComboBox<String> combopatientid;

    @FXML
    private TableColumn<TherapySessionTM,String> colpayment;

    @FXML
    private ComboBox<String> combopayment;
    private final String[] payment = {"Completed","Pending"};

    @FXML
    private ComboBox<String> combostatus;
    private final String[] Status = {"Scheduled", "Completed", "Cancelled"};


    @FXML
    private ComboBox<String> combotherapistId;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Label lbldate;

    @FXML
    private Label lblid;

    @FXML
    private Label lblpatientid;

    @FXML
    private Label lblstatus;

    @FXML
    private Label lbltherapistid;

    @FXML
    private AnchorPane patientpage;
    @FXML
    private Button btnsearch;


    @FXML
    private TextField txttime;

    @FXML
    private Label lblpatientname;

    @FXML
    private Label lbltherapistname;

    @FXML
    private Button btnpayment;

    @FXML
    private Label lblpayment;

    @FXML
    private TextField txtsearch;


    @FXML
    private TextField txttherapistsearch;

    @FXML
    private Button btntherapistsearch;

    private Stage stage;
    private SearchController searchController;

    TherapistBO therapistBO = (TherapistBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPIST);
    TherapySessionBO therapySessionBO = (TherapySessionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPY_SESSION);
    PatientBO patientBO = (PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        combostatus.getItems().addAll(Status);

        colid.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        coltime.setCellValueFactory(new PropertyValueFactory<>("sessionTime"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        coltherapist.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colpatientid.setCellValueFactory(new PropertyValueFactory<>("patientId"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Search.fxml"));

        try {
            LoadNextID();
            loadTherapistIDs();
            loadPatientIDs();
            loadTableData();
            refreshPage();
            Parent root = loader.load();
            searchController = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Session ID").show();
        }

    }

    @FXML
    void ComboPatientIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedID = combopatientid.getValue();
        PatientDTO patientDTO = patientBO.findById(selectedID);

        if (patientDTO != null) {
            lblpatientname.setText(patientDTO.getName());
        }
    }

    @FXML
    void ComboStatusOnAction(ActionEvent event) {
        String SelectedValue = combostatus.getValue();
        lblstatus.setText(SelectedValue);

    }

//    @FXML
//    void ComboPaymentOnAction(ActionEvent event) {
//        String SelectedValue = combopayment.getValue();
//        lblpayment.setText(SelectedValue);
//    }

    @FXML
    void ComboTherapistIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedID = combotherapistId.getValue();
        TherapistDTO therapistDTO = therapistBO.findById(selectedID);

        if (therapistDTO != null) {
            lbltherapistname.setText(therapistDTO.getTherapistName());
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
    void DeleteOnAction(ActionEvent event) {

    }

    @FXML
    void ResetOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void SaveOnAction(ActionEvent event) {
        String sessionId = lblid.getText();
        String sessionDate = lbldate.getText();
        String sessionTime = txttime.getText();
        String status = combostatus.getValue();
        String therapistId = combotherapistId.getValue();
        String patientId = combopatientid.getValue();

        if(isValid()) {
            try {
                TherapySessionDTO therapySessionDTO = new TherapySessionDTO(
                        sessionId, sessionDate, sessionTime, status, therapistId, patientId
                );

                boolean isRegistered = therapySessionBO.save(therapySessionDTO);

                if (isRegistered) {
                    refreshPage();  // UI එක refresh කරන්න
                    new Alert(Alert.AlertType.INFORMATION, "Session Saved SUCCESSFULLY 😎").show();
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
        TherapySessionTM therapySessionTM = SessionTable.getSelectionModel().getSelectedItem();
        if (therapySessionTM != null) {
            lblid.setText(therapySessionTM.getSessionId());
            lbldate.setText(String.valueOf(therapySessionTM.getSessionDate()));
            txttime.setText(therapySessionTM.getSessionTime());
            combostatus.setValue(therapySessionTM.getStatus());
            combopatientid.setValue(therapySessionTM.getPatientId());
            combotherapistId.setValue(therapySessionTM.getTherapistId());





            btnsave.setDisable(true);
            btnupdate.setDisable(false);
        }

    }



    @FXML
    void UpdateOnAction(ActionEvent event) {
        String sessionId = lblid.getText();
        String sessionDate = lbldate.getText();
        String sessionTime = txttime.getText();
        String status = combostatus.getValue();
        String therapistId = combotherapistId.getValue();
        String patientId = combopatientid.getValue();


        if(isValid()) {
            try {
                TherapySessionDTO therapySessionDTO = new TherapySessionDTO(
                        sessionId, sessionDate, sessionTime, status, therapistId, patientId
                );

                boolean isRegistered = therapySessionBO.update(therapySessionDTO);

                if (isRegistered) {
                    refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, "Session Saved SUCCESSFULLY 😎").show();
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
    private void loadTherapistIDs() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<String> therapistIds = therapistBO.getAllTherapistIDs();
        combotherapistId.getItems().addAll(therapistIds);
    }
    private void loadPatientIDs() throws SQLException, IOException, ClassNotFoundException {
        ArrayList<String> patientIds = patientBO.getAllPatientIds();
        combopatientid.getItems().addAll(patientIds);
    }
    private void LoadNextID() throws SQLException, IOException {
        String nextID = therapySessionBO.getNextId();
        lblid.setText(nextID);
    }
    private void loadTableData() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<TherapySessionDTO> therapySessionDTOS = (ArrayList<TherapySessionDTO>) therapySessionBO.getAll();
        ObservableList<TherapySessionTM> therapySessionTMS = FXCollections.observableArrayList();

        for (TherapySessionDTO therapySessionDTO : therapySessionDTOS) {
            TherapySessionTM therapySessionTM = new TherapySessionTM(
                    therapySessionDTO.getSessionId(),
                    therapySessionDTO.getSessionDate(),
                    therapySessionDTO.getSessionTime(),
                    therapySessionDTO.getStatus(),
                    therapySessionDTO.getTherapistId(),
                    therapySessionDTO.getPatientId()


            );
            therapySessionTMS.add(therapySessionTM);
        }
        SessionTable.setItems(therapySessionTMS);
    }
    void refreshPage() throws SQLException, ClassNotFoundException, IOException {
        LoadNextID();
        loadTableData();


        btnsave.setDisable(false);
        btnupdate.setDisable(true);

        txttime.setText("");
        lbldate.setText("");
        lblstatus.setText("");
        lbltherapistname.setText("");
        lblpatientname.setText("");


    }

    public void SearchOnAction(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        String input = txtsearch.getText().trim();

        try {
            List<Therapy_Session> sessionList = null;


            if (input.matches("T\\d+")) {
                sessionList = therapySessionBO.searchTherapistTherapySession(input);
            }


            if (sessionList == null || sessionList.isEmpty()) {
                sessionList = therapySessionBO.searchTherapySession(input);
            }


            if (sessionList == null || sessionList.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "No sessions found for: " + input).show();
                return;
            }

            ObservableList<TherapySessionDTO> observableList = FXCollections.observableArrayList();

            for (Therapy_Session session : sessionList) {
                observableList.add(new TherapySessionDTO(
                        session.getSessionId(),
                        session.getSessionDate(),
                        session.getSessionTime(),
                        session.getStatus(),
                        session.getTherapist().getTherapistId(),
                        session.getPatient().getPatientId()
                ));
            }


            String fxmlPath = input.matches("T\\d+")
                    ? "/view/TherapistSearch.fxml"
                    : "/view/Search.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent load = loader.load();

            if (fxmlPath.contains("TherapistSearch")) {
                TherapistSearchController controller = loader.getController();
                controller.setSessionList(observableList);
            } else {
                SearchController controller = loader.getController();
                controller.setSessionList(observableList);
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Therapy Session Search");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnsearch.getScene().getWindow());
            stage.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Search failed due to a database error.").show();
        }
    }
    @FXML
    void TherapistSearchOnAction(ActionEvent event) {

    }
    @FXML
    void txtTimeKeyReleasedOnAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.gdse.ormcw.util.TextField.TIME,txttime);

    }
    public boolean isValid(){
        if(!Regex.setTextColor(lk.ijse.gdse.ormcw.util.TextField.TIME, txttime)) return false;

        return true;
    }


    public void PaymentOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Paymentwindow.fxml"));
        Parent load = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(load));
        stage.setTitle("Registration");

        stage.initModality(Modality.APPLICATION_MODAL);

        Window underWindow = btnpayment.getScene().getWindow();
        stage.initOwner(underWindow);

        stage.showAndWait();
    }
}





