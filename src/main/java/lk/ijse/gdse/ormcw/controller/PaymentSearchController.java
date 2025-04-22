package lk.ijse.gdse.ormcw.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.ormcw.dto.PaymentDTO;
import lk.ijse.gdse.ormcw.dto.TherapySessionDTO;
import lk.ijse.gdse.ormcw.tm.PaymentTM;
import lk.ijse.gdse.ormcw.tm.TherapySessionTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentSearchController implements Initializable {

    @FXML
    private AnchorPane PaymentSearchpage;

    @FXML
    private TableColumn<PaymentTM,Double> colamount;



    @FXML
    private TableColumn<PaymentTM,String> colstatus;

    @FXML
    private TableColumn<PaymentTM, Date> coldate;

    @FXML
    private TableColumn<PaymentTM,String> colid;

    @FXML
    private TableColumn<PaymentTM,String> colpayment;



    @FXML
    private TableView<PaymentTM> paymentearchTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colid.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colamount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    public void setSessionList(List<PaymentDTO> dtoList) throws SQLException, ClassNotFoundException, IOException {
        ObservableList<PaymentTM> paymentList = FXCollections.observableArrayList();

        for(PaymentDTO paymentDTO : dtoList){
            paymentList.add(new PaymentTM(
                    paymentDTO.getPatientId(),
                    paymentDTO.getPaymentId(),
                    paymentDTO.getAmount(),
                    paymentDTO.getPaymentDate(),
                    paymentDTO.getStatus(),
                    0
            ));
        }
        paymentearchTable.setItems(paymentList);
    }
}