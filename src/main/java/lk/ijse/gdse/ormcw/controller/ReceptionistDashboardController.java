package lk.ijse.gdse.ormcw.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReceptionistDashboardController implements Initializable {

        @FXML
        private AnchorPane recipAncBody;

        @FXML
        private AnchorPane recipDashboard;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                loadMainLayout();
        }


        private void loadMainLayout() {
                try {
                        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/MainLayoutForm.fxml"));
                        recipAncBody.getChildren().clear();
                        recipAncBody.getChildren().add(load);
                } catch (IOException e) {
                        e.printStackTrace();

                }
        }


        @FXML
        void DashboardOnAction(ActionEvent event) throws IOException {
                AnchorPane load = FXMLLoader.load(getClass().getResource("/view/MainLayoutForm.fxml"));
                recipAncBody.getChildren().clear();
                recipAncBody.getChildren().add(load);
        }

        @FXML
        void PatientManageOnAction(ActionEvent event) throws IOException {
                AnchorPane userpage = FXMLLoader.load(getClass().getResource("/view/Patient.fxml"));
                recipAncBody.getChildren().clear();
                recipAncBody.getChildren().add(userpage);
        }

        @FXML
        void PayementOnAction(ActionEvent event) {

        }

        @FXML
        void TherapyManageOnAction(ActionEvent event) {

        }

        @FXML
        void TherapySessionOnAction(ActionEvent event) throws IOException {
                AnchorPane ls = FXMLLoader.load(getClass().getResource("/view/TherapySession.fxml"));
                recipAncBody.getChildren().clear();
                recipAncBody.getChildren().add(ls);
        }

        @FXML
        void UserOnAction(ActionEvent event) throws IOException {
                AnchorPane userpage = FXMLLoader.load(getClass().getResource("/view/UserForm.fxml"));
                recipAncBody.getChildren().clear();
                recipAncBody.getChildren().add(userpage);
        }
        @FXML
        void LogOutOnAction(ActionEvent event) throws IOException {
                AnchorPane load = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
                recipDashboard.getChildren().clear();
                recipDashboard.getChildren().add(load);

        }

}

