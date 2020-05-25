package blackJack.javafx;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;


@Slf4j
public class BlackJackApplication extends Application {

    private static Scene scene;

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
         FXMLLoader fxmlLoader = new FXMLLoader(BlackJackApplication.class.getResource( "/fxml/"+fxml+".fxml"));
        return fxmlLoader.load();
    }

    @Override
    public void start(Stage stage) throws Exception {
        log.info("Starting application...");
        scene = new Scene(loadFXML("login"));
        scene.getStylesheets().add("/fxml/main.css");
        stage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toString()));
        stage.setScene(scene);
        stage.setHeight(1000);
        stage.setWidth(1300);
        stage.setTitle("BlackJack");
        stage.setResizable(true);
        stage.show();
    }

}
