package blackJack.javafx;

import blackJack.results.UserDao;
import com.gluonhq.ignite.guice.GuiceContext;
import com.google.inject.AbstractModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import util.juice.PersistenceModule;

import javax.inject.Inject;
import java.util.List;

@Slf4j
public class BlackJackApplication extends Application {

    private GuiceContext context = new GuiceContext(this, () -> List.of(
            new AbstractModule() {
                @Override
                protected void configure() {
                    install(new PersistenceModule("blackJackGame"));
                    bind(UserDao.class);
                }
            }
    ));

    @Inject
    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws Exception {
        log.info("Starting application...");
        context.init();
        fxmlLoader.setLocation(getClass().getResource("/fxml/login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("main.css");
        stage.setScene(scene);
        stage.setHeight(1000);
        stage.setWidth(1300);
        stage.setTitle("BlackJack");
        stage.setResizable(true);
        stage.show();
    }

}
