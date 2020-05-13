package blackJack.javafx.controller;

import blackJack.javafx.BlackJackApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField userNameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Label loginLabel;
    @FXML
    private Button registerBtn;
    @FXML
    private Button loginBtn;

    @FXML
    public void loginBtnClicked(ActionEvent actionEvent) {
        //TODO database check
        try {
            BlackJackApplication.setRoot("primary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void registerBtnClicked(ActionEvent actionEvent) {
        if(validateUsername(userNameInput.getText()) &&validatePassword(passwordInput.getText())){
            setLabelText("Successful registering","green");
            userNameInput.clear();
            passwordInput.clear();
            //TODO add database
        }

    }
    private boolean validateUsername(String username){
        if (username.matches("[A-Za-z0-9_]+")){
            return true;
        }
        else{
            setLabelText("Not valid username!","red");
            return false;
        }
    }
    private boolean validatePassword(String password){
        if (password.length()<4){
            setLabelText("Password length must be >4","red");
            return false;
        }
        else{
            return true;
        }
    }

    private void setLabelText(String text,String color){
        loginLabel.setText(text);
        loginLabel.setTextFill(Paint.valueOf(color));
    }
}

