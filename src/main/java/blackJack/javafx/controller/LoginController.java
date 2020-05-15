package blackJack.javafx.controller;

import blackJack.model.Model;
import blackJack.results.User;
import blackJack.results.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
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

    private UserDao userDao;
    Model model = Model.getInstance();
    String username;
    String password;

    @FXML
    public void loginBtnClicked(ActionEvent event) {
         username = userNameInput.getText();
         password = passwordInput.getText();
         Optional<User> user = userDao.findbyUsername(username);
         if(user.isEmpty()){
             setLabelText("User not found!","red");
         }else{
             if(user.get().getPassword().equals(password) && user.get().getUsername().equals(username)){
                 setLabelText("Successful login!","green");
                 model.resetGame();
                 try {
                     passUserToGameController(user.get(),event);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }else{
                 setLabelText("Wrong password!","red");
             }
         }
    }
    @FXML
    public void registerBtnClicked(ActionEvent actionEvent) {
         username = userNameInput.getText();
         password = passwordInput.getText();
        if(validateUsername(username) && validatePassword(password)){
            if(userDao.findbyUsername(username).isPresent()){
                setLabelText("Name is already taken!","red");
            }else{
                userDao.persist(createUser(username,password));
                setLabelText("Successful registering!","green");
                userNameInput.clear();
                passwordInput.clear();
                System.out.println(userDao.findAll());
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

    private void passUserToGameController(User user, ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/fxml/primary.fxml"));
        Parent root = fxmlLoader.load();
        GameController controller = fxmlLoader.getController();
        controller.setUser(user);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("main.css");
        stage.setScene(scene);
        stage.show();
    }

    private User createUser(String username, String password){
        return User.builder()
                .username(username)
                .password(password)
                .funds(0)
                .lostMoney(0)
                .wonMoney(0)
                .wonCount(0)
                .loseCount(0)
                .build();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDao = UserDao.getInstance();
    }
}

