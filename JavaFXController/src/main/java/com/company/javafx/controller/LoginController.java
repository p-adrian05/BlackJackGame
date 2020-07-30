package com.company.javafx.controller;

import com.company.domain.GameData;
import com.company.blackJack.GameService;
import com.company.domain.User;
import com.company.UserDao.UserDao;
import com.company.javafx.BlackJackApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class LoginController {
    @FXML
    private TextField userNameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Label loginLabel;
    @Autowired
    private GameService gameService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder encoder;
    private String username;
    private String password;

    @FXML
    public void loginBtnClicked() throws IOException {
         log.info("Login button clicked.");
         username = userNameInput.getText();
         password = passwordInput.getText();
         log.debug("Username input: {}",username);
         log.debug("Password input: {}",password);
         Optional<User> user = userDao.findByUsername(username);
         if(user.isEmpty()){
             setLabelText("User not found!","red");
         }else{
             if(encoder.matches(password,user.get().getPassword()) && user.get().getUsername().equals(username)){
                 setLabelText("Successful login!","green");
                 log.info("Successful login!");
                 gameService.setUser(user.get());
                 gameService.resetGame();
                 log.info("Loading primary screen...");
                 BlackJackApplication.setRoot("primary");
             }else{
                 setLabelText("Wrong password or username!","red");
             }
         }
    }
    @FXML
    public void registerBtnClicked() {
         log.info("Register button clicked.");
         username = userNameInput.getText();
         password = passwordInput.getText();
         log.debug("Username input: {}",username);
         log.debug("Password input: {}",password);
        if(validateUsername(username) && validatePassword(password)){
            if(userDao.findByUsername(username).isPresent()){
                setLabelText("Name is already taken!","red");
            }else{
                userDao.persist(createUser(username,encoder.encode(password)));
                log.info("Successful registering!");
                setLabelText("Successful registering!","green");
                userNameInput.clear();
                passwordInput.clear();
            }
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

    private User createUser(String username, String password){
        return new User(username,password,new GameData());
    }

}

