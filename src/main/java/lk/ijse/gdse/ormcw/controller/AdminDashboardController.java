package lk.ijse.gdse.ormcw.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    @FXML
    private AnchorPane AdminDashboard;

    @FXML
    private AnchorPane ancBody;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMainLayout();
    }
    private void loadMainLayout() {
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/MainLayoutForm.fxml"));
            ancBody.getChildren().clear();
            ancBody.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    void DashboardOnAction(ActionEvent event) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/MainLayoutForm.fxml"));
        ancBody.getChildren().clear();
        ancBody.getChildren().add(load);
    }

    @FXML
    void PatientManageOnAction(ActionEvent event) throws IOException {
        AnchorPane userpage = FXMLLoader.load(getClass().getResource("/view/Patient.fxml"));
        ancBody.getChildren().clear();
        ancBody.getChildren().add(userpage);
    }

    @FXML
    void PayementOnAction(ActionEvent event) throws IOException {
        AnchorPane ls = FXMLLoader.load(getClass().getResource("/view/Payment.fxml"));
        ancBody.getChildren().clear();
        ancBody.getChildren().add(ls);
    }

    @FXML
    void TherapyManageOnAction(ActionEvent event) throws IOException {
        AnchorPane ls = FXMLLoader.load(getClass().getResource("/view/TherapyManage.fxml"));
        ancBody.getChildren().clear();
        ancBody.getChildren().add(ls);
    }

    @FXML
    void TherapySessionOnAction(ActionEvent event) throws IOException {
        AnchorPane ls = FXMLLoader.load(getClass().getResource("/view/TherapySession.fxml"));
        ancBody.getChildren().clear();
        ancBody.getChildren().add(ls);
    }

    @FXML
    void UserOnAction(ActionEvent event) throws IOException {
        AnchorPane userpage = FXMLLoader.load(getClass().getResource("/view/UserForm.fxml"));
        ancBody.getChildren().clear();
        ancBody.getChildren().add(userpage);
    }
    @FXML
    void TherapyProgramOnAction(ActionEvent event) throws IOException {
        AnchorPane userpage = FXMLLoader.load(getClass().getResource("/view/TherapyProgram.fxml"));
        ancBody.getChildren().clear();
        ancBody.getChildren().add(userpage);
    }
    @FXML
    void LogOutOnAction(ActionEvent event) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        AdminDashboard.getChildren().clear();
        AdminDashboard.getChildren().add(load);
    }

}