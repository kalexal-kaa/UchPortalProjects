package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/sample.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());
        primaryStage.setTitle("Конструктор Тестов");
        primaryStage.getIcons().add((new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("res/images/imagect.png")))));
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            we.consume();
            Controller controller=new Controller();
            controller.exitItem();
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
