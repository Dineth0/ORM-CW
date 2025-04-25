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
import lk.ijse.gdse.ormcw.bo.custom.UserBO;
import lk.ijse.gdse.ormcw.dto.UserDTO;
import lk.ijse.gdse.ormcw.tm.UserTM;
import lk.ijse.gdse.ormcw.util.Regex;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {

    @FXML
    private Label lblid;

    @FXML
    private Label lblrole;

    @FXML
    private PasswordField txtpassword;

    @FXML
    private TextField txtname;
    @FXML
    private TableView<UserTM> UserTable;

    @FXML
    private TableColumn<UserTM,String> colid;

    @FXML
    private TableColumn<UserTM,String> colname;

    @FXML
    private TableColumn<UserTM,String> colpassword;

    @FXML
    private TableColumn<UserTM,String> colrole;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnsave;

    @FXML
    private Button btnupdate;

    @FXML
    private ComboBox<String> rolecombo;
    private final String[] Role = {"admin","receptionist"};
    @FXML
    private TextField txtid;


    @FXML
    private AnchorPane userpage;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("UserFormController Initialized!");
        rolecombo.getItems().addAll(Role);
        colid.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        colname.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        colpassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
        colrole.setCellValueFactory(new PropertyValueFactory<>("Role"));
        try {
            LoadNextID();
            loadTableData();
            refreshPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void CpmboBoxOnAction(ActionEvent event) {
        String SelectedValue = rolecombo.getValue();
        lblrole.setText(SelectedValue);
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
        String Id = lblid.getText();
        String UserName = txtname.getText();
        String Password = txtpassword.getText();
        String Role = rolecombo.getValue();

        if(isValid()) {

            try {
                boolean isRegistered = userBO.save(new UserDTO(Id, UserName, Password, Role));
                if (isRegistered) {
                    refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, "User Saved SUCCESSFULLY 😎").show();

                } else {
                    new Alert(Alert.AlertType.ERROR, "PLEASE TRY AGAIN 😥").show();
                }
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "duplicate Id");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void loadTableData() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<UserDTO> leaveDtos = (ArrayList<UserDTO>) userBO.getAll();
        ObservableList<UserTM> userTMS = FXCollections.observableArrayList();

        for (UserDTO userDto : leaveDtos) {
            UserTM userTM = new UserTM(
                    userDto.getId(),
                    userDto.getUserName(),
                    userDto.getPassword(),
                    userDto.getRole()


            );
            userTMS.add(userTM);
        }
        UserTable.setItems(userTMS);
    }

    @FXML
    void TableOnClicked(MouseEvent event) {
        UserTM UserTM = UserTable.getSelectionModel().getSelectedItem();
        if (UserTM != null) {
            lblid.setText(UserTM.getUserID());
            txtname.setText(UserTM.getUserName());
            txtpassword.setText(UserTM.getPassword());
            rolecombo.setValue(UserTM.getRole());

            btndelete.setDisable(false);
            btnsave.setDisable(true);
            btnupdate.setDisable(false);
        }
    }

    @FXML
    void UpdateOnAction(ActionEvent event) {
        String Id = lblid.getText();
        String UserName = txtname.getText();
        String Password = txtpassword.getText();
        String Role = rolecombo.getValue();

        if(isValid()) {

            try {
                boolean isRegistered = userBO.update(new UserDTO(Id, UserName, Password, Role));
                if (isRegistered) {
                    refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, "User Saved SUCCESSFULLY 😎").show();

                } else {
                    new Alert(Alert.AlertType.ERROR, "PLEASE TRY AGAIN 😥").show();
                }
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "duplicate Id");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void LoadNextID() throws SQLException, IOException {
        String nextID = userBO.getNextId();
        Platform.runLater(() -> lblid.setText(nextID));
        System.out.println("Next ID: " + nextID);
        lblid.setText(nextID);
    }
    void refreshPage() throws SQLException, ClassNotFoundException, IOException {
        LoadNextID();
        loadTableData();

        btndelete.setDisable(true);
        btnsave.setDisable(false);
        btnupdate.setDisable(true);

        lblid.setText("");
        txtname.setText("");
        txtpassword.setText("");
        lblrole.setText("");

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
