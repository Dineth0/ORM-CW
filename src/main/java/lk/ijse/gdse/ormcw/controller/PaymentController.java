package lk.ijse.gdse.ormcw.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.ormcw.bo.BOFactory;
import lk.ijse.gdse.ormcw.bo.custom.PatientBO;
import lk.ijse.gdse.ormcw.bo.custom.PatientRegistrationBO;
import lk.ijse.gdse.ormcw.bo.custom.PaymentBO;
import lk.ijse.gdse.ormcw.config.FactoryConfiguration;
import lk.ijse.gdse.ormcw.dao.custom.PatientRegistrationDAO;
import lk.ijse.gdse.ormcw.dto.PatientDTO;
import lk.ijse.gdse.ormcw.dto.PatientRegistrationDTO;
import lk.ijse.gdse.ormcw.dto.PaymentDTO;
import lk.ijse.gdse.ormcw.entity.Patient_Registration;
import lk.ijse.gdse.ormcw.tm.PatientRegistrationTM;
import lk.ijse.gdse.ormcw.tm.PaymentTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PaymentController implements Initializable {

    @FXML
    private TableView<PaymentTM> PaymentTable;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<PaymentTM,Double> colamount;

    @FXML
    private TableColumn<PaymentTM, Date> coldate;

    @FXML
    private TableColumn<PaymentTM,String> colpatid;

    @FXML
    private TableColumn<PaymentTM,String> colpaymentid;

    @FXML
    private TableColumn<PaymentTM,String> colstatus;

    @FXML
    private ComboBox<String> combopatientid;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Label lbldate;

    @FXML
    private Label lblid;

    @FXML
    private Label lblPatientid;

    @FXML
    private Label lblprogramid;

    @FXML
    private Label lblstatus;

    @FXML
    private AnchorPane patientpage;

    @FXML
    private TextField txtamount;

    @FXML
    private Label lblbalance;

    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);
    PatientBO patientBO = (PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT);
    PatientRegistrationBO patientRegistrationBO = (PatientRegistrationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT_REGISTRATION);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colpaymentid.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colpatid.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colamount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

        try {
            LoadNextID();

            loadPatientIDs();
            loadTableData();
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load PaymentId").show();
        }
    }

    @FXML
    void ComboPatientIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        String selectedID = combopatientid.getValue();
        PatientDTO patientDTO = patientBO.findById(selectedID);

        if (patientDTO != null) {
            lblPatientid.setText(patientDTO.getName());
        }
        PatientRegistrationDTO patientRegistrationDTO = patientRegistrationBO.findById(selectedID);
        if(patientRegistrationDTO != null) {
            System.out.println("Balance :"+patientRegistrationDTO.getBalance());
            lblbalance.setText(String.valueOf(patientRegistrationDTO.getBalance()));
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

            boolean isDelete = paymentBO.delete(ID);
            if (isDelete) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Payment deleted...!").show();

            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Payment...!").show();

            }
        }
    }

    @FXML
    void ResetOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void SaveOnAction(ActionEvent event) {
        String PaymentId = lblid.getText();
        String patientId = combopatientid.getValue();
        double amount = Double.parseDouble(txtamount.getText());
        String paymentDate = lbldate.getText();
        String Status = lblstatus.getText();

        try{
            PaymentDTO paymentDTO = new PaymentDTO(
                    PaymentId,patientId,amount,paymentDate,Status
            );
            boolean isRegistered = paymentBO.save(paymentDTO);

            if (isRegistered) {
                refreshPage();  // UI එක refresh කරන්න
                new Alert(Alert.AlertType.INFORMATION, "Payment Saved SUCCESSFULLY 😎").show();
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

    @FXML
    void TableOnClicked(MouseEvent event) {
        PaymentTM paymentTM = PaymentTable.getSelectionModel().getSelectedItem();
        if (paymentTM != null) {
            lblid.setText(paymentTM.getPaymentId());
            combopatientid.setValue(paymentTM.getPatientId());
            txtamount.setText(String.valueOf(paymentTM.getAmount()));
            lbldate.setText(String.valueOf(paymentTM.getPaymentDate()));



            btndelete.setDisable(false);
            btnsave.setDisable(true);
            btnupdate.setDisable(false);
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) {
        String PaymentId = lblid.getText();
        String patientId = combopatientid.getValue();
        double amount = Double.parseDouble(txtamount.getText());
        String paymentDate = lbldate.getText();
        String Status = lblstatus.getText();

        try{
            PaymentDTO paymentDTO = new PaymentDTO(
                    PaymentId,patientId,amount,paymentDate,Status
            );
            boolean isRegistered = paymentBO.update(paymentDTO);

            if (isRegistered) {
                refreshPage();  // UI එක refresh කරන්න
                new Alert(Alert.AlertType.INFORMATION, "Payment Updated SUCCESSFULLY 😎").show();
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

    private void loadPatientIDs() throws SQLException, IOException, ClassNotFoundException {
        ArrayList<String> patientIds = patientBO.getAllPatientIds();
        combopatientid.getItems().addAll(patientIds);
    }
    private void LoadNextID() throws SQLException, IOException {
        String nextID = paymentBO.getNextId();
        lblid.setText(nextID);
    }
    private void loadTableData() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<PaymentDTO> paymentDTOS = (ArrayList<PaymentDTO>) paymentBO.getAll();
        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

        for (PaymentDTO paymentDTO : paymentDTOS) {
            PaymentTM paymentTM = new PaymentTM(
                    paymentDTO.getPaymentId(),
                    paymentDTO.getPatientId(),
                    paymentDTO.getAmount(),
                    paymentDTO.getPaymentDate(),
                    paymentDTO.getStatus()


            );
            paymentTMS.add(paymentTM);
        }
        PaymentTable.setItems(paymentTMS);
    }
    void refreshPage() throws SQLException, ClassNotFoundException, IOException {
        LoadNextID();
        loadTableData();

        btndelete.setDisable(true);
        btnsave.setDisable(false);
        btnupdate.setDisable(true);

        lblPatientid.setText("");
        txtamount.setText("");
        lbldate.setText("");


    }
    @FXML
    void ReportOnAction(ActionEvent event) {
        PaymentTM paymentTM = PaymentTable.getSelectionModel().getSelectedItem();
        if(paymentTM == null){
            return;
        }
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/PaymentInvoice.jrxml"
                            ));

            Session session = FactoryConfiguration.getInstance().getSession();
            session.beginTransaction();

            Connection connection = session.doReturningWork(conn -> conn);

            Map<String, Object> params = new HashMap<>();
            params.put("P_paymentId", paymentTM.getPaymentId() );

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    params,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
            session.getTransaction().commit();
            session.close();
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
//           e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
