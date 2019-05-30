package frontend.controllers;

import backend.controllers.*;
import backend.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthController {

    boolean loggedIn = false;

    User user;

    /* Database controllers */
    CredentialsController cc = new CredentialsController();
    UserController uc = new UserController();

    @FXML Button loginButton;
    @FXML Button goToRegisterButton;

    /* login form */
    @FXML TextField emailLoginInput;
    @FXML PasswordField passwordLoginInput;

    /* Buttons */
    @FXML Button registerButton;
    @FXML Button goToLoginButton;

    /* Register form */
    @FXML TextField emailRegisterInput;
    @FXML PasswordField passwordRegisterInput;
    @FXML PasswordField repeatPasswordRegisterInput;
    @FXML TextField firstNameRegisterInput;
    @FXML TextField lastNameRegisterInput;


    @FXML Label errorMessage;
    @FXML Label errorMessageRegister;

    public void clickedRegister(ActionEvent event){
        String emailValue = emailRegisterInput.getText();
        String passValue = passwordRegisterInput.getText();
        String repeatPassValue = repeatPasswordRegisterInput.getText();
        String fnameValue = firstNameRegisterInput.getText();
        String lnameValue = lastNameRegisterInput.getText();

        /* validate form here */
        if(fnameValue.isEmpty()){
            errorMessageRegister.setText("*Your first name? Please..");
            return;
        }
        if(lnameValue.isEmpty()){
            errorMessageRegister.setText("*Hi, what's your last name?");
            return;
        }
        if(emailValue.isEmpty()){
            errorMessageRegister.setText("*You usually don't fill in the email field?");
            return;
        }
        if(passValue.isEmpty()){
            errorMessageRegister.setText("*Don't you want a password?");
            return;
        }
        if(repeatPassValue.isEmpty()){
            errorMessageRegister.setText("*Your password.. Again..");
            return;
        }
        if(!passValue.equals(repeatPassValue)){
            System.out.println("*Confirm your pass or you'll forget it!");
            return;
        }

        boolean valid = false;
        try {
            valid = cc.register(fnameValue, lnameValue, emailValue, passValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!valid){
            errorMessageRegister.setText("*It appears that your email is already in use..");
            return;
        } else {
            int id = 0;
            try {
                id = cc.login(emailValue, passValue);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(id > 0){
                try {
                    user = uc.findById(id);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                UserInfo.setId(user.getId());
                UserInfo.setfName(user.getFirstName());
                UserInfo.setlName(user.getLastName());

                try {
                    /* Load main fxml */
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/main.fxml"));
                    Stage stage = (Stage) errorMessageRegister.getScene().getWindow();
                    Scene scene = new Scene(loader.load());
                    stage.setScene(scene);
                } catch (IOException io){
                    io.printStackTrace();
                }
            } else {
                System.out.println("OOh, this is bad!");
            }
        }


    }

    public void clickedLogin(ActionEvent event){
        System.out.println("LOGIN");
        String emailValue = emailLoginInput.getText();
        if(emailValue.isEmpty()){
            errorMessage.setText("*I think you should fill that email field!");
            return;
        }

        String passValue = passwordLoginInput.getText();
        if(passValue.isEmpty()){
            errorMessage.setText("*You don't usually use passwords, huh?");
            return;
        }

        int id = 0;
        try {
            id = cc.login(emailValue, passValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(id > 0){
            try {
                user = uc.findById(id);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            UserInfo.setId(user.getId());
            UserInfo.setfName(user.getFirstName());
            UserInfo.setlName(user.getLastName());

            try {
                /* Load main fxml */
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/main.fxml"));
                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException io){
                io.printStackTrace();
            }
            System.out.println("Login Successfull");
        } else {
            errorMessage.setText("*Wrong email/password combination!");
        }
    }

    public void goToLogin(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/login.fxml"));
            Stage stage = (Stage) registerButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }


    public void goToRegister(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/register.fxml"));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }
}
