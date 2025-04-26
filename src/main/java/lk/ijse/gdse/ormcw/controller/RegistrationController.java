package lk.ijse.gdse.ormcw.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.ormcw.bo.BOFactory;
import lk.ijse.gdse.ormcw.bo.custom.UserBO;
import lk.ijse.gdse.ormcw.bo.exception.RegistrationException;
import lk.ijse.gdse.ormcw.dto.UserDTO;
import lk.ijse.gdse.ormcw.util.Regex;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    @FXML
    private AnchorPane registor;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtname;

    @FXML
    private PasswordField txtpassword;

    @FXML
    private TextField txtrole;

     @FXML
    private Label lblid;

    @FXML
    private Label lblrole;



    UserBO userBO =  (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);


    @FXML
    void BackLoginPageOnAction(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            LoadNextID();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void RegistorOnAction(ActionEvent event) throws SQLException, IOException {
        String Id = lblid.getText();
        String UserName = txtname.getText();
        String Password = txtpassword.getText();
        String Role = lblrole.getText();

        try {

            if(UserName.isEmpty() || Password.isEmpty() || Role.isEmpty()){
                throw new RegistrationException("Filed is empty. Please fill it");
            }


            if (!isValid()) {
                throw new RegistrationException("Invalid input format");

            }
                    boolean isRegistered = userBO.save(new UserDTO(Id, UserName, Password, Role));
                    if (isRegistered) {
                        new Alert(Alert.AlertType.INFORMATION, "REGISTERED SUCCESSFULLY").show();
                        clearFeilds();
                        loginPage();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "PLEASE TRY AGAIN").show();
                    }
        }catch (RegistrationException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void clearFeilds(){
        lblid.setText("");
        txtpassword.setText("");
        txtname.setText("");
        lblrole.setText("");
    }
    private void loginPage() throws IOException {
        AnchorPane LoginPage = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        registor.getChildren().clear();
        registor.getChildren().add(LoginPage);
    }

    private void LoadNextID() throws SQLException, IOException {
        String nextID = userBO.getNextId();
        lblid.setText(nextID);
    }
    @FXML
    void txtnameKeyRelaesedOnAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.gdse.ormcw.util.TextField.NAME, txtname);

    }

    @FXML
    void txtpasswordKeyRelaesedOnAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.gdse.ormcw.util.TextField.PASSWORD, txtpassword);

    }
    public boolean isValid(){
        if(!Regex.setTextColor(lk.ijse.gdse.ormcw.util.TextField.NAME, txtname)) return false;
        if(!Regex.setTextColor(lk.ijse.gdse.ormcw.util.TextField.PASSWORD, txtpassword)) return false;

        return true;
    }

}

