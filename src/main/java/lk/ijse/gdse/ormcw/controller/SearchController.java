package lk.ijse.gdse.ormcw.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.ormcw.bo.BOFactory;
import lk.ijse.gdse.ormcw.bo.custom.TherapySessionBO;
import lk.ijse.gdse.ormcw.dto.TherapySessionDTO;
import lk.ijse.gdse.ormcw.tm.TherapySessionTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    private TableColumn<TherapySessionDTO, Date> coldate;

    @FXML
    private TableColumn<TherapySessionDTO, String> colid;

    @FXML
    private TableColumn<TherapySessionDTO, String> colstatus;

    @FXML
    private TableColumn<TherapySessionDTO, String> coltime;

    @FXML
    private AnchorPane searchpage;

    @FXML
    private TableView<TherapySessionTM> searchtable;

    TherapySessionBO therapySessionBO = (TherapySessionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.THERAPY_SESSION);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        coltime.setCellValueFactory(new PropertyValueFactory<>("sessionTime"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    }
    public void setSessionList(List<TherapySessionDTO> dtoList) throws SQLException, ClassNotFoundException, IOException {
        ObservableList<TherapySessionTM> sessionList = FXCollections.observableArrayList();

        for(TherapySessionDTO dto : dtoList){
            sessionList.add(new TherapySessionTM(
                    "",//dto.getSessionId()
                    dto.getSessionDate(),
                    dto.getSessionTime(),
                    dto.getStatus(),
                   "", //dto.getTherapistId(),
                    dto.getPatientId(),
                    ""//dto.getPayment()
            ));
        }
        searchtable.setItems(sessionList);
    }
}

