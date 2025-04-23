package lk.ijse.gdse.ormcw.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse.ormcw.bo.BOFactory;
import lk.ijse.gdse.ormcw.bo.custom.UserBO;
import lk.ijse.gdse.ormcw.dto.UserDTO;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private AnchorPane LoginPage;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtRole;

    @FXML
    private Button register;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    static UserDTO liveUserDto;

    @FXML
    void loginOnAction(ActionEvent event) throws Exception {
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        String role = txtRole.getText();

        String dbPassword =  getUserPassword();

        boolean isPasswordCorrect = BCrypt.checkpw(password,dbPassword);
        if (isPasswordCorrect) {

            if (role.equals("admin")) {
                AdminDashboard();
            } else if (role.equals("receptionist")){
                ResipDashboard();
            }

        } else {
            new Alert(Alert.AlertType.ERROR,"Invalid Password.Try Again").show();
        }






    }



    public String getUserPassword() {
        String name = txtUsername.getText();
        String password = null;
        try{
             password = userBO.getUser(name);
            return password;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private  void AdminDashboard(){
        try{
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"));
            LoginPage.getChildren().clear();
            LoginPage.getChildren().add(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private  void ResipDashboard(){
        try{
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/ReceptionistDashboard.fxml"));
            LoginPage.getChildren().clear();
            LoginPage.getChildren().add(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void RegistorOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegistrationForm.fxml"));
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
