package blackJack.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BlackJackApplication extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"));
        scene.getStylesheets().add("main.css");
        stage.setScene(scene);
        stage.setHeight(1000);
        stage.setWidth(1300);
        stage.setResizable(true);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource( "/fxml/"+fxml+".fxml"));
        return fxmlLoader.load();
    }

}
