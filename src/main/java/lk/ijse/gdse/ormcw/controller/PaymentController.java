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
import lk.ijse.gdse.ormcw.bo.custom.PatientRegistrationBO;
import lk.ijse.gdse.ormcw.bo.custom.PaymentBO;
import lk.ijse.gdse.ormcw.bo.custom.TherapySessionBO;
import lk.ijse.gdse.ormcw.bo.exception.PaymentException;
import lk.ijse.gdse.ormcw.config.FactoryConfiguration;
import lk.ijse.gdse.ormcw.dto.PatientDTO;
import lk.ijse.gdse.ormcw.dto.PaymentDTO;
import lk.ijse.gdse.ormcw.entity.Payment;
import lk.ijse.gdse.ormcw.entity.Therapy_Session;
import lk.ijse.gdse.ormcw.tm.PaymentTM;
import lk.ijse.gdse.ormcw.util.Regex;
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
    private ComboBox<String> comboStatus;
    private final String[] Status = {"Payment Completed","Pending"};

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
    private TableColumn<PaymentTM,Double> coltotalAmount;


    @FXML
    private TableColumn<PaymentTM,Date> colsessiondate;

    @FXML
    private TableColumn<PaymentTM,String> colsessiontime;

    @FXML
    private ComboBox<String> combopatientid;

    @FXML
    private DatePicker datepicker;

    @FXML
    private Label lbldate;

    @FXML
    private Label lblid;

    @FXML
    private Label lblregfee;

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

    @FXML
    private TextField txtsearch;

    @FXML
    private Button btnsearch;

    @FXML
    private Label lblsession;

    @FXML
    private Label lblsessiondate;

    @FXML
    private Label lblsessiontime;



    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);
    PatientBO patientBO = (PatientBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT);
    PatientRegistrationBO patientRegistrationBO = (PatientRegistrationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PATIENT_REGISTRATION);
    TherapySessionBO therapySessionBO = (TherapySessionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPY_SESSION);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboStatus.getItems().addAll(Status);
        colpaymentid.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colpatid.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colamount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        coltotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colsessiondate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        colsessiontime.setCellValueFactory(new PropertyValueFactory<>("sessionTime"));



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
    void StatusComboOnAction(ActionEvent event) {
        String SelectedValue = comboStatus.getValue();
        lblstatus.setText(SelectedValue);
    }
    @FXML
    void ComboPatientIdOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        String selectedID = combopatientid.getValue();
        PatientDTO patientDTO = patientBO.findById(selectedID);
        if (patientDTO != null) {
            lblPatientid.setText(patientDTO.getName());
        }
        if (selectedID != null) {
            double balance = patientRegistrationBO.getBalanceByPatientId(selectedID);
            lblbalance.setText(String.format("%.2f", balance));
        }
        if (selectedID != null) {
            double regfee = patientRegistrationBO.getRegisterFeeByPatientId(selectedID);
            lblregfee.setText(String.format("%.2f", regfee));
        }
        Therapy_Session session = therapySessionBO.findLastSessionByPatientId(selectedID);
        if (session != null) {
            lblsession.setText(session.getSessionId());
            lblsessiondate.setText(session.getSessionDate().toString());
            lblsessiontime.setText(session.getSessionTime());
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
    void SaveOnAction(ActionEvent event) throws IOException {
        String PaymentId = lblid.getText();
        String patientId = combopatientid.getValue();
        double amount = Double.parseDouble(txtamount.getText());
        String paymentDate = lbldate.getText();
        String Status = lblstatus.getText();

        String sessionDate = lblsessiondate.getText();
        String sessionTime = lblsessiontime.getText();
        double regfee = Double.parseDouble(lblregfee.getText());

        if(!isValid()) {
            throw new PaymentException("Invalid amount input format");
        }

        double previousTotalAmount = paymentBO.getPreviousTotalAmount(patientId);
        double totalAmount;
        if(previousTotalAmount == 0){
            totalAmount = amount + regfee;
        }else {
            totalAmount = previousTotalAmount + amount;
        }

       // System.out.println("Amount: " + amount + ", Reg Fee: " + regfee + ", Total Amount: " + totalAmount);
        System.out.println("Previous Total: " + previousTotalAmount);
        System.out.println("Amount: " + amount);
        System.out.println("Reg Fee: " + regfee);
        System.out.println("Calculated Total Amount: " + totalAmount);
        try{
            PaymentDTO paymentDTO = new PaymentDTO(
                    PaymentId,patientId,amount,paymentDate,Status,totalAmount,sessionDate,sessionTime
            );
            boolean isRegistered = paymentBO.save(paymentDTO);


            if (isRegistered) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Payment Saved SUCCESSFULLY 😎").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "PLEASE TRY AGAIN 😥").show();
            }
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Duplicate ID").show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (PaymentException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
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
            comboStatus.setValue(paymentTM.getStatus());




            btnsave.setDisable(true);

        }
    }

//    @FXML
//    void UpdateOnAction(ActionEvent event) {
//        String PaymentId = lblid.getText();
//        String patientId = combopatientid.getValue();
//        double amount = Double.parseDouble(txtamount.getText());
//        String paymentDate = lbldate.getText();
//        String Status = lblstatus.getText();
//
//        double regfee = Double.parseDouble(lblregfee.getText());
//
//        double totalAmount = amount + regfee;
//
//        try{
//            PaymentDTO paymentDTO = new PaymentDTO(
//                    PaymentId,patientId,amount,paymentDate,Status,totalAmount
//            );
//            boolean isRegistered = paymentBO.update(paymentDTO);
//
//            if (isRegistered) {
//                refreshPage();  // UI එක refresh කරන්න
//                new Alert(Alert.AlertType.INFORMATION, "Payment Updated SUCCESSFULLY 😎").show();
//            } else {
//                new Alert(Alert.AlertType.ERROR, "PLEASE TRY AGAIN 😥").show();
//            }
//        } catch (IOException e) {
//            new Alert(Alert.AlertType.ERROR, "Duplicate ID").show();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }


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
                    paymentDTO.getPatientId(),
                    paymentDTO.getPaymentId(),
                    paymentDTO.getAmount(),
                    paymentDTO.getPaymentDate(),
                    paymentDTO.getStatus(),
                    paymentDTO.getTotalAmount(),
                    paymentDTO.getSessionDate(),
                    paymentDTO.getSessionTime()

            );
            paymentTMS.add(paymentTM);
        }
        PaymentTable.setItems(paymentTMS);

    }
    void refreshPage() throws SQLException, ClassNotFoundException, IOException {
        LoadNextID();
        loadTableData();

        //btndelete.setDisable(true);
        btnsave.setDisable(false);
        //btnupdate.setDisable(true);

        lblPatientid.setText("");
        txtamount.setText("");
        lbldate.setText("");

        comboStatus.setValue("");

        lblsession.setText("");
        lblsessiondate.setText("");
        lblsessiontime.setText("");


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

    @FXML
    void PaymentSearchOnAction(ActionEvent event) {
        String name = txtsearch.getText();

        try{
            List<Payment> paymentList = paymentBO.searchPayment(name);
            ObservableList<PaymentDTO> observableList = FXCollections.observableArrayList();

            for (Payment payment : paymentList) {
                observableList.add(new PaymentDTO(
                        payment.getPaymentId(),
                        payment.getPatient().getPatientId(),
                        payment.getAmount(),
                        payment.getPaymentDate(),
                        payment.getStatus(),
                        payment.getTotalAmount(),
                        payment.getSessionDate(),
                        payment.getSessionTime()

                ));
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PaymentSearch.fxml"));
            Parent load = loader.load();

            PaymentSearchController paymentSearchController = loader.getController();
            paymentSearchController.setSessionList(observableList);
            System.out.println(observableList);
            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Search");

            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnsearch.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void txtfeeKeyReleasedOnAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.gdse.ormcw.util.TextField.FEE, txtamount);
    }
    public boolean isValid(){
        if(!Regex.setTextColor(lk.ijse.gdse.ormcw.util.TextField.FEE, txtamount)) return false;

        return true;
    }


}
